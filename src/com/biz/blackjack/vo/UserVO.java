package com.biz.blackjack.vo;

// 유저정보를 담을 VO Class
public class UserVO {
	
	private String strDate;		// 최근 플레이 일자
	private float floatTotal;	// 총 플레이한 게임 수
	private float floatWin;		// 승리한 게임 수
	private float floatLose;	// 패배한 게임 수
	
	// VO에 값을 저장하기 편리하게 하기 위한 생성자 생성
	public UserVO() {
		super();
	}

	public UserVO(String strDate, float floatTotal, float floatWin, float floatLose) {
		super();
		this.strDate = strDate;
		this.floatTotal = floatTotal;
		this.floatWin = floatWin;
		this.floatLose = floatLose;
	}

	//외부에서 접근할 getter와 setter 생성
	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
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
	//VO에 저장된 값을 확인하기 위한 toString Override
	@Override
	public String toString() {
		return "UserVO [strDate=" + strDate + ", floatTotal=" + floatTotal + ", floatWin="
				+ floatWin + ", floatLose=" + floatLose + "]";
	}

	
	

}
