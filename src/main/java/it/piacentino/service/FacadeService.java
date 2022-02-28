package it.piacentino.service;

import it.piacentino.bean.Bonifico;
import it.piacentino.bean.InfoMsg;
import it.piacentino.bean.TransazioniResponse;

public interface FacadeService {

	public String getSaldo(String id);
	
	public InfoMsg makeBonifico(Bonifico bean);
	
	public TransazioniResponse listTransazioni(String accountiId, String start, String end);
}
