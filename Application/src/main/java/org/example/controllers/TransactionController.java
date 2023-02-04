package org.example.controllers;

import java.util.List;

import org.example.data.AccountDTO;
import org.example.data.TransactionDTO;
import org.example.port.CreditCardServicePort;
import org.example.port.TransactionServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransactionController {
	
	private TransactionServicePort transactionServicePort;
	private CreditCardServicePort creditCardServicePort;

	
	public TransactionController(TransactionServicePort transactionServicePort, CreditCardServicePort creditCardServicePort) {
		this.transactionServicePort = transactionServicePort;
		this.creditCardServicePort = creditCardServicePort;

	}
	
	@GetMapping("/all")
	public ResponseEntity<List<TransactionDTO>> getAll(){
		return new ResponseEntity<>(transactionServicePort.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<TransactionDTO> getById(@RequestParam("id") Long id) {
		return new ResponseEntity<>(transactionServicePort.getById(id),HttpStatus.OK);
	}
	
	@DeleteMapping("del/{id}")
	public ResponseEntity deleteById(@PathVariable Long id) {
		creditCardServicePort.deleteById(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	
	@GetMapping("statement/")
	public ResponseEntity<List<TransactionDTO>> getByAccountId(@RequestParam("rib") Long id){
		return new ResponseEntity<>(transactionServicePort.getByAccountId(id),HttpStatus.OK);
	}
	
	@PostMapping("newWithdrawal/")
	public ResponseEntity<AccountDTO> withdrawal(@RequestParam("rib")Long rib,@RequestParam("amount")double amount){
		return new ResponseEntity<>(creditCardServicePort.withdrawal(rib, amount),HttpStatus.OK);
	}

	@PostMapping("newDeposit/")
	public ResponseEntity<AccountDTO> deposit(@RequestParam("rib")Long rib,@RequestParam("amount")double amount){
		return new ResponseEntity<>(creditCardServicePort.deposit(rib, amount),HttpStatus.OK);
	}

}
