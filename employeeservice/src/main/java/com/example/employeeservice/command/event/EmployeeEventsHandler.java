package com.example.employeeservice.command.event;

import com.example.employeeservice.command.data.Employee;
import com.example.employeeservice.command.data.EmployeeRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventsHandler {
    @Autowired
    private EmployeeRepository employeeRepository;
    // createEmployee
    @EventHandler
    public void on(EmployeeCreateEvent event ){
        Employee employee = new Employee();
        BeanUtils.copyProperties(event, employee);
        employeeRepository.save(employee);
    }
    // updateemployee
    @EventHandler
    public void  on(EmployeeUpdateEvent event){
        Employee employee = employeeRepository.getById(event.getEmployeeId());
        employee.setFirstName(event.getFirstName());
        employee.setLastName(event.getLastName());
        employee.setKin(event.getKin());
        employee.setIsDisciplined(event.getIsDisciplined());
        employeeRepository.save(employee);
    }
    @EventHandler
    public void on (EmployeeDeleteEvent event){
        employeeRepository.deleteById(event.getEmployeeId());
    }








}
