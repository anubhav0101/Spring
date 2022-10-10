package com.example.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.request.DeleteRequest;
import com.example.application.request.LoginRequest;
import com.example.application.request.LogoutRequest;
import com.example.application.request.SignUpRequest;
import com.example.application.request.UpdateRequest;
import com.example.application.response.ErrorResponse;
import com.example.application.response.ProfileResponse;
import com.example.application.response.ResultResponse;
import com.example.application.response.SignupResponse;
import com.example.application.service.UserService;
import com.example.application.service.exception.NoUserException;
import com.example.application.service.exception.UserAlreadyExistsException;
import com.example.application.service.exception.WrongPasswordException;

@RestController
@RequestMapping
public class UserController {

	@Autowired
	UserService userService;
	ErrorResponse errorResp = new ErrorResponse();

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest req) {
		if (req.getEmail() == null || req.getPassword() == null) {
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.BAD_REQUEST);
		}
		ResultResponse resultResponse = new ResultResponse();
		try {
			userService.login(req);
			resultResponse.setResult("Succesfully logged in");
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.OK);
		} catch (WrongPasswordException e) {
			resultResponse.setResult("Password mismatch");
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);
		} catch (NoUserException e) {
			resultResponse.setResult("No such user exists");
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/signup")
	public ResponseEntity<?> Signup(@RequestBody SignUpRequest req) {
		if (req.getEmail() == null || req.getPassword() == null) {
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.BAD_REQUEST);
		}
		SignupResponse signUpResp = new SignupResponse();
		try {
			int id = userService.signup(req);
			signUpResp.setId(id);
			signUpResp.setMessage("User Created");
			return new ResponseEntity<SignupResponse>(signUpResp, HttpStatus.OK);
		} catch (UserAlreadyExistsException e) {
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody DeleteRequest req) {
		if (req.getId() == null) {
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.BAD_REQUEST);
		}
		ResultResponse resultResp =new ResultResponse();
		try {
			userService.delete(req);
			return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.OK);
		} catch (NoUserException e) {
			resultResp.setResult("No such user exists");
			return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody LogoutRequest req) {
		ResultResponse resultResp =new ResultResponse();
		try{userService.logout(req);
		return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.OK);}
		catch(NoUserException e){
			
			resultResp.setResult("No such user exists");
			return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/getProfile/{id}")
	public ResponseEntity<?> getprofile(@PathVariable() int id) {
		try {
			ProfileResponse pf = userService.getprofileById(id);
			return new ResponseEntity<ProfileResponse>(pf, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/updateProfile")
	public ResponseEntity<?> update(@RequestBody UpdateRequest upreq) {

		if (upreq.getId() == null) {
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.BAD_REQUEST);
		}ResultResponse resp=new ResultResponse();
		try {
			userService.updateProfile(upreq);
			resp.setResult("Updated");
			return new ResponseEntity<ResultResponse>(resp, HttpStatus.OK);
		} catch (UserAlreadyExistsException e) {
			resp.setResult("User Already exists");
			return new ResponseEntity<ResultResponse>(resp, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
