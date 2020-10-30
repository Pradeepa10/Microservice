package com.centrica.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.centrica.model.UserInfo;


@Repository
@Transactional
public interface UserDetailsRepository extends CrudRepository<UserInfo, String> {
	public UserInfo findByEmail(String email);



	public UserInfo findById(Integer id);

}
