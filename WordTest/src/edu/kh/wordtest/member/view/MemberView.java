package edu.kh.wordtest.member.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.wordtest.main.view.MainView;
import edu.kh.wordtest.member.model.service.MemberService;
import edu.kh.wordtest.member.model.service.MemberServiceImpl;
import edu.kh.wordtest.member.vo.Member;

public class MemberView {
	private Scanner sc = new Scanner(System.in);
	private MemberService service = new MemberServiceImpl();
	private int input = -1;
	/**
	 * 회원 메뉴
	 */
	public void memberMenu() {
		 /* 1. 내 정보 조회
		 * 2. 회원 목록 조회(아이디, 이름, 성별)
		 * 3. 내 정보 수정(이름, 성별)
		 * 4. 비밀번호변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)
		 * 5. 회원 탈퇴 */
		do {
			try {
				System.out.println("\n***** 회원 기능 *****\n");
				System.out.println("1. 내 정보 조회");
				System.out.println("2. 내 정보 수정(이름, 성별)");
				System.out.println("3. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)");	// 1. 한번에 받기
																						// 2. 비밀번호 일치 선확인 후 변경할 비밀번호 받기
				System.out.println("4. 회원 탈퇴");
				System.out.println("0. 메인메뉴로 이동");
				
				System.out.print("\n메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1:  break;
				case 2:  break;
				case 3: updateMemberPw(); break;
				case 4: secession(); break;
				case 5:  break;
				case 0: System.out.println("[메인 메뉴로 이동합니다.]"); break;
				default : System.out.println("메뉴에 작성된 번호만 입력해주세요.");
				}
				
			}catch(InputMismatchException e) {
				System.out.println("\n <<입력 형식이 올바르지 않습니다.>>");
				sc.nextLine();
				input = -1;
			}			
		}while(input != 0);
	}
	
	/**
	 * 비밀번호 변경
	 */
	private void updateMemberPw() {
		System.out.println("\n[비밀번호 변경]\n");
		
		try {
			// 비밀번호 인증
			String memberPw = null;
			String newMemberPw1 = null;
			String newMemberPw2 = null;
			while(true) {
				System.out.print("현재 비밀번호 (이전 화면으로 0) : ");
				memberPw = sc.next();
				if (memberPw.equals("0")) {
					System.out.println("\n[이전화면으로 돌아갑니다.]\n");
					return;
				}
				// 비밀번호 인증 서비스 호출
				int result = service.checkMemberPw(memberPw, MainView.loginMember.getMemberNo());
				
				if(result > 0) {
					System.out.println("\n[현재 비밀번호가 일치합니다.]\n");
					break;
				} else {
					System.out.println("\n[현재 비밀번호가 일치하지 않습니다.]\n");
				}
			}
			
			while(true) {
				System.out.print("새 비밀번호 (이전 화면으로 0) : ");
				newMemberPw1 = sc.next();
				
				System.out.print("새 비밀번호 확인 (이전 화면으로 0) : ");
				newMemberPw2 = sc.next();
				
				if(newMemberPw1.equals("0") || newMemberPw2.equals("0")) {
					System.out.println("\n[이전화면으로 돌아갑니다.]\n");
					return;
				}
				
				if(newMemberPw1.equals(newMemberPw2)) {
					System.out.println("\n[새 비밀번호가 일치합니다]\n");
					break;
				} else {
					System.out.println("\n[새 비밀번호가 일치하지 않습니다.]\n");
				}
			}
			
			int result = service.updateMemberPw(newMemberPw1, MainView.loginMember.getMemberNo());
			
			if(result > 0 ) {		// 수정 성공 시
				System.out.println("\n[비밀번호 변경에 성공했습니다.]\n");
			} else {				// 실패 시
				System.out.println("\n[비밀번호 변경에 실패했습니다.]\n");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 회원 탈퇴
	 */
	private void secession() {
		System.out.println("\n[회원 탈퇴]\n");
		
		try {
			// 비밀번호 인증
			String memberPw = null;
			while(true) {
				System.out.print("현재 비밀번호 (이전 화면으로 0) : ");
				memberPw = sc.next();
				if (memberPw.equals("0")) {
					System.out.println("\n[이전화면으로 돌아갑니다.]\n");
					return;
				}
				// 비밀번호 인증 서비스 호출
				int result = service.checkMemberPw(memberPw, MainView.loginMember.getMemberNo());
				
				if(result > 0) {
					System.out.println("\n[현재 비밀번호가 일치합니다.]\n");
					break;
				} else {
					System.out.println("\n[현재 비밀번호가 일치하지 않습니다.]\n");
				}
			}
			
			while(true) {
				System.out.print("정말 탈퇴하시겠습니까? (Y/N) : ");
				char secessionFlag = sc.next().toUpperCase().charAt(0);
				
				if(secessionFlag == 'Y') {
					int result = service.secession(MainView.loginMember.getMemberNo());
					
					if(result > 0) {	// 탈퇴 성공
						System.out.println("\n[탈퇴 되었습니다.]\n");
						input = 0;
						MainView.loginMember = null;
						break;
					} else {			// 실패 
						System.out.println("\n[탈퇴 실패..]\n");
					}
				} else break;
			}
		}catch(Exception e) {
			System.out.println("\n<< 회원 탈퇴 중 예외 발생 >>\n");
			e.printStackTrace();
		}
	}
}
