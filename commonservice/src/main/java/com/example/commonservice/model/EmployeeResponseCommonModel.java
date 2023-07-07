package com.example.commonservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class EmployeeResponseCommonModel {
    private String employeeId;
    private String firstName ;
    private String lastName;
    private String kin ;
    private Boolean isDisciplined;


}
