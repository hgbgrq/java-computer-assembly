package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import store.Manager;

public class UserManager extends Manager<User> {

	private static final long serialVersionUID = 1L;
	
	// new UserManager() 할 때마다 mList의 첫 번째 user ID는 admin이다.
	UserManager() {
		User admin = new User("admin");
		mList.add(admin);
	}

	User findUser(String id) {
		ArrayList<User> result = findObjects(id);
		if (result.isEmpty())
			return null;
		return (User) result.get(0);
	}

	void signUp(Scanner keyin) {
		String id = "";
		while (true) {
			System.out.print("\n원하는 ID를 입력하세요 > ");
			id = keyin.next();
			if (findUser(id) != null)
				System.out.println("중복된 ID가 존재합니다.");
			else {
				System.out.printf("'%s'로 하시겠습니까?(y/n) > ", id);
				String yn = keyin.next();
				if (yn.equals("y"))
					break;
			}
		}
		
		User newbie = new User(id);
		mList.add(newbie);
		System.out.printf("회원가입이 완료되었습니다. ID는 '%s'입니다.\n", id);
	}
	
	void createFile() {
		File f = new File("user.ser");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printUsers() {
		for (User u : mList) {
			System.out.print(u.id + " ");
		}
	}
}
