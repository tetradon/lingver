
CREATE TABLE exercise (
                        exercise_pk bigint NOT NULL,
                        name character varying(256) NOT NULL
);

CREATE SEQUENCE exercise_exercise_pk_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE exercise_exercise_pk_seq OWNED BY exercise.exercise_pk;

CREATE TABLE exercise_history (
                                exercise_history_pk    bigint                      NOT NULL,
                                date                   timestamp without time zone NOT NULL,
                                profile_translation_fk bigint                      NOT NULL,
                                exercise_fk            bigint                      NOT NULL,
                                result                 boolean                     NOT NULL
);

CREATE SEQUENCE exercise_history_exercise_history_pk_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE exercise_history_exercise_history_pk_seq OWNED BY exercise_history.exercise_history_pk;

CREATE TABLE role (
                    role_pk bigint NOT NULL,
                    name character varying(256) NOT NULL
);

CREATE SEQUENCE role_role_pk_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE role_role_pk_seq OWNED BY role.role_pk;

CREATE TABLE translation (
                           translation_pk bigint                                                           NOT NULL,
                           word_fk        bigint                                                           NOT NULL,
                           value          character varying(256)                                           NOT NULL,
                           inserted_by    character varying(256)      DEFAULT 'LINGVER'::character varying NOT NULL,
                           insert_date    timestamp without time zone DEFAULT now()                        NOT NULL
);

CREATE SEQUENCE translation_translation_pk_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE translation_translation_pk_seq OWNED BY translation.translation_pk;

CREATE TABLE profile
(
  profile_pk                 bigint                 NOT NULL,
  username                   character varying(256) NOT NULL,
  password                   character varying(256) NOT NULL,
  email                      character varying(256),
  first_name                 character varying(256),
  last_name                  character varying(256),
  account_expiration_date    TIMESTAMP,
  credential_expiration_date TIMESTAMP,
  locked                     BOOLEAN default false,
  enabled                    BOOLEAN default true
);

CREATE TABLE profile_translation
(
  profile_translation_pk bigint NOT NULL,
  translation_fk         bigint NOT NULL,
  profile_fk             bigint NOT NULL,
  insert_date            timestamp without time zone DEFAULT now(),
  description            character varying(2000),
  example                character varying(2000)
);

CREATE SEQUENCE profile_translation_profile_translation_pk_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE profile_translation_profile_translation_pk_seq OWNED BY profile_translation.profile_translation_pk;

CREATE SEQUENCE profile_profile_pk_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE profile_profile_pk_seq OWNED BY profile.profile_pk;

CREATE TABLE word (
                    word_pk     bigint NOT NULL,
                    value       character varying(256) NOT NULL,
                    insert_date timestamp without time zone DEFAULT now() NOT NULL,
                    inserted_by character varying(256)      DEFAULT 'LINGVER'::character varying
);

CREATE SEQUENCE word_word_pk_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE word_word_pk_seq OWNED BY word.word_pk;

ALTER TABLE ONLY exercise ALTER COLUMN exercise_pk SET DEFAULT nextval('exercise_exercise_pk_seq'::regclass);

ALTER TABLE ONLY exercise_history ALTER COLUMN exercise_history_pk SET DEFAULT nextval('exercise_history_exercise_history_pk_seq'::regclass);

ALTER TABLE ONLY role ALTER COLUMN role_pk SET DEFAULT nextval('role_role_pk_seq'::regclass);

ALTER TABLE ONLY translation ALTER COLUMN translation_pk SET DEFAULT nextval('translation_translation_pk_seq'::regclass);

ALTER TABLE ONLY profile
  ALTER COLUMN profile_pk SET DEFAULT nextval('profile_profile_pk_seq'::regclass);

ALTER TABLE ONLY profile_translation
  ALTER COLUMN profile_translation_pk SET DEFAULT nextval('profile_translation_profile_translation_pk_seq'::regclass);

ALTER TABLE ONLY word ALTER COLUMN word_pk SET DEFAULT nextval('word_word_pk_seq'::regclass);

ALTER TABLE ONLY exercise_history
  ADD CONSTRAINT exercise_history_pk PRIMARY KEY (exercise_history_pk);

ALTER TABLE ONLY exercise
  ADD CONSTRAINT exercise_pk PRIMARY KEY (exercise_pk);

ALTER TABLE ONLY role
  ADD CONSTRAINT role_pk PRIMARY KEY (role_pk);

ALTER TABLE ONLY translation
  ADD CONSTRAINT translation_pk PRIMARY KEY (translation_pk);

ALTER TABLE ONLY profile
  ADD CONSTRAINT profile_pk PRIMARY KEY (profile_pk);

ALTER TABLE ONLY profile_translation
  ADD CONSTRAINT profile_translation_pk PRIMARY KEY (profile_translation_pk);

ALTER TABLE ONLY word
  ADD CONSTRAINT word_pk PRIMARY KEY (word_pk);


CREATE UNIQUE INDEX role_name_uindex ON role USING btree (name);

CREATE UNIQUE INDEX translation_wordfk_value_uindex ON translation USING btree (word_fk, value);

CREATE UNIQUE INDEX profile_email_uindex ON profile USING btree (email);

CREATE UNIQUE INDEX profile_translation_profile_fk_translation_fk_uindex ON profile_translation USING btree (profile_fk, translation_fk);

CREATE UNIQUE INDEX profile_username_uindex ON profile USING btree (username);

CREATE UNIQUE INDEX word_value_uindex ON word USING btree (value);

ALTER TABLE ONLY exercise_history
  ADD CONSTRAINT exercise_history_exercise_fk FOREIGN KEY (exercise_fk) REFERENCES exercise(exercise_pk);

ALTER TABLE ONLY exercise_history
  ADD CONSTRAINT exercise_history_profile_translation_fk FOREIGN KEY (profile_translation_fk) REFERENCES profile_translation (profile_translation_pk);

ALTER TABLE ONLY translation
  ADD CONSTRAINT translation_word_fk FOREIGN KEY (word_fk) REFERENCES word(word_pk);

ALTER TABLE ONLY profile_translation
  ADD CONSTRAINT profile_translation_translation_fk FOREIGN KEY (translation_fk) REFERENCES translation (translation_pk);


ALTER TABLE ONLY profile_translation
  ADD CONSTRAINT profile_translation_profile_fk FOREIGN KEY (profile_fk) REFERENCES profile (profile_pk);

CREATE TABLE profile_role
(
  profile_fk BIGINT not null
    constraint profile_role_profile_fk
      references profile,
  role_fk    BIGINT not null
    constraint profile_role_role_fk
      references role,
  constraint profile_role_pk
    primary key (profile_fk, role_fk)
);



