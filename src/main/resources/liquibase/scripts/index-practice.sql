-- liquibase formatted sql

-- changeset dmitrii:1
create index student_name_index on student (name);

-- changeset dmitrii:2

create index faculty_name_color_index on faculty (name, color);




