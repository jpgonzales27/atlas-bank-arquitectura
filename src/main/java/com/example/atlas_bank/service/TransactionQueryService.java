package com.example.atlas_bank.service;

import com.example.atlas_bank.model.Transaction;
import com.example.atlas_bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionQueryService implements ITransactionQueryService{

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getByAccountId(Long accountId){
        return transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }
}
