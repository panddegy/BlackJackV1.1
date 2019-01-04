package com.biz.blackjack.exec;

import java.util.Scanner;

import com.biz.blackjack.service.JackService;

public class BlackJackExec {

	public static void main(String[] args) {

		Scanner sc=new Scanner(System.in);
		
		// 메뉴
		logo();
		System.out.println(">> 1. 시작  2. 종료");
		System.out.print(">> ");
		String strInput=sc.nextLine();
		// 2 입력시 종료
		if(checkMenu(strInput)) {
			System.out.println(">> 안녕히 가세요.");
			return;
		}
		// 입력받은 ID를 변수에 담아 JackService에 전달
		System.out.println(">> ID를 입력하세요.");
		System.out.print(">> ");
		String strID=sc.nextLine();
		JackService js=new JackService(strID);
		// 게임시작
		js.gameMain();
		System.out.println(">> 안녕히 가세요.");
		
	}
	// Scanner로 입력 받은 값을 확인하는 Method
	public static boolean checkMenu(String strInput) {

		// 변환이 안될경우를 대비한 try-catch
		// 입력받은 값이 1이면 true 아니면 false return
		try {
			int intInput=Integer.valueOf(strInput);
			if(intInput!=1) return true;
		} catch (NumberFormatException e) {
			return true;
		} 
		return false;
	}
	// 로고
	public static void logo() {
		
		System.out.println("==================================================");
		System.out.println("                  BlackJack v1.1");
		System.out.println("==================================================");
	}
}
