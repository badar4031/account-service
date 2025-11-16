package com.example.banking_integration_api.controller;

import com.example.banking_integration_api.exception.ResourceNotFoundException;
import com.example.banking_integration_api.model.Account;
import com.example.banking_integration_api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    //Create New Account
    @PostMapping
    public Account createAccount(@RequestBody Account account){
        return  accountService.createAccount(account);
    }

    //get all account
    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    //get  account by id
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id).orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));
    }

    //Deposit Money
    @PutMapping("/{id}/deposit")
    public  Account deposit(@PathVariable Long id, @RequestParam BigDecimal amount){
        return accountService.deposit(id,amount);
    }

    //withdraw Money
    @PutMapping("/{id}/withdraw")
    public  Account withdraw(@PathVariable Long id, @RequestParam BigDecimal amount){
        return accountService.withdraw(id,amount);
    }

    //delete mapping
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return "Account Deleted Successfully";
    }
}
