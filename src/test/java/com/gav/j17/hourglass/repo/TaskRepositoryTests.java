package com.gav.j17.hourglass.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gav.j17.ptc.HourglassTaskApplication;
import com.gav.j17.ptc.domain.HourglassTaskStatus;
import com.gav.j17.ptc.domain.HourglassTask;
import com.gav.j17.ptc.repo.TaskJpaRepository;

/**
 * @author alex
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HourglassTaskApplication.class)
@TestPropertySource(locations = "classpath:./application.test.properties")
public class TaskRepositoryTests {
	
	@Autowired
	private TaskJpaRepository taskRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void testPersist_SaveAndGet() throws Exception {
		//given:
		HourglassTask task = new HourglassTask("testTask", 1);
		taskRepository.save(task);
		//when:
		HourglassTask persistedTask = taskRepository.findOne("testTask");
		//then:
		assertEquals("testTask", persistedTask.getTaskName());
		assertEquals(Integer.valueOf(1),  persistedTask.getDuration());
		assertNull(persistedTask.getActualDuration());
		assertEquals(HourglassTaskStatus.NEW.name(), persistedTask.getExecutionStatus());

		taskRepository.delete(persistedTask.getTaskName());
	}
	
	@Test
	@Transactional
	public void testPersist_UpdateFinalDuration() throws Exception {
		//given:
		HourglassTask task = new HourglassTask("testTask2", 10);
		taskRepository.save(task);
		//when:
		HourglassTask persistedTask = taskRepository.findOne("testTask2");
		persistedTask.markAsDone(200L);
		HourglassTask updatedTask = taskRepository.save(persistedTask);
		//then:
		assertEquals("testTask2", updatedTask.getTaskName());
		assertEquals(Integer.valueOf(10),  updatedTask.getDuration());
		assertEquals(Long.valueOf(200L),  updatedTask.getActualDuration());
		assertEquals(HourglassTaskStatus.DONE.name(), updatedTask.getExecutionStatus());

		taskRepository.delete(persistedTask.getTaskName());
	}
}
