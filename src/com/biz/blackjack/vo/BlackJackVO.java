package com.biz.blackjack.vo;

// BlackJack 카드를 구성할 VO Class
public class BlackJackVO {

	private String cardShape;	// 카드 모양(Diamond, Spade, Heart, Clover)
	private String cardNum;		// 숫자 (A~K)
	private int cardVal;		// 값 (1~10)
	
	// VO에 값을 저장하기 편리하게 하기 위한 생성자 생성	
	public BlackJackVO() {
		super();
	}
	public BlackJackVO(String cardShape, String cardNum, int cardVal) {
		super();
		this.cardShape = cardShape;
		this.cardNum = cardNum;
		this.cardVal = cardVal;
	}
	//외부에서 접글할 getter와 setter 생성
	public String getCardShape() {
		return cardShape;
	}
	public void setCardShape(String cardShape) {
		this.cardShape = cardShape;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public int getCardVal() {
		return cardVal;
	}
	public void setCardVal(int cardVal) {
		this.cardVal = cardVal;
	}
	
	//VO에 저장된 값을 확인하기 위한 toString Override
	@Override
	public String toString() {
		return "BlackJackVO [cardShape=" + cardShape + ", cardNum=" + cardNum + ", cardVal=" + cardVal + "]";
	}
	
	
	
}
