package com.gav.j17.ptc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gav.j17.ptc.domain.HourglassTask;
import com.gav.j17.ptc.domain.TaskSubmitionAcknowledge;
import com.gav.j17.ptc.service.TaskExecutionService;
import com.gav.j17.ptc.util.Loggable;

import ch.qos.logback.classic.Logger;

/**
 * @author alex
 *
 */
@RestController
@RequestMapping("/task")
public class HourglassTaskController {
	@Loggable
	private Logger logger;
	@Autowired
	private TaskExecutionService taskExecuionService;
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody TaskSubmitionAcknowledge submitTask(@RequestParam(name = "taskId", required = true) String taskId,
			 							   @RequestParam(name = "duration", required = true) Integer duration) {		
		logger.info("Submitted task for id/duration...", taskId, duration);
		TaskSubmitionAcknowledge ackn = new TaskSubmitionAcknowledge(taskId);
		String validationMsg = validateSubmitionParams(taskId, duration);
		if(validationMsg != null) {
			ackn.setStatus("NOT_VALID");
			ackn.setErrorMsg(validationMsg);
			return ackn;
		}
		
		if(taskExecuionService.isTaskSubmitted(taskId)) {
			ackn.setStatus("ALREADY_SUBMITTED");
			ackn.setErrorMsg("Task with id [" + taskId + "] has been already submitted.");
			return ackn;
		}		
		
		ackn.setStatus("OK_FOR_EXECUTION");
		taskExecuionService.executeTask(taskId, duration);		
		return ackn;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody HourglassTask trackTask(@RequestParam(name = "taskId", required = true) String taskId) {		
		HourglassTask resultTask = taskExecuionService.checkTaskStatus(taskId);
		return resultTask;
	}
	
	private String validateSubmitionParams(String taskId, Integer duration) {
		if(duration <= 0) {
			return "Duration is 0 or negative.";
		}
		return null;
	}
}
