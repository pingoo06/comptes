package comptes.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import comptes.util.MyDate;

public class ListComparable {

	public static void main(String[] args) {
		ArrayList<MyObject> list = new ArrayList<>();
		list.add(new MyObject(12, new MyDate("20/06/2046")));
		list.add(new MyObject(1, new MyDate("23/01/2012")));
		list.add(new MyObject(50, new MyDate("20/05/2000")));
		list.add(new MyObject(45, new MyDate("28/06/2046")));
		list.add(new MyObject(22, new MyDate("20/07/2046")));
		list.add(new MyObject(38, new MyDate("20/06/2016")));
		list.add(new MyObject(24, new MyDate("27/06/2046")));
		
		System.out.println("original order");
		printList(list);
		
		Collections.sort(list);
		
		System.out.println("triée avec le comparateur de l'objet, par date");
		printList(list);
		
		Collections.sort(list, new MyObjectIdComparator());
		
		System.out.println("triée avec le comparateur definit exterieurement, par id");
		printList(list);
	}
	
	public static void printList(ArrayList<MyObject> list) {
		System.out.println("List content: ");
		for(MyObject o : list) {
			System.out.println("\t"+o);
		}
		System.out.println();
	}

}

class MyObject implements Comparable<MyObject> {

	private int id;
	private MyDate myDate;
	
	
	public MyObject(int id, MyDate myDate) {
		this.id = id;
		this.myDate = myDate;
	}


	@Override
	public int compareTo(MyObject o) {
		System.out.println("compare my date" + myDate.compareTo(o.myDate));
		return myDate.compareTo(o.myDate);
	}


	public int getId() {
		return id;
	}


	public MyDate getMyDate() {
		return myDate;
	}


	@Override
	public String toString() {
		return "MyObject [id=" + id + ", myDate=" + myDate + "]";
	}
	
	
}

class MyObjectIdComparator implements Comparator<MyObject> {

	@Override
	public int compare(MyObject o1, MyObject o2) {
		return new Integer(o1.getId()).compareTo(new Integer(o2.getId()));
	}
	
}
