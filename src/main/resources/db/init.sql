------------------------------------------------------------------------------------------------------------------------
--  member 초기값
------------------------------------------------------------------------------------------------------------------------
insert into member(member_id , created_at, deleted_at, last_modified_at, email, is_deleted, name, password, role)
values(1, "2021-10-01 09:00:00", null, "2021-10-01 09:00:00", "ghkdwlgns612@naver.com", null, "jihun", "1234", "ADMIN");

insert into member(member_id , created_at, deleted_at, last_modified_at, email, is_deleted, name, password, role)
values(2, "2021-10-01 09:00:00", null, "2021-10-01 09:00:00", "rudgus91@naver.com", null, "kyunghyun", "1234", "MEMBER");

insert into member(member_id , created_at, deleted_at, last_modified_at, email, is_deleted, name, password, role)
values(3, "2021-10-01 09:00:00", null, "2021-10-01 09:00:00", "koom6@naver.com", null, "guhwa", "1234", "ADMIN");

------------------------------------------------------------------------------------------------------------------------
-- category 초기값
------------------------------------------------------------------------------------------------------------------------
insert into category(category_id, title, description, active_status, priority, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(1, '책읽는TV', '책읽는TV 상세설명', 'ACTIVE', 2, now(), now(), 1, null, false);
insert into category(category_id, title, description, active_status, priority, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(2, '영어유치원', '영어유치원 상세설명', 'ACTIVE', 1, now(), now(), 2, null, false);
insert into category(category_id, title, description, active_status, priority, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(3, '누리학습', '누리학습 상세설명', 'ACTIVE', 3, now(), now(), 1, null, false);

insert into category(category_id, title, description, active_status, priority, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(4, '비활성화카테고리', '비활성화카테고리 상세설명', 'IN_ACTIVE', 4, now(), now(), 1, null, false);
insert into category(category_id, title, description, active_status, priority, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(5, '삭제된카테고리', '삭제된카테고리 상세설명', 'ACTIVE', 5, now(), now(), 1, now(), true);
