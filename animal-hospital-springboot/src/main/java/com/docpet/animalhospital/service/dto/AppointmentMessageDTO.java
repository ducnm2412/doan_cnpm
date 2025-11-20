package com.docpet.animalhospital.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class AppointmentMessageDTO implements Serializable {

    private Long id;
    private String message;
    private ZonedDateTime timestamp;
    private Long appointmentId;
    private Long senderId;
    private String senderName; // Để hiển thị tên người gửi
    private String senderLogin; // Để phân biệt owner hay vet

    public AppointmentMessageDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentMessageDTO)) return false;
        AppointmentMessageDTO that = (AppointmentMessageDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AppointmentMessageDTO{" +
            "id=" + id +
            ", message='" + message + '\'' +
            ", timestamp=" + timestamp +
            ", appointmentId=" + appointmentId +
            ", senderId=" + senderId +
            ", senderName='" + senderName + '\'' +
            '}';
    }
}


