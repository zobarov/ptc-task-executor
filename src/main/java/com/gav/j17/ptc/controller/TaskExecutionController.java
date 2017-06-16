package com.gav.j17.ptc.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gav.j17.ptc.domain.TaskDTO;
import com.gav.j17.ptc.service.TaskExecutionService;
import com.gav.j17.ptc.util.Loggable;

import ch.qos.logback.classic.Logger;

/**
 * @author alex
 *
 */
@RestController
@RequestMapping("/task")
public class TaskExecutionController {
	@Loggable
	private Logger logger;
	@Autowired
	private TaskExecutionService taskExecuionService;
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody String submitTask(@RequestParam(name = "taskId", required = true) String taskId,
			 							   @RequestParam(name = "duration", required = true) Integer duration) {
		
		logger.info("Submitted task for id/durration...", taskId, duration);
		Future<String> future = taskExecuionService.executeTask(taskId, duration);
		
		String submittionAckn = "GOT";
		try {
			submittionAckn = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return submittionAckn;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody TaskDTO trackTask(@RequestParam(name = "taskId", required = true) String taskId) {
		TaskDTO resultTask = new TaskDTO();
		return resultTask;
	}
}
