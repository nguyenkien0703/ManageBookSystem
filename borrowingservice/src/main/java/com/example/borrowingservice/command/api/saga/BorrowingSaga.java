package com.example.borrowingservice.command.api.saga;

import com.example.borrowingservice.command.api.events.BorrowCreateEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.commonservice.query.GetDetailsBookQuery;
@Saga
public class BorrowingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowCreateEvent event){
        System.out.println("borrowCreatedEvent in saga for BookId: " + event.getBookId()+ " and EmployeeId: " + event.getEmployeeId());
        try{
            SagaLifecycle.associateWith("bookId",event.getBookId());
            GetDetailsBookQuery bookQuery = new GetDetailsBookQuery(event.getBookId());
            BookResponseCommonModel bookResponseModel = queryGateway.query(getDetailBookQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
            if(bookResponseModel.getIsReady() == true ) {
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(),false,event.getEmployeeId(),event.getId());
                commandGateway.sendAndWait(command);
            }else {
                throw new Exception("Sach da co nguoi muon roi ");
            }

        }catch (Exception e ){
            rollBackBorrowRecord(event.getId());
            System.out.println(e.getMessage());
        }
    }



}
