package com.ghailene.billing_job;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
/**
 @SpringBootTest
 @ExtendWith(OutputCaptureExtension.class)
 class BillingJobApplicationV2Tests {

 @Autowired
 private Job job;

 @Autowired
 private JobLauncher jobLauncher;

 **/
/* test without parameters
    @Test
    void testJobExecution(CapturedOutput output) throws Exception {
        // given
        JobParameters jobParameters = new JobParameters();

        // when
        JobExecution jobExecution = this.jobLauncher.run(this.job, jobParameters);

        // then
        Assertions.assertTrue(output.getOut().contains("processing billing information"));
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }*/

/** @Test
void testJobExecution(CapturedOutput output) throws Exception {
// given
JobParameters jobParameters = new JobParametersBuilder()
.addString("input.file", "/some/input/file")
.addString("file.format", "csv", false)
.toJobParameters();
// when
JobExecution jobExecution = this.jobLauncher.run(this.job, jobParameters);
// then
Assertions.assertTrue(output.getOut().contains("processing billing information from file /some/input/file"));
Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
}
}**/
