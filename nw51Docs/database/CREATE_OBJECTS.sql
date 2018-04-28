CREATE TABLE IF NOT EXISTS `josivansilva02`.`USER` (
  `USER_ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'The User id.',
  `EMAIL` VARCHAR(100) NOT NULL COMMENT 'The user email, used during login.',
  `PWD` VARCHAR(130) NOT NULL COMMENT 'The user pwd, used during login.',
  `STATE` INT NOT NULL,
  `LOGIN_ATTEMPTS` INT NULL,
  `UNAUTH_ACCESS_ATTEMPTS` INT NULL,
  `STATUS` TINYINT(1) NOT NULL COMMENT 'The user status (0=inactive and 1=active.',
  PRIMARY KEY (`USER_ID`))
ENGINE = InnoDB

ALTER TABLE USER ADD `STATE` INT NOT NULL;
ALTER TABLE USER ADD `LOGIN_ATTEMPTS` INT NULL;
ALTER TABLE USER ADD `UNAUTH_ACCESS_ATTEMPTS` INT NULL;

CREATE TABLE IF NOT EXISTS `josivansilva02`.`PROFILE` (
  `PROFILE_ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'The profile id.',
  `NAME` VARCHAR(100) NULL COMMENT 'The profile name.',
  PRIMARY KEY (`PROFILE_ID`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`ROLE` (
  `ROLE_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ROLE_ID`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`PROFILE_ROLE` (
  `ROLE_ID` INT(11) NOT NULL COMMENT 'The role id.',
  `PROFILE_ID` INT(11) NOT NULL COMMENT 'The profile id.',
  INDEX `fk_PROFILE_ROLE_ROLE1_idx` (`ROLE_ID` ASC),
  INDEX `fk_PROFILE_ROLE_PROFILE1_idx` (`PROFILE_ID` ASC),
  CONSTRAINT `fk_PROFILE_ROLE_ROLE1`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `josivansilva02`.`ROLE` (`ROLE_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PROFILE_ROLE_PROFILE1`
    FOREIGN KEY (`PROFILE_ID`)
    REFERENCES `josivansilva02`.`PROFILE` (`PROFILE_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`USER_PROFILE` (
  `PROFILE_ID` INT(11) NOT NULL COMMENT 'The profile id.',
  `USER_ID` INT(11) NOT NULL COMMENT 'The user id.',
  INDEX `fk_USER_PROFILE_PROFILE1_idx` (`PROFILE_ID` ASC),
  INDEX `fk_USER_PROFILE_USER1_idx` (`USER_ID` ASC),
  CONSTRAINT `fk_USER_PROFILE_PROFILE1`
    FOREIGN KEY (`PROFILE_ID`)
    REFERENCES `josivansilva02`.`PROFILE` (`PROFILE_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_PROFILE_USER1`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `josivansilva02`.`USER` (`USER_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`ADDRESS` (
  `ADDRESS_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `CEP` VARCHAR(9) NOT NULL,
  `ADDRESS1` VARCHAR(100) NOT NULL,
  `NUMBER` VARCHAR(10) NOT NULL,
  `ADDRESS2` VARCHAR(50) NULL,
  `NEIGHBORHOOD` VARCHAR(80) NOT NULL,
  `CITY` VARCHAR(100) NOT NULL,
  `STATE_ID` INT NOT NULL,
  PRIMARY KEY (`ADDRESS_ID`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`STUDENT` (
  `STUDENT_ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The student id.',
  `ADDRESS_ID` BIGINT NOT NULL,
  `EMAIL` VARCHAR(100) NOT NULL COMMENT 'The student email, used in the login.',
  `PWD` VARCHAR(130) NOT NULL COMMENT 'The student pwd, used in the login.',
  `GENDER` CHAR(1) NOT NULL COMMENT 'The gender\n(M=Masculino,\n F=Feminino).',
  `FULL_NAME` VARCHAR(150) NOT NULL COMMENT 'The student full name.',
  `CPF` VARCHAR(14) NOT NULL COMMENT 'The student CPF.',
  `PHONE` VARCHAR(15) NULL COMMENT 'The student phone.',
  `MOBILE` VARCHAR(15) NULL COMMENT 'The student mobile.',
  `IMAGE_PROFILE` MEDIUMBLOB NULL COMMENT 'The student image profile.',
  `JOIN_DATE` DATETIME NOT NULL COMMENT 'The student join date.',
  `EXPIRATION_DATE` DATETIME NOT NULL COMMENT 'The student access expiration.',
  `LAST_UPDATE_DATE` DATETIME NULL COMMENT 'The student last update date.',
  `LAST_LOGIN_DATE` DATETIME NULL COMMENT 'The student last login date.',
  `LAST_LOGIN_IP` VARCHAR(12) NOT NULL COMMENT 'The student last IP login.',
  `LOGIN_ATTEMPTS` INT NULL,
  `STATE` INT NOT NULL,
  `SOCIAL_PROGRAM` TINYINT NOT NULL,
  `STATUS` TINYINT NOT NULL COMMENT 'The student status\n(0=inactive,\n 1=active).',
  PRIMARY KEY (`STUDENT_ID`, `ADDRESS_ID`),
  INDEX `fk_STUDENT_ADDRESS1_idx` (`ADDRESS_ID` ASC),
  CONSTRAINT `fk_STUDENT_ADDRESS1`
    FOREIGN KEY (`ADDRESS_ID`)
    REFERENCES `josivansilva02`.`ADDRESS` (`ADDRESS_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

ALTER TABLE STUDENT ADD `STATE` INT NOT NULL;
ALTER TABLE STUDENT ADD `LOGIN_ATTEMPTS` INT NULL;
ALTER TABLE STUDENT ADD EXPIRATION_DATE DATETIME NOT NULL;
ALTER TABLE STUDENT MODIFY PHONE VARCHAR(15);
ALTER TABLE STUDENT MODIFY MOBILE VARCHAR(15);

CREATE TABLE IF NOT EXISTS `josivansilva02`.`STUDY_PLAN` (
  `STUDY_PLAN_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `STUDENT_ID` BIGINT NOT NULL,
  `COURSE_ID` INT(11) NOT NULL,
  `START_CLASS_DATETIME` DATETIME NOT NULL,
  `END_CLASS_DATETIME` DATETIME NOT NULL,
  PRIMARY KEY (`STUDY_PLAN_ID`),
  INDEX `fk_STUDY_PLAN_STUDENT1_idx` (`STUDENT_ID` ASC),
  INDEX `fk_STUDY_PLAN_COURSE1_idx` (`COURSE_ID` ASC),
  CONSTRAINT `fk_STUDY_PLAN_STUDENT1`
    FOREIGN KEY (`STUDENT_ID`)
    REFERENCES `josivansilva02`.`STUDENT` (`STUDENT_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_STUDY_PLAN_COURSE1`
    FOREIGN KEY (`COURSE_ID`)
    REFERENCES `josivansilva02`.`COURSE` (`COURSE_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`NOTIFICATION` (
  `NOTIFICATION_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `STUDENT_ID` BIGINT NOT NULL,
  `NOTIFICATION_TYPE` INT NOT NULL,
  `MESSAGE` VARCHAR(100) NOT NULL,
  `URL` VARCHAR(200) NOT NULL,
  `CREATION_DATE` DATETIME NOT NULL,
  `STATE` TINYINT NOT NULL,
  PRIMARY KEY (`NOTIFICATION_ID`, `STUDENT_ID`),
  INDEX `fk_NOTIFICATION_STUDENT1_idx` (`STUDENT_ID` ASC),
  CONSTRAINT `fk_NOTIFICATION_STUDENT1`
    FOREIGN KEY (`STUDENT_ID`)
    REFERENCES `josivansilva02`.`STUDENT` (`STUDENT_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

UPDATE STUDENT SET EXPIRATION_DATE = STR_TO_DATE('31/12/2016 23:59:59', '%d/%m/%Y %H:%i:%s');

CREATE TABLE IF NOT EXISTS `josivansilva02`.`PAYMENT` (
  `PAYMENT_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `STUDENT_ID` BIGINT NOT NULL,
  `DESCRIPTION` VARCHAR(200) NULL,
  `AMOUNT` DOUBLE NOT NULL,
  `PAYMENT_DATE` DATETIME NOT NULL,
  `PAYMENT_FOR_MONTH_YEAR` VARCHAR(7) NOT NULL,
  PRIMARY KEY (`PAYMENT_ID`),
  INDEX `fk_PAYMENT_STUDENT1_idx` (`STUDENT_ID` ASC),
  CONSTRAINT `fk_PAYMENT_STUDENT1`
    FOREIGN KEY (`STUDENT_ID`)
    REFERENCES `josivansilva02`.`STUDENT` (`STUDENT_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

ALTER TABLE PAYMENT DROP PAYMENT_FOR_MONTH;

ALTER TABLE PAYMENT ADD PAYMENT_FOR_MONTH_YEAR VARCHAR(7) NOT NULL;

CREATE TABLE IF NOT EXISTS `josivansilva02`.`TEACHER` (
  `TEACHER_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` INT(11) NOT NULL,
  `FULL_NAME` VARCHAR(150) NOT NULL,
  `PHONE` VARCHAR(13) NOT NULL,
  `MOBILE` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`TEACHER_ID`),
  INDEX `fk_TEACHER_USER1_idx` (`USER_ID` ASC),
  CONSTRAINT `fk_TEACHER_USER1`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `josivansilva02`.`USER` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`TEACHING_PLAN` (
  `TEACHING_PLAN_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `WORKLOAD_HOURS_THEORY_TYPE` INT NOT NULL,
  `WORKLOAD_HOURS_PRACTICE_TYPE` INT NOT NULL,
  `SUMMARY` LONGTEXT NOT NULL,
  `GENERAL_GOAL` VARCHAR(300) NOT NULL,
  `SPECIFIC_GOALS` LONGTEXT NOT NULL,
  `TEACHING_STRATEGY_TYPE` INT NOT NULL,
  `RESOURCES` VARCHAR(500) NOT NULL,
  `EVALUATION` TEXT NOT NULL,
  `BIBLIOGRAPHY` TEXT NOT NULL,
  PRIMARY KEY (`TEACHING_PLAN_ID`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`CONTENT_ITEM` (
  `CONTENT_ITEM_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `NUMERATION_FATHER` VARCHAR(15) NULL,
  `TEACHING_PLAN_ID` INT(11) NOT NULL,
  `CLASS_ID` INT(11) NULL,
  `NUMERATION` VARCHAR(15) NOT NULL,
  `NAME` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`CONTENT_ITEM_ID`),
  INDEX `fk_CONTENT_ITEM_TEACHING_PLAN1_idx` (`TEACHING_PLAN_ID` ASC),
  INDEX `fk_CONTENT_ITEM_CLASS1_idx` (`CLASS_ID` ASC),
  CONSTRAINT `fk_CONTENT_ITEM_TEACHING_PLAN1`
    FOREIGN KEY (`TEACHING_PLAN_ID`)
    REFERENCES `josivansilva02`.`TEACHING_PLAN` (`TEACHING_PLAN_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CONTENT_ITEM_CLASS1`
    FOREIGN KEY (`CLASS_ID`)
    REFERENCES `josivansilva02`.`CLASS` (`CLASS_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`CAREER` (
  `CAREER_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(200) NOT NULL,
  `CAREER_TYPE` INT NOT NULL,
  `DESCRIPTION` TEXT NOT NULL,
  `STATUS` TINYINT NOT NULL,
  PRIMARY KEY (`CAREER_ID`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`CAREER_COURSE` (
  `CAREER_ID` INT(11) NOT NULL,  
  `COURSE_ID` INT(11) NOT NULL,  
  `SEQUENCE_NUMBER` INT NOT NULL,
  INDEX `fk_CAREER_COURSE_CAREER1_idx` (`CAREER_ID` ASC),
  INDEX `fk_CAREER_COURSE_COURSE1_idx` (`COURSE_ID` ASC),  
  CONSTRAINT `fk_CAREER_COURSE_COURSE1`
    FOREIGN KEY (`COURSE_ID`)
    REFERENCES `josivansilva02`.`COURSE` (`COURSE_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CAREER_COURSE_CAREER1`
    FOREIGN KEY (`CAREER_ID`)
    REFERENCES `josivansilva02`.`CAREER` (`CAREER_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`COURSE` (
  `COURSE_ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'The course id.',
  `TEACHER_ID` INT(11) NOT NULL,
  `TEACHING_PLAN_ID` INT(11) NOT NULL,
  `COURSE_TYPE` INT NOT NULL COMMENT 'The course type.',
  `NAME` VARCHAR(200) NOT NULL COMMENT 'The course name.',
  `CREATION_DATE` DATETIME NOT NULL COMMENT 'The course creation date.',
  `LAST_UPDATE_DATE` DATETIME NULL COMMENT 'The course last update date.',
  `STATUS` TINYINT NOT NULL COMMENT 'The course status\n(0=inactive,\n 1=active).',
  PRIMARY KEY (`COURSE_ID`),
  INDEX `fk_COURSE_TEACHER1_idx` (`TEACHER_ID` ASC),
  INDEX `fk_COURSE_TEACHING_PLAN1_idx` (`TEACHING_PLAN_ID` ASC),
  CONSTRAINT `fk_COURSE_TEACHER1`
    FOREIGN KEY (`TEACHER_ID`)
    REFERENCES `josivansilva02`.`TEACHER` (`TEACHER_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_COURSE_TEACHING_PLAN1`
    FOREIGN KEY (`TEACHING_PLAN_ID`)
    REFERENCES `josivansilva02`.`TEACHING_PLAN` (`TEACHING_PLAN_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`STUDENT_COURSE` (
  `STUDENT_COURSE_ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Student course id.',
  `STUDENT_ID` BIGINT NOT NULL COMMENT 'The student id.',
  `COURSE_ID` INT(11) NOT NULL COMMENT 'The course id.',
  `START_DATE` DATETIME NOT NULL COMMENT 'The start date.',
  `PROGRESS` INT NOT NULL COMMENT 'The student course progress.',
  `END_DATE` DATETIME NOT NULL COMMENT 'The course end date.',
  `EXPIRATION_DATE` DATETIME NOT NULL COMMENT 'The student course expiration date.',
  INDEX `fk_STUDENT_COURSE_COURSE1_idx` (`COURSE_ID` ASC),
  PRIMARY KEY (`STUDENT_COURSE_ID`),
  CONSTRAINT `fk_STUDENT_COURSE_STUDENT1`
    FOREIGN KEY (`STUDENT_ID`)
    REFERENCES `josivansilva02`.`STUDENT` (`STUDENT_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_STUDENT_COURSE_COURSE1`
    FOREIGN KEY (`COURSE_ID`)
    REFERENCES `josivansilva02`.`COURSE` (`COURSE_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`CLASS_PLAN` (
  `CLASS_PLAN_ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'The class plan id.',
  `AVERAGE_TIME` FLOAT NOT NULL COMMENT 'The class average time.',
  `SKILLS` TEXT NOT NULL COMMENT 'The class plan skills.',
  PRIMARY KEY (`CLASS_PLAN_ID`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`TOOL` (
  `TOOL_ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`TOOL_ID`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`CLASS_PLAN_TOOL` (
  `CLASS_PLAN_ID` INT(11) NOT NULL,
  `TOOL_ID` INT NOT NULL,
  PRIMARY KEY (`CLASS_PLAN_ID`, `TOOL_ID`),
  INDEX `fk_CLASS_PLAN_TOOL_CLASS_PLAN1_idx` (`CLASS_PLAN_ID` ASC),
  CONSTRAINT `fk_CLASS_PLAN_TOOL_TOOL1`
    FOREIGN KEY (`TOOL_ID`)
    REFERENCES `josivansilva02`.`TOOL` (`TOOL_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CLASS_PLAN_TOOL_CLASS_PLAN1`
    FOREIGN KEY (`CLASS_PLAN_ID`)
    REFERENCES `josivansilva02`.`CLASS_PLAN` (`CLASS_PLAN_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`CLASS` (
  `CLASS_ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'The class id.',
  `COURSE_ID` INT(11) NOT NULL COMMENT 'The course id.',
  `CLASS_PLAN_ID` INT(11) NOT NULL,
  `NAME` VARCHAR(200) NOT NULL COMMENT 'The class name.',
  `DESCRIPTION` VARCHAR(500) NOT NULL COMMENT 'The class description.',
  `NOTES` MEDIUMTEXT NULL COMMENT 'The class notes (e.g., URL, comments, etc.)',
  PRIMARY KEY (`CLASS_ID`),
  INDEX `fk_CLASS_COURSE1_idx` (`COURSE_ID` ASC),
  INDEX `fk_CLASS_CLASS_PLAN1_idx` (`CLASS_PLAN_ID` ASC),
  CONSTRAINT `fk_CLASS_COURSE1`
    FOREIGN KEY (`COURSE_ID`)
    REFERENCES `josivansilva02`.`COURSE` (`COURSE_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CLASS_CLASS_PLAN1`
    FOREIGN KEY (`CLASS_PLAN_ID`)
    REFERENCES `josivansilva02`.`CLASS_PLAN` (`CLASS_PLAN_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`SLIDE` (
  `SLIDE_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `CLASS_ID` INT(11) NOT NULL,
  `NAME` VARCHAR(200) NOT NULL,
  `CONTENT` LONGTEXT NOT NULL,
  `STATUS` TINYINT NOT NULL,
  `SEQUENCE_NUMBER` INT NOT NULL,
  PRIMARY KEY (`SLIDE_ID`),
  INDEX `fk_SLIDE_CLASS1_idx` (`CLASS_ID` ASC),
  CONSTRAINT `fk_SLIDE_CLASS1`
    FOREIGN KEY (`CLASS_ID`)
    REFERENCES `josivansilva02`.`CLASS` (`CLASS_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`EXERCISE` (
  `EXERCISE_ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'The exercise id.',
  `CLASS_ID` INT(11) NOT NULL COMMENT 'The class id.',
  `EXERCISE_TYPE` INT NOT NULL COMMENT 'The exercise type\n(3=Exercício de Fixação,\n 4=Desafio).',
  `NAME` VARCHAR(200) NOT NULL COMMENT 'The exercise name.',
  `SEQUENCE_NUMBER` INT NOT NULL,
  PRIMARY KEY (`EXERCISE_ID`),
  INDEX `fk_EXERCISE_CLASS1_idx` (`CLASS_ID` ASC),
  CONSTRAINT `fk_EXERCISE_CLASS1`
    FOREIGN KEY (`CLASS_ID`)
    REFERENCES `josivansilva02`.`CLASS` (`CLASS_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`QUESTION` (
  `QUESTION_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `EXERCISE_ID` INT(11) NOT NULL,
  `ENUNCIATION` TEXT NOT NULL,
  `ALTERNATIVE_A` VARCHAR(300) NOT NULL,
  `ALTERNATIVE_B` VARCHAR(300) NOT NULL,
  `ALTERNATIVE_C` VARCHAR(300) NOT NULL,
  `ALTERNATIVE_D` VARCHAR(300) NOT NULL,
  `ALTERNATIVE_E` VARCHAR(300) NOT NULL,
  `ALTERNATIVE_CORRECT` CHAR NOT NULL,
  PRIMARY KEY (`QUESTION_ID`),
  INDEX `fk_QUESTION_EXERCISE1_idx` (`EXERCISE_ID` ASC),
  CONSTRAINT `fk_QUESTION_EXERCISE1`
    FOREIGN KEY (`EXERCISE_ID`)
    REFERENCES `josivansilva02`.`EXERCISE` (`EXERCISE_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`ATTACHMENT` (
  `ATTACHMENT_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `CLASS_ID` INT(11) NOT NULL,
  `NAME` VARCHAR(250) NOT NULL,
  `TYPE` VARCHAR(30) NOT NULL,
  `SIZE` VARCHAR(50) NOT NULL,
  `FILE` LONGBLOB NOT NULL,
  `CREATION_DATE` DATETIME NOT NULL,
  PRIMARY KEY (`ATTACHMENT_ID`),
  INDEX `fk_ATTACHMENT_CLASS1_idx` (`CLASS_ID` ASC),
  CONSTRAINT `fk_ATTACHMENT_CLASS1`
    FOREIGN KEY (`CLASS_ID`)
    REFERENCES `josivansilva02`.`CLASS` (`CLASS_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`CERTIFICATE` (
  `CERTIFICATE_ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The certificate id.',
  `STUDENT_COURSE_ID` BIGINT NOT NULL COMMENT 'The student course id.',
  `CREATION_DATE` DATETIME NOT NULL COMMENT 'The creation date.',
  PRIMARY KEY (`CERTIFICATE_ID`),
  INDEX `fk_CERTIFICATE_STUDENT_COURSE1_idx` (`STUDENT_COURSE_ID` ASC),
  CONSTRAINT `fk_CERTIFICATE_STUDENT_COURSE1`
    FOREIGN KEY (`STUDENT_COURSE_ID`)
    REFERENCES `josivansilva02`.`STUDENT_COURSE` (`STUDENT_COURSE_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `josivansilva02`.`LOG` (
  `LOG_ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The log id.',
  `STUDENT_ID` BIGINT NULL,
  `OPERATION` VARCHAR(150) NOT NULL COMMENT 'The method which\nthe event was \ngenerated.',
  `INPUT` LONGTEXT NOT NULL COMMENT 'The method input.',
  `OUTPUT` LONGTEXT NULL COMMENT 'The method output.',
  `ERROR` LONGTEXT NULL COMMENT 'The error, in case of failure.',
  `MESSAGE` VARCHAR(200) NOT NULL COMMENT 'The event message.',
  `LOG_TIME` TIMESTAMP NOT NULL COMMENT 'The event timestamp.',
  `STATUS` TINYINT NOT NULL COMMENT 'The log status\n(0=error,\n 1=success)',
  PRIMARY KEY (`LOG_ID`),
  INDEX `fk_LOG_STUDENT1_idx` (`STUDENT_ID` ASC),
  CONSTRAINT `fk_LOG_STUDENT1`
    FOREIGN KEY (`STUDENT_ID`)
    REFERENCES `josivansilva02`.`STUDENT` (`STUDENT_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
