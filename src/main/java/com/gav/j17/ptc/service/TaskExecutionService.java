/**
 * 
 */
package com.gav.j17.ptc.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gav.j17.ptc.domain.TaskDTO;
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
	
	private ExecutorService executorService;
	
	@Async
	public Future<String> executeTask(String taskId, Integer duration) {
		Future<String> future
	       = executorService.submit(new Callable<String>() {
	         public String call() {
	             return "Submitted";
	         }});
		return future;
	}
	
	
	public TaskDTO checkTaskStatus(String taskId) {
		return new TaskDTO();
	}	

}
