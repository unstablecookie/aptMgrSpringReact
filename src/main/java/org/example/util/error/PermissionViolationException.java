package org.example.util.error;

public class PermissionViolationException extends RuntimeException {
    private String reason;

    public PermissionViolationException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
