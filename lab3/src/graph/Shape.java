/*
 * @author Qu Xiangjun
 * @date 2020.05.28
 * @version 1.0
 */
package graph;

import java.awt.*;
import java.io.Serializable;

public abstract class Shape implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 14L;
	/*
	 * 图形颜色属性，边框颜色和填充颜色
	 */
	public Color lineColor = Color.BLACK;
	public Color fillColor = Color.WHITE;
	/*
	 *  画图函数
	 *  @paramg为传入的画图类
	 */
	public abstract void draw(Graphics g); 
	/*
	 *  判断传入的点是否是shape的顶点
	 *  @param mousePoint 接收一个Point类型的参数
	 *  @return 判断改参数是否包含在Rectangle的顶点中，是则返回true，否则返回false
	 */
	public abstract boolean isPointInShape(Point mousePoint);
	/*
	 *  找到传入点对应图形的编号
	 *  @param mousePoint 接收一个Point类型的参数
	 *  @return 返回该参数在图形中所对应的编号
	 */
	public abstract int FindVertexPoint(Point mousePoint); 
	/*
	 *  根据传入的点编号以及偏移量对Triangle的点坐标进行修改
	 *  @param index 点编号，通过该参数可以找到与之对应的图形中的点
	 *  @param newPoint 新的点更替
	 */
	public abstract void reshape(int index, Point newPoint);
	/*
	 * 移动图像
	 * @param x x方向移动
	 * @param y y方向移动	
	 */
	public abstract void moveShape(int x,int y);
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
