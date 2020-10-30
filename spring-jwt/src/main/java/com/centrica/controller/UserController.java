package com.centrica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centrica.model.UserInfo;
import com.centrica.service.UserInfoService;



@RestController
@RequestMapping("/v1")

public class UserController {
	@Autowired
	private UserInfoService userService;



	@PostMapping("/user")
	public UserInfo addUser(@RequestBody UserInfo userRecord) {
		return userService.addUser(userRecord);
	}

	

	@GetMapping("/user/{id}")
	public ResponseEntity<UserInfo> getUserById( @PathVariable Integer id) {
		UserInfo userInfo = userService.getUserInfoById(id);
		if (userInfo == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userInfo, HttpStatus.OK);
	}
//	
}
