/*
 * @author Qu Xiangjun
 * @date 2020.05.28
 * @version 1.0
 */
package graph;

import java.io.Serializable;

public class Point implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 15L;
	
	/*
	 * 坐标内容
	 */
	public int x;
	public int y;
	
	/*
	 * point构造函数
	 * @param xx 点的x坐标
	 * @param yy 点的y坐标
	 * @param a 拷贝的Point类
	 */
	public Point(){
		x = 0;
		y = 0;
	}
	public Point(int xx,int yy){
		x = xx;
		y = yy;
	}
	public Point(Point a){
		x = a.x;
		y = a.y;
	}
	
	/*
	 * 接口函数
	 */
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setPoint(Point a) {
		x = a.x;
		y = a.y;
	}
	public void setPoint(int xx,int yy) {
		x = xx;
		y = yy;
	}
	
	/*
	 * 移动点
	 * @param xx x方向的移动距离（含负数）
	 * @param yy y方向的移动距离（含负数）
	 */
	public void movePoint(int xx,int yy) {
		x += xx;
		y += yy;
	}
}
