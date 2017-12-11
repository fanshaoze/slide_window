package Other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Database {
	ArrayList<User> data;
	
	public Database(){
		data = new ArrayList<User>();
	}
	public void insert(User u){
		data.add(u);
	}
	
	public int getSize(){
		return data.size();
	}
	
	public void insertI(Interval i){
		data.get(getSize()-1).add(i);
	}
}

