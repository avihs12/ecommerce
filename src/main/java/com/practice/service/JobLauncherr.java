//package com.practice.service;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JobLauncherr {
//
//
//	 	private final JobLauncher jobLauncher;
//	    private final Job importCustomerJob;
//
//	    @Autowired
//	    public JobLauncherr(JobLauncher jobLauncher, Job importCustomerJob) {
//	        this.jobLauncher = jobLauncher;
//	        this.importCustomerJob = importCustomerJob;
//	    }
//
//	    @Scheduled(cron="0 */5 * * * ?")
//	    public void run() throws Exception {
//	        JobParameters jobParameters = new JobParametersBuilder()
//	                .addLong("time", System.currentTimeMillis())
//	                .toJobParameters();
//	        
//	        jobLauncher.run(importCustomerJob, jobParameters);
//	    }
//}
//
