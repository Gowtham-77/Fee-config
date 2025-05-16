package com.core.io.fees.controller;



import com.core.io.fees.dto.requestdto.FeeRequest;
import com.core.io.fees.dto.responsedto.FeeResponse;
import com.core.io.fees.service.FeeService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fee")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @PostMapping("/calculate")
    public FeeResponse calculateFee(@RequestBody FeeRequest request) {
        return feeService.calculateFee(request);
    }
}


