/*
 * @author Qu Xiangjun
 * @date 2020.05.28
 * @version 1.0
 */
package app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.* ;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import graph.*;

public class OpenGLApp extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9L;

	private Graphic graphic = new Graphic(this.getGraphics());	//ʵ����graphic��
	
	/*
	 * ��ť����
	 */
	JPanel buttonJpanel = new JPanel();
	JButton rectangleButton = new JButton("Rectangle");
	JButton triangleButton = new JButton("Triangle");
	JButton SelectButton = new JButton("Select");
	JButton blackButton = new JButton("black");
	JButton redButton = new JButton("red");
	JButton yellowButton = new JButton("yellow");
	JButton saveButton = new JButton("save");
	
	/*
	 * Destructor
	 */
	public OpenGLApp() {
		buttonJpanel.add(rectangleButton);
		buttonJpanel.add(triangleButton);
		buttonJpanel.add(SelectButton);
		buttonJpanel.add(blackButton);
		buttonJpanel.add(redButton);
		buttonJpanel.add(yellowButton);
		buttonJpanel.add(saveButton);
		this.add(buttonJpanel);
		
		//���ð�ť��С����ʾ��������ɫ��
		Dimension shapedimension = new Dimension(100,50);
		rectangleButton.setPreferredSize(shapedimension);
		rectangleButton.setToolTipText("This is rectangle.");  //��곤ʱ��ͣ���ó���ʾ
		triangleButton.setPreferredSize(shapedimension);
		triangleButton.setToolTipText("This is triangle.");
		SelectButton.setPreferredSize(shapedimension);
		SelectButton.setToolTipText("This is select.");
		blackButton.setPreferredSize(shapedimension);
		blackButton.setBackground(Color.BLACK);
		blackButton.setToolTipText("This is black.");
		redButton.setPreferredSize(shapedimension);
		redButton.setBackground(Color.RED);
		redButton.setToolTipText("This is red.");
		yellowButton.setPreferredSize(shapedimension);
		yellowButton.setBackground(Color.YELLOW);
		yellowButton.setToolTipText("This is yellow.");
		saveButton.setPreferredSize(shapedimension);
		saveButton.setToolTipText("save image");
		
		setTitle("Qu Xiangjun's Network whiteblock drawing");
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new FlowLayout());		
	}
			
	/*
	 * ����ͼƬ
	 */
	public void savePic() throws IOException{
		//ѡ��洢λ��
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		JLabel choose = new JLabel();
		chooser.showDialog(choose, "Select file");
		File file = chooser.getSelectedFile();
		String filePath = file.getAbsolutePath();
		//�ļ�����
		String imageName = JOptionPane.showInputDialog(this,"File name:","Please enter the storage path of the image file name",JOptionPane.PLAIN_MESSAGE);
		
		//�õ������������
		Container content=this.getContentPane();
		//��������ͼƬ����
		BufferedImage img=new BufferedImage(
				this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_RGB);
		//�õ�ͼ�ζ���
		Graphics2D g2d = img.createGraphics();
		//������������������ͼ�ζ�����
		g2d.setBackground(Color.white);
		graphic.draw(g2d);
		
		//����ΪͼƬ
		File f=new File(filePath + File.separator +imageName);
		try {
			ImageIO.write(img, "jpg", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//�ͷ�ͼ�ζ���
		g2d.dispose();
	}
	
	/*
	 * ����graphic
	 */
	public Graphic getGraphic() {
		return graphic;
	}
	
	/*
	 * ����paint����
	 */
	public void paint(Graphics g) {
		super.paint(g);
		graphic.draw(g);
	}
	
	
	
//	public static void main(String[] args)  {
//		OpenGLApp gui = new OpenGLApp();
//		gui.setVisible(true);
//	}
}
