package com.capgemini.services;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.beans.Customer;
import com.capgemini.beans.Wallet;
import com.capgemini.exceptions.ErrorDuringTransaction;
import com.capgemini.exceptions.InsufficientBalance;
import com.capgemini.exceptions.MobileNumbeDoesNotExist;
import com.capgemini.exceptions.MobileNumberAlreadyExist;
import com.capgemini.repo.WalletRepo;

public class WalletServiceImpl implements WalletService {
	WalletRepo walrepo;
	Customer customer;
	Wallet wallet;
	
	public WalletServiceImpl(WalletRepo walrepo) {
		super();
		this.walrepo = walrepo;
	}

	public Customer createAccount(String name, String mobileno, BigDecimal amount) throws MobileNumberAlreadyExist, ClassNotFoundException, SQLException {
		customer=new Customer();
		wallet=new Wallet();
		if(walrepo.findOne(mobileno)!=null)
		{
			throw new MobileNumberAlreadyExist();
		}
		customer.setName(name);
		customer.setMobileno(mobileno);
		wallet.setBalance(amount);
		customer.setWallet(wallet);
		walrepo.save(customer);
		return customer;
	}

	public Customer showBalance(String mobileno) throws MobileNumbeDoesNotExist, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Customer customer=walrepo.findOne(mobileno);
		if(customer==null)
		{
			throw new MobileNumbeDoesNotExist();
		}
		return customer;
	}

	public List<Customer> fundTransfer(String sourcemobileno, String destinationmobileno, BigDecimal amount) throws MobileNumbeDoesNotExist, InsufficientBalance, ErrorDuringTransaction, ClassNotFoundException, SQLException {
		List<Customer> l=new ArrayList<>();
		Customer c1=showBalance(sourcemobileno);
		Wallet w1=c1.getWallet();
		Customer c2=showBalance(destinationmobileno);
		Wallet w2=c2.getWallet();
		//Checking exception
		int res=w1.getBalance().compareTo(amount);
		if(res==-1)
		{
			throw new InsufficientBalance();
		}
		
		w1.setBalance(w1.getBalance().subtract(amount));
		c1.setWallet(w1);
		w2.setBalance(w2.getBalance().add(amount));
		c2.setWallet(w2);
		l.add(c1);
		l.add(c2);
		return l;
	}

	public Customer depositAmount(String mobileno, BigDecimal amount) throws MobileNumbeDoesNotExist, ClassNotFoundException, SQLException {
		customer=new Customer();
		customer =showBalance(mobileno);
		wallet=customer.getWallet();
		wallet.setBalance(wallet.getBalance().add(amount));
		customer.setWallet(wallet);
		walrepo.depositMoney(customer);
	return customer;
	}

	public Customer withdrawAmount(String mobileno, BigDecimal amount) throws MobileNumbeDoesNotExist, InsufficientBalance, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		customer =showBalance(mobileno);
		wallet=customer.getWallet();
		int res=wallet.getBalance().compareTo(amount);
		if(res==-1)
		{
			throw new InsufficientBalance();
		}
		wallet.setBalance(wallet.getBalance().subtract(amount));
		customer.setWallet(wallet);	
		boolean flag=walrepo.withdrawMoney(customer);
		if(flag==true)
			return customer;
		return null;
	}
	
}
