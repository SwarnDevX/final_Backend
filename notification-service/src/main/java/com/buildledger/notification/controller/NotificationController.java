package com.buildledger.notification.controller;

import com.buildledger.notification.entity.Notification;
import com.buildledger.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Management")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all notifications [ADMIN only]")
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(notificationService.getAll());
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get undelivered notifications [ADMIN only]")
    public ResponseEntity<List<Notification>> getPending() {
        return ResponseEntity.ok(notificationService.getPending());
    }

    @GetMapping("/recipient/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get notifications by recipient email [ADMIN only]")
    public ResponseEntity<List<Notification>> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(notificationService.getByEmail(email));
    }
}

