package com.example.bookservice.query.projection;

import com.example.bookservice.command.data.Book;
import com.example.bookservice.command.data.BookRepository;
import com.example.bookservice.query.model.BookResponseModel;
import com.example.bookservice.query.queries.GetAllBooksQuery;
import com.example.bookservice.query.queries.GetBooksQuery;
import com.example.commonservice.model.BookResponseCommonModel;
import com.example.commonservice.query.GetDetailsBookQuery;
import com.example.commonservice.query.GetListBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;
    // khi phats ddi 1 query thì nó sẽ nhảy vào đây

    @QueryHandler
    public BookResponseModel handle(GetBooksQuery getBooksQuery){
        BookResponseModel model = new BookResponseModel();
        Book book = bookRepository.getById(getBooksQuery.getBookId());
        BeanUtils.copyProperties(book, model);
        return model;
    }
    @QueryHandler
    public List<BookResponseModel> handle(GetAllBooksQuery getAllBooksQuery) {
        List<Book> listEntity = bookRepository.findAll();
        List<BookResponseModel> listbook = new ArrayList<>();
        listEntity.forEach( s -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(s, model);
            listbook.add(model);
        });
        return listbook;
    }
    @QueryHandler
    public List<BookResponseCommonModel> handle(GetListBookQuery getListBookQuery){
        List<Book> listEntity = bookRepository.findAll();
        List<BookResponseCommonModel> listbook = new ArrayList<>();
        listEntity.forEach(s -> {
            BookResponseCommonModel model = new BookResponseCommonModel();
            BeanUtils.copyProperties(s, model);
            listbook.add(model);
        });
        return listbook;
    }
    @QueryHandler
    public BookResponseCommonModel handle(GetDetailsBookQuery getDetailsBookQuery) {
        BookResponseCommonModel model = new BookResponseCommonModel();
        Book book = bookRepository.getById(getDetailsBookQuery.getBookId());
        BeanUtils.copyProperties(book, model);

        return model;
    }

}
