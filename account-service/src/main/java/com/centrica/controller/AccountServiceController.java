package com.centrica.controller;

import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.centrica.feign.CustomersClient;
import com.centrica.model.Account;
import com.centrica.model.Customer;
import com.centrica.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountServiceController {

	@Autowired
	private CustomersClient customersClient;
	@Autowired
	private AccountService service;

	@PostMapping
	public ResponseEntity<?> createAccount(@Valid @RequestBody Account account) {
		try {
			Customer customer = customersClient.getData(account.getCustomerId());
			List<String> energyaccount = customer.getEnergyAccounts();
			energyaccount.forEach(accountid -> {
				String id = account.getId();
				if (id.equalsIgnoreCase(accountid)) {
					service.add(account);
				}
			});
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<List<Account>> retrieveAccount(@RequestParam int customerId) {
		try {
			List<Account> accountdetails = service.retrieveAccount(customerId);
			if (!accountdetails.isEmpty())
				return new ResponseEntity<List<Account>>(accountdetails, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NoSuchElementException e) {
			throw e;
		} catch (Exception e) {
			System.out.print(e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Account> retrieveAccountById(@PathVariable String id) {
		try {
			Account accountdetails = service.retrieve(id);
			return new ResponseEntity<>(accountdetails, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			throw e;
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateAccount(@RequestBody Account account, @PathVariable String id) {
		try {
			Account existAccount = service.retrieve(id);
			service.update(account, id, existAccount);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (NoSuchElementException e) {
			throw e;
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable String id) throws Exception {
		try {
			Account account = service.retrieve(id);
			if (account.getStatus().equalsIgnoreCase("open")) {
				throw new Exception("Should not delete account if the account status is open");
			}
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			throw ex;
		}
	}
}
