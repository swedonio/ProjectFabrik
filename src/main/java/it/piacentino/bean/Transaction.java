package it.piacentino.bean;

import lombok.Data;

@Data
public class Transaction{
    public String transactionId;
    public String operationId;
    public String accountingDate;
    public String valueDate;
    public Type type;
    public int amount;
    public String currency;
    public String description;
}
