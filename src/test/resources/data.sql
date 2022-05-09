------------------------------------------------------------------------------------------------------------------------
--  member 초기값
------------------------------------------------------------------------------------------------------------------------
insert into member(member_id , created_at, deleted_at, last_modified_at, email, is_deleted, name, password, role)
values(1, {ts '2022-04-03 14:00:00'}, null, {ts '2022-04-03 14:00:00'}, 'intern@lguplus.co.kr', null, 'intern', 'password', 'ADMIN');

insert into member(member_id , created_at, deleted_at, last_modified_at, email, is_deleted, name, password, role)
values(2, {ts '2022-04-03 14:00:00'}, null, {ts '2022-04-03 14:00:00'}, 'admin@email.com', null, 'admin', 'password', 'ADMIN');
insert into member(member_id , created_at, deleted_at, last_modified_at, email, is_deleted, name, password, role)
values(3, {ts '2022-04-03 14:00:00'}, null, {ts '2022-04-03 14:00:00'}, 'user@email.com', null, 'user', 'password', 'MEMBER');

insert into member(member_id , created_at, deleted_at, last_modified_at, email, is_deleted, name, password, role)
values(4, {ts '2022-04-03 14:00:00'}, null, {ts '2022-04-03 14:00:00'}, 'ghkdwlgns612@naver.com', null, 'jihun', '1234', 'ADMIN');
insert into member(member_id , created_at, deleted_at, last_modified_at, email, is_deleted, name, password, role)
values(5, {ts '2022-04-03 14:00:00'}, null, {ts '2022-04-03 14:00:00'}, 'rudgus91@naver.com', null, 'kyunghyun', '1234', 'MEMBER');
insert into member(member_id , created_at, deleted_at, last_modified_at, email, is_deleted, name, password, role)
values(6, {ts '2022-04-03 14:00:00'}, null, {ts '2022-04-03 14:00:00'}, 'koom6@naver.com', null, 'guhwa', '1234', 'ADMIN');


