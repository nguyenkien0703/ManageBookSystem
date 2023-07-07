package com.example.bookservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookUpdateEvent {
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

   

}
