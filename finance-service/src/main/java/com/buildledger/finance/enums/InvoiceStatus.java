package com.buildledger.finance.enums;

public enum InvoiceStatus {
    UNDER_REVIEW, APPROVED, REJECTED, PAID;

    public boolean canTransitionTo(InvoiceStatus next) {
        return switch (this) {
            case UNDER_REVIEW -> next == APPROVED || next == REJECTED;
            case APPROVED     -> next == PAID;
            case REJECTED     -> next == UNDER_REVIEW; // can resubmit
            case PAID         -> false; // terminal
        };
    }
}

