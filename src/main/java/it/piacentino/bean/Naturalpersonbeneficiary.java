/* Copyright 2022 freecodeformat.com */
package it.piacentino.bean;
import com.fasterxml.jackson.annotation.JsonProperty;
/* Time: 2022-02-24 16:49:19 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class Naturalpersonbeneficiary {

    @JsonProperty("fiscalCode1")
    private String fiscalcode1;
    @JsonProperty("fiscalCode2")
    private String fiscalcode2;
    @JsonProperty("fiscalCode3")
    private String fiscalcode3;
    @JsonProperty("fiscalCode4")
    private String fiscalcode4;
    @JsonProperty("fiscalCode5")
    private String fiscalcode5;
    public void setFiscalcode1(String fiscalcode1) {
         this.fiscalcode1 = fiscalcode1;
     }
     public String getFiscalcode1() {
         return fiscalcode1;
     }

    public void setFiscalcode2(String fiscalcode2) {
         this.fiscalcode2 = fiscalcode2;
     }
     public String getFiscalcode2() {
         return fiscalcode2;
     }

    public void setFiscalcode3(String fiscalcode3) {
         this.fiscalcode3 = fiscalcode3;
     }
     public String getFiscalcode3() {
         return fiscalcode3;
     }

    public void setFiscalcode4(String fiscalcode4) {
         this.fiscalcode4 = fiscalcode4;
     }
     public String getFiscalcode4() {
         return fiscalcode4;
     }

    public void setFiscalcode5(String fiscalcode5) {
         this.fiscalcode5 = fiscalcode5;
     }
     public String getFiscalcode5() {
         return fiscalcode5;
     }

}