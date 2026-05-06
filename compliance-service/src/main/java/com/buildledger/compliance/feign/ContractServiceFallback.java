package com.buildledger.compliance.feign;

import com.buildledger.compliance.dto.response.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component @Slf4j
public class ContractServiceFallback implements ContractServiceClient {
    public static final String MARKER = "SERVICE_UNAVAILABLE";
    @Override public ApiResponseDTO<Map<String, Object>> getContractById(Long id) {
        log.error("Contract Service unavailable for contractId={}", id);
        return ApiResponseDTO.<Map<String,Object>>builder().success(false)
            .message(MARKER).build();
    }
}
