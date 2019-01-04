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

public class BlackService {

	List<BlackJackVO> deck;
	String filePath;
	
	public BlackService(String strID) {
		
		deck=new ArrayList<>();
		this.makeCard();
		this.filePath="src/com/biz/blackjack/"+strID+".txt";
	}
	
	public List<BlackJackVO> makeDeck(){
		return deck;
	}
	
	public void makeCard() {
		
		String[] shape={"Diamond","Spade","Clover","Heart"};
		for(String s:shape) {
			addDeck(s);
		}
	}
	
	public void addDeck(String shape) {
		
		String[] num= {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
		int cardVal=1;
		for(String s:num) {
			BlackJackVO vo=new BlackJackVO();
			vo.setCardShape(shape);
			vo.setCardNum(s);
			vo.setCardVal(cardVal++);
			if(cardVal>10) cardVal=10;
			deck.add(vo);
		}
	}
	
	public UserVO readFile() {
		
		FileReader fr;
		BufferedReader buffer;
		
		try {
			fr=new FileReader(filePath);
			buffer=new BufferedReader(fr);
			String reader=buffer.readLine();
			String[] readArr=reader.split(":");
			UserVO vo=new UserVO();
			vo.setStrDate(readArr[0]);
			vo.setFloatWin(Float.valueOf(readArr[1]));
			vo.setFloatLose(Float.valueOf(readArr[2]));
			vo.setFloatTotal(Float.valueOf(readArr[3]));
			buffer.close();
			fr.close();
			return vo;
		} catch (FileNotFoundException e) {
			UserVO vo=new UserVO();
			LocalDate ld=LocalDate.now();
			String strDate=ld.toString();
			vo.setStrDate(strDate);
			vo.setFloatWin(0f);
			vo.setFloatLose(0f);
			vo.setFloatTotal(0f);
			this.writeFile(vo);
			return vo;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeFile(UserVO vo) {
		
		PrintWriter pw;
		
		try {
			pw=new PrintWriter(filePath);
			String writer=vo.getStrDate()+":"+vo.getFloatWin()+":"+vo.getFloatLose()+":"+vo.getFloatTotal();
			pw.println(writer);
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}





















