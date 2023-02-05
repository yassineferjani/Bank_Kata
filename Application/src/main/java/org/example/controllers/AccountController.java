package org.example.controllers;

import java.util.List;

import org.example.data.AccountDTO;
import org.example.data.TransactionDTO;
import org.example.port.AccountServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
public class AccountController {
	
	private AccountServicePort accountServicePort;
	
	public AccountController(AccountServicePort accountServicePort) {
		this.accountServicePort = accountServicePort;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<AccountDTO>> getAll() {
		return new ResponseEntity<>(accountServicePort.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public  ResponseEntity<AccountDTO> getByRib(@RequestParam("rib") Long rib) {
		return new ResponseEntity<>(accountServicePort.getById(rib).get(),HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<AccountDTO> add(@RequestBody AccountDTO accountDTO) {
		accountServicePort.add(accountDTO);
		return new ResponseEntity<>(accountDTO,HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<AccountDTO> update(@RequestBody AccountDTO accountDTO) {
		accountServicePort.update(accountDTO);
		return  new ResponseEntity<>(accountDTO,HttpStatus.OK);
	}
	
	@DeleteMapping("/del/{id}")
	public ResponseEntity deleteById(@PathVariable Long id) {
		accountServicePort.deleteById(id);
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	


}
