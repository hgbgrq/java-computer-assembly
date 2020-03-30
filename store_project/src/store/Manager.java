package store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager<T extends Manageable> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public ArrayList<T> mList = new ArrayList<>();
	
	protected Scanner openFile(String fileName) {
		Scanner fileIn = null;
		File f = new File(fileName);
		try {
			fileIn = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return fileIn;
	}
	
	public ArrayList<T> findObjects(String kwd) {
		ArrayList<T> resultList = new ArrayList<>();
		
		if (mList == null) 
			mList = new ArrayList<>();
		for (T obj : mList) {
			if (obj.compare(kwd)) 
				resultList.add(obj);
		}
		return resultList;
	}
	
	public void printAll() {
		if (mList.isEmpty())
			return;
		
		for (T obj : mList)
			obj.print();
	}
	
	public void printSimpleAll() {
		if (mList.isEmpty())
			return;
		
		for (T obj : mList)
			obj.printSimple();
	}
}
