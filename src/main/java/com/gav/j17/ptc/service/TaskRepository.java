
package com.gav.j17.ptc.service;

//import org.springframework.data.repository.CrudRepository;

import com.gav.j17.ptc.domain.TaskDTO;

/**
 * @author alex
 *
 */

public interface TaskRepository {
//extends CrudRepository<TaskDTO, String> {
	
	public TaskDTO findByTaskId(String taskId);
	
	public String saveTask(TaskDTO task);	

}
