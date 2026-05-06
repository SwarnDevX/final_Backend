package com.buildledger.finance.dto.response;

import lombok.*; import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ApiResponseDTO<T> {
    private boolean success; private String message; private T data;
    @Builder.Default private LocalDateTime timestamp = LocalDateTime.now();
    public static <T> ApiResponseDTO<T> success(String m, T d) { return ApiResponseDTO.<T>builder().success(true).message(m).data(d).build(); }
    public static <T> ApiResponseDTO<T> success(String m) { return ApiResponseDTO.<T>builder().success(true).message(m).build(); }
}

