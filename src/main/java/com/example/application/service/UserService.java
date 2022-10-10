package com.example.application.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.application.Repo.Imp.AddressRepoImp;
import com.example.application.Repo.Imp.UserRepoImp;
import com.example.application.entity.Address;
import com.example.application.entity.Users;
import com.example.application.request.Loginreq;
import com.example.application.request.LogoutReq;
import com.example.application.request.Signup;
import com.example.application.request.UpdateReq;
import com.example.application.response.ProfileResponse;
import com.example.application.response.ResultResp;
import com.example.application.response.SignupResp;

@Service
public class UserService {

	@Autowired
	UserRepoImp userRepoImp;
	@Autowired
	AddressRepoImp addressRepoImp;

	public ResultResp login(Loginreq req) {
		String email = req.getEmail();
		ResultResp resultResp = new ResultResp();
		try {
			if (userRepoImp.checkMail(email) == false) {

				Users user = userRepoImp.findUserByEmail(email);
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

	public SignupResp signup(Signup req) throws IOException {
		Users u = new Users();
		SignupResp signResp = new SignupResp();
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
			if (userRepoImp.checkMail(req.getEmail()) == false) {
					throw new IOException("IOException Occurred");
			}
			else {
				addressRepoImp.addAddress(a);
				u.setAddress(a);
				userRepoImp.addUser(u);
				signResp.setId(u.getId());
				signResp.setMessage("User created");
			}
		}
		
		catch (Exception e) {
			if(e.getClass().getName().equals("java.io.IOException"))
				throw new IOException("IOException Occurred");
		}
		return signResp;
	}

	public ResultResp delete(LogoutReq req) {
		ResultResp resultResp = new ResultResp();
		try {
		userRepoImp.delete(req.getId());
		resultResp.setResult("deleted");
		}
		catch(Exception e) {
			resultResp.setResult("Id Not Exist");
		}
		return resultResp;
	}

	public ResultResp logout(LogoutReq req) {
		ResultResp res = new ResultResp();
		res.setResult("Success");
		return res;
	}

	public ProfileResponse getprofileById(Integer id) {
		Users u = new Users();
		Users prof = userRepoImp.findEntityById(id);
		ProfileResponse pf = new ProfileResponse();
		pf.setId(prof.getId());
		pf.setName(prof.getName());
		pf.setEmail(prof.getEmail());
		pf.setPhone(prof.getPhone());
		pf.setAddress(prof.getAddress());
		return pf;

	}

	public ResultResp updateProfile(UpdateReq upreq) throws IOException{
		Users u = new Users();
		ResultResp upresp = new ResultResp();
		u.setId(upreq.getId());
		Users ur = userRepoImp.findEntityById(upreq.getId());
		u.setName(upreq.getName());
		try {
			
			if (userRepoImp.checkMail(upreq.getEmail()) == false||userRepoImp.findEntityById(upreq.getId())==null) {
			throw new IOException("IOException Occurred");
			}
			else {
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
			upresp.setResult("Updated");
		}
		}
			catch(Exception e){
				if(e.getClass().getName().equals("java.io.IOException"))
					throw new IOException("IOException Occurred");
			}
		return upresp;
	
	}
}
