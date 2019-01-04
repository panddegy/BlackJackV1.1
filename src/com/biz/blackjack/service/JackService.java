package com.biz.blackjack.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.biz.blackjack.vo.BlackJackVO;
import com.biz.blackjack.vo.UserVO;

// BlackJack 게임을 진행할 Service Class
public class JackService {
	
	List<BlackJackVO> deck;		// 카드 덱
	List<BlackJackVO> dealer;	// 딜러가 받을 카드
	List<BlackJackVO> user;		// 유저가 받을 카드
	BlackService bs;			// 덱 구성과 유저정보를 관리하는 객체
	Scanner sc;					
	String strID;
	
	public JackService(String strID) {
		
		// Main Method에서 전달받은 ID 매개변수를 다시 BlackService에 전달
		this.strID=strID;
		bs=new BlackService(strID);
		// 각 리스트 초기화
		dealer=new ArrayList<>();
		user=new ArrayList<>();
		sc=new Scanner(System.in);
	}
	
	public void gameMain() {
		
		// User 정보를 file에서 읽어옴
		UserVO vo=bs.readFile();
		// 게임 진행중에 뽑을 카드의 순서를 정하는 변수
		int index=0;
		// 반복진행을 결정할 변수
		boolean b=true;
		while(b) {
			// 카드 덱 생성과 shuffle
			deck=bs.makeDeck();
			Collections.shuffle(deck);
			// 각 리스트 clear
			dealer.clear();
			user.clear();
			// 유저의 플레이 정보를 표시
			this.record(vo);
			// 게임 시작
			System.out.println(">> 게임을 시작합니다.");
			// index변수를 getFirstCard로 전달하고 
			// return값을 다시 index변수에 저장
			index=getFirstCard(index);
			// 카드 추가 Method에 index변수 전달
			getMoreCard(index);
			// 딜러와 유저의 각 합계를 구하고 승패를 결정하는 Method
			// 불러온 유저정보 vo를 매개변수로 전달
			checkSum(vo);
			// 오늘날짜를 저장한 변수 생성
			LocalDate ld=LocalDate.now();
			String strDate=ld.toString();
			vo.setStrDate(strDate);
			// 게임이 끝난 유저의 정보가 담긴 vo를 file에 저장
			bs.writeFile(vo);
			// 다시하기를 물어보고 입력된 값을 checkInput()에 전달
			System.out.println(">> 계속 하시겠습니까?");
			System.out.println(">> 1.예  2.아니오");
			System.out.print(">> ");
			String strInput=sc.nextLine();
			// checkInput()에서 return 값을 boolean b에 저장
			b=checkInput(strInput);
		}
	}
	
	// 딜러와 유저가 처음2장씩 카드를 받는 Method
	public int getFirstCard(int index) {
		
		// for를 이용하여 2회 반복
		for(int i=0; i<2; i++) {
			// 매개변수로 전달 받은 index값에 해당하는 deckList의 카드를
			// dealerList에 전달하고 ++
			dealer.add(deck.get(index++));
			System.out.println(">> ");
			System.out.println(">> 딜러가 카드를 한장 뽑았습니다.");
			// ++된 index값에 해당하는 deckList의 카드를
			// userList에 저장하고 ++
			user.add(deck.get(index++));
			System.out.println(">> "+strID+"이(가) 카드를 한장 뽑았습니다.");
			// checkUserCard()를 통해 유저의 카드를 콘솔에 표시
			checkUserCard();
		}
		// ++로 증가된 index값을 return
		return index;
	}
	
	// 카드를 추가로 뽑을지 결정하는 Method
	public void getMoreCard(int index) {
		
		// dealer와 user의 선택을 저장할 변수
		boolean dealer=true;
		boolean user=true;
		
		// dealer와 user가 모두 false일때까지 반복
		while(dealer||user) {
			if(dealer=checkDealer()) index=getDealer(index);
			if(user) {
				if(user=checkUser()) index=getUser(index);
			}
			// index가 52를 초과하면 break
			if(index>52) {
				System.out.println(">> 카드가 떨어졌습니다.");
				break;
			}
		}
	}
	
	// dealer가 카드를 추가로 뽑을 것을 결정하는 Method
	public boolean checkDealer() {
		
		// dealer가 가진 카드값의 합
		int intDealer=0;
		for(BlackJackVO vo:dealer) {
			intDealer+=vo.getCardVal();
		}
		// 카드 A의 값을 1 or 11로 사용할지 여부를 결정
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
		// 딜러의 카드합이 17미만이면 true를 return
		if(intDealer<17) return true;
		// 17이상이면 false를 return
		return false;
	}
	
