package main;

import java.io.*;
import java.util.*;

import store.Store;

public class Main {
	Scanner keyin = new Scanner(System.in);
	ComputerStore danawa = new ComputerStore();
	public static UserManager uMgr = new UserManager();

	void doit() {
		danawa.load();

		// 파일이 없으면 파일 생성.
		if (!(new File("user.ser")).exists()) {
			System.out.println("user.ser 파일이 없습니다. 새로 생성합니다.");
			uMgr.createFile();
		}

		// 파일의 내용을 uMgr로 가져옴.
		try {
			undoSerialize();
		} catch (Exception e) {
			e.printStackTrace();

		}

		mainMenu();

		// 종료 전에 uMgr을 user.ser 파일에 저장.
		try {
			Store.doSerialize("user.ser", uMgr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 역직렬화: file의 객체를 읽어들임.
	void undoSerialize() throws IOException, ClassNotFoundException, EOFException {
		FileInputStream fis = new FileInputStream("user.ser");
		ObjectInputStream ois = new ObjectInputStream(fis);
		uMgr = (UserManager) ois.readObject();
		// ID가 제대로 저장되고 있는지 확인하기 위해 출력
		System.out.print("\nID목록: ");
		uMgr.printUsers();
		ois.close();
	}

	public void mainMenu() {
		User nowUser = null;
		while (true) {
			System.out.print("\nDanawa 메인화면\n");
			String input = "";

			if (nowUser != null) { // 로그인 후

				// 로그아웃 시 true 리턴
				if (danawa.loginMenu(nowUser, keyin))
					nowUser = null;
				// 로그아웃이 아닌 일반적인 함수 종료 시
				else
					break;

			} else { // 로그인 정보가 없으면 기본 메뉴
				System.out.print("\t1. 로그인\n\t2. 회원가입\n\t0. 종료\n > ");
				try {
					input = keyin.next();
				} catch (InputMismatchException e) { // 정수가 아닌 것을 입력받음
					System.out.println("잘못된 입력입니다.");
					input = "";
				}

				if (input.equals("0")) // 종료
					break;

				switch (Integer.valueOf(input)) {
				case 1:
					nowUser = login();
					break;
				case 2:
					uMgr.signUp(keyin);
					break;
				default:
					System.out.println("잘못된 입력입니다.");
					break;
				}
			}
		}
	}

	User login() {
		User u = null;
		String id = "";
		int count = 0;
		while (true) {
			count++;
			if (count == 4) {
				System.out.println("연속 3회 오류로 인해 처음으로 돌아갑니다.");
				return null;
			}

			System.out.print("\nID > ");
			id = keyin.next();

			u = uMgr.findUser(id);
			if (u == null)
				System.out.printf("ID가 잘못 되었습니다. 다시 입력해주세요. %d번 오류\n", count);
			else
				break;
		}
		System.out.printf("로그인 되었습니다. %s\n", id);
		return u;
	}

	private static Main demo = null;

	private Main() {
	}

	public static Main GetInstance() {
		if (demo == null)
			return new Main();
		return demo;
	}

	public static void main(String args[]) {
		demo = GetInstance();
		demo.doit();
	}
}
