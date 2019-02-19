package com.capgemini.services;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.capgemini.beans.Customer;
import com.capgemini.exceptions.ErrorDuringTransaction;
import com.capgemini.exceptions.InsufficientBalance;
import com.capgemini.exceptions.MobileNumbeDoesNotExist;
import com.capgemini.exceptions.MobileNumberAlreadyExist;

public interface WalletService {
	public Customer createAccount(String name,String mobileno, BigDecimal amount) throws MobileNumberAlreadyExist, ClassNotFoundException, SQLException;
	public Customer showBalance(String mobileno) throws MobileNumbeDoesNotExist, ClassNotFoundException, SQLException;
	public List<Customer> fundTransfer(String sourceMobileno, String destinationMobileno, BigDecimal amount) throws MobileNumbeDoesNotExist, InsufficientBalance, ErrorDuringTransaction, ClassNotFoundException, SQLException;
	public Customer depositAmount(String mobileno, BigDecimal amount) throws MobileNumbeDoesNotExist, ClassNotFoundException, SQLException;
	public Customer withdrawAmount(String mobileno, BigDecimal amount) throws MobileNumbeDoesNotExist, InsufficientBalance, ClassNotFoundException, SQLException;
}
