/*
 * @author Qu Xiangjun
 * @date 2020.05.28
 * @version 1.0
 */
package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Triangle extends Shape implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 12L;

	//Foundation Point
	public Point a,b,c;
	
	//Color
	Color lineColor = Color.BLACK;
	Color fillColor = Color.WHITE;

	/*
	 * Rectangle构造函数
	 * 多类型重载
	 */
	public Triangle(){
		// Initialize (0,0) for every Point
		a = new Point();
		b = new Point();
		c = new Point();
	}
	public Triangle(Point aa, Point bb, Point cc){
		// Initialize by Point copy constructor
		a = new Point(aa);
		b = new Point(bb);
		c = new Point(cc);
	}
	
	/*
	 *  将Triangle画出
	 */
	public void draw(Graphics g) {
		int px[] = {a.getX(),b.getX(),c.getX(),a.getX()};
		int py[] = {a.getY(),b.getY(),c.getY(),a.getY()};
		g.setColor(lineColor);
		g.drawPolygon(px, py, 4);  //首尾连接画出
		g.setColor(fillColor);
		g.fillPolygon(px, py, 4);
		
		System.out.println("--------------------");
		System.out.println("Draw Triangle:");
		System.out.println("Point a: ("+a.x+","+a.y+")");
		System.out.println("Point b: ("+b.x+","+b.y+")");
		System.out.println("Point c: ("+c.x+","+c.y+")");
		System.out.println("lineColor: "+lineColor.toString());
		System.out.println("fillColor: "+fillColor.toString());
		System.out.println("--------------------");
	}
	
	/*
	 *  属性接口
	 */
	public void setPoint(Point aa, Point bb, Point cc){
		a = new Point(aa);
		b = new Point(bb);
		c = new Point(cc);
	}
	public Point getA() {
		return a;
	}
	public void setA(Point a) {
		this.a = a;
	}
	public Point getB() {
		return b;
	}
	public void setB(Point b) {
		this.b = b;
	}
	public Point getC() {
		return c;
	}
	public void setC(Point c) {
		this.c = c;
	}
	
	/*
	 *  判断传入的点是否是Triangle内的点
	 *  @param mousePoint 接收一个Point类型的参数
	 *  @return 判断改参数是否包含在Rectangle中，是则返回true，否则返回false
	 */
	@Override
	public boolean isPointInShape(Point mousePoint) {
		double ABC = triAngleArea(a, b, c);
        double ABp = triAngleArea(a, b, mousePoint);
        double ACp = triAngleArea(a, c, mousePoint);
        double BCp = triAngleArea(b, c, mousePoint);
        double sumOther = ABp + ACp + BCp;
        double ABS_DOUBLE_0 = 1;
        if (Math.abs(ABC - sumOther) < ABS_DOUBLE_0) { // 若面积之和等于原三角形面积，证明点在三角形内
            return true;
        } else {
            return false;
        }
	}
	/*
	 * 计算三角形面积
	 * @param A B C为三角形三点
	 * @return 面积
	 */
	private static double triAngleArea(Point A, Point B, Point C) { // 由三个点计算这三个点组成三角形面积
		double a = Math.pow(Math.pow(A.x-B.x,2)+Math.pow(A.y-B.y,2),0.5);
		double b = Math.pow(Math.pow(A.x-C.x,2)+Math.pow(A.y-C.y,2),0.5);
		double c = Math.pow(Math.pow(C.x-B.x,2)+Math.pow(C.y-B.y,2),0.5);
		double d = (a + b + c) / 2f;
		double s = (float) Math.sqrt(d * (d - a) * (d - b) * (d - c));
		return s;
    }
	
	/*
	 *  根据传入的点编号以及偏移量对Triangle的点坐标进行修改
	 *  @param index 点编号，通过该参数可以找到与之对应的图形中的点
	 *  @param newPoint newPoint=新的坐标
	 */
	@Override
	public void reshape(int index, Point newPoint) {
		if(index==1) {
			a.setX(newPoint.getX());
			a.setY(newPoint.getY());
			System.out.println("Triangle a 修改成功");
		}else if(index==2) {
			b.setX(newPoint.getX());
			b.setY(newPoint.getY());
			System.out.println("Triangle b 修改成功");
		}else if(index==3) {
			c.setX(newPoint.getX());
			c.setY(newPoint.getY());
			System.out.println("Triangle c 修改成功");
		}
	}
	
	/*
	 *  找到传入点对应图形的编号
	 *  @param mousePoint 接收一个Point类型的参数
	 *  @return 返回该参数在图形中所对应的编号,如果没有对应则返回-1
	 */
	@Override
	public int FindVertexPoint(Point mousePoint) {
		if( (Math.abs(mousePoint.getX()-a.getX())<=1) && (Math.abs(mousePoint.getY()-a.getY())<=1)) {
			return 1;
		}else if( (Math.abs(mousePoint.getX()-b.getX())<=1) && (Math.abs(mousePoint.getY()-b.getY())<=1) ) {
			return 2;
		}else if( (Math.abs(mousePoint.getX()-c.getX())<=1) && (Math.abs(mousePoint.getY()-c.getY())<=1) ) {
			return 3;
		}
		return -1;
	}
	
	/*
	 * 移动图像
	 * @param x x方向移动
	 * @param y y方向移动	
	 */
	public void moveShape(int x,int y) {
		a.movePoint(x, y);
		b.movePoint(x, y);
		c.movePoint(x, y);
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





















