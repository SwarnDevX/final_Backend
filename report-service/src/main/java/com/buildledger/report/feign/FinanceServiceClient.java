package com.buildledger.report.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@FeignClient(name = "finance-service")
public interface FinanceServiceClient {
    @GetMapping("/api/invoices")
    Map<String, Object> getAllInvoices();
    
    @GetMapping("/api/payments")
    Map<String, Object> getAllPayments();
}

