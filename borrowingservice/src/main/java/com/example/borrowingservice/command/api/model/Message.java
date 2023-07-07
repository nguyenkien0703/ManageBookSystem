package com.example.borrowingservice.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
