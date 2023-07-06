package com.example.borrowingservice.command.api.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBorrowCommand {
    @TargetAggregateIdentifier
    private String id ;


}
