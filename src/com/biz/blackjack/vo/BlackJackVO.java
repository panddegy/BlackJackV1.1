package com.biz.blackjack.vo;

public class BlackJackVO {

	private String cardShape;
	private String cardNum;
	private int cardVal;
	
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
	
	@Override
	public String toString() {
		return "BlackJackVO [cardShape=" + cardShape + ", cardNum=" + cardNum + ", cardVal=" + cardVal + "]";
	}
	
	
	
}
