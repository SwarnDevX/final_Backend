package com.buildledger.vendor.feign;

import com.buildledger.vendor.dto.response.ApiResponseDTO;
import com.buildledger.vendor.dto.response.VendorResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Feign client to call IAM Service for creating vendor user accounts
 * after document approval.
 */
@FeignClient(name = "iam-service", fallback = IamServiceFallback.class)
public interface IamServiceClient {

    @PostMapping("/api/users/internal/vendor")
    ApiResponseDTO<VendorResponseDTO> createVendorUser(
        @RequestParam("username") String username,
        @RequestParam("encodedPassword") String encodedPassword,
        @RequestParam("name") String name,
        @RequestParam(value = "email", required = false) String email,
        @RequestParam(value = "phone", required = false) String phone
    );
}

