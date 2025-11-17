package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDepositSuccess(){
        Account account = new Account(1L,"1001","uzma", "saving",new BigDecimal("1000"));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn((account));

        Account updated = accountService.deposit(2L,new BigDecimal("400"));

        assertEquals(new BigDecimal("1400"), updated.getBalance());
        verify(accountRepository).save(account);
    }

    @Test
    void testWithdrawSuccess(){
        Account account = new Account(1L,"1001","uzma", "saving",new BigDecimal("1000"));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn((account));

        Account updated = accountService.withdraw(2L,new BigDecimal("400"));

        assertEquals(new BigDecimal("600"), updated.getBalance());
        verify(accountRepository).save(account);
    }

    @Test
    void testWithdrawInsufficientFunds(){
        Account account = new Account(3L,"1001","uzma", "saving",new BigDecimal("100"));
        when(accountRepository.findById(3L)).thenReturn(Optional.of(account));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                accountService.withdraw(3L, new BigDecimal("200")));

        assertEquals("Insufficient balance for withdrawl", ex.getMessage());
    }
}
