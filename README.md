# SPRING BATCH :


![image](https://github.com/user-attachments/assets/d128ac75-9f46-4216-99ec-dd7219fd37e7)

![image](https://github.com/user-attachments/assets/d7653885-9e11-4872-b7f1-5128bf48ba05)

![image](https://github.com/user-attachments/assets/18d9f995-e225-4f89-a40c-a8a39603925e)

![image](https://github.com/user-attachments/assets/177ce091-8006-465d-9059-d76dad5f8a1e)

![image](https://github.com/user-attachments/assets/64553c94-3894-46f3-9f67-2d4940dd0ef0)

![image](https://github.com/user-attachments/assets/e7cde4bb-09eb-428c-8421-359aeed3a6e6)

![image](https://github.com/user-attachments/assets/8f0e2b5d-1b2f-4f80-88c7-fa55ab54859d)

![image](https://github.com/user-attachments/assets/e1ed784e-f3c5-4c1a-871d-227388b8f6da)


![image](https://github.com/user-attachments/assets/aab1d5ae-7b32-47b0-9035-86ce661a7050)

lets create a spring boot projet using spring initiliazer:
![image](https://github.com/user-attachments/assets/a7729002-7205-4b82-abf5-1aa0f2142802)

and let s create a postgres databse in docker: use this documentation https://www.commandprompt.com/education/how-to-create-a-postgresql-database-in-docker/
docker pull postgres
![image](https://github.com/user-attachments/assets/9175adcd-b888-42ef-b493-5ac73479d575)

and run it :
docker run -d --name postgresCont -p 5432:5432 POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres postgres

-e parameters value can be anything you want 
![image](https://github.com/user-attachments/assets/245b3f7b-8e13-46df-8022-43e316030a64)

![image](https://github.com/user-attachments/assets/a3ccc5fe-3523-49cb-a020-89f873e085e3)

to check the container use : docker ps

![image](https://github.com/user-attachments/assets/13db17d4-3bd5-40a9-a049-9d57c2678e7f)


now lets Interact With Executing Container:

docker exec -it postgres-container psql -U postgres -d postgres 

where the postgres-container is the name of the container runnger 
![image](https://github.com/user-attachments/assets/9827928e-3f7a-4f92-b2c7-62ff278c1972)

![image](https://github.com/user-attachments/assets/51c741ff-9182-4186-bf10-0e656b7b2832)

now let s create tables, execute thoses scripts:
CREATE TABLE BATCH_JOB_INSTANCE  (
    JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT ,
    JOB_NAME VARCHAR(100) NOT NULL,
    JOB_KEY VARCHAR(32) NOT NULL,
    constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ;
CREATE TABLE BATCH_JOB_EXECUTION  (
    JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT  ,
    JOB_INSTANCE_ID BIGINT NOT NULL,
    CREATE_TIME TIMESTAMP NOT NULL,
    START_TIME TIMESTAMP DEFAULT NULL ,
    END_TIME TIMESTAMP DEFAULT NULL ,
    STATUS VARCHAR(10) ,
    EXIT_CODE VARCHAR(2500) ,
    EXIT_MESSAGE VARCHAR(2500) ,
    LAST_UPDATED TIMESTAMP,
    constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
    references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ;
CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
    JOB_EXECUTION_ID BIGINT NOT NULL ,
    PARAMETER_NAME VARCHAR(100) NOT NULL ,
    PARAMETER_TYPE VARCHAR(100) NOT NULL ,
    PARAMETER_VALUE VARCHAR(2500) ,
    IDENTIFYING CHAR(1) NOT NULL ,
    constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;
CREATE TABLE BATCH_STEP_EXECUTION  (
    STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT NOT NULL,
    STEP_NAME VARCHAR(100) NOT NULL,
    JOB_EXECUTION_ID BIGINT NOT NULL,
    CREATE_TIME TIMESTAMP NOT NULL,
    START_TIME TIMESTAMP DEFAULT NULL ,
    END_TIME TIMESTAMP DEFAULT NULL ,
    STATUS VARCHAR(10) ,
    COMMIT_COUNT BIGINT ,
    READ_COUNT BIGINT ,
    FILTER_COUNT BIGINT ,
    WRITE_COUNT BIGINT ,
    READ_SKIP_COUNT BIGINT ,
    WRITE_SKIP_COUNT BIGINT ,
    PROCESS_SKIP_COUNT BIGINT ,
    ROLLBACK_COUNT BIGINT ,
    EXIT_CODE VARCHAR(2500) ,
    EXIT_MESSAGE VARCHAR(2500) ,
    LAST_UPDATED TIMESTAMP,
    constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;
CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
    STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT ,
    constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
    references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;
CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
    JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT ,
    constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;
CREATE SEQUENCE BATCH_STEP_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE BATCH_JOB_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE BATCH_JOB_SEQ MAXVALUE 9223372036854775807 NO CYCLE;

=> 

![image](https://github.com/user-attachments/assets/7ab9f096-251b-455a-9ada-30b95cb2c061)


to check the add of the table and sequences:
use :  \d

![image](https://github.com/user-attachments/assets/223e0181-f44d-4a86-9bd3-207821a4c16a)


or easily :  postgres:17-alpine
docker run --name postgres-container -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=pass1234 -e POSTGRES_DB=postgres -p 5432:5432 -d postgres

![image](https://github.com/user-attachments/assets/35cca9b3-ffc1-47ea-9bbe-ac4fcd71857f)


![image](https://github.com/user-attachments/assets/1d912b60-b8cc-429b-9cb9-2cea541bf632)

docker exec -it postgres-container psql -U postgres -d postgres

![image](https://github.com/user-attachments/assets/859ddd47-793f-4835-8abe-0f7ef7a68cc8)

to exit the command line use : \q
![image](https://github.com/user-attachments/assets/5456fe3b-49b0-4a34-ba75-1f7a73eca88b)


![image](https://github.com/user-attachments/assets/73a98c9e-8835-4974-a748-2842eeb447d8)


