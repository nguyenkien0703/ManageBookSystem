package com.example.employeeservice.query.projection;

import com.example.commonservice.model.EmployeeResponseCommonModel;
import com.example.commonservice.query.GetDetailsEmployeeQuery;
import com.example.employeeservice.command.data.Employee;
import com.example.employeeservice.command.data.EmployeeRepository;
import com.example.employeeservice.query.model.EmployeeResponseModel;
import com.example.employeeservice.query.queries.GetAllEmployeeQuery;
import com.example.employeeservice.query.queries.GetEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeProjection {

    @Autowired
    private EmployeeRepository employeeRepository;
    @QueryHandler
    public EmployeeResponseModel handle (GetEmployeeQuery getEmployeeQuery){
        EmployeeResponseModel model = new EmployeeResponseModel();
        Employee employee = employeeRepository.getById(getEmployeeQuery.getEmployeeId());
        BeanUtils.copyProperties(employee, model);
        return  model;
    }
    @QueryHandler
    public List<EmployeeResponseModel> handle (GetAllEmployeeQuery getAllEmployeeQuery){
        List<Employee> listEntity = employeeRepository.findAll();
        List<EmployeeResponseModel> listemployee =  new ArrayList<>();
        listEntity.forEach( s -> {
            EmployeeResponseModel model = new EmployeeResponseModel();
            BeanUtils.copyProperties(s, model);
            listemployee.add(model);
        });
        return listemployee;
    }
    @QueryHandler
    public EmployeeResponseCommonModel handle(GetDetailsEmployeeQuery getDetailsEmployeeQuery){
        EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
        Employee employee= employeeRepository.getById(getDetailsEmployeeQuery.getEmployeeId());
        BeanUtils.copyProperties(employee,model);
        return  model;
    }







}
