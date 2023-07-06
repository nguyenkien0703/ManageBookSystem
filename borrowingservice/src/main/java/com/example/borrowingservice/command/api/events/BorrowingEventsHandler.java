package com.example.borrowingservice.command.api.events;

import com.example.borrowingservice.command.api.data.BorrowRepository;
import com.example.borrowingservice.command.api.data.Borrowing;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BorrowingEventsHandler {

    @Autowired
    private BorrowRepository borrowRepository;

    @EventHandler
    public void on(BorrowCreateEvent event){
        Borrowing borrowing  = new Borrowing();
        BeanUtils.copyProperties(event,borrowing);
        borrowRepository.save(borrowing);
    }



}
