package com.buildledger.compliance.feign;

import com.buildledger.compliance.dto.response.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component @Slf4j
public class IamServiceFallback implements IamServiceClient {
    public static final String MARKER = "SERVICE_UNAVAILABLE";
    @Override public ApiResponseDTO<Map<String, Object>> getUserById(Long id) {
        log.error("IAM Service unavailable for userId={}", id);
        return ApiResponseDTO.<Map<String,Object>>builder().success(false)
            .message(MARKER).build();
    }
}
