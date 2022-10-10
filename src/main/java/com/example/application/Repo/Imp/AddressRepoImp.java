package com.example.application.Repo.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.Repo.AddressRepo;
import com.example.application.entity.Address;

@Component
public class AddressRepoImp {
	 @Autowired
	 AddressRepo addressRepo;
	 	 
	  public Address findEntityById(int id) {

	        List<Address> u = addressRepo.findById(id);
	        return u.get(0);
	    }
	  public void addAddress(Address a){
	        addressRepo.save(a);
	    }
	  public void updateAddress(Address a) {
		  addressRepo.saveAndFlush(a);
	  }
	 
}
