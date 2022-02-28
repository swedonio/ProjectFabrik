package it.piacentino.bean;

import java.util.List;

import lombok.Data;

@Data
public class TransazioniResponse {
	
	private InfoMsg info;
	private List<Transazione> lista;
}
