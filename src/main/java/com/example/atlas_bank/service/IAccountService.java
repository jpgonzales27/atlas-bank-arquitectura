package com.example.atlas_bank.service;

import com.example.atlas_bank.model.Account;

import java.util.List;

public interface IAccountService {
    Account create(Account account);

    List<Account> findAll();

    Account findById(Long id);
}