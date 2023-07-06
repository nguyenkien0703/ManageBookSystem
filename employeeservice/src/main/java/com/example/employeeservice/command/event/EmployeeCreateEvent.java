package com.example.employeeservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeCreateEvent {
    private String employeeId;
    private String firstName;
    private String lastName ;
    private  String kin ;
    private Boolean isDisciplined;




}
