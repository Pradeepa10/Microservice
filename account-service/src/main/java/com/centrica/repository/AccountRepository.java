package com.centrica.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.centrica.model.AccountDto;

public interface AccountRepository extends JpaRepository<AccountDto, String> {
	Optional<AccountDto> findById(String id);
	Optional<List<AccountDto>> findByCustomerId(int customerId);
}