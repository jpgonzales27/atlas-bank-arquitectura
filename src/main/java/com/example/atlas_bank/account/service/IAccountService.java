package com.example.atlas_bank.account.service;

import com.example.atlas_bank.account.model.Account;

import java.util.List;

public interface IAccountService {
    Account create(Account account);

    List<Account> findAll();

    Account findById(Long id);
}