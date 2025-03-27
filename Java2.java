package Java_Project;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import day0319.DbConnect;

public class Java2 extends JFrame implements ActionListener {

	Container cp;
	JButton btnAdd, btnDel, btnUpdate,btnSearch,btnSelect;
	JTable table;
	DefaultTableModel model;
	DbConnect db=new DbConnect();
	
	SawonAdd addSawon=new SawonAdd("사원추가폼");
	SawonUpdate updateSawon=new SawonUpdate("사원정보 수정");
	
		//생성자
		public Java2(String title) {
			super(title);//JFrame 부모생성자를 통해서 창제목으로 제목을 보게하자
			
			this.setBounds(200, 100, 800, 500);
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
			String [] title= {"번호", "사원번호", "이름","부서", "직급", "전화번호","이메일","주소"};
			model=new DefaultTableModel(title, 0);
			table=new JTable(model);
			
			JScrollPane js=new JScrollPane(table);
			js.setBounds(30, 100, 500, 300);
			this.add(js);
			
			
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
			
			addSawon.btnAdd.addActionListener(this);

		}
		public void SelectSawon()
		{
			model.setRowCount(0); //초기화
			
			Connection conn=db.getConnection();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			String sql="select ROWNUM no,id,name,dept,position,tel,email,addr from SawonManagement order by no";
			
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
			
			String sql="insert into SawonManagement values(seq_test.nextval,?,?,?,?,?,?)";
			
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
				
				pstmt.execute();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				db.dbClose(pstmt, conn);
			}
		
		}
		
		public void isOneData(String num)
		{
			String sql="select * from stuScore where id=?";
			
			Connection conn=db.getConnection();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
					
				try {
					pstmt=conn.prepareStatement(sql);
					pstmt.setString(1, num);
					
					rs=pstmt.executeQuery();
					
					if(rs.next())
					{
						updateSawon.num=num;
						updateSawon.tfName.setText(rs.getString("name"));
						updateSawon.tfDept.setText(rs.getString("Dept"));
						updateSawon.tfPos.setText(rs.getString("position"));
						updateSawon.tfTel.setText(rs.getString("tel"));
						updateSawon.tfEmail.setText(rs.getString("email"));
						updateSawon.tfAddr.setText(rs.getString("addr"));
						
						updateSawon.setVisible(true);
					}
					else
						JOptionPane.showMessageDialog(this, "없는 사원입니다");
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					db.dbClose(rs, pstmt, conn);
				}
				
				
			
			
		}
		
		
		
		
		
		public void updateSawon()
		{
			String name=addSawon.tfName.getText();
			String dept=addSawon.tfDept.getText();
			String pos=addSawon.tfPos.getText();
			String tel=addSawon.tfTel.getText();
			String email=addSawon.tfEmail.getText();
			String addr=addSawon.tfAddr.getText();
			

			String sql="update SawonManagement set name=?,dept=?,position=?,tel=?,email=?,addr=?";
			
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
				
				pstmt.execute();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				db.dbClose(pstmt, conn);
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
			
			else if(ob==btnSearch)
			{
				String fn=JOptionPane.showInputDialog("검색할 사원의 성을 입력하세요.");
				
				searchSawon(fn);
			}
			
			else if(ob==btnUpdate)
			{
				int row=table.getSelectedRow()	;
				System.out.println(row);
				
				if(row==-1)
				{
					JOptionPane.showMessageDialog(this, "수정할 행을 선택해주세요.");
					return;
				}
				String num=(String)model.getValueAt(row, 1);
				isOneData(num);
				
				
				
				
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
		}
		
		
		
		

		

		public static void main(String[] args) {
			// TODO Auto-generated method stub

			Java2 sw1=new Java2("Java_Project");
			
		}


	

}