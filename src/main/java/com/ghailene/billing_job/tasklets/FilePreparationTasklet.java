package com.ghailene.billing_job.tasklets;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FilePreparationTasklet implements Tasklet {


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        JobParameters jobParameters = contribution.getStepExecution().getJobParameters();
        String inputFile = jobParameters.getString("input.file");
        ClassPathResource resource = new ClassPathResource(inputFile);
        try (InputStream inputStream = resource.getInputStream()) {
            // Set the target directory (ensure the directory exists)
            Path targetDir = Paths.get("staging");
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }
            // Copy the InputStream to the target file
            Path target = targetDir.resolve(inputFile);
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
        }
        return RepeatStatus.FINISHED;
    }
}
