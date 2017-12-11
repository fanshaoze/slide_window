package Other;

import Other.Interval;

import java.util.ArrayList;

public class User {
	String userId;
	ArrayList<Interval> intervals;
	
	public User(String u, Interval i){
		userId = u;
		intervals = new ArrayList<Interval>();
		intervals.add(i);
	}
	
	
	String getUser(){
		return userId;
	}

	public void add(Interval i){
		intervals.add(i);
	}
	
}
