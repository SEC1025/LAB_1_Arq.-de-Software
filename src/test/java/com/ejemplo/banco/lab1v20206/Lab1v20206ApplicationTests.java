package com.ejemplo.banco.lab1v20206;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class Lab1v20206ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void health() throws Exception {
		mockMvc.perform(get("/health"))
				.andExpect(status().isOk())
				.andExpect(content().string("HEALTH CHECK OK!"));
	}

	@Test
	void version() throws Exception {
		mockMvc.perform(get("/version"))
				.andExpect(status().isOk())
				.andExpect(content().string("The actual version is 1.0.0"));
	}

	@Test
	void createAndListCustomers() throws Exception {
		createCustomer("ACC-100", 1500.0);

		mockMvc.perform(get("/api/customers"))
				.andExpect(status().isOk());

		MvcResult created = createCustomer("ACC-101", 800.0);
		long id = objectMapper.readTree(created.getResponse().getContentAsString()).get("id").asLong();

		mockMvc.perform(get("/api/customers/" + id))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"accountNumber\":\"ACC-101\",\"balance\":800.0}"));
	}

	@Test
	void transferUpdatesBalancesAndHistory() throws Exception {
		createCustomer("ACC-200", 1000.0);
		createCustomer("ACC-201", 50.0);

		String transfer = """
				{"senderAccountNumber":"ACC-200","receiverAccountNumber":"ACC-201","amount":200.0}
				""";

		mockMvc.perform(post("/api/transactions")
						.contentType(MediaType.APPLICATION_JSON)
						.content(transfer))
				.andExpect(status().isOk());

		MvcResult history = mockMvc.perform(get("/api/transactions/ACC-200"))
				.andExpect(status().isOk())
				.andReturn();

		JsonNode transactions = objectMapper.readTree(history.getResponse().getContentAsString());
		org.junit.jupiter.api.Assertions.assertFalse(transactions.isEmpty());
	}

	@Test
	void transferRejectsMissingAccountsAndInsufficientBalance() throws Exception {
		mockMvc.perform(post("/api/transactions")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"amount\":10.0}"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("cannot be null")));

		createCustomer("ACC-300", 20.0);
		createCustomer("ACC-301", 5.0);

		mockMvc.perform(post("/api/transactions")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"senderAccountNumber":"ACC-300","receiverAccountNumber":"ACC-301","amount":999.0}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("not enough")));
	}

	private MvcResult createCustomer(String accountNumber, double balance) throws Exception {
		String body = """
				{"firstName":"Ana","lastName":"Perez","accountNumber":"%s","balance":%s}
				""".formatted(accountNumber, balance);
		return mockMvc.perform(post("/api/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body))
				.andExpect(status().isOk())
				.andReturn();
	}
}
