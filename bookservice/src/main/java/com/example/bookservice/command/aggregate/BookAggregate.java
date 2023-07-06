package com.example.bookservice.command.aggregate;

import com.example.bookservice.command.command.CreateBookCommand;
import com.example.bookservice.command.command.DeleteBookCommand;
import com.example.bookservice.command.command.UpdateBookCommand;
import com.example.bookservice.command.event.BookCreatedEvent;
import com.example.bookservice.command.event.BookDeleteEvent;
import com.example.bookservice.command.event.BookUpdateEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@AllArgsConstructor
@NoArgsConstructor
public class BookAggregate {
    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand) {
        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
        //copy all attribute of obejct createBookCommand to object bookCreatedEvent
        // same same such as convert attribute syntax:
//       // bookCreatedEvent.setAuthor(createBookCommand.getAuthor());
        BeanUtils.copyProperties(createBookCommand,bookCreatedEvent);
        //emit event this

        AggregateLifecycle.apply(bookCreatedEvent);
    }
    @CommandHandler
    public void handle(UpdateBookCommand updateBookCommand){
        BookUpdateEvent bookUpdateEvent = new BookUpdateEvent();
        BeanUtils.copyProperties(updateBookCommand,bookUpdateEvent);
        AggregateLifecycle.apply(bookUpdateEvent);
    }





    // sau khi nó xong cái khoiois code trên ( public BookAggregate) thì nó sẽ nhảy vào đây, mục đích của hàm này
//    là nó lấy dữ lieuj từ tk BookCreateEvent sau ddó đưa cho tk Book|Aggregtae
    @EventSourcingHandler
    // nó cập nhật lại để nó lấy dữ liệu của thằng BookCreatedEvent  và cập nhật lại cho tk BookAggregate
    // thì khi cập nhật lại nó sẽ biết là sự kiện thêm sách đó nó thay đổi trg dữ liệu nào
    // trên cái event đó sẽ luuwu lại lịch sử cảu sự kiện sẽ thấy dc trg nào dữ liệu đã thay đổi
    // sau khi cập nhật xong thì mk sẽ cần có 1 cái để xử lí cái thay đổi đó
    public void on(BookCreatedEvent event ){
        this.bookId = event.getBookId();
        this.name = event.getName();
        this.isReady = event.getIsReady();
        this.author =event.getAuthor();
    }

    // hứng suwj kiện handle của updateBook

    @EventSourcingHandler
    public void on(BookUpdateEvent event ){
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.name = event.getName();
        this.isReady = event.getIsReady();
    }

    // xoa sach
    @CommandHandler
    public void handle(DeleteBookCommand deleteBookCommand){
        BookDeleteEvent bookDeleteEvent = new BookDeleteEvent(deleteBookCommand.getBookId());

        AggregateLifecycle.apply(bookDeleteEvent);
    }
    @EventSourcingHandler
    public void on(BookDeleteEvent event ){
        this.bookId = event.getBookId();

    }

}
