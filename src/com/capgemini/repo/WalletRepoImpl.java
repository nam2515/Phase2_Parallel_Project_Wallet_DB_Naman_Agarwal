package com.capgemini.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.capgemini.beans.Customer;
import com.capgemini.beans.Wallet;

public class WalletRepoImpl implements WalletRepo {
	//Customer customer=new Customer();
//	HashMap<String, Customer> map=new HashMap<>();
	
	/* (non-Javadoc)
	 * @see com.capgemini.repo.WalletRepo#save(com.capgemini.beans.Customer)
	 */
	@Override
	public boolean save(Customer customer) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.OracleDriver");
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Capgemini123");
		PreparedStatement smt=conn.prepareStatement("Insert into Client values("+customer.getMobileno()+","+"'"+customer.getName()+"'"+")");		
		PreparedStatement stmt=conn.prepareStatement("Insert into Wallet values("+customer.getMobileno()+","+customer.getWallet().getBalance()+")");
		stmt.executeQuery();
		smt.executeQuery();
		conn.close();
			return true ;
	}

	@Override
	public Customer findOne(String mobileno) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		Wallet wallet=new Wallet();
		Class.forName("oracle.jdbc.OracleDriver");
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Capgemini123");
		PreparedStatement smt= conn.prepareStatement("Select * from Wallet where mobileno="+mobileno+"" );
		//PreparedStatement smt2= conn.prepareStatement("Select * from Wallet where mobileno="+mobileno+"" );
		ResultSet rs=smt.executeQuery();
		//ResultSet rs2=smt2.executeQuery();
		
		
		Customer customer=null;
		//rs2.next();
		//String mob= rs2.getString(1);
//		if(mob!=null) {
//		customer=new Customer();
//		
		while(rs.next())
		{
			customer = new Customer();
			customer.setMobileno(rs.getString(1));
			wallet.setBalance(new BigDecimal(rs.getInt(2)));
			customer.setWallet(wallet);	
		}
	//	}
		conn.close();
			return customer;	
	}
	
	@Override
	public boolean depositMoney(Customer customer) throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.OracleDriver");
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Capgemini123");
		PreparedStatement smt=conn.prepareStatement("Update Wallet set balance="+customer.getWallet().getBalance()+"where mobileno="+customer.getMobileno()+"");
		int a=smt.executeUpdate();
		conn.close();
		if(a>=1)
			return true;
		return false;
	}
	@Override
	public boolean withdrawMoney(Customer customer) throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.OracleDriver");
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Capgemini123");
		PreparedStatement smt=conn.prepareStatement("Update Wallet set balance="+customer.getWallet().getBalance()+ "where mobileno="+customer.getMobileno());
		int a= smt.executeUpdate();
		conn.close();
		if(a>=1)
			return true;
		return false;
	}

}
