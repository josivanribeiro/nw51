package br.com.nw51.common.vo;
/**
 * Value Object class for Address.
 * 
 * @author Josivan Silva
 *
 */
public class AddressVO {
   
   private Long addressId;
   private String CEP;
   private String address1;
   private String number;
   private String address2;
   private String neighborhood;
   private String city;
   private int stateId;

   public Long getAddressId() {
	   return addressId;
   }
   public void setAddressId(Long addressId) {
	   this.addressId = addressId;
   }
   public String getCEP() {
	   return CEP;
   }
   public void setCEP(String cEP) {
	   CEP = cEP;
   }
   public String getAddress1() {
	   return address1;
   }
   public void setAddress1(String address1) {
	   this.address1 = address1;
   }
   public String getNumber() {
	   return number;
   }
   public void setNumber(String number) {
	   this.number = number;
   }
   public String getAddress2() {
	   return address2;
   }
   public void setAddress2(String address2) {
	   this.address2 = address2;
   }
   public String getNeighborhood() {
	   return neighborhood;
   }
   public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
   }
   public String getCity() {
		return city;
   }
   public void setCity(String city) {
		this.city = city;
   }
   public int getStateId() {
		return stateId;
   }
   public void setStateId(int stateId) {
		this.stateId = stateId;
   }
	
}
