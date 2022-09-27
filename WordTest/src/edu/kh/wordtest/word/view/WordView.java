package edu.kh.wordtest.word.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.wordtest.main.view.MainView;

public class WordView {
	private int input = -1;
	private Scanner sc = new Scanner(System.in);
	private SelectView sView = new SelectView();
	private InsertView iView = new InsertView();
	private UpdateView uView = new UpdateView();
	private DeleteView dView = new DeleteView();
	
	
	public void wordMenu() {
		boolean isAdmin = MainView.loginMember.getAdminFlag().equals("Y");
		try {
			do {
				System.out.println("\n***** 단어 사전 *****\n");
				System.out.println("1. 단어 검색");
				if(isAdmin) {
					System.out.println("3. 단어 추가");
					System.out.println("4. 단어 수정");
					System.out.println("5. 단어 삭제");
				}
				System.out.println("0. 이전 화면으로");
				System.out.println("99 프로그램 종료");
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: sView.selectMenu(); break;
				case 2:  break;
				case 0: System.out.println("\n[이전화면으로...]\n"); break;
				case 99: System.out.println("\n[프로그램 종료]\n"); System.exit(0); break;
				case 3: case 4: case 5:
					if(isAdmin) {
						if(input == 3) {
							iView.insertMenu();
						}
						if(input == 4) {
							uView.updateMenu();
						}
						if(input == 5) {
							dView.deleteMenu();
						}
						break;
					}
				default: System.out.println("\n[메뉴에 있는 번호를 입력해주세요.]\n");
				}
			} while(input != 0);
			
		}catch(InputMismatchException e) {
			System.out.println("입력 형식이 올바르지 않습니다.");
			e.printStackTrace();
		}
	}
}
