package com.example.borrowingservice.command.api.controller;

import com.example.borrowingservice.command.api.command.CreateBorrowCommand;
import com.example.borrowingservice.command.api.command.UpdateBookReturnCommand;
import com.example.borrowingservice.command.api.model.BorrowRequestModel;
import com.example.borrowingservice.command.api.service.BorrowService;
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
    @Autowired
    private BorrowService borrowService;
    @PostMapping
    public String addBookBorrowing(@RequestBody BorrowRequestModel model){
        try{
            System.out.println("bat dau su kien muon ");
            CreateBorrowCommand command =new CreateBorrowCommand(model.getBookId(), model.getEmployeeId(), new Date(),UUID.randomUUID().toString());
            System.out.println("bat dau chuyen sang saga ddaay ");
            commandGateway.sendAndWait(command);


        }catch (Exception e ){
            System.out.println(e.getMessage());
        }
        return "kien ";
    }
    @PutMapping
    public String updateBookReturn(@RequestBody BorrowRequestModel model ){
        UpdateBookReturnCommand command = new UpdateBookReturnCommand(borrowService.findIdBorrowing(model.getEmployeeId(), model.getBookId()), model.getBookId(),model.getEmployeeId(),new Date());
        commandGateway.sendAndWait(command);
        return "book returned";

    }






}
