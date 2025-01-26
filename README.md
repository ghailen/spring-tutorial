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

![image](https://github.com/user-attachments/assets/2139517d-18a1-4ff0-9deb-68f7a81b4c11)

in local : 
![image](https://github.com/user-attachments/assets/021a29e0-687f-47aa-9990-6edb53a6650e)

![image](https://github.com/user-attachments/assets/000472be-dd97-427f-bd0d-efec0452a8ea)

![image](https://github.com/user-attachments/assets/30ef2a20-4078-4d50-803a-cfa209cf3d88)

the same number as the last execution.
![image](https://github.com/user-attachments/assets/be298f7c-867b-4b3e-bb09-77d5a18a96d3)

![image](https://github.com/user-attachments/assets/7280cb92-ad77-4528-8c95-1cffb626d0f5)

![image](https://github.com/user-attachments/assets/74a72f5e-e9f7-4168-8c19-f57806d07cf4)


![image](https://github.com/user-attachments/assets/7ac6a058-b4b1-4c8a-b814-de812d562dfd)

![image](https://github.com/user-attachments/assets/99a0c9db-5cb5-40eb-a6eb-e11d89086e2c)

![image](https://github.com/user-attachments/assets/7c336a8f-ae80-4ef9-ae63-8a2a6c1bc574)
![image](https://github.com/user-attachments/assets/dc04b857-e149-4f92-9628-8166e2a1c4cd)

![image](https://github.com/user-attachments/assets/313dd87d-648c-4bfa-9bcc-5c0921b37f39)

![image](https://github.com/user-attachments/assets/f28243b0-f892-4653-b9a8-a1b547998b39)

![image](https://github.com/user-attachments/assets/8937338d-19a7-4c51-83f9-3442da205a36)

![image](https://github.com/user-attachments/assets/0c814aa3-5663-45de-8edb-caaebd51f196)
![image](https://github.com/user-attachments/assets/3bf447bb-7adf-4acf-8539-fb8626f3d6b6)

even when adding parameters we still get the error: 
![image](https://github.com/user-attachments/assets/ba35b0a8-2a65-406f-be74-6f7f34543dbc)

![image](https://github.com/user-attachments/assets/effd165a-c553-4612-87ca-8295f6b8f87d)

![image](https://github.com/user-attachments/assets/4a57fd0e-b355-4aed-92fd-bffc506dae3a)

![image](https://github.com/user-attachments/assets/a4e7cafe-b95a-49e4-80fe-25f64b765bb7)

![image](https://github.com/user-attachments/assets/24a88118-1b9a-452c-b4c1-cab2e5e6d5de)

![image](https://github.com/user-attachments/assets/3474e845-532d-4bec-ba49-0a6ee9c30cde)

![image](https://github.com/user-attachments/assets/cb074e88-52d4-447a-9267-be5beb86cce2)

![image](https://github.com/user-attachments/assets/e8d024fa-fa0e-4906-b5bf-d95f2c56237a)

![image](https://github.com/user-attachments/assets/5808aee6-43f0-4ec3-a15c-cc594f66b4bf)

![image](https://github.com/user-attachments/assets/4640b351-932b-48e5-b392-9c1d6e5cbfe7)

![image](https://github.com/user-attachments/assets/8069e58b-f08f-4101-904a-cf05d8bbe015)

![image](https://github.com/user-attachments/assets/b4665700-a76c-4b0a-b0a3-d2c4892cf2eb)

dont forget to delete the databse and add it again , because the old instance still exists.
because we got the error Job already exist.
nwo rerun the test job :
![image](https://github.com/user-attachments/assets/6963d7c1-7e9b-4886-8e89-e3d5cf8e2a5e)
the second time we will get again : 
![image](https://github.com/user-attachments/assets/5732e141-66ac-4b87-bec3-0fab18cfa88d)

![image](https://github.com/user-attachments/assets/fff4a672-565e-4486-8f80-0fdf3acf24a4)

![image](https://github.com/user-attachments/assets/cab30e1f-ab6c-4aec-9eea-0aca696b825e)

![image](https://github.com/user-attachments/assets/42ace8d4-b437-4c57-89e0-c92dcc8bc3d0)
![image](https://github.com/user-attachments/assets/4df77983-f24c-4e97-ae28-b10f4c51ccbc)

![image](https://github.com/user-attachments/assets/98af54b2-5791-450c-a15c-6d4f03cb47de)

![image](https://github.com/user-attachments/assets/ce08342a-45f0-4714-89ee-c25a9ba0be2d)

it works now even whithout using this.jobRepositoryTestUtils.removeJobExecutions() because we added this.jobLauncherTestUtils.getUniqueJobParametersBuilder(). => so the parameter are unique in every excution.
![image](https://github.com/user-attachments/assets/5c6e793f-d0ef-427b-b66c-1849a0aac968)

![image](https://github.com/user-attachments/assets/29f69ca4-1106-4256-984d-4d4233b434ff)

![image](https://github.com/user-attachments/assets/8c8947d8-afad-4aab-90f7-0d3fa2e7c782)

![image](https://github.com/user-attachments/assets/8ed97187-e56e-46e0-80f0-fceaf5a8cc65)

rerun the test after readding :  this.jobRepositoryTestUtils.removeJobExecutions();
in database the parameters are deleted and a new one are created after the recexcution of the test:

![image](https://github.com/user-attachments/assets/c16f8bb0-d496-4b95-8cd4-7abd989bd74f)
![image](https://github.com/user-attachments/assets/7009fcda-6d50-4f53-8e79-073faa947389)


Module 2 :  Structure your job with steps :

![image](https://github.com/user-attachments/assets/4ce836d1-1c3f-48d9-97ad-bc36e0d136ac)

![image](https://github.com/user-attachments/assets/311aa3a1-5e5c-4775-87e5-b354f5169a08)

![image](https://github.com/user-attachments/assets/5925da9a-e6c9-4b47-b4e7-fff6c5245e3e)

![image](https://github.com/user-attachments/assets/7b7bbaed-9f69-48a9-a4d9-64de54ae21cc)

![image](https://github.com/user-attachments/assets/07c76060-e8dd-4563-a0cf-b54c16aecdcd)

![image](https://github.com/user-attachments/assets/43f01343-dc05-4a4e-ada0-b56c8eec37b7)


![image](https://github.com/user-attachments/assets/6b179be1-6acf-404e-984e-45e2a6d363b8)

![image](https://github.com/user-attachments/assets/f0ee9624-eb97-42ec-8f5d-b1dfa43e0611)

![image](https://github.com/user-attachments/assets/f7b9b202-3ce5-4708-99a3-a32e7bc357c2)

![image](https://github.com/user-attachments/assets/91663be0-2152-4c67-9cac-016e62a2b846)

![image](https://github.com/user-attachments/assets/2998e3d9-fcf0-4f5b-80b6-152ffb9017bd)
![image](https://github.com/user-attachments/assets/fc249aec-2f23-400d-b720-c361532665c0)


The method provided in course does not work , when reading the file so i changed it by this code:
![image](https://github.com/user-attachments/assets/e70c6c82-69d8-49b2-bfc0-5f5b893bac83)

![image](https://github.com/user-attachments/assets/2e7d40db-bf9c-48c2-af48-252758b39b13)

![image](https://github.com/user-attachments/assets/60282e75-4899-4bbf-bcb9-172a6f09495f)

java -jar target/billing-job-0.0.1-SNAPSHOT.jar input.file=billing-2023-01.csv

![image](https://github.com/user-attachments/assets/1fa86736-61a4-4abc-a485-92249a88726a)

![image](https://github.com/user-attachments/assets/97c907ec-71f6-45a5-a88b-be56dc866f84)

![image](https://github.com/user-attachments/assets/f9705985-a26a-4cf7-81c6-f7932919baa7)

![image](https://github.com/user-attachments/assets/4a7f2e20-7537-4b22-bf97-d8dd0f16d85f)

for the test, it is already updated , after the changement i done: 
![image](https://github.com/user-attachments/assets/0e41ef33-8d8a-443d-8aca-7153fde07969)
=> OK
![image](https://github.com/user-attachments/assets/38494edc-02df-4d21-83fd-907e5fe1155d)

https://docs.spring.io/spring-batch/docs/5.0.4/reference/html/job.html#jobparametersvalidator
![image](https://github.com/user-attachments/assets/2f7e5121-64d3-4836-a2f5-78adc753d559)
![image](https://github.com/user-attachments/assets/94f73fde-8184-43c4-a526-f8f3ecea87aa)
![image](https://github.com/user-attachments/assets/e6179dc8-2a4a-4d6f-913c-cc9047669b55)
![image](https://github.com/user-attachments/assets/3b1f5479-08de-47e3-9bc3-4a2b57e34959)
![image](https://github.com/user-attachments/assets/8e8da4b5-616a-4542-a0f7-841c98923bee)
![image](https://github.com/user-attachments/assets/6fdbf1e0-befd-4cf3-92c7-5a793d79a6db)
![image](https://github.com/user-attachments/assets/505303ad-afb4-472c-bde5-9061f70dc26a)

LAB :
![image](https://github.com/user-attachments/assets/f241aa58-2a12-484a-9f91-27a05d321227)
![image](https://github.com/user-attachments/assets/3e20b566-31de-4e9b-a3fb-69d3dd21f085)


![image](https://github.com/user-attachments/assets/e94464ce-8c48-431d-b5cd-17993adb3eb4)
![image](https://github.com/user-attachments/assets/d01a2b13-d05a-4693-99c5-6e61b14fba89)
![image](https://github.com/user-attachments/assets/aa46f325-78a7-48ba-8518-3dc876a25683)

![image](https://github.com/user-attachments/assets/e25e2aa3-de98-448d-b884-10ab1bd96aed)
![image](https://github.com/user-attachments/assets/e752ce87-c575-4ccb-86f2-63e5a8be3cf2)

![image](https://github.com/user-attachments/assets/356e0d14-1e88-43df-b132-3ae7d859c9e1)
in local :
![image](https://github.com/user-attachments/assets/ca1eba02-cd19-4d2d-bb22-db65ff638ee4)
![image](https://github.com/user-attachments/assets/c71aae5c-e893-4db2-b118-793ee1e2a207)

![image](https://github.com/user-attachments/assets/f94b6001-a5a5-49d5-b14e-6d9b3f81914f)
![image](https://github.com/user-attachments/assets/1f5c87f5-275c-4c75-84f5-02a98fb85735)

![image](https://github.com/user-attachments/assets/430371d7-dda9-4deb-afe6-a32157d7982b)

![image](https://github.com/user-attachments/assets/a588a1bd-aa2f-4991-a4e7-fb1eb6cc9546)
It worked!

in local :
before execution of the job dont forget to clean instance with the same parameter or run the jobRepositoryTestUtils.removeJobExections();
![image](https://github.com/user-attachments/assets/c8515a97-f917-4a7e-8b91-4be25076deec)
java -jar target/billing-job-0.0.1-SNAPSHOT.jar input.file=billing-2023-01.csv
![image](https://github.com/user-attachments/assets/7f7433ec-ffcd-4da5-a132-b467f30407f0)
![image](https://github.com/user-attachments/assets/74c8454c-6c5b-461d-8d9e-ac672a21b7b8)

all is good.
![image](https://github.com/user-attachments/assets/79a1913e-87ed-4aae-a1b9-f851d846331d)
![image](https://github.com/user-attachments/assets/78e5fdac-28fb-42aa-b883-de8b5c4dac36)
![image](https://github.com/user-attachments/assets/588f2d7d-fe81-422c-948a-50572449bf3e)
![image](https://github.com/user-attachments/assets/bc437b2e-9b1b-47b7-91c7-785adb1a87b2)

in local:
![image](https://github.com/user-attachments/assets/a065d9d3-027e-405c-9521-089457364735)

same error.
so let s clean the table in setUp () method because we use the same database as mentionned in tutorial:
JdbcTestUtils.deleteFromTables(this.jdbcTemplate, "BILLING_DATA");

now its work :
![image](https://github.com/user-attachments/assets/9f8f4d63-3de1-4ed8-a376-340191c2b441)
![image](https://github.com/user-attachments/assets/c2839668-7267-49c6-ac00-5372427f74a2)

*** Processing Data ***
![image](https://github.com/user-attachments/assets/5d594bc1-cc80-4205-ae0d-1df626737729)
![image](https://github.com/user-attachments/assets/7bef37a3-53c5-444e-98dc-442b90df69b9)
![image](https://github.com/user-attachments/assets/4a3d0120-3e1a-42dd-b153-acb08657f140)
![image](https://github.com/user-attachments/assets/bf1347a4-c029-48c8-b4c0-b4018ea7f5f4)
![image](https://github.com/user-attachments/assets/9598be5d-38b5-4271-a5f1-ec2a6dcf1268)

LAB : 
![image](https://github.com/user-attachments/assets/39e998c8-f590-4bab-b554-ceba123be9f6)
![image](https://github.com/user-attachments/assets/e2a11083-e073-4300-9406-6b2afe506134)
![image](https://github.com/user-attachments/assets/d13b7892-8f38-4602-99d6-5faf13af2c05)
![image](https://github.com/user-attachments/assets/f5bfb0a1-4764-4974-aa06-5e390e470579)
![image](https://github.com/user-attachments/assets/f0106429-92e6-41d7-9ba1-5f11a3964907)
![image](https://github.com/user-attachments/assets/bdf75d46-ec5a-4afa-9cf6-545891b004bc)
![image](https://github.com/user-attachments/assets/74c0e225-51c2-4009-a64f-4a0f5a98630e)
![image](https://github.com/user-attachments/assets/5eb8906e-2731-4211-9c27-b01ddef8ce9d)
![image](https://github.com/user-attachments/assets/e3d695b3-4588-4f70-898d-68da985272f6)
![image](https://github.com/user-attachments/assets/6f555f55-0697-4c03-8bcb-0be46568978d)
![image](https://github.com/user-attachments/assets/68a32f53-2c6e-435a-9dc7-fc52f3edea5d)
![image](https://github.com/user-attachments/assets/43cbda36-323d-42d0-a62d-c5cc184edf01)
![image](https://github.com/user-attachments/assets/b0073bf7-968a-4989-ad28-c0b4d9812df9)
in local:
![image](https://github.com/user-attachments/assets/7ed88d3c-3507-41f1-8f39-67e09998078c)
![image](https://github.com/user-attachments/assets/a4d08fdb-a9e2-436b-9e71-bc8ec34a940f)
in local:
![image](https://github.com/user-attachments/assets/9a252956-fc28-4ee5-846f-c9143d0d72b0)
![image](https://github.com/user-attachments/assets/1a6cfd10-9fd6-46c7-9605-b1942e5d2416)
![image](https://github.com/user-attachments/assets/f16b6162-871a-4ff2-ace4-c1443120ad82)
![image](https://github.com/user-attachments/assets/3d0df485-6b67-450c-8ea1-838c2ff7c2f1)
![image](https://github.com/user-attachments/assets/dff0f57e-9229-4bde-990f-635c48a6acc3)

*** Batch Scoped Components ***
![image](https://github.com/user-attachments/assets/0af22b5a-6afd-45f8-b914-9cb42e5489b2)
![image](https://github.com/user-attachments/assets/ed835c45-fe6a-4bdb-acee-a161ab9e78ec)
![image](https://github.com/user-attachments/assets/4faefe8d-151f-47c9-be09-04ba4c3332f0)
LAB:
![image](https://github.com/user-attachments/assets/e4728025-52e3-4f6b-85e1-b78367099ee0)
![image](https://github.com/user-attachments/assets/fb22f4d5-aae6-480b-b252-c6d4c76582f0)
![image](https://github.com/user-attachments/assets/bd4eaf97-6d01-466a-9834-2864db57f1da)
![image](https://github.com/user-attachments/assets/be105e4d-d1a2-44af-be34-07e6bba3ba10)
![image](https://github.com/user-attachments/assets/4cce4f1f-537d-4754-8e00-4fc8b87b5cab)
![image](https://github.com/user-attachments/assets/d5c2cfb1-4a5a-47cf-8528-0bf2165f1008)
![image](https://github.com/user-attachments/assets/694e442c-2568-4346-a845-e04ddede941a)
![image](https://github.com/user-attachments/assets/7b9827f0-208a-4dde-9d71-6a0f92691809)
![image](https://github.com/user-attachments/assets/e1c10480-5b3e-470d-9311-dce2bb2f1d11)
![image](https://github.com/user-attachments/assets/4ae5e5fa-b0ad-483f-82ab-66dbd4fa9b15)
![image](https://github.com/user-attachments/assets/d77ea2d4-ece1-4209-86f6-498030b66ede)

command : java -jar target/billing-job-0.0.1-SNAPSHOT.jar input.file=billing-2023-01.csv output.file=staging/billing-report-2023-01.csv data.year=2023 data.month=1
![image](https://github.com/user-attachments/assets/1ec7e2d0-fabe-4a1f-8278-98dd7e94e1b5)

now with the second file: java -jar target/billing-job-0.0.1-SNAPSHOT.jar input.file=billing-2023-02.csv output.file=staging/billing-report-2023-02.csv data.year=2023 data.month=2
![image](https://github.com/user-attachments/assets/76fdda38-2268-4508-ae03-514cb3fef5d3)

as we can see all file are generated :
![image](https://github.com/user-attachments/assets/f0493296-8bad-4470-8a2b-255481089922)

![image](https://github.com/user-attachments/assets/6900a267-e458-46d3-8f21-efae44f39ab2)
in local:
![image](https://github.com/user-attachments/assets/124be052-fbce-4383-b406-2dc2f3fb4b68)
![image](https://github.com/user-attachments/assets/4aaad9dd-b7e6-444f-8b46-51b42f0c8b21)

















