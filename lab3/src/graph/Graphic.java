/*
 * @author Qu Xiangjun
 * @date 2020.05.28
 * @version 1.0
 */
package graph;

import java.io.Serializable;
import java.awt.*;
import java.util.ArrayList;

public class Graphic implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;
	// Shape dynamic list
	ArrayList<Shape> GList; 
	//Graphics 画图类
	private Graphics g;
	
	/*
	 *  Constructor	
	 */
	public Graphic(Graphics graphics) {
		// TODO Auto-generated constructor stub
		GList = new ArrayList<Shape>();
		this.g = graphics;
	}

	/*
	 *  Graphics 画图类设置
	 *  @param gr1 frame的Graphics画图类
	 */
	public void setgraphics(Graphics gr1) {
		g = gr1;
	}
	
	/*
	 *  ArrayList 图像存储设置
	 *  @param list 需要设置的Arraylist
	 */
	public void setArrayListShape(ArrayList<Shape> list) {
		System.out.println("in setArrayListShape");		
		GList.clear();
		for(Shape s:list) {
			GList.add(s);
		}		
	}
	
	/*
	 *  ArrayList 图像存储返回
	 *  @return 返回Arraylist
	 */
	public ArrayList<Shape> getArrayListShape() {
		return this.GList;
	}
	
	/*
	 * @param s 是图像
	 * @return 返回图像在Arraylist中的index
	 */
	public int getShapeIndex(Shape s) {
		return GList.indexOf(s);
	}
	
	/*
	 * @return 返回index对应的arraylist中的shape
	 * @param index 是下标
	 */
	public Shape getShapeByIndex(int index) {
		return GList.get(index);
	}
	
	/*
	 *  Add shape
	 *  @param shape 需要添加的图像
	 */
	public void add(Shape shape) {
		GList.add(shape);
	}
	
	/*
	 *  Draw all shape in list
	 *  @param graphics 绘图需要frame的Graphics类 实例
	 */
	public void draw(Graphics graphics) {
		for(Shape i:GList) {
			i.draw(graphics);
		}
	};
	
	/*
	 * 改变存储图形的属性，找到传入点是否属于某个存储的图形。
	 * 如果属于，则对那个图形的那个点进行增加偏移量的修改
	 * @param source 传入的点
	 * @param offset 该点的偏移量
	 */
	public void reshape(Point source, Point newPoint) {
		System.out.println("in graphic reshape");
		for(int i=0;i<GList.size();i++) {
			if(GList.get(i).FindVertexPoint(source) != -1) {
				System.out.println("graphics find point in reshape");
				int index=GList.get(i).FindVertexPoint(source);
				System.out.println("In Graphic reshape");
				GList.get(i).reshape(index, newPoint);
				break;
			}
		}
	}
	
	/*
	 * 判断输入的点属于哪一图形内部，若在多个图形区域内，返回第一个检索到的。
	 * @param sourse 传入的点
	 * @return 返回所属的图形
	 */
	public Shape isInShape(Point source) {
		for(int i=0;i<GList.size();i++) {
			if(GList.get(i).isPointInShape(source)) {
				return GList.get(i);
			}
		}
		return null;
	}
	
	/*
	 * 判断输入的点属于哪一图形顶点，若在多个图形区域内，返回第一个检索到的。
	 * @param sourse 传入的点
	 * @return Shape 返回所属的图形
	 */
	public Shape isInPoint(Point source) {
		for(int i=0;i<GList.size();i++) {
			if(GList.get(i).FindVertexPoint(source) != -1) {
				return GList.get(i);
			}
		}
		return null;
	}
	
	/*
	 * @param 图形s，需要删除的
	 */
	public void remove(Shape s) {
		GList.remove(s);
	}
	
}
