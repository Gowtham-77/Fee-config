package com.core.io.fees.service;

import com.core.io.fees.dto.requestdto.FeeRequest;
import com.core.io.fees.dto.responsedto.FeeResponse;
import com.core.io.fees.entity.Rule;
import com.core.io.fees.repository.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class FeeServiceTest {

    @Mock
    private RuleRepository ruleRepository;

    @InjectMocks
    private FeeService feeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateFee_validInput_returnsFeeResponse() {

        FeeRequest request = new FeeRequest();
        request.setToCountryCode("91");
        request.setProductSchema("NEFT");
        request.setAmount(100.0);

        Rule rule = new Rule();
        rule.setToCountryCode("91");
        rule.setProductSchema("NEFT");
        rule.setMinAmount(50.0);
        rule.setMaxAmount(200.0);
        rule.setPercentage(10.0);
        when(ruleRepository.findByToCountryCodeAndProductSchemaAndMinAmountLessThanEqualAndMaxAmountGreaterThanEqual(
                eq("91"), eq("NEFT"), eq(100.0), eq(100.0)))
                .thenReturn(Optional.of(rule));


        FeeResponse response = feeService.calculateFee(request);

        assertNotNull(response);
        assertEquals(10.0, response.getPercentage());
        assertEquals(10.0, response.getFeeAmount());      // 10% of 100
        assertEquals(110.0, response.getTotalAmount());   // 100 + 10
    }

    @Test
    public void testCalculateFee_noMatchingRule_throwsException() {

        FeeRequest request = new FeeRequest();
        request.setToCountryCode("91");
        request.setProductSchema("NEFT");
        request.setAmount(300.0);

        when(ruleRepository.findByToCountryCodeAndProductSchemaAndMinAmountLessThanEqualAndMaxAmountGreaterThanEqual(
                "91", "NEFT", 300.0, 300.0))
                .thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            feeService.calculateFee(request);
        });

        assertEquals("No rule found for given input", exception.getMessage());
    }
}
