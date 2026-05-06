package com.buildledger.vendor.feign;

import com.buildledger.vendor.dto.response.ApiResponseDTO;
import com.buildledger.vendor.dto.response.VendorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IamServiceFallback implements IamServiceClient {

    @Override
    public ApiResponseDTO<VendorResponseDTO> createVendorUser(
            String username, String encodedPassword, String name, String email, String phone) {
        log.error("IAM Service is unavailable – cannot create vendor user account for: {}", username);
        return ApiResponseDTO.<VendorResponseDTO>builder()
            .success(false)
            .message("IAM Service is currently unavailable. Vendor user account creation will be retried.")
            .build();
    }
}


