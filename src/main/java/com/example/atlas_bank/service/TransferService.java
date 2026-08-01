package com.example.atlas_bank.service;

import com.example.atlas_bank.model.Account;
import com.example.atlas_bank.model.Transaction;
import com.example.atlas_bank.repository.AccountRepository;
import com.example.atlas_bank.repository.TransactionRepository;
import com.example.atlas_bank.service.fee.FeeCalculator;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TransferService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final List<FeeCalculator> feeCalculators;

    @Transactional
    public Transaction transfer(Long fromId, Long toId, BigDecimal amount) {
        // Buscar cuentas
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada"));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada"));

        // Validar que la cuenta esté activa
        if (!"ACTIVE".equals(from.getStatus())) {
            throw new RuntimeException("La cuenta origen no está activa");
        }
        if (!"ACTIVE".equals(to.getStatus())) {
            throw new RuntimeException("La cuenta destino no está activa");
        }

        // Validar fondos
        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Fondos insuficientes");
        }

        // Calcular comisión
        BigDecimal fee = feeCalculators.stream()
                .filter(fc -> fc.supports(from.getType()))
                .findFirst()
                .orElseThrow( () ->  new RuntimeException("No hay calculador para el tipo " + from.getType()))
                .calculate(amount);

        // Actualizar saldos
        from.setBalance(from.getBalance().subtract(amount).subtract(fee));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);

        // Crear transacción
        Transaction transaction = new Transaction();
        transaction.setType("TRANSFER");
        transaction.setSourceAccountId(fromId);
        transaction.setTargetAccountId(toId);
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setStatus("EXECUTED");

        return transactionRepository.save(transaction);
    }
}
