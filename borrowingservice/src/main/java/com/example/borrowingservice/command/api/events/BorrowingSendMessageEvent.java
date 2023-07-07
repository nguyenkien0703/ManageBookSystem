package com.example.borrowingservice.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BorrowingSendMessageEvent {
    private String id ;
    private String employeeId;
    private String message;
}
