package com.example.secure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.secure.dao.HibernateUtils;
import com.example.secure.entity.Users;
import com.example.secure.request.Loginreq;
import com.example.secure.request.LogoutReq;
import com.example.secure.request.Signup;
import com.example.secure.request.UpdateReq;
import com.example.secure.response.LogoutResp;
import com.example.secure.response.ProfileResponse;
import com.example.secure.response.ResultResp;
import com.example.secure.response.SignupResp;
import com.example.secure.response.UpdateResp;

@RestController
@RequestMapping
public class PageController {

	@Autowired
	HibernateUtils hibernate;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Loginreq req) {
		ResultResp resultResp = new ResultResp();
		String email = req.getEmail();

		try {
			if (hibernate.checkEmail(email) == true) {
				Users user = hibernate.userByEmail(email);
				if (user.getPassword().equals(req.getPassword())) {
					resultResp.setResult("Success");
					return new ResponseEntity<ResultResp>(resultResp, HttpStatus.OK);
				} else {
					resultResp.setResult("Password mismatch");
				}
			}
		} catch (Exception e) {
			resultResp.setResult("This user does not exists");
		}
		return new ResponseEntity<ResultResp>(resultResp, HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/signup")
	public ResponseEntity<?> Signup(@RequestBody Signup req) {
		
		Users u = new Users();
		u.setName(req.getName());
		u.setEmail(req.getEmail());
		u.setPassword(req.getPassword());
		u.setPhone(req.getPhone());
//		u.setAddressid(req.getAddress());
		SignupResp signResp = new SignupResp();
		try {
			if (hibernate.checkEmail(req.getEmail()) == true) {

				signResp.setId(u.getId());
				signResp.setMessage("Email already exists");
			}
		} catch (Exception e) {

			hibernate.save(u);
			signResp.setId(u.getId());
			if (u.getId() == null)
				signResp.setMessage("Email and password are required field");
			else
				signResp.setMessage("User created");
		}
		return new ResponseEntity<SignupResp>(signResp, HttpStatus.OK);
	}

	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody LogoutReq req) {

		Users u = new Users();
		LogoutResp logoutResp = new LogoutResp();
		if(req.getId()==null)
			logoutResp.setMessage("please send id");
		else {
		hibernate.delete(hibernate.findEntityById(u, req.getId()));
		logoutResp.setMessage("deleted");}
		return new ResponseEntity<LogoutResp>(logoutResp, HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody LogoutReq req) {

//		Users u = new Users();
		LogoutResp logoutResp = new LogoutResp();
//		Users m = hibernate.findEntityById(u, req.getId());
		logoutResp.setMessage("Success");
		return new ResponseEntity<LogoutResp>(logoutResp, HttpStatus.OK);
	}

	@PostMapping("/getProfile/{id}")
	public ResponseEntity<?> getprofile(@PathVariable() int id) {
		ProfileResponse pf = new ProfileResponse();
		Users u = new Users();
		Users prof = hibernate.findEntityById(u, id);
		pf.setId(prof.getId());
		pf.setName(prof.getName());
		pf.setEmail(prof.getEmail());
		return new ResponseEntity<ProfileResponse>(pf, HttpStatus.OK);
	}

	@PostMapping("/updateProfile")
	public ResponseEntity<?> update(@RequestBody UpdateReq upreq) {
		Users u = new Users();
		u.setId(upreq.getId());
		Users ur = hibernate.findEntityById(u, upreq.getId());
		u.setName(upreq.getName());
		u.setEmail(upreq.getEmail());
		u.setPassword(ur.getPassword());
		hibernate.update(u);
		UpdateResp upresp = new UpdateResp();
		upresp.setResult("Updated");
		return new ResponseEntity<UpdateResp>(upresp, HttpStatus.OK);
	}

}
