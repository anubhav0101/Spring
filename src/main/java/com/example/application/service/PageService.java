package com.example.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.application.dao.HibernateUtils;
import com.example.application.entity.Address;
import com.example.application.entity.Users;
import com.example.application.request.Loginreq;
import com.example.application.request.LogoutReq;
import com.example.application.request.Signup;
import com.example.application.request.UpdateReq;
import com.example.application.response.LogoutResp;
import com.example.application.response.ProfileResponse;
import com.example.application.response.ResultResp;
import com.example.application.response.SignupResp;
import com.example.application.response.UpdateResp;

@Service
public class PageService {

	@Autowired
	HibernateUtils hibernate;

	public ResultResp login(Loginreq req) {
		String email = req.getEmail();
		ResultResp resultResp = new ResultResp();
		try {
			if (hibernate.checkEmail(email) == true) {
			
				Users user = hibernate.userByEmail(email);
				if (user.getPassword().equals(req.getPassword())) {
					resultResp.setResult("Success");
				} else {
					resultResp.setResult("Password mismatch");
				}
			}
		} catch (Exception e) {
			resultResp.setResult("This user does not exists");
		}

		return resultResp;
	}

	public ResponseEntity<?> signup(Signup req) {
		Users u = new Users();
		SignupResp signResp = new SignupResp();
		if (req.getEmail() == null||req.getPassword()==null) {
			signResp.setMessage("Email and password are required field");
		return new ResponseEntity<SignupResp>(signResp, HttpStatus.BAD_REQUEST);
		} 
		u.setName(req.getName());
		u.setEmail(req.getEmail());
		u.setPassword(req.getPassword());
		u.setPhone(req.getPhone());
		Address a = new Address();
		a.setCity(req.getAddress().getCity());
		a.setState(req.getAddress().getState());
		a.setStreet(req.getAddress().getStreet());
		a.setPincode(req.getAddress().getPin());
		
		try {
			if (hibernate.checkEmail(req.getEmail()) == true) {
				signResp.setMessage("Email already exists");
				return new ResponseEntity<SignupResp>(signResp, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			hibernate.save(a);
			u.setAddress(a);
			hibernate.save(u);
			signResp.setId(u.getId());
			signResp.setMessage("User created");
		}
		return new ResponseEntity<SignupResp>(signResp, HttpStatus.OK);
	}

	public LogoutResp delete(LogoutReq req) {
		Users u = new Users();
		LogoutResp logoutResp = new LogoutResp();
		if (req.getId() == null)
			logoutResp.setMessage("please send id");
		else {
			hibernate.delete(hibernate.findEntityById(u, req.getId()));
			logoutResp.setMessage("deleted");
		}
		return logoutResp;
	}

	public LogoutResp logout(LogoutReq req) {
		LogoutResp logoutResp = new LogoutResp();
		logoutResp.setMessage("Success");

		return logoutResp;
	}

	public ProfileResponse getprofileById(Integer id) {

		Users u = new Users();
		Users prof = hibernate.findEntityById(u, id);
		ProfileResponse pf = new ProfileResponse();
		pf.setId(prof.getId());
		pf.setName(prof.getName());
		pf.setEmail(prof.getEmail());
		pf.setPhone(prof.getPhone());
		pf.setAddress(prof.getAddress());

		return pf;

	}

	public UpdateResp updateProfile(UpdateReq upreq) {
		Users u = new Users();
		UpdateResp upresp = new UpdateResp();
		if (upreq.getId() == null) {

			upresp.setResult("Id is Required");
			return upresp;
		}

			u.setId(upreq.getId());
			Users ur = hibernate.findEntityById(u, upreq.getId());
			u.setName(upreq.getName());
			try{if(hibernate.checkEmail(upreq.getEmail()) == true) {
				upresp.setResult("Email already exists");
				return upresp;}
			}
			catch(Exception e) {u.setEmail(upreq.getEmail());
			u.setPhone(upreq.getPhone());
			u.setPassword(ur.getPassword());
			Address ad = new Address();
			ad.setId(ur.getAddress().getId());
			ad.setCity(upreq.getAddress().getCity());
			ad.setState(upreq.getAddress().getState());
			ad.setPincode(upreq.getAddress().getPin());
			ad.setStreet(upreq.getAddress().getStreet());
			hibernate.update(ad);
			u.setAddress(ad);
			hibernate.update(u);
			upresp.setResult("Updated");}

		return upresp;
	}

}
