package it.piacentino;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.piacentino.bean.Bonifico;
import it.piacentino.bean.InfoMsg;
import it.piacentino.bean.Transazione;
import it.piacentino.bean.TransazioniResponse;
import it.piacentino.service.FacadeService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "operazioni")
public class OperazioniController  {

//	private static final Logger logger = LoggerFactory.getLogger(OperazioniController.class);

	@Autowired
	private FacadeService facadeService;

    @GetMapping(value = "/saldo/{id}", produces = "application/json")
    public ResponseEntity<String> getSaldo(@PathVariable("id") String id) throws Exception {
    	
    	log.info("****** Otteniamo il saldo per l'accountId " + id + " *******");
		
    	if(id == null || id.trim().length()==0) {
    		return new ResponseEntity<String>(String.format("Il valore dell'accountId: %s è vuoto", id), HttpStatus.NO_CONTENT);
    	}
    	
    	try {
			Long.parseLong(id);
		} catch (NumberFormatException e) {
			return new ResponseEntity<String>(String.format("Il valore dell'accountId: %s non è numerico", id), HttpStatus.BAD_REQUEST);
		}
    	
		String saldo = facadeService.getSaldo(id);
		
		if (saldo == null)
		{
			String ErrMsg = String.format("L'accountId  %s non è stato trovato!", id);
			
			log.warn(ErrMsg);
			
			return new ResponseEntity<String>(ErrMsg, HttpStatus.NOT_FOUND);
		}
		 
		return new ResponseEntity<String>(saldo, HttpStatus.OK);
		
    }

	@PostMapping(value = "/bonifico")
	public ResponseEntity<?> bonifico(@Valid @RequestBody Bonifico bonifico, BindingResult bindingResult)
		throws Exception
	{
		log.info("Eseguiamo il bonifico " + bonifico.getDescription());
		
		//controllo validità dati articolo
		if (bindingResult.hasErrors())
		{
			String MsgErr = "Errore di validazione: "+bindingResult.getFieldError().getField()+" - "+bindingResult.getFieldError().getDefaultMessage();
			
			return new ResponseEntity<String>(MsgErr, HttpStatus.BAD_REQUEST);
		}
		
		InfoMsg esito = facadeService.makeBonifico(bonifico);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		HttpStatus hs = null;
		
		if (esito == null ) {
			esito = new InfoMsg("99", "Errore Generico");
		} 

		responseNode.put("code", esito.getCode());
		responseNode.put("message", esito.getMessage());
		
		if("00".equals(esito.getCode())){
			hs = HttpStatus.CREATED;
		}else {
			hs = HttpStatus.NOT_ACCEPTABLE;
		}
		
		return new ResponseEntity<>(responseNode, new HttpHeaders(), hs);

	}

	
	@GetMapping(value = "/transazioni/{accountId}")
	public ResponseEntity<TransazioniResponse> transazioni(@PathVariable int accountId, @RequestParam(name = "fromDate") String fromDate, @RequestParam(name = "toDate") String toDate)
		throws Exception
	{
		log.info("Richeista operazioni per accountId " + accountId +" per le date "+fromDate+ " - "+toDate);
		
    	if(accountId==0) {
    		TransazioniResponse response= new TransazioniResponse();
    		response.setInfo(new InfoMsg("99", String.format("Il valore dell'accountId: %s è vuoto", accountId)));
    		return new ResponseEntity<TransazioniResponse>(response, HttpStatus.NO_CONTENT);
    	}
    	
    	Date startDate = null;
		Date endDate = null;
    	
    	try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			startDate = sdf.parse(fromDate);
			endDate = sdf.parse(toDate);
		} catch (ParseException e) {
			TransazioniResponse response= new TransazioniResponse();
			response.setInfo(new InfoMsg("98", String.format("Il valore delle date non corretto %s - %s", fromDate, toDate)));
    		return new ResponseEntity<TransazioniResponse>(response, HttpStatus.BAD_REQUEST);
		}
    	
    	if (startDate.after(endDate)) {
    		TransazioniResponse response= new TransazioniResponse();
    		response.setInfo(new InfoMsg("97", String.format("Intervallo non corretto delle date %s - %s", fromDate, toDate)));
    		return new ResponseEntity<TransazioniResponse>(response, HttpStatus.BAD_REQUEST);
    	}
		
		TransazioniResponse listaTransazioni = facadeService.listTransazioni(String.valueOf(accountId),fromDate,toDate);
		
		return new ResponseEntity<>(listaTransazioni, new HttpHeaders(), HttpStatus.OK);

	}
}
	

