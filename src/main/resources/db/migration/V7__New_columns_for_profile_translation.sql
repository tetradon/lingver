alter table profile_translation
  add column last_repeat_date timestamp;
alter table profile_translation
  add column repeat_count int not null default 0;
alter table profile_translation
  add column success_repeat_count int not null default 0;
