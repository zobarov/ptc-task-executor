/**
 * 
 */
package com.gav.j17.ptc.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gav.j17.ptc.domain.HourglassTask;
import com.gav.j17.ptc.repo.TaskJpaRepository;
import com.gav.j17.ptc.util.Loggable;

import ch.qos.logback.classic.Logger;

/**
 * @author alex
 *
 */
@Service
public class TaskExecutionService {
	@Loggable
	private Logger logger;

	@Autowired
	private TaskJpaRepository taskPersister;
	
	@Autowired
	private ExecutorService executorService;
	
	@Async
	public Future<String> executeTask(String taskId, Integer duration) {
		logger.debug("Getting to submit a task {}/{}.", taskId, duration);
		HourglassTask hTask = new HourglassTask(taskId, duration);
		taskPersister.save(hTask);
		
		
		Future<String> future
	       = executorService.submit(new Callable<String>() {
	         public String call() {
	             return "Submitted";
	         }});
		return future;
	}
	
	
	public HourglassTask checkTaskStatus(String taskId) {
		return new HourglassTask();
	}	

}
