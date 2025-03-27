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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import day0319.DbConnect;

public class JavaProject extends JFrame implements ActionListener {

	Container cp;
	JButton btnAdd, btnDel, btnUpdate,btnSearch,btnSelect;
	JTable table;
	DefaultTableModel model;
	DbConnect db=new DbConnect();
	
	SawonAdd addSawon=new SawonAdd("성적추가폼");

		//생성자
		public JavaProject(String title) {
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
			String [] title= { "사원번호", "이름","부서", "직급", "전화번호","이메일","주소"};
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
			btnAdd.addActionListener(this);

			btnDel=new JButton("사원 삭제");
			btnDel.setBounds(230, 20, 90, 30);
			this.add(btnDel);
			btnDel.addActionListener(this);

			btnSearch=new JButton("사원 검색");
			btnSearch.setBounds(330, 20, 90, 30);
			this.add(btnSearch);
			btnAdd.addActionListener(this);

			btnSelect=new JButton("전체 보기");
			btnSelect.setBounds(430, 20, 90, 30);
			this.add(btnSelect);
			btnSelect.addActionListener(this);
			

		}
		public void SelectSawon()
		{
			model.setRowCount(0); //초기화
			
			Connection conn=db.getConnection();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			String sql="select * from SawonManagement order by num";
			
			try {
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				
				while(rs.next())
				{
					Vector<String >data=new Vector<String>();
					data.add(rs.getString("num"));
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

		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			Object ob=e.getSource();
			
			Connection conn=db.getConnection();
			PreparedStatement pstmt=null;
			String sql="";
			
			if(ob==btnAdd)
			{
				
			}
			
			if(ob==btnSelect)
			{
				SelectSawon();
			}
		
			if(ob==btnDel)
			{
				int row=table.getSelectedRow()	;
				System.out.println(row);
				
				if(row==-1)
				{
					JOptionPane.showMessageDialog(this, "삭제할 행을 선택해주세요.");
					return;
				}
				
				String num=(String)model.getValueAt(row, 0);
				System.out.println(num);
				
				sql="delete from SawonMangement where num=num";
						
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
		
		

		

		public static void main(String[] args) {
			// TODO Auto-generated method stub

			JavaProject sw1=new JavaProject("Java_Project");
			
		}


	

}
