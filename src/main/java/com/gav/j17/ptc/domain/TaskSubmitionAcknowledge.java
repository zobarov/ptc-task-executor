/**
 * 
 */
package com.gav.j17.ptc.domain;

/**
 * @author alex
 *
 */
public class TaskSubmitionAcknowledge {
	private String taskName;
	private String status;
	private String errorMsg;
	
	public TaskSubmitionAcknowledge(String taskName) {
		this.taskName = taskName;
		this.errorMsg = "";
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getTaskName() {
		return taskName;
	}
}
