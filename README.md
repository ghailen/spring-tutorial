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

![image](https://github.com/user-attachments/assets/9e2629dc-47e6-45be-b5e7-a5c70bc7dd9f)


Module 1 : CREATE , RUN AND TEST YOUR JOB

![image](https://github.com/user-attachments/assets/a5dd8d90-00d0-415e-8c51-3261f8442360)


![image](https://github.com/user-attachments/assets/7b5d565f-0d4d-4083-9d22-fe043d784022)


![image](https://github.com/user-attachments/assets/3d3a28c3-c924-4f59-9cff-1efbf2ee4590)
![image](https://github.com/user-attachments/assets/fdd9abeb-a3ad-48f8-918d-036dc33862fd)

Lab: Implement Your First Job:

![image](https://github.com/user-attachments/assets/9ce87fee-ae2b-46ce-b511-b2c7cc948921)
![image](https://github.com/user-attachments/assets/513490a0-2d01-46f5-99e5-4082d1a148f0)
![image](https://github.com/user-attachments/assets/c75fc470-0042-4ae2-843c-3a05d40ef2e5)

![image](https://github.com/user-attachments/assets/ef02c3cf-0805-4760-92f3-d2f621599c5a)

![image](https://github.com/user-attachments/assets/047192db-b915-439e-8db4-68e008218dac)

![image](https://github.com/user-attachments/assets/04b98f4b-0297-4d12-b288-60ab0e5bdc10)
![image](https://github.com/user-attachments/assets/b0a73aa0-c394-4c2d-96d0-634dd21698bf)

in my local whe running the application:
![image](https://github.com/user-attachments/assets/6ab4eabb-f4a9-44f5-b573-3b1a0a7382dc)


![image](https://github.com/user-attachments/assets/ee9494bd-4610-48bb-a848-41b9c7928227)

in my local test:
![image](https://github.com/user-attachments/assets/53a06537-0157-4fe1-a766-21f6aacb9fe4)

![image](https://github.com/user-attachments/assets/f3a9b721-6ccd-4dc1-bdd2-70bfd0170705)

and we can check that in dbeaver also
![image](https://github.com/user-attachments/assets/ff0d4e1d-2895-4315-bd82-0179886483ce)
![image](https://github.com/user-attachments/assets/60da7e28-1b1f-4f1f-823f-9cf1c000fee6)
![image](https://github.com/user-attachments/assets/99c89e35-ac62-44ba-b8b7-dcdc8795fb7e)
![image](https://github.com/user-attachments/assets/1bed5c1a-5949-4a34-a0d8-656e0bb08e46)
![image](https://github.com/user-attachments/assets/cf65662c-b6bb-4422-9a52-04bd557fab2f)
![image](https://github.com/user-attachments/assets/27bf43b6-f144-4cfe-a405-39bc8321e564)

![image](https://github.com/user-attachments/assets/e91c5f9d-db02-429c-a598-c2e181ad768e)
![image](https://github.com/user-attachments/assets/6248d32f-4bed-41a7-813f-8fc56f713ad6)

and rerun the application:
i delete the table manually from dbeaver because the script does not work in my local:
![image](https://github.com/user-attachments/assets/914f751b-de5c-4dfb-b565-9fccfe44bc29)

and let s replay the script on top:
![image](https://github.com/user-attachments/assets/41690df7-eaa9-4a0a-b64f-2eb38592b94d)
it is created again :
![image](https://github.com/user-attachments/assets/d9d7b6d4-39fa-4c33-8f07-3364383b093d)

lets rerun the app
![image](https://github.com/user-attachments/assets/e4391e54-1f03-44e2-8e79-bdc1781e9d5a)
![image](https://github.com/user-attachments/assets/c5e53c4b-ba36-4d87-a9f4-f268612fbaf6)

and the status are updated:
![image](https://github.com/user-attachments/assets/e2b2c3b9-aaad-44cd-893e-264d26d3e860)

![image](https://github.com/user-attachments/assets/d467cc28-f239-449c-b4c9-194acd07a269)


![image](https://github.com/user-attachments/assets/3e2fa516-ffbe-42f3-aa62-2ddd4a76d0c5)

in my local:

![image](https://github.com/user-attachments/assets/b9f4e6c2-2cbc-4a8d-bd11-d4368e5979b1)
![image](https://github.com/user-attachments/assets/bb6564f8-67f5-4a01-9400-d745b6b19f42)


![image](https://github.com/user-attachments/assets/7b8da886-7964-4ca2-be4a-6b7895852758)

![image](https://github.com/user-attachments/assets/89612f54-3ba8-4119-91a1-cbbd5fc54423)

![image](https://github.com/user-attachments/assets/66bec80b-3768-407a-8473-2ba0cf3984b8)

![image](https://github.com/user-attachments/assets/2791e8e3-0b23-4d2f-ba8f-125611ae841c)

=> 

![image](https://github.com/user-attachments/assets/dbd0a743-304d-40df-a485-dbdad355394e)


![image](https://github.com/user-attachments/assets/e07885ac-dd49-4444-b132-158b98f9761c)
![image](https://github.com/user-attachments/assets/b833d113-8fa5-4ece-8df8-7ee7a31a24fa)

![image](https://github.com/user-attachments/assets/3f9f050c-8a46-4e07-a8b4-0eed58555619)

![image](https://github.com/user-attachments/assets/5b2efe5e-72af-4287-a22f-f9c802fd486e)

![image](https://github.com/user-attachments/assets/fc16c5d7-6842-4039-a3c5-36ca42c20af4)

![image](https://github.com/user-attachments/assets/2ab0f4d0-e744-4a66-a27f-2c9fe68f7641)


![image](https://github.com/user-attachments/assets/42ef52b6-8c68-46eb-a10d-8dd6781c6839)

![image](https://github.com/user-attachments/assets/543f009e-c5ae-4a6a-900e-fb461fa05edf)

in local:
![image](https://github.com/user-attachments/assets/528b2f2c-012b-41a5-a16f-95931ca061a1)
![image](https://github.com/user-attachments/assets/1f21ae3c-9fb2-4244-b98e-33707c716e49)

![image](https://github.com/user-attachments/assets/e2a8eaef-bbdd-4673-a89c-92f5a006a74c)

in tutoriel:
![image](https://github.com/user-attachments/assets/7f487365-fc82-4b74-ac44-9fe351de1e5b)

![image](https://github.com/user-attachments/assets/1b00de1b-f486-4fab-80a4-2d9e2515d164)

in local:
![image](https://github.com/user-attachments/assets/43b51cc6-bed0-47e0-b70f-c3fa1298637b)

![image](https://github.com/user-attachments/assets/ac176a13-60a6-45b6-875f-def90e8438eb)
![image](https://github.com/user-attachments/assets/3dd72c4c-a1e6-4a21-82a2-319ea50ad655)



