package com.example.borrowingservice.command.api.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;
@Data
@NoArgsConstructor

public class CreateBorrowCommand {
    @TargetAggregateIdentifier
    private String id ;

    private String bookId;
    private String employeeId;
    private Date borrowingDate;



    public CreateBorrowCommand(String bookId, String employeeId, Date borrowingDate,String id) {
		super();
		this.bookId = bookId;
		this.employeeId = employeeId;
		this.borrowingDate = borrowingDate;
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Date getBorrowingDate() {
		return borrowingDate;
	}
	public void setBorrowingDate(Date borrowingDate) {
		this.borrowingDate = borrowingDate;
	}

}
