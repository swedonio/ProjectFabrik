/* Copyright 2022 freecodeformat.com */
package it.piacentino.bean;
import com.fasterxml.jackson.annotation.JsonProperty;
/* Time: 2022-02-24 16:49:19 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class Address {

    private String address;
    private String city;
    @JsonProperty("countryCode")
    private String countrycode;
    public void setAddress(String address) {
         this.address = address;
     }
     public String getAddress() {
         return address;
     }

    public void setCity(String city) {
         this.city = city;
     }
     public String getCity() {
         return city;
     }

    public void setCountrycode(String countrycode) {
         this.countrycode = countrycode;
     }
     public String getCountrycode() {
         return countrycode;
     }

}