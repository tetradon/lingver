create table user_role
(
  user_fk BIGINT not null
    constraint user_role_user_fk
      references user,
  role_fk BIGINT not null
    constraint user_role_role_fk
      references role,
  constraint user_role_pk
    primary key (user_fk, role_fk)
);

alter table user
  drop constraint user_role_fk;

alter table user
  add account_expiration_date TIMESTAMP;

alter table user
  add credential_expiration_date TIMESTAMP;

alter table user
  add locked BOOLEAN default false;

alter table user
  add enabled BOOLEAN default true;

