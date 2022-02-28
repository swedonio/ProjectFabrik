/* Copyright 2022 freecodeformat.com */
package it.piacentino.bean;
import com.fasterxml.jackson.annotation.JsonProperty;
/* Time: 2022-02-24 16:49:19 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class Legalpersonbeneficiary {

    @JsonProperty("fiscalCode")
    private String fiscalcode;
    @JsonProperty("legalRepresentativeFiscalCode")
    private String legalrepresentativefiscalcode;
    public void setFiscalcode(String fiscalcode) {
         this.fiscalcode = fiscalcode;
     }
     public String getFiscalcode() {
         return fiscalcode;
     }

    public void setLegalrepresentativefiscalcode(String legalrepresentativefiscalcode) {
         this.legalrepresentativefiscalcode = legalrepresentativefiscalcode;
     }
     public String getLegalrepresentativefiscalcode() {
         return legalrepresentativefiscalcode;
     }

}