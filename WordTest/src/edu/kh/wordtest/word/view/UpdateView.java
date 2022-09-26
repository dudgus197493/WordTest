package edu.kh.wordtest.word.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UpdateView {
	private Scanner sc = new Scanner(System.in);
	
	public void updateMenu() {
		int input = -1;
		try {
			do {
				System.out.println("\n***** 단어 추가 메뉴 *****\n");
				System.out.println("[1. 새로운 단어 추가]");
				System.out.println("[2. 기존단어 뜻 추가]");
				System.out.println("[0. 이전 화면으로]");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: break;
				case 2: break;
				case 3: break;
				case 4: break;
				case 0: System.out.println("\n[이전 화면으로 돌아갑니다.]\n"); break;
				default:System.out.println("\n[메뉴에 있는 번호를 입력해주세요]\n");
				}
			} while(input != 0);
			
		}catch(InputMismatchException e) {
			System.out.println("입력 형식이 올바르지 않습니다.");
			e.printStackTrace();
		}
	}
}
