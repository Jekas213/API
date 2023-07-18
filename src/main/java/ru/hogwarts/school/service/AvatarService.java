package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.transaction.Transactional;

import java.io.*;
import java.nio.file.Files;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;


@Service
@Transactional
public class AvatarService {
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public void uploadAvatar(long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for upload avatar");
        logger.debug("id of the student we are upload avatar: id = {}", studentId);
        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtensions(Objects.requireNonNull(avatarFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        avatarRepository.save(avatar);

    }

    public Avatar findAvatar(long studentId) {
        logger.info("Was invoked method for find avatar");
        logger.debug("id of the student we are looking for an avatar from: id = {}", studentId);
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<String> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method for get all avatars");
        logger.debug("Page number and page size for method get all avatars: page number = {}, page size = {}", pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).stream()
                .map(Avatar::getFilePath)
                .toList();
    }
}
