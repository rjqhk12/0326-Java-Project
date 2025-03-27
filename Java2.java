package Java_Project;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Java_Project.SawonAdd.PhotoDraw;
import day0319.DbConnect;

public class Java2 extends JFrame implements ActionListener {

	Container cp;
	JButton btnAdd, btnDel, btnUpdate,btnSearch,btnSelect, btnInfo;
	JTable table;
	DefaultTableModel model;
	DbConnect db=new DbConnect();
	String imageName;

	PhotoDraw pDraw=new PhotoDraw();
	
	SawonAdd addSawon=new SawonAdd("사원추가폼");
	SawonUpdate updateSawon=new SawonUpdate("사원수정폼") ;
	
	
	
	

		//생성자
		public Java2(String title) {
			super(title);//JFrame 부모생성자를 통해서 창제목으로 제목을 보게하자
			
			this.setBounds(200, 100, 900, 500);
			
			//색상
			//this.setBackground(Color.BLUE); // 이렇게만 하면 안나옴.
			//this.getContentPane().setBackground(Color.PINK); //Color라는 클라스의 상수변수를 가져와서 변경해도 되고
			//this.getContentPane().setBackground(new Color(255,165,0)); //rgb통해서 구하기
			cp=this.getContentPane();
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			initDesign();
			cp.setBackground(Color.WHITE);
			
			this.setVisible(true);
			
		}
		
		
		public void initDesign()
		{
			this.setLayout(null);
			String [] title= { "번호", "사원번호", "이름","부서", "직급", "전화번호","이메일","주소"};
			model=new DefaultTableModel(title, 0);
			table=new JTable(model);
			
			table.getColumnModel().getColumn(0).setPreferredWidth(30); //셀너비
			table.getColumnModel().getColumn(1).setPreferredWidth(80);
			table.getColumnModel().getColumn(2).setPreferredWidth(60);
			table.getColumnModel().getColumn(3).setPreferredWidth(60);
			table.getColumnModel().getColumn(4).setPreferredWidth(40);
			
			JScrollPane js=new JScrollPane(table);
			js.setBounds(30, 80, 500, 300);
			this.add(js);
			
			

			/*jp=new JPanel();
			jp.setBounds(550, 30, 320, 380);		
			this.add(jp);*/
			
			
			SelectSawon();
			
			
			btnAdd=new JButton("사원 추가");
			btnAdd.setBounds(30, 20, 90, 30);
			this.add(btnAdd);
			btnAdd.addActionListener(this);
			
			
			btnUpdate=new JButton("사원 수정");
			btnUpdate.setBounds(130, 20, 90, 30);
			this.add(btnUpdate);
			btnUpdate.addActionListener(this);

			btnDel=new JButton("사원 삭제");
			btnDel.setBounds(230, 20, 90, 30);
			this.add(btnDel);
			btnDel.addActionListener(this);

			btnSearch=new JButton("사원 검색");
			btnSearch.setBounds(330, 20, 90, 30);
			this.add(btnSearch);
			btnSearch.addActionListener(this);

			btnSelect=new JButton("전체 보기");
			btnSelect.setBounds(430, 20, 90, 30);
			this.add(btnSelect);
			btnSelect.addActionListener(this);
			
			btnInfo=new JButton("상세 정보");
			btnInfo.setBounds(230, 400, 90, 30);
			this.add(btnInfo);
			btnInfo.addActionListener(this);
			
			
			

			
			addSawon.btnAdd.addActionListener(this);
			updateSawon.btnUpdate.addActionListener(this);

		}
		
