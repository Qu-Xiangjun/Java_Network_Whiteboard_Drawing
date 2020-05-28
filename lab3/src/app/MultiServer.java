/*
 * @author Qu Xiangjun
 * @date 2020.05.28
 * @version 1.0
 */
package app;

import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.* ;
import java.io.*;
import java.util.Vector;

/*
 * 服务器端程序ui
 * 负责建立与客户端的连接
 */
public class MultiServer extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;
	
	/*
	 * MultiServer constructor
	 */
	public MultiServer() {
		JTextArea jtaLog = new JTextArea();

		// Create a scroll pane to hold text area
		JScrollPane scrollPane = new JScrollPane(jtaLog);

		// Add the scroll pane to the frame
		add(scrollPane, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setTitle("MultiServer by 20186471 quxiangjun");
		setVisible(true);
		
		/*
		 * 建立与客户端的连接
		 */
		try {
			// Create a server socket
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(5500);
			
			jtaLog.append(new Date() + ": MultiServer started at socket 5500\n");
			int count = 1;  //客户端加入的数量
			while(true) {						
				// Connect to player
				Socket player = serverSocket.accept();
				
				//在服务端gui程序中显示加入客户端的信息
				jtaLog.append(new Date() + ": Player" + count + " joined " + '\n');
				jtaLog.append("Player" + count + "'s IP address" + player.getInetAddress().getHostAddress() + '\n');
				
				//为客户端打开一个服务端的监听线程，负责数据的传输
				ServerThread task = new ServerThread(player);
				task.start();
				ServerThread.initIns(task);
				//管理每一个server client线程，加入管理静态类
				DrawManager.GetDrawManager().AddDrawPeople(task);	
				count++;
			}		
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MultiServer frame = new MultiServer();
	}

}

/*
 * 为每一个客户端单开一个线程，拥有接收与发送到所维护线程指令数据的
 */
class ServerThread extends Thread{
	/*
	 * 存储所有已经发出的指令数据
	 */
	private static ArrayList<instruction> insList = new ArrayList<instruction>();
	
	/*
	 * io
	 */
	Socket player;
	DataOutputStream oos;
	DataInputStream ois;
	
	/*
	 * Constructor
	 */
	public ServerThread(Socket player) throws IOException {
		// TODO Auto-generated constructor stub
		this.player = player;		
		InputStream is = player.getInputStream();
		ois = new DataInputStream(is);
	}
	
	/*
	 * 发指令送到此客户端线程所连接的客户端
	 * @param sm 一共5个指令，第一个为操作类型，其余为数据
	 */
	public synchronized void Out(int sm1, int sm2, int sm3, int sm4, int sm5) {
		try {
			OutputStream os = player.getOutputStream();
			oos = new DataOutputStream(os);
			oos.writeInt(sm1);
			oos.writeInt(sm2);
			oos.writeInt(sm3);
			oos.writeInt(sm4);
			oos.writeInt(sm5);
			oos.flush();
		}catch(UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 接收数据线程
	 * 每一次接收数据，都调用群发送指令
	 */
	public void run() {
		try{
			while(true) {
				/*
				 * 循环读取有无此线程下的数据传入
				 */
				int sm1 = ois.readInt();
				int sm2 = ois.readInt();
				int sm3 = ois.readInt();
				int sm4 = ois.readInt();
				int sm5 = ois.readInt();
				//生成指令，存储
				instruction ins = new instruction();
				ins.operation = sm1;
				ins.a = sm2;
				ins.b = sm3;
				ins.c = sm4;
				ins.d = sm5;
				insList.add(ins);
				//调用所有线程的静态管理类，群发到每一个线程
				DrawManager.GetDrawManager().Send(this,sm1,sm2,sm3,sm4,sm5);
			}
		}catch(UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/*
	 * 初始化此线程的客户端
	 * 将以往所有的指令全发送一遍
	 * 静态类
	 * @param serverThread 新开的线程
	 */
	public static void initIns(ServerThread st) {
		if(insList.isEmpty()) {
			return;
		}
		for(instruction ins:insList) {
			st.Out(ins.operation, ins.a, ins.b, ins.c, ins.d);
		}
	}
}



/*
 * 管理所有加入的客户端线程
 */
class DrawManager {
	//因为一个画图系统只有一个管理，所以需进行单例化private
    /*
     *单例化 
     */
    private DrawManager() {}
    private static final DrawManager cm=new DrawManager();
    public static DrawManager GetDrawManager() {
        return cm;
    }

  //创建保存socket的队列
    Vector<ServerThread> vector=new Vector<ServerThread>();

    //添加画图用户
    public void AddDrawPeople(ServerThread cs) {
        vector.add(cs);
    }

    //群发消息
    public void Send(ServerThread cs,int sm1, int sm2, int sm3, int sm4, int sm5) {
        for (int i = 0; i < vector.size(); i++){
        	ServerThread drawSocket=(ServerThread)vector.get(i);
            if(!cs.equals(drawSocket))
            {
            	//调用对应线程的发送指令函数
                drawSocket.Out(sm1,sm2,sm3,sm4,sm5);
            }              
        }
        System.out.println("send");
    }
}

/*
 * 指令类
 * operation 为操作类型
 * abcd 为数据
 */
class instruction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	public int operation;
	public int a;
	public int b;
	public int c;
	public int d;	
}
