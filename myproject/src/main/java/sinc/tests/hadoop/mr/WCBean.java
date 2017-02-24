package sinc.tests.hadoop.mr;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class WCBean implements WritableComparable<WCBean>{
	private String account;
	private double income;
	private double expenses;
	private double surplus;
	
	public void write(DataOutput out) throws IOException {
		out.writeUTF(account);
		out.writeDouble(income);
		
		
	}

	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public int compareTo(WCBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double inCome) {
		this.income = inCome;
	}

	public double getExpenses() {
		return expenses;
	}

	public void setExpenses(double expenses) {
		this.expenses = expenses;
	}

	public double getSurplus() {
		return surplus;
	}

	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}

}
