package com.biz.blackjack.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.biz.blackjack.vo.BlackJackVO;
import com.biz.blackjack.vo.UserVO;

// 카드 Deck 구성과 User정보를 Read/Write 하는 Service Class
public class BlackService {

	List<BlackJackVO> deck;	// Card Deck List
	String filePath;		// User 정보를 저장할 파일경로 선언
	
	// BlackService 생성자 Class에서 User의 ID를 매개변수로 받음
	public BlackService(String strID) {
		
		// List 초기화와 makeCard()를 사용하여 Deck 구성
		deck=new ArrayList<>();
		this.makeCard();
		// 매개변수로 전달받은 ID로 파일경로를 만들고 변수에 저장
		this.filePath="src/com/biz/blackjack/"+strID+".txt";
	}
	
	// 만들어진 deckList를 다른 Class로 Return하기 위한 Method
	public List<BlackJackVO> makeDeck(){
		return deck;
	}
	
	// 카드 모양을 만들기 위한 Method
	public void makeCard() {
		
		// 배열에 4가지 모양을 저장하고 for를 이용하여 반복하여 addDeck()에 전달
		String[] shape={"Diamond","Spade","Clover","Heart"};
		for(String s:shape) {
			addDeck(s);
		}
	}
	// 카드 숫자를 만들기 위한 Method
	public void addDeck(String shape) {
		
		// 배열에 A~K를 저장하고 Value에 저장할 정수형 변수를 선언하고 1을 저장
		String[] num= {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
		int cardVal=1;
		// for를 사용하여 BlackJackVO를 선언하고 vo에 값을 저장
		for(String s:num) {
			BlackJackVO vo=new BlackJackVO(shape, s, cardVal++);
			// Value에 저장할 값은 ++를 이용하여 저장후 1씩 증가
			// Value에 저장될 값이 10을 초과하면 10으로 저장
			if(cardVal>10) cardVal=10;
			// deck 리스트에 vo를 추가
			deck.add(vo);
		}
	}
	
	// User 정보를 file에서 읽어오는 Method
	public UserVO readFile() {
		
		FileReader fr;
		BufferedReader buffer;
		
		try {
			fr=new FileReader(filePath);
			buffer=new BufferedReader(fr);
			String reader=buffer.readLine();
			String[] readArr=reader.split(":");
			UserVO vo=new UserVO(readArr[0],
				Float.valueOf(readArr[1]),
				Float.valueOf(readArr[2]),
				Float.valueOf(readArr[3]));
			buffer.close();
			fr.close();
			return vo;
		} catch (FileNotFoundException e) {
			// 파일경로를 못찾을 경우(신규 사용자의 경우)
			// VO를 생성하고 0을 저장하고 write()에 전달하여 파일 생성
			LocalDate ld=LocalDate.now();
			String strDate=ld.toString();
			UserVO vo=new UserVO(strDate, 0f, 0f, 0f);
			this.writeFile(vo);
			return vo;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// User 정보를 file에 저장하는 Method
	public void writeFile(UserVO vo) {
		
		PrintWriter pw;
		
		try {
			pw=new PrintWriter(filePath);
			String writer=vo.getStrDate()+":"+vo.getFloatWin()+":"+
					vo.getFloatLose()+":"+vo.getFloatTotal();
			pw.println(writer);
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}





















