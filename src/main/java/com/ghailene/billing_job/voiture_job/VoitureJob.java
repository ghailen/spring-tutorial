package com.ghailene.billing_job.voiture_job;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.repository.JobRepository;

public class VoitureJob implements Job {


    private JobRepository jobRepository;

    public VoitureJob(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    @Override
    public String getName() {
        return "VoitureJob";
    }


     @Override
     public void execute(JobExecution execution) {
     System.out.println("processing voiture information");
     execution.setStatus(BatchStatus.COMPLETED);
     execution.setExitStatus(ExitStatus.COMPLETED);
     this.jobRepository.update(execution);
     }
}
