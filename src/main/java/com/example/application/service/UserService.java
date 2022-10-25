package com.example.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.Repo.AddressRepo;
import com.example.application.Repo.UserRepo;
import com.example.application.entity.Address;
import com.example.application.entity.Users;
import com.example.application.exception.NoUserException;
import com.example.application.exception.UserAlreadyExistsException;
import com.example.application.exception.WrongPasswordException;
import com.example.application.request.DeleteRequest;
import com.example.application.request.LoginRequest;
import com.example.application.request.LogoutRequest;
import com.example.application.request.SignUpRequest;

@Service
public class UserService {
	@Autowired
	UserRepo userR;
	@Autowired
	AddressRepo addressRepo;

	public void login(LoginRequest req) throws Exception {
		String email = req.getEmail();
		if (userR.existsByEmail(email)) {
			Users user = userR.findByEmail(email).get(0);
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

		if (userR.existsByEmail(req.getEmail())) {
			throw new UserAlreadyExistsException();
		} else {
			addressRepo.save(a);
			u.setAddress(a);
			userR.save(u);
			return u.getId();
		}
	}

	public void delete(DeleteRequest req) throws Exception {
		if (!userR.existsById(req.getId())) {
			throw new NoUserException();
		} else
			userR.deleteById(req.getId());
	}

	public void logout(LogoutRequest req) throws Exception {
		if (!userR.existsById(req.getId())) {
			throw new NoUserException();
		}
	}

	public Users getprofileById(Integer id) {
		Users profile = userR.findEntityById(id);
		return profile;
	}

	public void updateProfile(Users upreq) throws Exception {
		Users u = new Users();
		u.setId(upreq.getId());
		Users ur = userR.findEntityById(upreq.getId());
		u.setName(upreq.getName());
		if (userR.existsByEmail(upreq.getEmail())) {
			throw new UserAlreadyExistsException();
		} else {
			u.setEmail(upreq.getEmail());
			u.setPhone(upreq.getPhone());
			u.setPassword(ur.getPassword());
			Address ad = new Address();
			ad.setId(ur.getAddress().getId());
			ad.setCity(upreq.getAddress().getCity());
			ad.setState(upreq.getAddress().getState());
			ad.setPincode(upreq.getAddress().getPincode());
			ad.setStreet(upreq.getAddress().getStreet());
			addressRepo.save(ad);
			u.setAddress(ad);
			userR.save(u);
		}
	}
}
