package Java_Project;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Java_Project.SawonAdd.PhotoDraw;

public class SawonUpdate extends JFrame implements ActionListener{
	
	Container cp;
	JTextField tfName,tfDept,tfPos,tfTel,tfEmail,tfAddr;
	JButton btnUpdate, btnImage;
	String num;
	String imageName;
	
	PhotoDraw pDraw=new PhotoDraw();
	
	public SawonUpdate (String title) {
	super(title); 
	
	//위치
	this.setBounds(500, 100, 500, 500);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//색상
	cp=this.getContentPane();
	cp.setBackground(Color.white);
	initDesign();
	this.setVisible(false);
	}
	
	public void initDesign()
	{
		this.setLayout(null);
		
		JLabel lbl1=new JLabel("이름");
		JLabel lbl2=new JLabel("부서");
		JLabel lbl3=new JLabel("직급");
		JLabel lbl4=new JLabel("전화번호");
		JLabel lbl5=new JLabel("이메일");
		JLabel lbl6=new JLabel("주소");
		
		lbl1.setBounds(30, 30, 80, 30);
		this.add(lbl1);
		lbl2.setBounds(30, 90, 80, 30);
		this.add(lbl2);
		lbl3.setBounds(30, 150, 80, 30);
		this.add(lbl3);
		lbl4.setBounds(30, 210, 80, 30);
		this.add(lbl4);
		lbl5.setBounds(30, 270, 80, 30);
		this.add(lbl5);
		lbl6.setBounds(30, 330, 80, 30);
		this.add(lbl6);
		
		tfName=new JTextField(6);
		tfDept=new JTextField(6);
		tfPos=new JTextField(6);
		tfTel=new JTextField(10);
		tfEmail=new JTextField(15);
		tfAddr=new JTextField(20);
		
		tfName.setBounds(150, 30, 100, 30);
		this.add(tfName);
		tfDept.setBounds(150, 90, 100, 30);
		this.add(tfDept);
		tfPos.setBounds(150, 150, 150, 30);
		this.add(tfPos);
		tfTel.setBounds(150, 210, 200, 30);
		this.add(tfTel);
		tfEmail.setBounds(150, 270, 200, 30);
		this.add(tfEmail);
		tfAddr.setBounds(150, 330, 200, 30);
		this.add(tfAddr);
		
		btnUpdate=new JButton("사원정보수정");
		btnUpdate.setBounds(100, 380, 180, 50);
		this.add(btnUpdate);
		
		btnImage=new JButton("사진선택");
		btnImage.setBounds(350, 210, 100, 30);
		this.add(btnImage);
		btnImage.addActionListener(this); 
		
		pDraw.setBounds(330, 30, 140, 170);
		pDraw.setBackground(Color.white);
		this.add(pDraw);
		
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	Object ob= e.getSource();
		
		if(ob==btnImage)
		{
			FileDialog dlg=new FileDialog(this, "이미지 가져오기", FileDialog.LOAD);
			dlg.setVisible(true);
			imageName=dlg.getDirectory()+dlg.getFile(); 
			
			pDraw.repaint();

		}
		
		
	}
	
class PhotoDraw extends Canvas{
		
		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			super.paint(g);
			
			if(imageName!=null)
			{
				Image image=new ImageIcon(imageName).getImage();
				g.drawImage(image, 0, 0,130,150, this);
				
			}
		}
	}
	
	

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SawonUpdate("사원정보수정");
	}

	

}
