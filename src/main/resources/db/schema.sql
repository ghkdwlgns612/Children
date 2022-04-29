DROP TABLE IF EXISTS MEMBER CASCADE;

CREATE TABLE MEMBER (
                      member_id         BIGINT     PRIMARY KEY AUTO_INCREMENT,
                      created_at        DATETIME    ,
                      deleted_at        DATETIME    ,
                      last_modified_at  DATETIME    ,
                      email             VARCHAR(255)       UNIQUE,
                      is_deleted        BIT ,
                      name              VARCHAR(255),
                      password          VARCHAR(255),
                      role              VARCHAR(255)
);
