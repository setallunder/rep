package model;

public class SnakePart {
	private int x;
	private int y;
	public SnakePart(){
		x=0;
		y=0;
	}
	public SnakePart(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setCoord(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
