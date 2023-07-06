package com.example.bookservice.command.event;

import com.example.bookservice.command.data.Book;
import com.example.bookservice.command.data.BookRepository;
import com.netflix.discovery.converters.Auto;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookEventsHandler {

    @Autowired
    private BookRepository bookRepository;
    //createBook
    @EventHandler
    public void on(BookCreatedEvent event){
        Book book = new Book();
        BeanUtils.copyProperties(event,book);
        bookRepository.save(book);

    }
    //updateBook
    @EventHandler
    public void on(BookUpdateEvent event){
        Book book = bookRepository.getById(event.getBookId());
        book.setAuthor(event.getAuthor());
        book.setName(event.getName());
        book.setIsReady(event.getIsReady());
        bookRepository.save(book);
    }
    // xoa sach
    @EventHandler
    public void on(BookDeleteEvent event ){
        bookRepository.deleteById(event.getBookId());
    }



}
