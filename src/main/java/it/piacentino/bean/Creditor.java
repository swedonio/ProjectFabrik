/* Copyright 2022 freecodeformat.com */
package it.piacentino.bean;

/* Time: 2022-02-24 16:49:19 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class Creditor {

    private String name;
    private Account account;
    private Address address;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setAccount(Account account) {
         this.account = account;
     }
     public Account getAccount() {
         return account;
     }

    public void setAddress(Address address) {
         this.address = address;
     }
     public Address getAddress() {
         return address;
     }

}