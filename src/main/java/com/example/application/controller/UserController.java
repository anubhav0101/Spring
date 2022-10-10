package com.example.application.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.abc.HibernateUtils;
import com.example.application.request.Loginreq;
import com.example.application.request.LogoutReq;
import com.example.application.request.Signup;
import com.example.application.request.UpdateReq;
import com.example.application.response.ErrorResp;
import com.example.application.response.ProfileResponse;
import com.example.application.response.ResultResp;
import com.example.application.response.SignupResp;
import com.example.application.service.UserService;

@RestController
@RequestMapping
public class UserController {

	@Autowired
	UserService pageService;
	ErrorResp errresp = new ErrorResp();

	@PostMapping("/login")

	public ResponseEntity<?> login(@RequestBody Loginreq req) {
		if (req.getEmail() == null || req.getPassword() == null) {
			return new ResponseEntity<ErrorResp>(errresp, HttpStatus.BAD_REQUEST);
		}
		ResultResp resultResp = pageService.login(req);
		return new ResponseEntity<ResultResp>(resultResp, HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> Signup(@RequestBody Signup req) {
		if (req.getEmail() == null || req.getPassword() == null) {
			return new ResponseEntity<ErrorResp>(errresp, HttpStatus.BAD_REQUEST);
		}
		try {
			SignupResp signResp = pageService.signup(req);
			return new ResponseEntity<SignupResp>(signResp, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<ErrorResp>(errresp, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody LogoutReq req) {
		if (req.getId() == null) {
			return new ResponseEntity<ErrorResp>(errresp, HttpStatus.BAD_REQUEST);
		}
		ResultResp resultResp = pageService.delete(req);
		return new ResponseEntity<ResultResp>(resultResp, HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody LogoutReq req) {
		ResultResp res = pageService.logout(req);
		return new ResponseEntity<ResultResp>(res, HttpStatus.OK);
	}

	@PostMapping("/getProfile/{id}")
	public ResponseEntity<?> getprofile(@PathVariable() int id) {
		try {
			ProfileResponse pf = pageService.getprofileById(id);
			return new ResponseEntity<ProfileResponse>(pf, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResp>(errresp, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/updateProfile")
	public ResponseEntity<?> update(@RequestBody UpdateReq upreq) {

		if (upreq.getId() == null) {
			return new ResponseEntity<ErrorResp>(errresp, HttpStatus.BAD_REQUEST);
		}
		try {
			ResultResp resp = pageService.updateProfile(upreq);
			return new ResponseEntity<ResultResp>(resp, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<ErrorResp>(errresp, HttpStatus.CONFLICT);
		}
	}

}
