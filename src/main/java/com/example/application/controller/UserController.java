package com.example.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.entity.Users;
import com.example.application.exception.NoUserException;
import com.example.application.exception.UserAlreadyExistsException;
import com.example.application.exception.WrongPasswordException;
import com.example.application.request.DeleteRequest;
import com.example.application.request.LoginRequest;
import com.example.application.request.LogoutRequest;
import com.example.application.request.SignUpRequest;
import com.example.application.response.ResultResponse;
import com.example.application.response.SignupResponse;
import com.example.application.service.UserService;

@RestController
@RequestMapping
public class UserController {

	@Autowired
	UserService userService;
	
	ResultResponse resultResponse = new ResultResponse();

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest req) {
		if (req.getEmail() == null || req.getPassword() == null) {
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);
		}
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
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/signup")
	public ResponseEntity<?> Signup(@RequestBody SignUpRequest req) {
		if (req.getEmail() == null || req.getPassword() == null) {
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);
		}
		SignupResponse signUpResp = new SignupResponse();
		try {
			int id = userService.signup(req);
			signUpResp.setId(id);
			signUpResp.setMessage("User Created");
			return new ResponseEntity<SignupResponse>(signUpResp, HttpStatus.OK);
		} catch (UserAlreadyExistsException e) {
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody DeleteRequest req) {
		if (req.getId() == null) {
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);
		}
		try {
			userService.delete(req);
			resultResponse.setResult("Deleted");
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.OK);
		} catch (NoUserException e) {
			resultResponse.setResult("No such user exists");
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody LogoutRequest req) {
		if (req.getId() == null) {
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);
		}
		try{userService.logout(req);
		resultResponse.setResult("Logout Done");
		return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.OK);}
		catch(NoUserException e){
			resultResponse.setResult("No such user exists");
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/getProfile/{id}")
	public ResponseEntity<?> getprofile(@PathVariable() int id) {
		try {
			Users user= userService.getprofileById(id);
			return new ResponseEntity<Users>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/updateProfile")
	public ResponseEntity<?> update(@RequestBody Users upreq) {

		if (upreq.getId() == null) {
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.BAD_REQUEST);}
		try {
			userService.updateProfile(upreq);
			resultResponse.setResult("Updated");
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.OK);
		} catch (UserAlreadyExistsException e) {
			resultResponse.setResult("User Already exists");
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<ResultResponse>(resultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	}

