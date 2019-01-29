
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
                                exercise_history_pk bigint NOT NULL,
                                date timestamp without time zone NOT NULL,
                                user_translation_fk bigint NOT NULL,
                                exercise_fk bigint NOT NULL,
                                result boolean NOT NULL
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
                           translation_pk bigint NOT NULL,
                           word_fk bigint NOT NULL,
                           value character varying(256) NOT NULL,
                           insert_user character varying(256) DEFAULT 'LINGVER'::character varying NOT NULL,
                           insert_date timestamp without time zone DEFAULT now() NOT NULL
);

CREATE SEQUENCE translation_translation_pk_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE translation_translation_pk_seq OWNED BY translation.translation_pk;

CREATE TABLE "user" (
                      user_pk bigint NOT NULL,
                      role_fk bigint NOT NULL,
                      username character varying(256) NOT NULL,
                      email character varying(256) NOT NULL,
                      first_name character varying(256),
                      last_name character varying(256)
);

CREATE TABLE user_translation (
                                user_translation_pk bigint NOT NULL,
                                translation_fk bigint NOT NULL,
                                user_fk bigint NOT NULL,
                                insert_date timestamp without time zone DEFAULT now(),
                                description character varying(2000),
                                example character varying(2000)
);

CREATE SEQUENCE user_translation_user_translation_pk_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE user_translation_user_translation_pk_seq OWNED BY user_translation.user_translation_pk;

CREATE SEQUENCE user_user_pk_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER SEQUENCE user_user_pk_seq OWNED BY "user".user_pk;

CREATE TABLE word (
                    word_pk bigint NOT NULL,
                    value character varying(256) NOT NULL,
                    insert_date timestamp without time zone DEFAULT now() NOT NULL,
                    insert_user character varying(256) DEFAULT 'LINGVER'::character varying
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

ALTER TABLE ONLY "user" ALTER COLUMN user_pk SET DEFAULT nextval('user_user_pk_seq'::regclass);

ALTER TABLE ONLY user_translation ALTER COLUMN user_translation_pk SET DEFAULT nextval('user_translation_user_translation_pk_seq'::regclass);

ALTER TABLE ONLY word ALTER COLUMN word_pk SET DEFAULT nextval('word_word_pk_seq'::regclass);

ALTER TABLE ONLY exercise_history
  ADD CONSTRAINT exercise_history_pk PRIMARY KEY (exercise_history_pk);

ALTER TABLE ONLY exercise
  ADD CONSTRAINT exercise_pk PRIMARY KEY (exercise_pk);

ALTER TABLE ONLY role
  ADD CONSTRAINT role_pk PRIMARY KEY (role_pk);

ALTER TABLE ONLY translation
  ADD CONSTRAINT translation_pk PRIMARY KEY (translation_pk);

ALTER TABLE ONLY "user"
  ADD CONSTRAINT user_pk PRIMARY KEY (user_pk);

ALTER TABLE ONLY user_translation
  ADD CONSTRAINT user_translation_pk PRIMARY KEY (user_translation_pk);

ALTER TABLE ONLY word
  ADD CONSTRAINT word_pk PRIMARY KEY (word_pk);


CREATE UNIQUE INDEX role_name_uindex ON role USING btree (name);

CREATE UNIQUE INDEX translation_wordfk_value_uindex ON translation USING btree (word_fk, value);

CREATE UNIQUE INDEX user_email_uindex ON "user" USING btree (email);

CREATE UNIQUE INDEX user_translation_user_fk_translation_fk_uindex ON user_translation USING btree (user_fk, translation_fk);

CREATE UNIQUE INDEX user_username_uindex ON "user" USING btree (username);

CREATE UNIQUE INDEX word_value_uindex ON word USING btree (value);

ALTER TABLE ONLY exercise_history
  ADD CONSTRAINT exercise_history_exercise_fk FOREIGN KEY (exercise_fk) REFERENCES exercise(exercise_pk);

ALTER TABLE ONLY exercise_history
  ADD CONSTRAINT exercise_history_user_translation_fk FOREIGN KEY (user_translation_fk) REFERENCES user_translation(user_translation_pk);

ALTER TABLE ONLY translation
  ADD CONSTRAINT translation_word_fk FOREIGN KEY (word_fk) REFERENCES word(word_pk);

ALTER TABLE ONLY "user"
  ADD CONSTRAINT user_role_fk FOREIGN KEY (role_fk) REFERENCES role(role_pk);

ALTER TABLE ONLY user_translation
  ADD CONSTRAINT user_translation_translation_fk FOREIGN KEY (translation_fk) REFERENCES translation(translation_pk);


ALTER TABLE ONLY user_translation
  ADD CONSTRAINT user_translation_user_fk FOREIGN KEY (user_fk) REFERENCES "user"(user_pk);


