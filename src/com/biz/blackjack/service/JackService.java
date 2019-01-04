package com.biz.blackjack.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.biz.blackjack.vo.BlackJackVO;
import com.biz.blackjack.vo.UserVO;

public class JackService {
	
	List<BlackJackVO> deck;
	List<BlackJackVO> dealer;
	List<BlackJackVO> user;
	BlackService bs;
	Scanner sc;
	String strID;
	
	public JackService(String strID) {
		
		this.strID=strID;
		bs=new BlackService(strID);
		dealer=new ArrayList<>();
		user=new ArrayList<>();
		sc=new Scanner(System.in);
	}
	
	public void gameMain() {
		
		UserVO vo=bs.readFile();
		int index=0;
		boolean b=true;
		while(b) {
			deck=bs.makeDeck();
			Collections.shuffle(deck);
			dealer.clear();
			user.clear();
			this.record(vo);
			System.out.println(">> 게임을 시작합니다.");
			index=getFirstCard(index);
			getMoreCard(index);
			checkSum(vo);
			LocalDate ld=LocalDate.now();
			String strDate=ld.toString();
			vo.setStrDate(strDate);
			bs.writeFile(vo);
			System.out.println(">> 계속 하시겠습니까?");
			System.out.println(">> 1.예  2.아니오");
			System.out.print(">> ");
			String strInput=sc.nextLine();
			b=checkInput(strInput);
		}
	}
	
	public int getFirstCard(int index) {
		
		for(int i=0; i<2; i++) {
			dealer.add(deck.get(index++));
			System.out.println(">> ");
			System.out.println(">> 딜러가 카드를 한장 뽑았습니다.");
			user.add(deck.get(index++));
			System.out.println(">> "+strID+"이(가) 카드를 한장 뽑았습니다.");
			checkUserCard();
		}
		return index;
	}
	
	public void getMoreCard(int index) {
		
		boolean dealer=true;
		boolean user=true;
		
		while(dealer||user) {
			if(dealer=checkDealer()) index=getDealer(index);
			if(user) {
				if(user=checkUser()) index=getUser(index);
			}
			if(index>52) {
				System.out.println(">> 카드가 떨어졌습니다.");
				break;
			}
		}
	}
	
	public boolean checkDealer() {
		
		int intDealer=0;
		for(BlackJackVO vo:dealer) {
			intDealer+=vo.getCardVal();
		}
		for(BlackJackVO vo:dealer) {
			if(intDealer<11&&vo.getCardVal()==1) {
				vo.setCardVal(11);
				intDealer+=10;
			}
			if(intDealer>21&&vo.getCardVal()==11) {
				vo.setCardVal(1);
				intDealer-=10;
			}
		}
		if(intDealer<17) return true;
		return false;
	}
	
	public boolean checkUser() {
		
		System.out.println(">> ");
		System.out.println(">> 카드를 더 뽑으시겠습니까?");
		System.out.println(">> 1.예  2.아니오");
		System.out.print(">> ");
		String strInput=sc.nextLine();
		if(checkInput(strInput)) return true;
		return false;
	}
	
	public int getDealer(int index) {
		dealer.add(deck.get(index++));
		System.out.println(">> ");
		System.out.println(">> 딜러가 카드를 한장 뽑았습니다.");
		return index;
	}
	
	public int getUser(int index) {
		user.add(deck.get(index++));
		System.out.println(">> "+strID+"이(가) 카드를 한장 뽑았습니다.");
		checkUserCard();
		return index;
	}
	
	public void checkUserCard() {
		
		System.out.print(">> "+strID+"카드 : ");
		int intSum=0;
		for(BlackJackVO vo:user) {
			System.out.print(vo.getCardShape()+vo.getCardNum()+"("+vo.getCardVal()+") ");
			intSum+=vo.getCardVal();
		}
		System.out.println(" - 합계 : "+intSum);
	}
	
	public void checkSum(UserVO uvo) {
		
		int intDealerSum=0;
		int intUserSum=0;
		System.out.print(">> 딜러카드 : ");
		for(BlackJackVO vo:dealer) {
			intDealerSum+=vo.getCardVal();
			System.out.print(vo.getCardShape()+vo.getCardNum()+"("+vo.getCardVal()+") ");
			
		}
		System.out.println(" = 합계 : "+intDealerSum);
		for(BlackJackVO vo:user) {
			if(vo.getCardVal()==1) {
				System.out.println(">> A를 1(1)번 or 2(11)로 쓰시겠습니까?");
				System.out.print(">> ");
				String strOR=sc.nextLine();
				if(!checkInput(strOR)) vo.setCardVal(11);
			}
			intUserSum+=vo.getCardVal();
		}
		checkUserCard();
		System.out.println(">> ");
		if(intUserSum>21) {
			System.out.println(">> "+strID+"이(가) 21을 초과하여 게임에서 패배하였습니다.");
			uvo.setFloatLose(uvo.getFloatLose()+1f);
			uvo.setFloatTotal(uvo.getFloatTotal()+1f);
			return;
		}
		if(intDealerSum>21) {
			System.out.println(">> 딜러가 21을 초과하여 게임에서 승리하였습니다.");
			uvo.setFloatWin(uvo.getFloatWin()+1f);
			uvo.setFloatTotal(uvo.getFloatTotal()+1f);
			return;
		}
		if(intUserSum>intDealerSum) {
			System.out.println(">> 게임에서 승리하였습니다.");
			uvo.setFloatWin(uvo.getFloatWin()+1f);
			uvo.setFloatTotal(uvo.getFloatTotal()+1f);
			return;
		}
		uvo.setFloatLose(uvo.getFloatLose()+1f);
		uvo.setFloatTotal(uvo.getFloatTotal()+1f);
		System.out.println(">> 게임에서 패배하였습니다.");
	}
	
	public void record(UserVO vo) {
		
		float rate=vo.getFloatWin()/vo.getFloatTotal()*100;
		System.out.println(">> Recent Play Date : "+vo.getStrDate());
		System.out.printf(">> Win : %.0f Lose : %.0f",vo.getFloatWin(),vo.getFloatLose());
		System.out.printf(" Rate : %.2f", rate);
		System.out.println("%");
		System.out.println(">> ");
	}
	
	public boolean checkInput(String strInput) {
		
		try {
			int intInput=Integer.valueOf(strInput);
			if(intInput==1) return true;
		} catch (NumberFormatException e) {
			return false;
		}
		return false;
	}
}
