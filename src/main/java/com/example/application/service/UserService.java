package com.example.application.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.Repo.AddressRepo;
import com.example.application.Repo.UserRepo;
import com.example.application.Repo.Imp.AddressRepoImp;
import com.example.application.Repo.Imp.UserRepoImp;
import com.example.application.entity.Address;
import com.example.application.entity.Users;
import com.example.application.request.DeleteRequest;
import com.example.application.request.LoginRequest;
import com.example.application.request.LogoutRequest;
import com.example.application.request.SignUpRequest;
import com.example.application.request.UpdateRequest;
import com.example.application.response.ProfileResponse;
import com.example.application.service.exception.NoUserException;
import com.example.application.service.exception.UserAlreadyExistsException;
import com.example.application.service.exception.WrongPasswordException;

@Service
public class UserService {
	@Autowired
	UserRepo userR;
	@Autowired
	UserRepoImp userRepoImp;
	@Autowired
	AddressRepoImp addressRepoImp;
	@Autowired
	AddressRepo addressRepo;

	public void login(LoginRequest req) throws Exception {
		String email = req.getEmail();
		if (userR.findByemail(email) != null) {
			Users user = userR.findByemail(email).get(0);
			if (user.getPassword().equals(req.getPassword())) {
				return;
			} else {
				throw new WrongPasswordException();
			}
		} else {
			throw new NoUserException();
		}

	}

	public Integer signup(SignUpRequest req) throws Exception {
		Users u = new Users();
		u.setName(req.getName());
		u.setEmail(req.getEmail());
		u.setPassword(req.getPassword());
		u.setPhone(req.getPhone());
		Address a = new Address();
		a.setCity(req.getAddress().getCity());
		a.setState(req.getAddress().getState());
		a.setStreet(req.getAddress().getStreet());
		a.setPincode(req.getAddress().getPin());

		if (userR.findByemail(req.getEmail())!= null) {
			throw new UserAlreadyExistsException();
		} else {
			addressRepo.save(a);
			u.setAddress(a);
			userR.save(u);
			return u.getId();
		}
	}

	public void delete(DeleteRequest req) throws Exception {
		if ((userR.findById(req.getId()) == null)) {
			throw new NoUserException();
		} else
			userRepoImp.delete(req.getId());
	}

	public void logout(LogoutRequest req) throws Exception {
		if ((userR.findById(req.getId()) == null)) {
			throw new NoUserException();
		}
	}

	public ProfileResponse getprofileById(Integer id) {
		Users prof = userR.findEntityById(id);
		ProfileResponse pf = new ProfileResponse();
		pf.setId(prof.getId());
		pf.setName(prof.getName());
		pf.setEmail(prof.getEmail());	
		pf.setPhone(prof.getPhone());
		pf.setAddress(prof.getAddress());
		return pf;
	}

	public void updateProfile(UpdateRequest upreq) throws Exception {
		Users u = new Users();
		u.setId(upreq.getId());
		Users ur = userRepoImp.findEntityById(upreq.getId());
		u.setName(upreq.getName());
		if (userRepoImp.checkUser(upreq.getEmail())|| userRepoImp.findEntityById(upreq.getId()) == null) {
			throw new UserAlreadyExistsException();
		} else {
			u.setEmail(upreq.getEmail());
			u.setPhone(upreq.getPhone());
			u.setPassword(ur.getPassword());
			Address ad = new Address();
			ad.setId(ur.getAddress().getId());
			ad.setCity(upreq.getAddress().getCity());
			ad.setState(upreq.getAddress().getState());
			ad.setPincode(upreq.getAddress().getPin());
			ad.setStreet(upreq.getAddress().getStreet());
			addressRepoImp.updateAddress(ad);
			u.setAddress(ad);
			userRepoImp.updateUser(u);
		}
	}
}
