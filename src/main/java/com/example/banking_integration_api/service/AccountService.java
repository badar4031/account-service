package com.example.banking_integration_api.service;

import com.example.banking_integration_api.exception.ResourceNotFoundException;
import com.example.banking_integration_api.model.Account;
import com.example.banking_integration_api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    //create or open a new Account
    public Account createAccount(Account account){
        return  accountRepository.save(account);
    }

    //get All Accounts
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    //get account by ID
    public Optional<Account> getAccountById(Long id){
        return accountRepository.findById(id);
    }

    //deposit money
    public Account deposit(Long id, BigDecimal amount){
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account Not found"));
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    //withdraw money
    public Account withdraw(Long id, BigDecimal amount){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account Not found with ID: "+id));
        if(account.getBalance().compareTo(amount) < 0){
            throw new IllegalArgumentException("Insufficient balance for withdrawl");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    //Delete Account
    public void deleteAccount(Long id){
       accountRepository.deleteById(id);
    }
}
