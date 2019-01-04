package com.biz.blackjack.vo;

public class UserVO {
	
	private String strDate;
	private int intBal;
	private float floatTotal;
	private float floatWin;
	private float floatLose;
	
	
	public String getStrDate() {
		return strDate;
	}


	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}


	public int getIntBal() {
		return intBal;
	}


	public void setIntBal(int intBal) {
		this.intBal = intBal;
	}


	public float getFloatTotal() {
		return floatTotal;
	}


	public void setFloatTotal(float floatTotal) {
		this.floatTotal = floatTotal;
	}


	public float getFloatWin() {
		return floatWin;
	}


	public void setFloatWin(float floatWin) {
		this.floatWin = floatWin;
	}


	public float getFloatLose() {
		return floatLose;
	}


	public void setFloatLose(float floatLose) {
		this.floatLose = floatLose;
	}


	@Override
	public String toString() {
		return "UserVO [strDate=" + strDate + ", intBal=" + intBal + ", floatTotal=" + floatTotal + ", floatWin="
				+ floatWin + ", floatLose=" + floatLose + "]";
	}

	
	

}
