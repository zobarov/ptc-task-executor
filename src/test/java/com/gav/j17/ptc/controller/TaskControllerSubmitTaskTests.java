package com.gav.j17.ptc.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.gav.j17.ptc.HourglassTaskApplication;

/**
 * @author alex.gera
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HourglassTaskApplication.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:./application.test.properties")
public class TaskControllerSubmitTaskTests {
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
	
	/////////Params acceptance checks:

	 @Test
	 public void testAppNameOnly_badRequest() throws Exception {
	        mockMvc.perform(post("/task"))
	        	.andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testWrongAppNameOnly_notFound() throws Exception {
	        mockMvc.perform(post("/non-task"))
	        	.andExpect(status().isNotFound());
	 }

	 @Test
	 public void testNonTaskIdParamOnly_badRequest() throws Exception {
	        mockMvc.perform(post("/task")
	        		.param("non-task-id", "3"))
            	.andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testTaskIdParamOnly_badRequest() throws Exception {
	        mockMvc.perform(post("/task")
	        		.param("taskId", "3"))
            	.andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testTaskIdAndDurationParam_Ok() throws Exception {
	        mockMvc.perform(post("/task")
	        		.param("taskId", "3")
	        		.param("duration", "10"))
                 .andExpect(status().isOk());
	 }

	 @Test
	 public void testTaskIdAndNonDurationParam_badRequest() throws Exception {
	        mockMvc.perform(post("/task")
	        		.param("taskId", "3")
	        		.param("non-duration", "10"))
                 .andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testTaskIdAndDurationAndExtraParam_Ok() throws Exception {
	        mockMvc.perform(post("/task")
	        		.param("taskId", "3")
	        		.param("duration", "10")
	        		.param("extra-param", "extra"))
                 .andExpect(status().isOk());
	 }
	 
	 /////////Content checks:
	 
	 @Test
	 public void testContent_OkForExecution() throws Exception {
		mockMvc.perform(post("/task")
	        		.param("taskId", "4")
	        		.param("duration", "20"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(contentType))
                 .andExpect(jsonPath("$.taskName", is("4")))
                 .andExpect(jsonPath("$.status", is("OK_FOR_EXECUTION")));
	 }
	 
	 @Test
	 public void testContent_NotValidForExecution_NegativeDuration() throws Exception {
		mockMvc.perform(post("/task")
	        		.param("taskId", "5")
	        		.param("duration", "-20"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(contentType))
                 .andExpect(jsonPath("$.taskName", is("5")))
                 .andExpect(jsonPath("$.errorMsg", containsString("Duration")))
                 .andExpect(jsonPath("$.status", is("NOT_VALID")));
	 }
}
