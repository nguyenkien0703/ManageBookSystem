package com.example.commonservice.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class GetDetailsBookQuery {
    private String bookId;




    public GetDetailsBookQuery(String bookId) {
        super();
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

}
