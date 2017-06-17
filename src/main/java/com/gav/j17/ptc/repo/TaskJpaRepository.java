package com.gav.j17.ptc.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gav.j17.ptc.domain.HourglassTask;

/**
 * @author alex
 *
 */
@Repository
public interface TaskJpaRepository extends CrudRepository<HourglassTask, Long> {
	
	
	//public HourglassTask findByTaskId(Long taskId);
	
	//public String saveTask(HourglassTask task);	

}
