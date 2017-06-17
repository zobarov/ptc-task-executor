package com.gav.j17.ptc.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gav.j17.ptc.domain.HourglassTask;

/**
 * @author alex
 *
 */
@Repository
public interface TaskJpaRepository extends CrudRepository<HourglassTask, String> {

}
