package com.biz.blackjack.exec;

import java.util.Scanner;

import com.biz.blackjack.service.JackService;

public class BlackJackExec {

	public static void main(String[] args) {

		Scanner sc=new Scanner(System.in);
		
		logo();
		System.out.println(">> 1. 시작  2. 종료");
		System.out.print(">> ");
		String strInput=sc.nextLine();
		if(checkMenu(strInput)) {
			System.out.println(">> 안녕히 가세요.");
			return;
		}
		System.out.println(">> ID를 입력하세요.");
		System.out.print(">> ");
		String strID=sc.nextLine();
		JackService js=new JackService(strID);
		js.gameMain();
		System.out.println(">> 안녕히 가세요.");
		
	}
	
	public static boolean checkMenu(String strInput) {

		try {
			int intInput=Integer.valueOf(strInput);
			if(intInput!=1) return true;
		} catch (NumberFormatException e) {
			return true;
		} 
		return false;
	}
	
	public static void logo() {
		
		System.out.println("==================================================");
		System.out.println("                  BlackJack v1.1");
		System.out.println("==================================================");
	}
}
