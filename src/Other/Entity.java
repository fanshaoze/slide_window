package Other;

public class Entity {

private String userId;
private	int start;
private	int end;
	
	public Entity(){
		userId = "";
		start = -1;
		end = 0;
	}
	
	public Entity(String user, int a, int b)throws MyException {
		if(a>b) {
			throw new MyException("时间区间设置错误");
		}
		userId = user;
		start = a;
		end = b;
	}
	
	public String getUser(){
		return userId;
	}
	public int getStartTime(){
		return start ;
	}

	public int getEndTime(){
		return end;
	}
}
