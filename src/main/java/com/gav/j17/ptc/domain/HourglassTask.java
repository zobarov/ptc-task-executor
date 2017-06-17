package com.gav.j17.ptc.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author alex
 *
 */
@Entity
@Table(name="HORGLASS_TASK")
public class HourglassTask {	
	@Id
	private String taskName;	
	private Integer duration;

	private Integer actualDuration;
	private String executionStatus;
	
	public HourglassTask(){		
	}
	
	public HourglassTask(String taskName, Integer duration) {
		this.taskName = taskName;
		this.duration = duration;
		this.executionStatus = HorglassTaskStatus.NEW.name();
	}
	
	public HourglassTask(String status) {
		this.executionStatus = status;
	}
	
	public void markAsDone(Integer actualDuration) {
		this.actualDuration = actualDuration;
		this.executionStatus = HorglassTaskStatus.DONE.name();
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
	public Integer getActualDuration() {
		return actualDuration;
	}
	public void setActualDuration(Integer actDuration) {
		this.actualDuration = actDuration;
	}
	public String getExecutionStatus() {
		return executionStatus;
	}
	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}
}
