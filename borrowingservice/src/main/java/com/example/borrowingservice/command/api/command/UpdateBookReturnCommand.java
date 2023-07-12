package com.example.borrowingservice.command.api.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;
@Data
@NoArgsConstructor
public class UpdateBookReturnCommand {
    @TargetAggregateIdentifier
    private String id ;

    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;
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
	public String getEmployee() {
		return employeeId;
	}
	public void setEmployee(String employeeId) {
		this.employeeId = employeeId;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public UpdateBookReturnCommand(String id, String bookId, String employeeId, Date returnDate) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.employeeId = employeeId;
		this.returnDate = returnDate;
	}
	

}
