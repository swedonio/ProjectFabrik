package it.piacentino.bean;

import lombok.Data; 

@Data
public class Amount{
    public int debtorAmount;
    public String debtorCurrency;
    public int creditorAmount;
    public String creditorCurrency;
    public String creditorCurrencyDate;
    public int exchangeRate;
}
