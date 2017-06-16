package com.gav.j17.ptc.domain;

/**
 * @author alex
 *
 */

public class TaskDTO {	
	private String taskId;
	private Integer currentDuration;
	private Integer finalDuration;
	private String executionStatus;

	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Integer getCurrentDuration() {
		return currentDuration;
	}
	public void setCurrentDuration(Integer currentDuration) {
		this.currentDuration = currentDuration;
	}
	public Integer getFinalDuration() {
		return finalDuration;
	}
	public void setFinalDuration(Integer finalDuration) {
		this.finalDuration = finalDuration;
	}
	public String getExecutionStatus() {
		return executionStatus;
	}
	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}
}
