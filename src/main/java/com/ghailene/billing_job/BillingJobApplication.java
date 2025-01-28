package com.ghailene.billing_job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableBatchProcessing
public class BillingJobApplication implements CommandLineRunner{


	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(BillingJobApplication.class, args);

	}

	/*@Bean
	public CommandLineRunner commandLineRunner(JobLauncher jobLauncher, JobRegistry jobRegistry) {
		return args -> {
			if (args.length < 1) {
				throw new IllegalArgumentException("Veuillez fournir un nom de job à exécuter");
			}

			String jobName = args[0];
			Job job = jobRegistry.getJob(jobName);

			JobParameters jobParameters = new JobParametersBuilder()
					.addLong("time", System.currentTimeMillis())
					.toJobParameters();

			jobLauncher.run(job, jobParameters);
		};
	}*/

	/*@Override
	public void run(String... args) throws Exception {
		// Parse arguments passed to the program
		if (args.length == 0) {
			throw new IllegalArgumentException("No job parameters provided!");
		}

		// Build JobParameters from command-line arguments
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		for (String arg : args) {
			String[] keyValue = arg.split("=");
			if (keyValue.length == 2) {
				jobParametersBuilder.addString(keyValue[0], keyValue[1]);
			} else {
				throw new IllegalArgumentException("Invalid parameter: " + arg);
			}
		}

		// Launch the job with the parsed parameters
		JobExecution jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());
		System.out.println("Job Status: " + jobExecution.getStatus());

	}*/
	@Override
	public void run(String... args) throws Exception {
		if (args.length == 0) {
			throw new IllegalArgumentException("No job name provided!");
		}

		// Extract job name from arguments
		String jobName = args[0]; // The first argument is the job name

		// Attempt to get the job bean by name
		Job job;
		try {
			job = applicationContext.getBean(jobName, Job.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("Job not found with name: " + jobName, e);
		}

		// Build job parameters dynamically
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		for (int i = 1; i < args.length; i++) { // Remaining args are key=value pairs
			String[] keyValue = args[i].split("=");
			if (keyValue.length == 2) {
				jobParametersBuilder.addString(keyValue[0], keyValue[1]);
			} else {
				throw new IllegalArgumentException("Invalid parameter: " + args[i]);
			}
		}

		// Launch the job
		JobExecution jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());
		System.out.println("Job " + jobName + " executed with status: " + jobExecution.getStatus());
	}
}
