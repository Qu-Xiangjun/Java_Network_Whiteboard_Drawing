/*
 * @author Qu Xiangjun
 * @date 2020.05.28
 * @version 1.0
 */
package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Rectangle extends Shape implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 13L;
	
	/*
	 * 图形属性，坐标和长宽
	 */
	public Point a,b;
	public int width,height;
	
	/*
	 * 图形颜色属性，边框和填充
	 */
	public Color lineColor = Color.BLACK;
	public Color fillColor = Color.WHITE;
	
	/*
	 * Rectangle构造函数
	 * 重载三个类型
	 */
	public Rectangle(){
		// Initialize (0,0) for every Point
		a = new Point();
		b = new Point();
		width = height = 0;
	}
	public Rectangle(Point aa,Point bb) {
		// Initialize by Point copy constructor
		if(aa.getX()>bb.getX()) {
			//a始终在左上角
			int x,y;
			x = aa.getX();
			y = aa.getY();
			bb.setPoint(aa);
			aa.setPoint(new Point(x,y));
		}
		a = new Point(aa);
		b = new Point(bb);
		height = Math.abs(aa.getY()-bb.getY());
		width = Math.abs(aa.getX()-bb.getX());
	}
	public Rectangle(Point aa,Point bb,int w,int h) {
		// Initialize by Point copy constructor
		if(aa.getX()>bb.getX()) {
			//a始终在左上角
			int x,y;
			x = aa.getX();
			y = aa.getY();
			bb.setPoint(aa);
			aa.setPoint(new Point(x,y));
		}
		a = new Point(aa);
		b = new Point(bb);
		width = w;
		height = h;
	}
	
	/*
	 *  将Rectangle画出
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(this.lineColor);
		g.drawRect(a.getX(), a.getY(), width, height);
		g.setColor(this.fillColor);
		g.fillRect(a.getX()+1, a.getY()+1, width-2, height-2);
		
		System.out.println("--------------------");
		System.out.println("Draw Rectangle:");
		System.out.println("Point a: ("+a.x+","+a.y+")");
		System.out.println("Point b: ("+b.x+","+b.y+")");
		System.out.println("width: "+width);
		System.out.println("height: "+height);
		System.out.println("lineColor: "+lineColor.toString());
		System.out.println("fillColor: "+fillColor.toString());
		System.out.println("--------------------");
	}
	/*
	 * 属性接口
	 */
	public void setPoint(Point aa,Point bb) {
		a = new Point(aa);
		b = new Point(bb);
	}
	public void setA(Point a) {
		this.a = a;
	}
	public Point getA() {
		return a;
	}
	public void setB(Point b) {
		this.b = b;
	}
	public Point getB() {
		return b;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	/*
	 *  判断传入的点是否是Rectangle内的点
	 *  @param mousePoint 接收一个Point类型的参数
	 *  @return 判断改参数是否包含在Rectangle中，是则返回true，否则返回false
	 */
	@Override
	public boolean isPointInShape(Point mousePoint) {
		if(mousePoint.getX()>=a.getX() && mousePoint.getX()<=b.getX() && mousePoint.getY()>=a.getY() && mousePoint.getY()<=b.getY() ) {
			return true;
		}
		return false;
	}
	
	
	/*
	 *  根据传入的点编号以及偏移量对Rectangle的点坐标进行修改
	 *  @param index 点编号，通过该参数可以找到与之对应的图形中的点
	 *  @param newPoint newPoint=新的坐标
	 */
	@Override
	public void reshape(int index, Point newPoint) {
		if(index==1) {
			a.setX(newPoint.getX());
			a.setY(newPoint.getY());
			System.out.println("Rectangle:Point a 修改成功");
		}else if(index==2) {
			b.setX(newPoint.getX());
			b.setY(newPoint.getY());
			System.out.println("Rectangle:Point b 修改成功");
		}
		width = Math.abs(a.getX()-b.getX());
		height = Math.abs(a.getY()-b.getY());
	}
	
	/*
	 *  找到传入点对应图形的编号
	 *  @param mousePoint 接收一个Point类型的参数
	 *  @return 返回该参数在图形中所对应的编号,没有对应则返回-1
	 */
	@Override
	public int FindVertexPoint(Point mousePoint) {
		//设置点击误差范围为1
		if( (Math.abs(mousePoint.getX()-a.getX())<=1) && (Math.abs(mousePoint.getY()-a.getY())<=1) ) {
			return 1;
		}else if( (Math.abs(mousePoint.getX()-b.getX())<=1) && (Math.abs(mousePoint.getY()-b.getY())<=1)  ) {
			return 2;
		}
		return -1;
	}
	
	/*
	 * 移动图像
	 * @param x x方向移动
	 * @param y y方向移动	
	 */
	@Override
	public void moveShape(int x, int y) {
		// TODO Auto-generated method stub
		a.movePoint(x, y);
		b.movePoint(x, y);
	}
	
	/*
	 *  修改图像边框颜色
	 *  @paramg 颜色
	 */
	public void setLineColor(Color c) {
		lineColor = c;
	}
	/*
	 *  修改图像内部填充颜色
	 *  @paramg 颜色
	 */
	public void setFillColor(Color c) {
		fillColor = c;
	}
}
