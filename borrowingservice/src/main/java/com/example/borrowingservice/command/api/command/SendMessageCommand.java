package com.example.borrowingservice.command.api.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;
@Data
@NoArgsConstructor
public class SendMessageCommand {
    @TargetAggregateIdentifier
    private String id;
    private String employeeId;
    private String message;
    public SendMessageCommand(String id, String employeeId, String message) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.message = message;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
