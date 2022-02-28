/* Copyright 2022 freecodeformat.com */
package it.piacentino.bean;
import com.fasterxml.jackson.annotation.JsonProperty;
/* Time: 2022-02-24 16:49:19 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class Account {

    @JsonProperty("accountCode")
    private String accountcode;
    @JsonProperty("bicCode")
    private String biccode;
    public void setAccountcode(String accountcode) {
         this.accountcode = accountcode;
     }
     public String getAccountcode() {
         return accountcode;
     }

    public void setBiccode(String biccode) {
         this.biccode = biccode;
     }
     public String getBiccode() {
         return biccode;
     }

}