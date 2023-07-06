package com.example.bookservice.query.controller;

import com.example.bookservice.query.model.BookResponseModel;
import com.example.bookservice.query.queries.GetAllBooksQuery;
import com.example.bookservice.query.queries.GetBooksQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
    @Autowired
    private QueryGateway queryGateway;
    @GetMapping("/{bookId}")
    public BookResponseModel getBookDetail(@PathVariable String bookId){
        GetBooksQuery getBooksQuery  = new GetBooksQuery();
        getBooksQuery.setBookId(bookId);
//     tham số thứ nhhaast là  nó sẽ query theo các thuojc tính đã set ở trong getBooksQuerry
//        tham só thứ hai là thể loại mà nó phản hồi về đó chính là 1 class BookResponseModel
        BookResponseModel bookResponseModel = queryGateway.query(getBooksQuery, ResponseTypes.instanceOf(BookResponseModel.class)).join();
        return bookResponseModel;

    }
    @GetMapping
    public List<BookResponseModel> getAllBooks(){
        GetAllBooksQuery getAllBooksQuery =new GetAllBooksQuery();
        List<BookResponseModel> list = queryGateway.query(getAllBooksQuery,ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
        return  list ;
    }

}
