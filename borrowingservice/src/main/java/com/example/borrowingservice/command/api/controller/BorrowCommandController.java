package com.example.borrowingservice.command.api.controller;

import com.example.borrowingservice.command.api.command.CreateBorrowCommand;
import com.example.borrowingservice.command.api.model.BorrowRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrowing")
@EnableBinding(Source.class)
public class BorrowCommandController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private MessageChannel output ;

    @PostMapping
    public String addBookBorrowing(@RequestBody BorrowRequestModel model){
        Integer ok =1;
        try{
            CreateBorrowCommand command = new CreateBorrowCommand(UUID.randomUUID().toString(),model.getBookId(),model.getEmployeeId(),new Date());
            commandGateway.sendAndWait(command);

//            return "Book borrowing added";
        }catch (Exception e ){
            ok =0;
            System.out.println(e.getMessage());
        }
        if(ok==1){
            return "borrowing added book";
        }else
        {
            return "not borrowing added bood";
        }


    }


}
