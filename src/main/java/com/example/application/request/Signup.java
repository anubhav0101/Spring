package com.example.application.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties 
public class Signup {
private String name;
private String email;
private String password;
private Long Phone;
private AddressReq address;

public AddressReq getAddress() {
	return address;
}
public void setAddress(AddressReq address) {
	this.address = address;
}
public Long getPhone() {
	return Phone;
}
public void setPhone(Long phone) {
	Phone = phone;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

}