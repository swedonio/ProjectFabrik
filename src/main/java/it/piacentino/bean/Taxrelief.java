package it.piacentino.bean;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Taxrelief {

    @JsonProperty("taxReliefId")
    private String taxreliefid;
    @JsonProperty("isCondoUpgrade")
    private boolean iscondoupgrade;
    @JsonProperty("creditorFiscalCode")
    private String creditorfiscalcode;
    @JsonProperty("beneficiaryType")
    private String beneficiarytype;
    @JsonProperty("naturalPersonBeneficiary")
    private Naturalpersonbeneficiary naturalpersonbeneficiary;
    @JsonProperty("legalPersonBeneficiary")
    private Legalpersonbeneficiary legalpersonbeneficiary;
    
    public void setTaxreliefid(String taxreliefid) {
         this.taxreliefid = taxreliefid;
     }
     public String getTaxreliefid() {
         return taxreliefid;
     }

    public void setIscondoupgrade(boolean iscondoupgrade) {
         this.iscondoupgrade = iscondoupgrade;
     }
     public boolean getIscondoupgrade() {
         return iscondoupgrade;
     }

    public void setCreditorfiscalcode(String creditorfiscalcode) {
         this.creditorfiscalcode = creditorfiscalcode;
     }
     public String getCreditorfiscalcode() {
         return creditorfiscalcode;
     }

    public void setBeneficiarytype(String beneficiarytype) {
         this.beneficiarytype = beneficiarytype;
     }
     public String getBeneficiarytype() {
         return beneficiarytype;
     }

    public void setNaturalpersonbeneficiary(Naturalpersonbeneficiary naturalpersonbeneficiary) {
         this.naturalpersonbeneficiary = naturalpersonbeneficiary;
     }
     public Naturalpersonbeneficiary getNaturalpersonbeneficiary() {
         return naturalpersonbeneficiary;
     }

    public void setLegalpersonbeneficiary(Legalpersonbeneficiary legalpersonbeneficiary) {
         this.legalpersonbeneficiary = legalpersonbeneficiary;
     }
     public Legalpersonbeneficiary getLegalpersonbeneficiary() {
         return legalpersonbeneficiary;
     }

}