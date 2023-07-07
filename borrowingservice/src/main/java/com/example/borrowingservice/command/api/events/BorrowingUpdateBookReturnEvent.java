package com.example.borrowingservice.command.api.events;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingUpdateBookReturnEvent {
    private String id;
    private String bookId;
    private String employee;
    private Date returnDate;
}
