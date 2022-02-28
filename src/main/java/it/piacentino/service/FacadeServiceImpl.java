package it.piacentino.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.piacentino.bean.Bonifico;
import it.piacentino.bean.BonificoRequest;
import it.piacentino.bean.BonificoResponse;
import it.piacentino.bean.InfoMsg;
import it.piacentino.bean.Transazione;
import it.piacentino.bean.TransazioniResponse;
import it.piacentino.bean.Type;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FacadeServiceImpl implements FacadeService{

	@Value("${rootUrl}")
	private String rootUrl;

	@Value("${Auth-Schema}")
	private String authSchema;

	@Value("${Api-Key}")
	private String apiKey;

	@Autowired
	private ModelMapper modelMapper;

	private HttpEntity<String> getHeader() {


		HttpHeaders headers = new HttpHeaders();
		headers.set("Auth-Schema", authSchema);
		headers.set("Api-Key", apiKey);

		//Create a new HttpEntity
		final HttpEntity<String> entity = new HttpEntity<String>(headers);

		return entity;
	}

	public String getSaldo(String id) {

		String saldo=null;

		RestTemplate restTemplate = new RestTemplate();

		log.debug("rootUrl "+rootUrl);

		String fooResourceUrl = rootUrl+"/api/gbs/banking/v4.0/accounts/{id}/balance";
		fooResourceUrl = fooResourceUrl.replace("{id}", id);

		try {

			ResponseEntity<Map> response = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, getHeader(), Map.class);
			log.info("Response status "+response.getStatusCode());
			log.info(response.getBody().toString());
			HashMap payLoad = (HashMap) response.getBody().get("payload");
			Double avaibleBalance = (Double) payLoad.get("availableBalance");
			saldo=String.valueOf(avaibleBalance);
		}  catch (Exception e) {
			log.error("Errore "+e.getMessage());
		}        

		return saldo;
	}

	@Override
	public InfoMsg makeBonifico(Bonifico bean) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		String jsonString ="{\"creditor\":{\"name\":\"John Doe\",\"account\":{\"accountCode\":\"IT23A0336844430152923804660\",\"bicCode\":\"SELBIT2BXXX\"},\"address\":{\"address\":null,\"city\":null,\"countryCode\":null}},\"executionDate\":\"2019-04-01\",\"uri\":\"REMITTANCE_INFORMATION\",\"description\":\"Paymentinvoice75/2017\",\"amount\":800,\"currency\":\"EUR\",\"isUrgent\":false,\"isInstant\":false,\"feeType\":\"SHA\",\"feeAccountId\":\"45685475\",\"taxRelief\":{\"taxReliefId\":\"L449\",\"isCondoUpgrade\":false,\"creditorFiscalCode\":\"56258745832\",\"beneficiaryType\":\"NATURAL_PERSON\",\"naturalPersonBeneficiary\":{\"fiscalCode1\":\"MRLFNC81L04A859L\",\"fiscalCode2\":null,\"fiscalCode3\":null,\"fiscalCode4\":null,\"fiscalCode5\":null},\"legalPersonBeneficiary\":{\"fiscalCode\":null,\"legalRepresentativeFiscalCode\":null}}}";

		ObjectMapper objectMapper = new ObjectMapper();
		BonificoRequest bonificoReq = null;
		try {
			bonificoReq = objectMapper.readValue(jsonString, BonificoRequest.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			String msgErr = e.getMessage();
			log.error(msgErr);
			return new InfoMsg("86", msgErr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			String msgErr = e.getMessage();
			log.error(msgErr);
			return new InfoMsg("85", msgErr);
		}	

		bonificoReq.setFeeaccountid(String.valueOf(bean.getAccountId()));
		bonificoReq.setAmount(bean.getAmount());
		bonificoReq.setCurrency(bean.getCurrency());
		bonificoReq.setDescription(bean.getDescription());

		//controllo duplicato
		try {
			bonificoReq.setExecutiondate(sdf.format(bean.getExecutionDate()));
		} catch (Exception e1) {
			String msgErr = "Data non nel formato YYYY-MM-DD "+bean.getExecutionDate();
			log.error(msgErr);
			return new InfoMsg("87", msgErr);
		}
		bonificoReq.getCreditor().setName(bean.getReceiverName());

		RestTemplate restTemplate = new RestTemplate();

		log.debug("rootUrl "+rootUrl);

		String fooResourceUrl = rootUrl+"/api/gbs/banking/v4.0/accounts/{id}/payments/money-transfers";
		fooResourceUrl = fooResourceUrl.replace("{id}", bean.getAccountId()+"");

		String croNumber = null;

		try {
			HttpHeaders headers = getHeader().getHeaders();

			HttpEntity<Object> requestEntity = new HttpEntity<>(bonificoReq, headers);
			ResponseEntity<BonificoResponse> response = 
					restTemplate.exchange(fooResourceUrl, HttpMethod.POST, requestEntity, 
							BonificoResponse.class);

			log.info("Response status "+response);
			croNumber = response.getBody().getCro();

		}  catch (HttpClientErrorException e) {
			log.error("Errore "+e.getResponseBodyAsString());

			try {
				JSONObject jsonObject = new JSONObject(e.getResponseBodyAsString());
				JSONArray jsonArray = jsonObject.getJSONArray("errors");
				if (jsonArray != null) {
					JSONObject jsonObj = jsonArray.getJSONObject(0);
					String code = jsonObj.getString("code");
					String descr = jsonObj.getString("description");
					return new InfoMsg(code, descr);
				}
			} catch (JSONException err) {
				String msgErr = err.toString();
				log.error(msgErr);
				return new InfoMsg("88", msgErr);
			}


		} catch (Exception e) {
			String msgErr = "Errore Generico: "+e.getMessage();
			log.error(msgErr);
			return new InfoMsg("99", msgErr);
		}        

		return new InfoMsg("00", croNumber);
	}

	@Override
	public TransazioniResponse listTransazioni(String accountiId, String start, String end) {		

		TransazioniResponse risposta = new TransazioniResponse();

		RestTemplate restTemplate = new RestTemplate();

		log.debug("rootUrl "+rootUrl);

		String fooResourceUrl = rootUrl+"/api/gbs/banking/v4.0/accounts/{id}/transactions?fromAccountingDate={fromDate}&toAccountingDate={toDate}";
		fooResourceUrl = fooResourceUrl.replace("{id}", accountiId);
		fooResourceUrl = fooResourceUrl.replace("{fromDate}", start);
		fooResourceUrl = fooResourceUrl.replace("{toDate}", end);

		try {

			ResponseEntity<Map> response = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, getHeader(),  Map.class );
			log.info("Response status "+response.getStatusCode());
			String status = (String)response.getBody().get("status");
			if(!"OK".equals(status)) {
				String errMsg = "Errore con status "+status;
				log.error(errMsg);
				risposta.setInfo(new InfoMsg("87", errMsg));
				return risposta;
			}
			LinkedHashMap<String,List<LinkedHashMap>> body = (LinkedHashMap<String,List<LinkedHashMap>>)response.getBody().get("payload");
			log.info(body);
			List<LinkedHashMap> lista =body.get("list");

			List<Transazione> listTrans = new ArrayList<Transazione>();

			listTrans = lista.stream()
					.map(source -> convertToDto(source))
					.collect(Collectors.toList());

			risposta.setLista(listTrans);
			risposta.setInfo(new InfoMsg("00",response.getStatusCode().toString()));
		}  catch (HttpClientErrorException e) {

			log.error("Errore "+e.getResponseBodyAsString());

			try {
				JSONObject jsonObject = new JSONObject(e.getResponseBodyAsString());
				JSONArray jsonArray = jsonObject.getJSONArray("errors");
				if(jsonArray != null) {
					JSONObject jsonObj = jsonArray.getJSONObject(0);
					String code=jsonObj.getString("code");
					String descr=jsonObj.getString("description");
					risposta.setInfo(new InfoMsg(code, descr));
					return risposta;
				}
			}catch (JSONException err){
				log.debug("Error", err.toString());
				risposta.setInfo(new InfoMsg("88", err.getMessage()));
				return risposta;
			}

		} catch (Exception e) {
			log.error("Errore "+e.getMessage());
			risposta.setInfo(new InfoMsg("89", e.getMessage()));
			return risposta;
		}        

		return risposta;
	}

	private Transazione convertToDto(LinkedHashMap t)
	{
		Transazione transOut = null;

		if (t != null)
		{
			transOut =  modelMapper.map(t, Transazione.class);

			Type tipo = modelMapper.map(t.get("type"), Type.class);

			transOut.setType(tipo.getValue());
		}

		return transOut;
	}

}