------------------------------------------------------------------------------------------------------------------------
-- category 초기값
------------------------------------------------------------------------------------------------------------------------
insert into category(category_id, title, description, active_status, priority, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(1, '책읽는TV', '책읽는TV 상세설명', 'ACTIVE', 2, now(), now(), 1, null, false);
insert into category(category_id, title, description, active_status, priority, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(2, '영어유치원', '영어유치원 상세설명', 'ACTIVE', 1, now(), now(), 2, null, false);
insert into category(category_id, title, description, active_status, priority, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(3, '누리학습', '누리학습 상세설명', 'ACTIVE', 3, now(), now(), 4, null, false);

insert into category(category_id, title, description, active_status, priority, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(4, '비활성화카테고리', '비활성화카테고리 상세설명', 'IN_ACTIVE', 4, now(), now(), 1, null, false);
insert into category(category_id, title, description, active_status, priority, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(5, '삭제된카테고리', '삭제된카테고리 상세설명', 'ACTIVE', 5, now(), now(), 1, now(), true);


------------------------------------------------------------------------------------------------------------------------
-- faq 초기값
------------------------------------------------------------------------------------------------------------------------
insert into faq(faq_id, question, answer, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(1, '1번째 질문입니다. hello world', '1번째 답변입니다.', now(), now(), 1, null, false);
insert into faq(faq_id, question, answer, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(2, '2번째 질문입니다.', '2번째 답변입니다. hello world ', now(), now(), 2, null, false);
insert into faq(faq_id, question, answer, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(3, '3번째 질문입니다.', '3번째 hello world 답변입니다.', now(), now(), 2, null, false);
insert into faq(faq_id, question, answer, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(4, '4번째 질문입니다.', '4번째 답변입니다.', now(), now(), 2, null, false);
insert into faq(faq_id, question, answer, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(5, '5번째 질문입니다.', '5번째 답변입니다.', now(), now(), 1, null, false);
insert into faq(faq_id, question, answer, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(6, '6번째 질문입니다.', '6번째 답변입니다.', now(), now(), 1, null, false);
insert into faq(faq_id, question, answer, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(7, '7번째 질문입니다.hello ', '7번째 답변입니다.', now(), now(), 1, null, false);
insert into faq(faq_id, question, answer, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(8, '8번째 질문입니다.hello ', '8번째 답변입니다.', now(), now(), 1, null, false);
insert into faq(faq_id, question, answer, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(9, '9번째 질문입니다. hello ', '9번째 답변입니다.', now(), now(), 1, null, false);
insert into faq(faq_id, question, answer, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(10, '10번째 질문입니다. hello', '10번째 답변입니다.', now(), now(), 1, null, false);

insert into faq(faq_id, question, answer, created_at, last_modified_at, last_modifier, deleted_at, is_deleted)
values(11, '11번째 질문입니다.(삭제된 질문)', '11번째 답변입니다.(삭제된 답변)', now(), now(), 1, now(), true);

------------------------------------------------------------------------------------------------------------------------
-- content 초기값
------------------------------------------------------------------------------------------------------------------------
insert into content(content_id, title, description, image_url, video_url, active_status, display_start_date, display_end_date, priority, upload_status, last_modifier, category_id, last_modified_at, deleted_at, is_deleted, created_at)
values(1,'title1','category1','https://image.yes24.com/goods/68746076/XL','https://youtu.be/PQrfV_CtmP8','ACTIVE',now(), now(),1,'SUCCESS',1,1,now(),null,false,now());
insert into content(content_id, title, description, image_url, video_url, active_status, display_start_date, display_end_date, priority, upload_status, last_modifier, category_id, last_modified_at, deleted_at, is_deleted, created_at)
values(2,'title2','category1','https://image.yes24.com/goods/68746076/XL','https://youtu.be/PQrfV_CtmP8','ACTIVE',now(), now(),3,'FAIL',3,1,now(),now(),true,now());
insert into content(content_id, title, description, image_url, video_url, active_status, display_start_date, display_end_date, priority, upload_status, last_modifier, category_id, last_modified_at, deleted_at, is_deleted, created_at)
values(3,'title3','category1','https://image.yes24.com/goods/68746076/XL','https://youtu.be/PQrfV_CtmP8','IN_ACTIVE',now(), now(),2,'SUCCESS',3,1,now(),null,false,now());
insert into content(content_id, title, description, image_url, video_url, active_status, display_start_date, display_end_date, priority, upload_status, last_modifier, category_id, last_modified_at, deleted_at, is_deleted, created_at)
values(4,'title4','category1','https://image.yes24.com/goods/68746076/XL','https://youtu.be/PQrfV_CtmP8','IN_ACTIVE',now(), now(),3,'SUCCESS',4,1,now(),null,false,now());
insert into content(content_id, title, description, image_url, video_url, active_status, display_start_date, display_end_date, priority, upload_status, last_modifier, category_id, last_modified_at, deleted_at, is_deleted, created_at)
values(5,'title5','category1','https://image.yes24.com/goods/68746076/XL','https://youtu.be/PQrfV_CtmP8','ACTIVE',now(), '2022-10-01 09:00:00',4,'SUCCESS',5,1,now(),null,false,now());
insert into content(content_id, title, description, image_url, video_url, active_status, display_start_date, display_end_date, priority, upload_status, last_modifier, category_id, last_modified_at, deleted_at, is_deleted, created_at)
values(6,'title6','category1','https://image.yes24.com/goods/68746076/XL','https://youtu.be/PQrfV_CtmP8','ACTIVE',now(), '2022-10-01 09:00:00',5,'SUCCESS',6,1,now(),null,false,now());
insert into content(content_id, title, description, image_url, video_url, active_status, display_start_date, display_end_date, priority, upload_status, last_modifier, category_id, last_modified_at, deleted_at, is_deleted, created_at)
values(7,'title7','category2','https://image.yes24.com/goods/68746076/XL','https://youtu.be/PQrfV_CtmP8','ACTIVE',now(), now(),2,'SUCCESS',1,2,now(),null,false,now());
insert into content(content_id, title, description, image_url, video_url, active_status, display_start_date, display_end_date, priority, upload_status, last_modifier, category_id, last_modified_at, deleted_at, is_deleted, created_at)
values(8,'title8','category2','https://image.yes24.com/goods/68746076/XL','https://youtu.be/PQrfV_CtmP8','IN_ACTIVE',now(), now(),1,'SUCCESS',3,2,now(),null,false,now());
insert into content(content_id, title, description, image_url, video_url, active_status, display_start_date, display_end_date, priority, upload_status, last_modifier, category_id, last_modified_at, deleted_at, is_deleted, created_at)
values(9,'title9','category2','https://image.yes24.com/goods/68746076/XL','https://youtu.be/PQrfV_CtmP8','ACTIVE',now(), now(),3,'SUCCESS',1,2,now(),null,false,now());
insert into content(content_id, title, description, image_url, video_url, active_status, display_start_date, display_end_date, priority, upload_status, last_modifier, category_id, last_modified_at, deleted_at, is_deleted, created_at)
values(10, null, null, 'https://image.yes24.com/goods/68746076/XL', 'https://youtu.be/PQrfV_CtmP8', null, null, null, null, 'UPLOADING',1, null, now(),null, true, now());
