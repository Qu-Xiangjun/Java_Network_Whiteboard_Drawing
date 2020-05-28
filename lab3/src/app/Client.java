/*
 * @author Qu Xiangjun
 * @date 2020.05.28
 * @version 1.0
 */
package app;

import graph.Point;
import graph.Rectangle;
import graph.Shape;
import graph.Triangle;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.*;
import java.net.Socket;

/*
 * 客户端控制程序
 */
public class Client extends Thread {
	/*
	 * 设置选中按钮全局变量
	 */
	private String currentButton = new String("");	//当前选中的按钮
	private Color currentColor;  //当前选中的颜色
	private int selectedPoint;	//当前选中的点
	private Shape selectedShape;	//当前选中的图形
	
	/*
	 * 鼠标的位置
	 * 包括最开始点击开始的位置
	 * 包括 点击拖动过程的实时位置
	 */
	private int lastX = -1;
	private int lastY = -1;
	private int lastX2 = -1;
	private int lastY2 = -1;
	
	/*
	 * gui程序，画图窗体
	 */
	private OpenGLApp gui;
	
	/*
	 * 数据传输io
	 */
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	Socket socket;
	
	/*
	 * Constructor
	 */
	public Client() {
		gui = new OpenGLApp();
		
		/*
		 * 按钮点击事件
		 */
		gui.rectangleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentButton = "rectangleButton";
			}
			
		});
		gui.triangleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentButton = "triangleButton";
			}
			
		});
		gui.SelectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentButton = "SelectButton";
			}
			
		});
		gui.blackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("click black");
				// TODO Auto-generated method stub
				currentColor = Color.BLACK;
				//若选择了图像，则改变其颜色
				if(selectedShape != null) {
					if(selectedPoint == -1) {
						//选择的是图像内部，填充图像
						selectedShape.setFillColor(currentColor);
						gui.repaint();
						
					}
					else {
						//选择的是点，改变边框颜色
						selectedShape.setLineColor(currentColor);
						gui.repaint();
					}
					try {
						toServer = new DataOutputStream(socket.getOutputStream());
						//发送操作识别码 上色为3
						toServer.writeInt(3);
						//发送图像在arraylist中的index
						toServer.writeInt(gui.getGraphic().getShapeIndex(selectedShape));
						//发送是边1还是填充2
						if(selectedPoint == -1)  
							toServer.writeInt(2);
						else 
							toServer.writeInt(1);
						//发送颜色 black为1
						toServer.writeInt(1);
						
						//多余发送
						toServer.writeInt(0);
						toServer.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("send change data");
				}
			}
			
		});
		gui.redButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("click red");
				// TODO Auto-generated method stub
				currentColor = Color.RED;
				if(selectedShape != null) {
					if(selectedPoint == -1) {
						//选择的是图像内部，填充图像
						selectedShape.setFillColor(currentColor);
						gui.repaint();
					}
					else {
						//选择的是点，改变边框颜色
						selectedShape.setLineColor(currentColor);
						gui.repaint();
					}
					try {
						toServer = new DataOutputStream(socket.getOutputStream());
						//发送操作识别码 上色为3
						toServer.writeInt(3);
						//发送图像在arraylist中的index
						toServer.writeInt(gui.getGraphic().getShapeIndex(selectedShape));
						//发送是边1还是填充2
						if(selectedPoint == -1)  
							toServer.writeInt(2);
						else 
							toServer.writeInt(1);
						//发送颜色 red2
						toServer.writeInt(2);
						//多余发送
						toServer.writeInt(0);
						
						toServer.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("send change data");
				}
			}
			
		});
		gui.yellowButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("click yellow");
				// TODO Auto-generated method stub
				currentColor = Color.YELLOW;
				if(selectedShape != null) {
					if(selectedPoint == -1) {
						//选择的是图像内部，填充图像
						selectedShape.setFillColor(currentColor);
						gui.repaint();
					}
					else {
						//选择的是点，改变边框颜色
						selectedShape.setLineColor(currentColor);
						gui.repaint();
					}
					try {
						toServer = new DataOutputStream(socket.getOutputStream());
						//发送操作识别码 上色为3
						toServer.writeInt(3);
						//发送图像在arraylist中的index
						toServer.writeInt(gui.getGraphic().getShapeIndex(selectedShape));
						//发送是边1还是填充2
						if(selectedPoint == -1)  
							toServer.writeInt(2);
						else 
							toServer.writeInt(1);
						//发送颜色 yellow为3
						toServer.writeInt(3);
						//多余发送
						toServer.writeInt(0);
						
						toServer.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("send change data");
				}
			}
			
		});
		gui.saveButton.addActionListener(new ActionListener() {
			/*
			 * 保存图像
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					gui.savePic();	
				}catch(IOException e1){
					e1.printStackTrace();
				}
			}
			
		});
		
		
		/*
		 * 鼠标点击事件
		 */
		gui.addMouseListener(new MouseAdapter() {
			/*
			 * 画图点击事件
			 * 保存进入的位置作为画图起始顶点
			 * 拖拽的位置为实时更新图像
			 * 结束位置将图像保存至arraylist
			 */
			public void mousePressed(MouseEvent event) {
				System.out.println("mousePressed");
				//初始化各选择项
				selectedPoint = -1;
				selectedShape = null;
				lastX = -1; //开始点击的坐标
				lastY = -1;
				
				lastX2 = lastX = event.getX();	//点击过程中的临时坐标
				lastY2 = lastY = event.getY();
				//选择图像，做出改变
				if(currentButton == "SelectButton") {
					
					//若选择到图像的顶点，为改变图像的形状
					//选择到图形内部，为移动图像
					selectedShape = gui.getGraphic().isInPoint(new Point(lastX,lastY));
					if(selectedShape == null) {
						selectedShape = gui.getGraphic().isInShape(new Point(lastX,lastY));
					}else {
						selectedPoint = selectedShape.FindVertexPoint(new Point(lastX,lastY));
					}	
					
					
				}
			}
			/*
			 * 鼠标释放
			 * 将个操作结果更改到graphic类中，并更新画板
			 * 同时传输更改指令到服务器
			 */
			public void mouseReleased(MouseEvent event) {
				System.out.println("mouseReleased");
				int currentX,currentY;
				currentX = event.getX();
				currentY = event.getY();
				//画矩形
				if(currentButton == "rectangleButton") {
					Rectangle rec = new Rectangle(new Point(lastX,lastY),new Point(currentX,currentY));
					gui.getGraphic().add(rec);
					gui.repaint();
					try {
						toServer = new DataOutputStream(socket.getOutputStream());
						//发送操作识别码 画矩形为1
						toServer.writeInt(1);
						//发送新矩形的xy x2,y2
						toServer.writeInt(lastX);
						toServer.writeInt(lastY);
						toServer.writeInt(currentX);
						toServer.writeInt(currentY);
						
						toServer.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("send change data");
				}
				//画等腰三角形
				else if(currentButton == "triangleButton") {
					Point point3 = new Point(lastX-(currentX-lastX),currentY);
					Triangle triangle = new Triangle(new Point(lastX,lastY),new Point(currentX,currentY),point3);
					gui.getGraphic().add(triangle);
					gui.repaint();
					try {
						toServer = new DataOutputStream(socket.getOutputStream());
						//发送操作识别码 画三角形为2
						toServer.writeInt(2);
						//发送新矩形的xy x2,y2
						toServer.writeInt(lastX);
						toServer.writeInt(lastY);
						toServer.writeInt(currentX);
						toServer.writeInt(currentY);
						toServer.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("send change data");
				}
				//选择图像，做出改变
				else if(currentButton == "SelectButton") {
					// 改变图像（扩大、缩小、变形）
					if(selectedPoint != -1) {
						gui.getGraphic().reshape(new Point(lastX,lastY), new Point(currentX,currentY));
						System.out.println("change points");
						gui.repaint();
						try {
							toServer = new DataOutputStream(socket.getOutputStream());
							//发送操作识别码 改变点为5
							toServer.writeInt(5);
							//发送改变的点前后位置
							toServer.writeInt(lastX);
							toServer.writeInt(lastY);
							toServer.writeInt(currentX);
							toServer.writeInt(currentY);
							
							toServer.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println("send change data");
					}
					// 改变位置
					else {
						if(selectedShape == null) return;
						int x = currentX-lastX2;
						int y = currentY-lastY2;
						selectedShape.moveShape(x,y);
						gui.repaint();
						try {
							toServer = new DataOutputStream(socket.getOutputStream());
							//发送操作识别码 移动为4
							toServer.writeInt(4);
							//发送图像在arraylist中的index
							toServer.writeInt(gui.getGraphic().getShapeIndex(selectedShape));
							//发送移动距离 xy
							toServer.writeInt(currentX-lastX);
							toServer.writeInt(currentY-lastY);
							
							//多余发送
							toServer.writeInt(0);
							
							toServer.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println("send change data");
					}
				}
			}
			
			
			/*
			 * 点击事件
			 * 保存点击位置
			 * 保存选中当前位置有的相关图像
			 */
			public void mouseClicked(MouseEvent event) {
				System.out.println("mouseClicked");
				//初始化各选择项
				selectedPoint = -1;
				selectedShape = null;
				lastX = -1;
				lastY = -1;
				
				lastX = event.getX();
				lastY = event.getY();
				//选择图像，做出改变
				if(currentButton == "SelectButton") {					
					//若选择到图像的顶点，为改变图像的形状
					selectedShape = gui.getGraphic().isInPoint(new Point(lastX,lastY));
					if(selectedShape == null) {
						selectedShape = gui.getGraphic().isInShape(new Point(lastX,lastY));
					}else {
						selectedPoint = selectedShape.FindVertexPoint(new Point(lastX,lastY));
					}	
				}
			}
			
		});	
		
		/*
		 * 鼠标拖动时间
		 * 更新拖动时的动画
		 */
		gui.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent event) {
				System.out.println("mouseDragged");
				int currentX,currentY;
				currentX = event.getX();
				currentY = event.getY();
				//画矩形
				if(currentButton == "rectangleButton") {
					Rectangle rec = new Rectangle(new Point(lastX,lastY),new Point(currentX,currentY));
					rec.draw(gui.getGraphics());
					gui.repaint();
					System.out.println("x:"+currentX+" y:"+currentY);
				}
				//画等腰三角形
				else if(currentButton == "triangleButton") {
					Point point3 = new Point(lastX-(currentX-lastX),currentY);
					Triangle triangle = new Triangle(new Point(lastX,lastY),new Point(currentX,currentY),point3);
					triangle.draw(gui.getGraphics());
					gui.repaint();
				}
				//选择图像，做出改变
				else if(currentButton == "SelectButton") {
					System.out.println("selectedPoint："+selectedPoint);
					// 改变图像
					if(selectedPoint != -1 ) {
						System.out.println("改变形状：");
						int x = lastX2;
						int y = lastY2;
						gui.getGraphic().reshape(new Point(x,y), new Point(currentX,currentY));
						gui.repaint();
						lastY2 = currentY;
						lastX2 = currentX;
					}
					// 改变位置
					else {
						if(selectedShape == null) return;
						int x = currentX - lastX2;
						int y = currentY - lastY2;
						selectedShape.moveShape(x,y);
						gui.repaint();
						lastY2 = currentY;
						lastX2 = currentX;
					}
					
				}
			}
        });
		
		connectToServer();
	}
	
	/*
	 * 连接服务端
	 */
	private void connectToServer() {
		// TODO Auto-generated method stub
		System.out.println("in Client ThreadInt connectToServer");
		try {
			System.out.println("before connectToServer");
			socket = new Socket("localhost",5500);
			System.out.println("after connectToServer");
			
			fromServer = new DataInputStream(socket.getInputStream());
			System.out.println("to end connectToServer");
			
//			toServer.writeIntInt(null);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/*
	 * 线程启动
	 * 用于接收指令数据
	 * 指令接收共五个整数，第一个是操作类型，其余为数据，包括位置信息、图形编号等，不足五个置零传输，不采用
	 */
	public void run() {
		System.out.println("in Client ThreadInt run");
		// TODO Auto-generated method stub
		try {
			
			while(true) {
				//接收数据
				int operation = fromServer.readInt();  //操作类型
				int a,b,c,d;	//数据
				a = fromServer.readInt();
				b = fromServer.readInt();
				c = fromServer.readInt();
				d = fromServer.readInt();
				switch(operation) {  //判断操作类型
				case 1:
					/*
					 * rectangle draw
					 */
					Rectangle rec = new Rectangle(new Point(a,b),new Point(c,d));
					gui.getGraphic().add(rec);
					gui.repaint();
					break;
				case 2:
					/*
					 * Triangle draw
					 */
					Point point3 = new Point(a-(c-a),d);
					Triangle triangle = new Triangle(new Point(a,b),new Point(c,d),point3);
					gui.getGraphic().add(triangle);
					gui.repaint();
					break;
				case 3:
					/*
					 * 上色
					 */
					int shapeIndex = a;
					Shape tempShape = gui.getGraphic().getShapeByIndex(shapeIndex);
					int lineAndFill = b; //代表选择线或者填充	
					int color = c; //颜色
					Color c1;
					switch (color) {  	//选择颜色
					case 1:
						c1 = Color.black;
						break;
					case 2:
						c1 = Color.red;
						break;
					default:
						c1 = Color.yellow;
						break;
					}
					
					if(lineAndFill==1) {
						// 改变边框的颜色
						tempShape.setLineColor(c1);
						gui.repaint();
					}
					else {
						//改变填充颜色
						tempShape.setFillColor(c1);
						gui.repaint();
					}
					break;
				case 4:
					int shapeIndex1 = a; //图像在arraylist 中的index
					Shape tempShape1 = gui.getGraphic().getShapeByIndex(shapeIndex1);
					int moveX,moveY;
					moveX = b;
					moveY = c;
					tempShape1.moveShape(moveX,moveY);
					gui.repaint();
					break;
				case 5:

					int oldX,oldY,newX,newY;
					oldX = a;
					oldY = b;
					newX = c;
					newY = d;
					gui.getGraphic().reshape(new Point(oldX,oldY), new Point(newX,newY));
					gui.repaint();
					break;
				}
				System.out.println("recieve change data");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client frame = new Client();
		frame.start();
		frame.gui.setVisible(true);
	}

	

}
