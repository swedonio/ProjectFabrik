package it.piacentino;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ContextConfiguration(classes = ProjectFabrikApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class ProjectFabrikApplicationTests {

	
    private MockMvc mvc;
	
	@Autowired
	private WebApplicationContext wac;
    
	@BeforeEach
	public void setup() throws JSONException, IOException
	{
		mvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.build();	
	}
	
	@Test
	public void saldoAccountIdNotFound() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/operazioni/saldo/8008490000021")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print());
	}


	@Test
	public void saldoAccountIdFound() throws Exception
	{
		ResultActions ra= mvc.perform(MockMvcRequestBuilders.get("/operazioni/saldo/14537780").accept(MediaType.APPLICATION_JSON));
				ra.andExpect(status().isOk());
				ra.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
				ra.andExpect(jsonPath("$").exists());//("DROGHERIA ALIMENTARE")) 
				ra.andExpect(jsonPath("$").value("8.4")); 
				ra.andDo(print());
	}

	@Test
	public void saldoAccountIdNotNumeric() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/operazioni/saldo/KKK")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print());
	}


	@Test
	public void saldoAccountIdEmptyRequest() throws Exception
	{
		ResultActions ra= mvc.perform(MockMvcRequestBuilders.get("/operazioni/saldo/ ").accept(MediaType.APPLICATION_JSON));
				ra.andExpect(status().isNoContent());
				ra.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
				ra.andDo(print());
	}
	
	String JsonData =  
			"{\r\n" + 
			"    \"accountId\": 14537780,\r\n" + 
			"    \"receiverName\": \"Piacentino\",\r\n" + 
			"    \"description\": \"Pagamento Quota\",\r\n" + 
			"    \"currency\": \"USD\",\r\n" + 
			"    \"amount\": 50000,\r\n" + 
			"    \"executionDate\": \"2022-01-24\"\r\n"+
			"}";
	
	@Test
	public void testInsBonificoKOMonthlyLimit() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.post("/operazioni/bonifico")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable())
				.andExpect(jsonPath("$.code").value("REQ014"))
				.andExpect(jsonPath("$.message").value("Invalid Amount: Monthly Limit Exceeding"))
				.andDo(print());
	}

	String JsonData2 =  
			"{\r\n" + 
			"    \"accountId\": 14537780,\r\n" + 
			"    \"receiverName\": \"Piacentino\",\r\n" + 
			"    \"description\": \"Pagamento Quota\",\r\n" + 
			"    \"currency\": \"USD\",\r\n" + 
			"    \"amount\": 500,\r\n" + 
			"    \"executionDate\": \"2022-02-24\"\r\n"+
			"}";
	
	@Test
	public void testInsBonificoKO() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.post("/operazioni/bonifico")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData2)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable())
				.andExpect(jsonPath("$.code").value("API000"))
				.andExpect(jsonPath("$.message").value("IbanBeneficiario è obbligatorio"))
				.andDo(print());
	}

	@Test
	public void testListaTransazioni() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/operazioni/transazioni/14537780?fromDate=2019-01-01&toDate=2019-02-01")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lista", hasSize(2)))
				.andDo(print());
	}	

	@Test
	public void testListaTransazioniVuota() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/operazioni/transazioni/14537780?fromDate=2019-01-01&toDate=2019-01-01")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.info.code").value("95"))
				.andDo(print());
	}	
}
