package com.example.borrowingservice.command.api.aggregate;

import com.example.borrowingservice.command.api.command.CreateBorrowCommand;
import com.example.borrowingservice.command.api.command.UpdateBorrowCommand;
import com.example.borrowingservice.command.api.events.BorrowCreateEvent;
import com.example.borrowingservice.command.api.events.BorrowUpdateEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

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
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }
    @CommandHandler
    public void handle (UpdateBorrowCommand command){
        BorrowUpdateEvent event = new BorrowUpdateEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BorrowCreateEvent event){
        this.bookId =event.getBookId();
        this.employeeId  = event.getEmployeeId();
        this.borrowingDate  = event.getBorrowingDate();
        this.id = event.getId();
    }

}
