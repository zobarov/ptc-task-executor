/**
 * 
 */
package com.gav.j17.ptc.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
	
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	@Async
	public Future<HourglassTask> executeTask(String taskId, Integer duration) {
		logger.debug("Submitting a task {}/{}.", taskId, duration);
		HourglassTask hTask = new HourglassTask(taskId, duration);
		taskPersister.save(hTask);		
		
		Future<HourglassTask> future
	       = executorService.submit(new Callable<HourglassTask>() {
	         public HourglassTask call() {
	             return hTask;
	         }});
		return future;
	}
	
	public boolean isTaskSubmitted(String taskId) {
		HourglassTask persistedTask = taskPersister.findOne(taskId);
		return persistedTask != null;		
	}
	
	
	public HourglassTask checkTaskStatus(String taskId) {
		logger.debug("Getting info about task {}.", taskId);
		HourglassTask persistedTask = taskPersister.findOne(taskId);
		if(persistedTask == null) {
			logger.warn("Checking unpersisted task with id [{}]", taskId);
			return new HourglassTask("NON-PERSISTED");
			
		}
		return persistedTask;
	}
}
