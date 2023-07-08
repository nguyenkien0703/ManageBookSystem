package com.example.borrowingservice.command.api.saga;

import com.example.borrowingservice.command.api.command.DeleteBorrowCommand;
import com.example.borrowingservice.command.api.command.SendMessageCommand;
import com.example.borrowingservice.command.api.data.BorrowRepository;
import com.example.borrowingservice.command.api.events.BorrowCreateEvent;
import com.example.borrowingservice.command.api.events.BorrowDeleteEvent;
import com.example.commonservice.command.RollBackStatusBookCommand;
import com.example.commonservice.command.UpdateStatusBookCommand;
import com.example.commonservice.events.BookRollBackStatusEvent;
import com.example.commonservice.events.BookUpdateStatusEvent;
import com.example.commonservice.model.BookResponseCommonModel;
import com.example.commonservice.model.EmployeeResponseCommonModel;
import com.example.commonservice.query.GetDetailsBookQuery;
import com.example.commonservice.query.GetDetailsEmployeeQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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

        System.out.println("borrowCreatedEvent in saga for BookId: " + event.getBookId()+ " and EmployeeId: " + event.getEmployeeId());
        try{
            // đây là saga đang quản lí transaction của cái book có id là bookId
            SagaLifecycle.associateWith("bookId",event.getBookId());
            System.out.println(event.getBookId());
            System.out.println("chuan bi query");

            GetDetailsBookQuery getDetailBookQuery = new GetDetailsBookQuery(event.getBookId());
            BookResponseCommonModel bookResponseModel = queryGateway.query(getDetailBookQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
            System.out.println("1234567890");
            if(bookResponseModel.getIsReady() == true ) {// true: là chưa có ai mượn sách này cả
                System.out.println("1111111111");
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(),false,event.getEmployeeId(),event.getId());
                System.out.println("2222222222222222");
                commandGateway.sendAndWait(command);

            }else {
                throw new Exception("Sach da co nguoi muon roi ");
            }

        }catch (Exception e ){
            System.out.println("loi vai lon roi ");
            rollBackBorrowRecord(event.getId());
            System.out.println(new Date());
            System.out.println("loi roi bạn oi ======");
        }
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookUpdateStatusEvent event ){
        System.out.println("BookUpdateStatusEvent in saga for bookId: " + event.getBookId());
        try {
            GetDetailsEmployeeQuery getDetailsEmployeeQuery = new GetDetailsEmployeeQuery(event.getEmployeeId());
            EmployeeResponseCommonModel employeeResponseCommonModel = queryGateway.query(getDetailsEmployeeQuery,ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
            if(employeeResponseCommonModel.getIsDisciplined() == true ){
                throw  new Exception("nhan vien bi ki luat ");
            }else {
                commandGateway.sendAndWait(new SendMessageCommand(event.getBorrowId(), event.getEmployeeId(),"da muon sach thanh cong!!!!!"));
                SagaLifecycle.end();
            }
        }catch (Exception e ){
            System.out.println(e.getMessage());
            rollbackBookstatus(event.getBookId(),event.getEmployeeId(), event.getBorrowId());
        }
    }
    private void rollbackBookstatus(String bookId, String employeeId, String borrowId){
        SagaLifecycle.associateWith("bookId", bookId);
        RollBackStatusBookCommand command = new RollBackStatusBookCommand(bookId, true, employeeId,bookId);
        commandGateway.sendAndWait(command);
    }
    @SagaEventHandler(associationProperty = "bookId")
    public void handleRollBackBookStatus(BookRollBackStatusEvent event ){
        System.out.println("BookRollBackStatusEvent in Saga for bookId: " + event.getBookId());
        rollBackBorrowRecord(event.getBorrowId());

    }
    private void rollBackBorrowRecord(String id ){
        commandGateway.sendAndWait(new DeleteBorrowCommand(id));
    }
    @EndSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(BorrowDeleteEvent event ){
        System.out.println("BorrowDeleteEvent in Saga for Borrowing Id: " + event.getId());
        SagaLifecycle.end();
    }







}
