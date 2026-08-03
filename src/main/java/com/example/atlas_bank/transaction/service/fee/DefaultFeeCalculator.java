package com.example.atlas_bank.transaction.service.fee;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order() //como no tiene un valor lo pone al ultimo de la lista
public class DefaultFeeCalculator implements FeeCalculator{
    @Override
    public boolean supports(String accountType) {
        return true;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}