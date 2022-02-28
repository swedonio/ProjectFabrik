package it.piacentino.bean;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BonificoRequest {

    private Creditor creditor;
    @JsonProperty("executionDate")
    private String executiondate;
    private String uri;
    private String description;
    private int amount;
    private String currency;
    @JsonProperty("isUrgent")
    private boolean isurgent;
    @JsonProperty("isInstant")
    private boolean isinstant;
    @JsonProperty("feeType")
    private String feetype;
    @JsonProperty("feeAccountId")
    private String feeaccountid;
    @JsonProperty("taxRelief")
    private Taxrelief taxrelief;
    
    public void setCreditor(Creditor creditor) {
         this.creditor = creditor;
     }
     public Creditor getCreditor() {
         return creditor;
     }

    public void setExecutiondate(String executiondate) {
         this.executiondate = executiondate;
     }
     public String getExecutiondate() {
         return executiondate;
     }

    public void setUri(String uri) {
         this.uri = uri;
     }
     public String getUri() {
         return uri;
     }

    public void setDescription(String description) {
         this.description = description;
     }
     public String getDescription() {
         return description;
     }

    public void setAmount(int amount) {
         this.amount = amount;
     }
     public int getAmount() {
         return amount;
     }

    public void setCurrency(String currency) {
         this.currency = currency;
     }
     public String getCurrency() {
         return currency;
     }

    public void setIsurgent(boolean isurgent) {
         this.isurgent = isurgent;
     }
     public boolean getIsurgent() {
         return isurgent;
     }

    public void setIsinstant(boolean isinstant) {
         this.isinstant = isinstant;
     }
     public boolean getIsinstant() {
         return isinstant;
     }

    public void setFeetype(String feetype) {
         this.feetype = feetype;
     }
     public String getFeetype() {
         return feetype;
     }

    public void setFeeaccountid(String feeaccountid) {
         this.feeaccountid = feeaccountid;
     }
     public String getFeeaccountid() {
         return feeaccountid;
     }

    public void setTaxrelief(Taxrelief taxrelief) {
         this.taxrelief = taxrelief;
     }
     public Taxrelief getTaxrelief() {
         return taxrelief;
     }

}