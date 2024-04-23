package com.example.dto;

public class ContactDto {

    private Long id;
	private String mobileNumber;
    private String address;
    
    
    
    
    
    
    
    
    
    
    
    private String city;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public ContactDto(Long id, String mobileNumber, String address, String city) {
		super();
		this.id = id;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.city = city;
	}
	@Override
	public String toString() {
		return "ContactDto [id=" + id + ", mobileNumber=" + mobileNumber + ", address=" + address + ", city=" + city
				+ "]";
	}

    

	
	


	
	
	
	
	
	
    
    
}
