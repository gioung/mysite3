package com.cafe24.mysite.controller.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.config.web.TestWebConfig;
import com.cafe24.mysite.config.AppConfig;
import com.cafe24.mysite.repository.vo.GuestbookVo;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, TestWebConfig.class })
@WebAppConfiguration
public class GuestbookControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	// Mock객체란?

	/*
	 * @Test public void testDIGuestbookService() { assertNotNull(guestbookService);
	 * //assert 단언하다. }
	 */

	@Test
	public void testFetchGuestbookList() { // url이 여기서 결정 날 수도 있다.
		try {
			ResultActions resultActions = mockMvc
					.perform(get("/api/guestbook/list/{no}", 1L).contentType(MediaType.APPLICATION_JSON));
			resultActions.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.result", is("success")))
					.andExpect(jsonPath("$.data", hasSize(2))).andExpect(jsonPath("$.data[0].no", is(1)))
					.andExpect(jsonPath("$.data[0].name", is("user1")))
					.andExpect(jsonPath("$.data[0].contents", is("test1")));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertGuestbook() throws Exception {
		GuestbookVo vo = new GuestbookVo(); 
		vo.setName("user1");
		vo.setPassword("1234");
		vo.setContents("test1");
			
		// MailSender mailSender = Mockito.mock(MailSender.class);
		// Mockito.when(mailSenderMock.sendMail("", "")).thenReturn(true);
		// isSuccess = mailSenderMock.sendMail("");
		
		ResultActions resultActions =
		mockMvc
		.perform(post("/api/guestbook/add").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(vo)));

		resultActions
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.result", is("success")))
		.andExpect(jsonPath("$.data.name", is(vo.getName())))
		.andExpect(jsonPath("$.data.contents", is(vo.getContents())));
	}

	@Test
	public void testDeleteGuestbook() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("no", 1L);
		map.put("password", "1234");
		
		ResultActions resultActions = mockMvc.perform(delete("/api/guestbook/delete")
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(map)));
		resultActions.andExpect(status().isOk()).andDo(print())
		.andExpect(jsonPath("$.data", is(map.get("no"))));
	}

}
