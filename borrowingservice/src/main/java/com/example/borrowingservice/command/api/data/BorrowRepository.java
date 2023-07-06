package com.example.borrowingservice.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrowing,String > {

//    List<Borrowing> findByEmployeeIdAndReturnDateIsNull(String employeeId);
//    Borrowing findByEmployeeIdAnAndBookIdAndReturnDateIsNull(String employeeId, String bookId);


}
