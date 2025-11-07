package com.docpet.animalhospital.domain.enumeration;

/**
 * The AppointmentStatus enumeration.
 */
public enum AppointmentStatus {
    PENDING("PENDING", "Chờ phê duyệt"),
    APPROVED("APPROVED", "Đã phê duyệt"),
    REJECTED("REJECTED", "Đã từ chối"),
    SCHEDULED("SCHEDULED", "Đã lên lịch"),
    CONFIRMED("CONFIRMED", "Đã xác nhận"),
    IN_PROGRESS("IN_PROGRESS", "Đang thực hiện"),
    COMPLETED("COMPLETED", "Hoàn thành"),
    CANCELLED("CANCELLED", "Đã hủy"),
    RESCHEDULED("RESCHEDULED", "Đã đổi lịch");

    private final String value;
    private final String description;

    AppointmentStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return value;
    }
}



