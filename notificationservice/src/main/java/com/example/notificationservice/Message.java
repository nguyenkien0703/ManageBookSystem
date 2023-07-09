package com.example.notificationservice;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Message {
    private String employeeId;
    private String message;

    @Override
    public String toString() {
        return "Message{" +
                "employeeId='" + employeeId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
