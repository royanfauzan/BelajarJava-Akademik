package akademik;

import javax.swing.JFrame;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//import com.mysql.jdbc.PreparedStatement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormMhs extends JFrame {
	private JTable table;
	String header[] = {"NIM","Nama","Jurusan","Alamat"};
	DefaultTableModel tabelModel;
	private JScrollPane scrollPane;
	private JTextField textFieldNim;
	private JTextField textFieldNama;
	private JTextField textFieldAlamat;
	private JComboBox comboBoxJurusan;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormMhs frame = new FormMhs();
					frame.setVisible(true);
					frame.setSize(850, 600);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	public FormMhs() {
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("TAMPILKAN DATA");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kosonginTabel();
				getDataTabel();
			}
		});
		btnNewButton.setBounds(328, 266, 151, 23);
		getContentPane().add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 312, 445, 154);
		getContentPane().add(scrollPane);
		
		tabelModel = new DefaultTableModel(null,header);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int i = table.getSelectedRow();
//				if (i==0) {
//					i=1;
//				}
				String nim=(String) tabelModel.getValueAt(i,0);
				String nama=(String) tabelModel.getValueAt(i,1);
				String jurusan=(String) tabelModel.getValueAt(i,2);
				String alamat=(String) tabelModel.getValueAt(i,3);
				
				textFieldNim.setText(nim);
				textFieldNama.setText(nama);
				comboBoxJurusan.setSelectedItem(jurusan);
				textFieldAlamat.setText(alamat);
			}
		});
		table.setModel(tabelModel);
		scrollPane.setViewportView(table);
		
		textFieldNim = new JTextField();
		textFieldNim.setBounds(131, 31, 142, 20);
		getContentPane().add(textFieldNim);
		textFieldNim.setColumns(10);
		
		textFieldNama = new JTextField();
		textFieldNama.setColumns(10);
		textFieldNama.setBounds(131, 68, 142, 20);
		getContentPane().add(textFieldNama);
		
		textFieldAlamat = new JTextField();
		textFieldAlamat.setColumns(10);
		textFieldAlamat.setBounds(131, 150, 142, 20);
		getContentPane().add(textFieldAlamat);
		
		comboBoxJurusan = new JComboBox();
		comboBoxJurusan.setModel(new DefaultComboBoxModel(new String[] {"Teknik Mesin", "Teknik Elektro", "Teknnik Sipil", "Pariwisata", "Administrasi Niaga", "Akuntansi"}));
		comboBoxJurusan.setBounds(131, 110, 142, 20);
		getContentPane().add(comboBoxJurusan);
		
		JLabel lblNewLabel = new JLabel("NIM");
		lblNewLabel.setBounds(26, 34, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nama");
		lblNewLabel_1.setBounds(26, 71, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Jurusan");
		lblNewLabel_2.setBounds(26, 113, 46, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Alamat");
		lblNewLabel_3.setBounds(26, 153, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JButton btnNewButton_1 = new JButton("TAMBAH");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				menambah();
				bersihkan();
				kosonginTabel();
				getDataTabel();
			}
		});
		btnNewButton_1.setBounds(37, 266, 89, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButtonUpdate = new JButton("UPDATE");
		btnNewButtonUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				perubah();
				bersihkan();
				kosonginTabel();
				getDataTabel();
			}
		});
		btnNewButtonUpdate.setBounds(131, 266, 89, 23);
		getContentPane().add(btnNewButtonUpdate);
		
		JButton btnNewButtonDelete = new JButton("DELETE");
		btnNewButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hapus();
				bersihkan();
				kosonginTabel();
				getDataTabel();
			}
		});
		btnNewButtonDelete.setBounds(230, 266, 89, 23);
		getContentPane().add(btnNewButtonDelete);
	}

	
	
	public void getDataTabel() {
		try
		{
			Connection konek = Koneksi.getKoneksi();
			Statement state = konek.createStatement();
			String query = "SELECT * FROM mahasiswa ORDER BY jurusan,nim";
			ResultSet rs = state.executeQuery(query);
			while(rs.next())
			{
				Object obj[] = new Object[4];
				obj[0] = rs.getString(1);
				obj[1] = rs.getString(2);
				obj[2] = rs.getString(3);
				obj[3] = rs.getString(4);
				tabelModel.addRow(obj);
			}
			rs.close();
			state.close();
		}
		catch(Exception ex)
		{
			
		}
		
	}
	
	public void kosonginTabel() {
		tabelModel.getDataVector().removeAllElements();
		tabelModel.fireTableDataChanged();
	}
	
	public void bersihkan() {
		textFieldNim.setText("");
		textFieldNama.setText("");
		comboBoxJurusan.setSelectedIndex(0);
		textFieldAlamat.setText("");
	}
	
	public void menambah() {
		String nim=textFieldNim.getText();
		String nama=textFieldNama.getText();
		String jurusan=comboBoxJurusan.getSelectedItem().toString();
		String alamat =textFieldAlamat.getText();
		
		try {
			Connection pengkonek = Koneksi.getKoneksi();
			String query = "INSERT INTO mahasiswa VALUES(?,?,?,?)";
			PreparedStatement p = pengkonek.prepareStatement(query);
			p.setString(1, nim);
			p.setString(2, nama);
			p.setString(3, jurusan);
			p.setString(4, alamat);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}
	
	public void perubah() {
		String nim=textFieldNim.getText();
		String nama=textFieldNama.getText();
		String jurusan=comboBoxJurusan.getSelectedItem().toString();
		String alamat =textFieldAlamat.getText();
		
		try{
			Connection konek=Koneksi.getKoneksi();
			 
			String query = "Update mahasiswa set Nama=?,Jurusan=?,Alamat=? where Nim=?"; 
			PreparedStatement p = konek.prepareStatement(query);
			p.setString(1, nama);
			p.setString(2, jurusan);
			p.setString(3, alamat);
			p.setString(4, nim);
		
			p.executeUpdate();
			p.close();
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
	}
	
	public void hapus() {
		String nim=textFieldNim.getText();
		try {
			Connection konek=Koneksi.getKoneksi();
			String query = "Delete From mahasiswa where Nim=?"; 
			PreparedStatement p = konek.prepareStatement(query);
			p.setString(1, nim);
			p.executeUpdate();
			p.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
