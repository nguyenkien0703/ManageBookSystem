package com.example.borrowingservice.command.api.aggregate;

import com.example.borrowingservice.command.api.command.CreateBorrowCommand;
import com.example.borrowingservice.command.api.command.DeleteBorrowCommand;
import com.example.borrowingservice.command.api.command.SendMessageCommand;
import com.example.borrowingservice.command.api.command.UpdateBookReturnCommand;
import com.example.borrowingservice.command.api.events.BorrowCreateEvent;
import com.example.borrowingservice.command.api.events.BorrowDeleteEvent;
import com.example.borrowingservice.command.api.events.BorrowingSendMessageEvent;
import com.example.borrowingservice.command.api.events.BorrowingUpdateBookReturnEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@Aggregate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowAggregate {
    @AggregateIdentifier
    private String id ;

    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;
    private String message;
    @CommandHandler
    public BorrowAggregate(CreateBorrowCommand command){
        BorrowCreateEvent event = new BorrowCreateEvent();
        System.out.println("CommandbookId "+ command.getBookId());
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }
    @CommandHandler
    public void handle (UpdateBookReturnCommand command){
        BorrowingUpdateBookReturnEvent event = new BorrowingUpdateBookReturnEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteBorrowCommand command){
        BorrowDeleteEvent event = new BorrowDeleteEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }
    //send message
    @CommandHandler
    public void handle(SendMessageCommand command){
        BorrowingSendMessageEvent event = new BorrowingSendMessageEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }



    @EventSourcingHandler
    public void on(BorrowCreateEvent event){
        this.bookId =event.getBookId();
        this.employeeId  = event.getEmployeeId();
        this.borrowingDate  = event.getBorrowingDate();
        this.id = event.getId();
    }
    @EventSourcingHandler
    public void on(BorrowDeleteEvent event){
        this.id = event.getId();
    }
    @EventSourcingHandler
    public void on(BorrowingSendMessageEvent event ){
        this.id = event.getId();
        this.employeeId = event.getEmployeeId();
        this.message =event.getMessage();
    }




}
