package com.centrica.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.centrica.model.CustomerDto;

public interface CustomerRepository extends JpaRepository<CustomerDto, Integer> {
	Optional<List<CustomerDto>> findByUcrn(String ucrn);
	Optional<CustomerDto> findById(int id);
}
