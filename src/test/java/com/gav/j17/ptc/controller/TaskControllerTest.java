package com.gav.j17.ptc.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Ignore;
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

import com.gav.j17.ptc.PtcTaskApplication;

/**
 * @author alex.gera
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtcTaskApplication.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:./application.test.properties")
public class TaskControllerTest {
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
	        mockMvc.perform(get("/task"))
	        	.andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testWrongAppNameOnly_notFound() throws Exception {
	        mockMvc.perform(get("/non-task"))
	        	.andExpect(status().isNotFound());
	 }

	 @Test
	 public void testNonTaskIdParamOnly_badRequest() throws Exception {
	        mockMvc.perform(get("/task")
	        		.param("non-task-id", "3"))
            	.andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testTaskIdParamOnly_badRequest() throws Exception {
	        mockMvc.perform(get("/task")
	        		.param("taskId", "3"))
            	.andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testTaskIdAndDurationParam_Ok() throws Exception {
	        mockMvc.perform(get("/task")
	        		.param("taskId", "3")
	        		.param("duration", "10"))
                 .andExpect(status().isOk());
	 }

	 @Test
	 public void testTaskIdAndNonDurationParam_badRequest() throws Exception {
	        mockMvc.perform(get("/task")
	        		.param("taskId", "3")
	        		.param("non-duration", "10"))
                 .andExpect(status().isBadRequest());
	 }

	 @Test
	 public void testTaskIdAndDurationAndExtraParam_Ok() throws Exception {
	        mockMvc.perform(get("/task")
	        		.param("taskId", "3")
	        		.param("duration", "10")
	        		.param("extra-param", "extra"))
                 .andExpect(status().isOk());
	 }
	 
	 /////////Content checks by example:
	 
	 @Test
	 @Ignore
	 public void testContent_ExampleOne() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "3")
	        		.param("stairwell", "17"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(contentType))
                 .andExpect(jsonPath("$.minimumStridesAmount", is(6)));
	 }
	 
	 @Test
	 @Ignore
	 public void testContent_ExampleTwo() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "3")
	        		.param("stairwell", "17,17"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(contentType))
                 .andExpect(jsonPath("$.minimumStridesAmount", is(14)));
	 }
	 
	 @Test
	 @Ignore
	 public void testContent_ExampleThree() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "2")
	        		.param("stairwell", "4,9,8,11,7,20,14"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(contentType))
                 .andExpect(jsonPath("$.minimumStridesAmount", is(50)));
	 }
	 
	 /////////Content checks in invalid requests:
	 @Test
	 @Ignore
	 public void testContent_InvalidZeroSps() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "0")
	        		.param("stairwell", "4,9,8,11,7,20,14"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(contentType))
                 .andExpect(jsonPath("$.minimumStridesAmount", is(0)));
	 }
	 
	 @Test
	 @Ignore
	 public void testContent_InvalidNegativeSps() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "-3")
	        		.param("stairwell", "4,9,8,11,7,20,14"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(contentType))
              .andExpect(jsonPath("$.minimumStridesAmount", is(0)));
	 }
	 
	 @Test
	 @Ignore
	 public void testContent_InvalidZeroInStairwell() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "2")
	        		.param("stairwell", "4,9,8,0,7,20,14"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(contentType))
              .andExpect(jsonPath("$.minimumStridesAmount", is(0)));
	 }
	 
	 @Test
	 @Ignore
	 public void testContent_InvalidNegativeInStairwell() throws Exception {
		mockMvc.perform(get("/strider")
	        		.param("sps", "-3")
	        		.param("stairwell", "4,9,8,-11,7,20,14"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(contentType))
              .andExpect(jsonPath("$.minimumStridesAmount", is(0)));
	 }
}
