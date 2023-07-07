package com.example.borrowingservice.command.api.saga;

import com.example.borrowingservice.command.api.command.DeleteBorrowCommand;
import com.example.borrowingservice.command.api.data.BorrowRepository;
import com.example.borrowingservice.command.api.events.BorrowCreateEvent;
import com.example.commonservice.command.UpdateStatusBookCommand;
import com.example.commonservice.model.BookResponseCommonModel;
import com.example.commonservice.query.GetDetailsBookQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class BorrowingSaga {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowCreateEvent event){
        // sau khi mượn xong 1 phát gồm bookId và employeeId thì thành công là nó sẽ nhảy vảo đây
        System.out.println("=======1 event muon sach da duoc tao ra =========");
        System.out.println("borrowCreatedEvent in saga for BookId: " + event.getBookId()+ " and EmployeeId: " + event.getEmployeeId());
        try{
            // đây là saga đang quản lí transaction của cái book có id là bookId
            SagaLifecycle.associateWith("bookId",event.getBookId());
            GetDetailsBookQuery getDetailBookQuery = new GetDetailsBookQuery(event.getBookId());
            BookResponseCommonModel bookResponseModel = queryGateway.query(getDetailBookQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
            if(bookResponseModel.getIsReady() == true ) {// true: là chưa có ai mượn sách này cả
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
    private void rollBackBorrowRecord(String id ){
        commandGateway.sendAndWait(new DeleteBorrowCommand(id));
    }




}
