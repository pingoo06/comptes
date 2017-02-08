package comptes.example;


import java.util.ArrayList;
import java.util.Iterator;

public class TestList {

	public static void main(String[] args) {

		ArrayList<ListItem> list = new ArrayList<>();
		
		for(int i=0;i<20;i++) {
			list.add(new ListItem("Pouet"+i));
		}
		
		
		//Tous les parcourir : 
		
		//methode 1
		for(int i=0;i<list.size();i++) {
			ListItem current = list.get(i);
		}
		
		//foreach
		for(ListItem item : list) {
			ListItem current = item;
		}
		
		//iterator
		Iterator<ListItem> iterator = list.iterator();
		while(iterator.hasNext()) {
			ListItem current = iterator.next();
		}
		
		
		// Supprimer un element : 
		
		//par index : 
		list.remove(0);
		
		//par objet
		ListItem item = new ListItem("pouet4");
		list.remove(item);
//		Creer une fonction equals qui compare les deux items
		
		//par recharhe par iterator
		Iterator<ListItem> it = list.iterator();
		while(it.hasNext()) {
			ListItem current = it.next();
			if(current.getVal().equals("pouet5")) {
				it.remove();
			}
		}
		
		
	}
	
	static class ListItem {
		String val;

		
		public ListItem(String val) {
			super();
			this.val = val;
		}

		public String getVal() {
			return val;
		}

		public void setVal(String val) {
			this.val = val;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((val == null) ? 0 : val.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ListItem other = (ListItem) obj;
			if (val == null) {
				if (other.val != null)
					return false;
			} else if (!val.equals(other.val))
				return false;
			return true;
		}
		
		
		
	}

}
