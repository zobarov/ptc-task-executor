package com.gav.j17.ptc.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author alex
 *
 */
@Entity
public class HourglassTask {	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long taskId;
	
	private String taskName;
	
	private Integer duration;
	private Integer finalDuration;
	private String executionStatus;
	
	public HourglassTask(){		
	}
	
	public HourglassTask(String taskName , Integer duration) {
		this.taskName = taskName;
		this.duration = duration;
	}

	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer currentDuration) {
		this.duration = currentDuration;
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
