package it.piacentino.bean;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data; 

@Data
public class BonificoResponse{
    public String moneyTransferId;
    public String status;
    public String direction;
    public Creditor creditor;
    public Debtor debtor;
    public String cro;
    public String uri;
    public String trn;
    public String description;
    public Date createdDatetime;
    public Date accountedDatetime;
    public String debtorValueDate;
    public String creditorValueDate;
    public Amount amount;
    public boolean isUrgent;
    public boolean isInstant;
    public String feeType;
    public String feeAccountId;
    public ArrayList<Fee> fees;
    public boolean hasTaxRelief;
    
    
    
}
