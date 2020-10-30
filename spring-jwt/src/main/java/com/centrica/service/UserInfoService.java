package com.centrica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.centrica.model.UserInfo;
import com.centrica.repository.UserDetailsRepository;



@Repository
@Transactional
public class UserInfoService {

	@Autowired
	private UserDetailsRepository userDatailsRepository;

	public UserInfo getUserInfoByUserName(String email) {
		
		return userDatailsRepository.findByEmail(email);
	}



	public UserInfo getUserInfoById(Integer id) {
		return userDatailsRepository.findById(id);
	}

	public UserInfo addUser(UserInfo userInfo) {
		userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
		return userDatailsRepository.save(userInfo);
	}

	




	
}
