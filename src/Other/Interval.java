package Other;

public class Interval {
	int start;
	int end;
	
	public Interval(int s, int e){
		start = s;
		end = e;
	}
	
	public boolean cover(int s, int e){
		if(start<=s && end >= e)
			return true;
		return false;
	}
	
	public int getStart(){
		return start;
	}
	
	public int getEnd(){
		return end;
	}
}
