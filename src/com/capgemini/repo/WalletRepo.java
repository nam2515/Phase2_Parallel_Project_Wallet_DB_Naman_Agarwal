package com.capgemini.repo;

import java.sql.SQLException;
import com.capgemini.beans.Customer;

public interface WalletRepo {

	boolean save(Customer customer) throws ClassNotFoundException, SQLException;
	Customer findOne(String mobileno) throws ClassNotFoundException, SQLException;
	public boolean depositMoney(Customer customer) throws ClassNotFoundException, SQLException;
	boolean withdrawMoney(Customer customer) throws ClassNotFoundException, SQLException;
	
}