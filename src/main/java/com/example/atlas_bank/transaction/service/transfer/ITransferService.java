package com.example.atlas_bank.transaction.service.transfer;

import com.example.atlas_bank.transaction.model.Transaction;

import java.math.BigDecimal;

public interface ITransferService {
    Transaction execute(Long fromId, Long toId, BigDecimal amount);
}
