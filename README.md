if(row==-1)
				{
					JOptionPane.showMessageDialog(this, "수정할 행을 선택해주세요.");
					return;
				}
				String num=(String)model.getValueAt(row, 1);
