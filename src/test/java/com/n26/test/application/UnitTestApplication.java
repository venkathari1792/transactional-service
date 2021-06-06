package com.n26.test.application;

import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.n26.controller.ApplicationCommonController;
import com.n26.exception.ApplicationException;
import com.n26.service.TransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest(ApplicationCommonController.class)
public class UnitTestApplication {

	@MockBean
	private TransactionService transactionService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void postTransactionsInvalidData() throws Exception {

		String json = "{\"amount\":\"10.33\",\"timestamp\":\"1622910978110\"}";
		MockHttpServletResponse response = null;
		try {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions")
					.accept(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			response = result.getResponse();
		} catch (ApplicationException ex) {
			assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
		}
	}

	@Test
	public void postTransactionsInvalidJson() throws Exception {

		String json = "{\"amount\":\"10.33\"timestamp\":\"1622910978110\"}";
		MockHttpServletResponse response = null;
		try {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions")
					.accept(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			response = result.getResponse();
		} catch (ApplicationException ex) {
			assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		}

	}

	@Test
	public void postTransactionsOldData() throws Exception {

		String json = "{\n" + "\"amount\": \"20.3\",\n" + "\"timestamp\": \"2021-06-05T13:07:11.012Z\"\n" + "}";
		MockHttpServletResponse response = null;
		try {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions")
					.accept(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			response = result.getResponse();
		} catch (ApplicationException ex) {
			assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
		}

	}

	@Test
	public void postTransactionsProperData() throws Exception {

		String json = "{\n" + "\"amount\": \"20.3\",\n" + "\"timestamp\": \"" + Instant.now().toString() + "\"\n" + "}";
		MockHttpServletResponse response = null;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions").accept(MediaType.APPLICATION_JSON)
				.content(json).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void getStatistics() throws Exception {

		MockHttpServletResponse response = null;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/statistics").accept(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void deleteTransactions() throws Exception {

		String json = "{}";
		MockHttpServletResponse response = null;
		try {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/transactions")
					.accept(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			response = result.getResponse();
		} catch (ApplicationException ex) {
			assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
		}
	}
}
