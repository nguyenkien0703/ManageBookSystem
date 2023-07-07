package com.example.borrowingservice.command.api.events;

import com.example.borrowingservice.command.api.data.BorrowRepository;
import com.example.borrowingservice.command.api.data.Borrowing;
import com.example.borrowingservice.command.api.model.Message;
import com.example.borrowingservice.command.api.service.BorrowService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BorrowingEventsHandler {

    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private BorrowService borrowService;

    @EventHandler
    public void on(BorrowCreateEvent event){
        Borrowing borrowing  = new Borrowing();
        BeanUtils.copyProperties(event,borrowing);
        borrowRepository.save(borrowing);
    }


    @EventHandler
    public void on(BorrowDeleteEvent event ){
        if(borrowRepository.findById(event.getId()).isPresent()){
            borrowRepository.deleteById(event.getId());
        }else {
            return ;
        }
    }
    @EventHandler
    public void on(BorrowingSendMessageEvent event ){
        Message message =new Message(event.getEmployeeId(),event.getMessage());
        borrowService.sendMessage(message);
    }


}
