package dbmanager.autocompletion;
import java.text.Collator;
import java.util.Comparator;

public class StringComparator implements Comparator{

	public StringComparator(){
		collator = Collator.getInstance();
	}

	 public int	compare(Object o1, Object o2){
		 String str1 = (String)o1;
		 String str2 = (String)o2;
		 return collator.compare(str1, str2);
	}

	 public boolean equals(Object obj){ return true; }

	private Collator collator;
}