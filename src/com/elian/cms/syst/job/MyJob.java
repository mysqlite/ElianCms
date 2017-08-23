package com.elian.cms.syst.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("Quartz Test");
	}
}