	// user가 카드를 추가로 뽑을것 인지를 결정하는 Method
	public boolean checkUser() {
		
		System.out.println(">> ");
		System.out.println(">> 카드를 더 뽑으시겠습니까?");
		System.out.println(">> 1.예  2.아니오");
		System.out.print(">> ");
		String strInput=sc.nextLine();
		if(checkInput(strInput)) return true;
		return false;
	}
	
	// 딜러가 추가로 카드를 뽑을 경우의 Method
	public int getDealer(int index) {
		dealer.add(deck.get(index++));
		System.out.println(">> ");
		System.out.println(">> 딜러가 카드를 한장 뽑았습니다.");
		return index;
	}
	
	// user가 추가로 카드르 뽑을 경우의 Method
	public int getUser(int index) {
		user.add(deck.get(index++));
		System.out.println(">> "+strID+"이(가) 카드를 한장 뽑았습니다.");
		checkUserCard();
		return index;
	}
	
	// 현재 user가 가지고 있는 카드를 콘솔에 표시하는 Method
	public void checkUserCard() {
		
		System.out.print(">> "+strID+"카드 : ");
		int intSum=0;
		for(BlackJackVO vo:user) {
			System.out.print(vo.getCardShape()+vo.getCardNum()+"("+vo.getCardVal()+") ");
			intSum+=vo.getCardVal();
		}
		System.out.println(" - 합계 : "+intSum);
	}
	
	// 게임이 끝난 후 승패를 결정하는 Method
	public void checkSum(UserVO uvo) {
		// user 정보가 담긴 vo를 매개변수로 받음
		int intDealerSum=0;
		int intUserSum=0;
		System.out.print(">> 딜러카드 : ");
		for(BlackJackVO vo:dealer) {
			intDealerSum+=vo.getCardVal();
			System.out.print(vo.getCardShape()+vo.getCardNum()+"("+vo.getCardVal()+") ");
			
		}
		System.out.println(" = 합계 : "+intDealerSum);
		// user가 카드 A를 가지고 있으면 1 or 11로 사용할지 결정
		for(BlackJackVO vo:user) {
			if(vo.getCardVal()==1) {
				System.out.println(">> ");
				System.out.println(">> A를 1(1)번 or 2(11)로 쓰시겠습니까?");
				System.out.print(">> ");
				String strOR=sc.nextLine();
				if(!checkInput(strOR)) vo.setCardVal(11);
			}
			intUserSum+=vo.getCardVal();
		}
		checkUserCard();
		System.out.println(">> ");
		// 게임의 승패를 결정하고 콘솔에 표시
		// 그 결과를 UserVO에 저장
		if(intUserSum>21) {
			System.out.println(">> "+strID+"이(가) 21을 초과하여 게임에서 패배하였습니다.");
			System.out.println(">> ");
			uvo.setFloatLose(uvo.getFloatLose()+1f);
			uvo.setFloatTotal(uvo.getFloatTotal()+1f);
			return;
		}
		if(intDealerSum>21) {
			System.out.println(">> 딜러가 21을 초과하여 게임에서 승리하였습니다.");
			System.out.println(">> ");
			uvo.setFloatWin(uvo.getFloatWin()+1f);
			uvo.setFloatTotal(uvo.getFloatTotal()+1f);
			return;
		}
		if(intUserSum>intDealerSum) {
			System.out.println(">> 게임에서 승리하였습니다.");
			System.out.println(">> ");
			uvo.setFloatWin(uvo.getFloatWin()+1f);
			uvo.setFloatTotal(uvo.getFloatTotal()+1f);
			return;
		}
		if(intUserSum==intDealerSum) {
			System.out.println(">> 게임에서 비기셨습니다.");
			System.out.println(">> ");
			return;
		}
		uvo.setFloatLose(uvo.getFloatLose()+1f);
		uvo.setFloatTotal(uvo.getFloatTotal()+1f);
		System.out.println(">> 게임에서 패배하였습니다.");
		System.out.println(">> ");
	}
	
	public void record(UserVO vo) {
		
		// UserVO에 저장된 값을 콘솔에 표시
		float rate=vo.getFloatWin()/vo.getFloatTotal()*100;
		System.out.println(">> Recent Play Date : "+vo.getStrDate());
		System.out.printf(">> Win : %.0f Lose : %.0f",vo.getFloatWin(),vo.getFloatLose());
		System.out.printf(" Rate : %.2f", rate);
		System.out.println("%");
		System.out.println(">> ");
	}
	
	// Scanner로 입력 받은 값을 확인하는 Method
	public boolean checkInput(String strInput) {
		
		// 변환이 안될경우를 대비한 try-catch
		// 입력받은 값이 1이면 true 아니면 false return
		try {
			int intInput=Integer.valueOf(strInput);
			if(intInput==1) return true;
		} catch (NumberFormatException e) {
			return false;
		}
		return false;
	}
}
