package com.core.io.fees.service;

import com.core.io.fees.dto.requestdto.FeeRequest;
import com.core.io.fees.dto.responsedto.FeeResponse;
import com.core.io.fees.repository.RuleRepository;

import com.core.io.fees.entity.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FeeService {

    @Autowired
    private RuleRepository ruleRepository;

    public FeeResponse calculateFee(FeeRequest request) {
        Rule rule = ruleRepository
                .findByToCountryCodeAndProductSchemaAndMinAmountLessThanEqualAndMaxAmountGreaterThanEqual(
                        request.getToCountryCode(),
                        request.getProductSchema(),
                        request.getAmount(),
                        request.getAmount()
                )
                .orElseThrow(() -> new RuntimeException("No rule found for given input"));

        double percentage = rule.getPercentage();
        double feeAmount = request.getAmount() * (percentage/100);
        double totalAmount = request.getAmount() + feeAmount;

        FeeResponse response = new FeeResponse();
        response.setPercentage(percentage);
        response.setFeeAmount(feeAmount);
        response.setTotalAmount(totalAmount);

        return response;
    }
}