		public void SawonInfo(String num)
		{
			
			
			
			
			
			
			Connection conn=db.getConnection();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			String name = null,dept = null,position = null,tel = null,email = null,addr = null;
			String sql="select * from sawonmanagement where id="+num;
			
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				
				
				while(rs.next())
				{
					
						
					
					
				 name=rs.getString("name");
				 dept=rs.getString("dept");
				 position=rs.getString("Position");
				 tel=rs.getString("tel");
				 email=rs.getString("email");
				 addr=rs.getString("addr");
				 imageName=rs.getString("imageName");
				 
				
				
				 
				 
				 	JLabel lblname= new JLabel("이름 : "+name);
					lblname.setBounds(550, 200, 100, 30);
					cp.add(lblname);

					JLabel lbldept= new JLabel("부서 : "+dept);
					lbldept.setBounds(550, 230, 100, 30);
					cp.add(lbldept);
					
					JLabel lblposition= new JLabel("직급 : "+position);
					lblposition.setBounds(550, 260, 100, 30);
					cp.add(lblposition);
					
					JLabel lbltel= new JLabel("전화번호 : "+tel);
					lbltel.setBounds(550, 290, 200, 30);
					cp.add(lbltel);
					
					
					JLabel lblemail= new JLabel("이메일 : "+email);
					lblemail.setBounds(550, 320, 200, 30);
					cp.add(lblemail);
					
					JLabel lbladdr= new JLabel("주소 : "+addr);
					lbladdr.setBounds(550, 350, 200, 30);
					cp.add(lbladdr);	 
					
					pDraw.setBounds(550, 30, 140, 170);
					
					pDraw.setBackground(Color.white);
					this.add(pDraw);				
					//pDraw.repaint();
					
					
					
					
				 
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				db.dbClose(rs, pstmt, conn);
				
			}
			

			
		}
		
		
		
		
		public void SelectSawon()
		{
			model.setRowCount(0); //초기화
			
			Connection conn=db.getConnection();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			String sql="select ROWNUM no,id,name,dept,position,tel,email,addr from SawonManagement order by name";
			
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				
				while(rs.next())
				{
					
					Vector<String >data=new Vector<String>();
					data.add(rs.getString("no"));
					data.add(rs.getString("id"));
					data.add(rs.getString("name"));
					data.add(rs.getString("dept"));
					data.add(rs.getString("position"));
					data.add(rs.getString("tel"));
					data.add(rs.getString("email"));
					data.add(rs.getString("addr"));
					
					model.addRow(data);					
					
				}				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				db.dbClose(rs, pstmt, conn);
			}
		}


		
		public void insertSawon()
		{
			
			
			
			String name=addSawon.tfName.getText();
			String dept=addSawon.tfDept.getText();
			String pos=addSawon.tfPos.getText();
			String tel=addSawon.tfTel.getText();
			String email=addSawon.tfEmail.getText();
			String addr=addSawon.tfAddr.getText();
			String imageName=addSawon.imageName;
			
			
			String sql="insert into SawonManagement values(seq_test.nextval,?,?,?,?,?,?,?)";
			
			Connection conn=db.getConnection();
			PreparedStatement pstmt=null;
			
			try {
				pstmt=conn.prepareStatement(sql);
				
				pstmt.setString(1, name);
				pstmt.setString(2, dept);
				pstmt.setString(3, pos);
				pstmt.setString(4, tel);
				pstmt.setString(5, email);
				pstmt.setString(6, addr);
				pstmt.setString(7, imageName);
				
				pstmt.execute();
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				db.dbClose(pstmt, conn);
			}
		
		}
		
		public void updateSawon()
		{
			String num=updateSawon.num;
			String name=updateSawon.tfName.getText();
			String dept=updateSawon.tfDept.getText();
			String pos=updateSawon.tfPos.getText();
			String tel=updateSawon.tfTel.getText();
			String email=updateSawon.tfEmail.getText();
			String addr=updateSawon.tfAddr.getText();
			String imageName=updateSawon.imageName;
			String sql="update SawonManagement set name=?,dept=?,position=?,tel=?,email=?,addr=?,imageName=? where id=?";
			
			Connection conn=db.getConnection();
			PreparedStatement pstmt=null;
			
			try {
				pstmt=conn.prepareStatement(sql);
				
				pstmt.setString(1, name);
				pstmt.setString(2, dept);
				pstmt.setString(3, pos);
				pstmt.setString(4, tel);
				pstmt.setString(5, email);
				pstmt.setString(6, addr);
				pstmt.setString(8, num);
				pstmt.setString(7, imageName);
				pstmt.executeUpdate();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				db.dbClose(pstmt, conn);
			}
			
			
		}
		
		public void oneSawonData(String num)
		{
			
			String sql="select * from SawonManagement where id="+num;
			
			Connection conn=db.getConnection();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				
				if(rs.next())
				{
					updateSawon.num=num;
					updateSawon.tfName.setText(rs.getString("name"));
					updateSawon.tfDept.setText(rs.getString("dept"));
					updateSawon.tfPos.setText(rs.getString("position"));
					updateSawon.tfTel.setText(rs.getString("tel"));
					updateSawon.tfEmail.setText(rs.getString("email"));
					updateSawon.tfAddr.setText(rs.getString("addr"));
					updateSawon.imageName=rs.getString("imageName");
					updateSawon.setVisible(true);
				}else
				{
					System.out.println("없는번호");
				}
				
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				db.dbClose(rs, pstmt, conn);
			}
			
			
			
			
		}

		
		public void searchSawon(String fn)
		{
			
			model.setRowCount(0);
			Connection conn=db.getConnection();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			String sql="select ROWNUM no,id,name,dept,position,tel,email,addr from SawonManagement where name like ?";
			
			try {
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, fn+"%");
				rs=pstmt.executeQuery();
				
				while(rs.next())
				{
					Vector<String >data=new Vector<String>();
					data.add(rs.getString("no"));
					data.add(rs.getString("id"));
					data.add(rs.getString("name"));
					data.add(rs.getString("dept"));
					data.add(rs.getString("position"));
					data.add(rs.getString("tel"));
					data.add(rs.getString("email"));
					data.add(rs.getString("addr"));
					
					model.addRow(data);			
				}
				int n=pstmt.executeUpdate();	
				if(n==1)
					System.out.println("검색 성공");
				else 
					System.out.println("검색 실패");
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				db.dbClose(rs, pstmt, conn);
				
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			Object ob=e.getSource();
			
			Connection conn=db.getConnection();
			PreparedStatement pstmt=null;
			String sql="";
			
			if(ob==btnAdd)
			{
				addSawon.setVisible(true);
			}
			
			else if(ob==btnSelect)
			{
				SelectSawon();
			}
		
			else if(ob==btnDel)
			{
				int row=table.getSelectedRow()	;
				System.out.println(row);
				
				if(row==-1)
				{
					JOptionPane.showMessageDialog(this, "삭제할 행을 선택해주세요.");
					return;
				}
				
				String num=(String)model.getValueAt(row, 1);
				System.out.println(num);
				
				sql="delete from SawonManagement where id=?";
						
				try {
					pstmt=conn.prepareStatement(sql);
					
					pstmt.setString(1, num);
					
					pstmt.execute();
					
					this.SelectSawon();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally {
					db.dbClose(pstmt, conn);
				}
			}
			else if (ob==addSawon.btnAdd)
			{
				this.insertSawon();
				
				this.SelectSawon();
				
				addSawon.tfName.setText("");
				addSawon.tfDept.setText("");
				addSawon.tfPos.setText("");
				addSawon.tfTel.setText("");
				addSawon.tfEmail.setText("");
				addSawon.tfAddr.setText("");
				
				
				addSawon.setVisible(false);
			}
			
			
			else if (ob==btnUpdate)
			{
				int row=table.getSelectedRow() ;
				
				if(row==-1)
				{
					JOptionPane.showMessageDialog(this, "수정할 행을 선택해주세요.");
					return;
				}
				String num=(String)model.getValueAt(row, 1);
				
				//String num=JOptionPane.showInputDialog("번호입력");
				this.oneSawonData(num);
				//updateSawon.setVisible(true);
			}
			else if(ob==updateSawon.btnUpdate)
			{
				this.updateSawon();
				this.SelectSawon();
				updateSawon.setVisible(false);
			}
			else if(ob==btnSearch)
			{
				String fn=JOptionPane.showInputDialog("검색할 사원의 성을 입력하세요.");
				
				searchSawon(fn);
			}
			
			else if(ob==btnInfo)
			{	
				
				int row=table.getSelectedRow()	;
				
				
				if(row==-1)
				{
					JOptionPane.showMessageDialog(this, "불러 올 행을 선택해주세요.");
					return;
				}
				
				String num=(String)model.getValueAt(row, 1);
				
				
				SawonInfo(num);
				
				
				
				
				
				
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
					g.drawImage(image, 0, 0,140,150, this);
					
				}
			}
		}
		
		

		public static void main(String[] args) {
			// TODO Auto-generated method stub

			Java2 sw1=new Java2("Java_Project");
			
		}


	

}