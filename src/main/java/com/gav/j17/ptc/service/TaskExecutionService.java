package com.gav.j17.ptc.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gav.j17.ptc.domain.HourglassTask;
import com.gav.j17.ptc.domain.HourglassTaskStatus;
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
	private Map<String, Future<HourglassTask>> callbacksContainer = new HashMap<String, Future<HourglassTask>>();
	
	@Async
	public Future<HourglassTask> executeTask(String taskId, Integer duration) {
		logger.debug("Submitting a task {}/{}.", taskId, duration);
		Instant startInstant = Instant.now();
		
		Future<HourglassTask> future = executorService.submit(() -> {
			HourglassTask hTask = new HourglassTask(taskId, duration);
			hTask.setExecutionStatus(HourglassTaskStatus.IN_PROCESS);
			taskPersister.save(hTask);
			TimeUnit.SECONDS.sleep(duration);
			//updating...
			Instant finishInstant = Instant.now();
			Duration actualDuration = Duration.between(startInstant, finishInstant);
			hTask.markAsDone(actualDuration.getSeconds());
		    return hTask;
		});
		
		callbacksContainer.put(taskId, future);
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
		
		Future<HourglassTask> taskFuture = callbacksContainer.get(taskId);
		if(taskFuture.isDone()) {
			logger.debug("Execution is done for taskId [{}].", taskId);
			try {
				return taskFuture.get();
			} catch (InterruptedException ie) {
				logger.error("Execution is interrupted for taskId [{}] with msg {}.", taskId, ie.getMessage());
				persistedTask.setExecutionStatus(HourglassTaskStatus.ERROR);
			} catch (ExecutionException ee) {
				logger.error("Execution with exception for taskId [{}] with msg {}.", taskId, ee.getMessage());
				persistedTask.setExecutionStatus(HourglassTaskStatus.ERROR);
			}
		}
		logger.info("Execution task [{}] is NOT done yet.", taskId);
		return persistedTask;
	}
		
		
}
