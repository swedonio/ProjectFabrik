package it.piacentino.bean;

import java.util.Date;

import lombok.Data;

@Data
public class Transazione {

	Long transactionId;
    String operationId;
    Date accountingDate;
    Date valueDate;
    String type;
    Long amount;
    String currency;
    String description;
	
}
