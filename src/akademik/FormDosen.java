package akademik;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;


public class FormDosen extends JFrame {
	private JTable table;
	String header[] = {"NIP","Nama Dosen","Alamat","Pendidikan Terakhir","Jenis Kelamin"};
	DefaultTableModel tabelModel;
	private JScrollPane scrollPane;
	private JTextField textfieldNip;
	private JTextField textFieldNama;
	private JTextField textFieldAlamat;
	private JComboBox comboboxPendidikan;
	private JRadioButton rdbtnNewRadioButtonLaki;
	private JRadioButton rdbtnNewRadioButtonPerempuan;
	private String jenis;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormDosen frame = new FormDosen();
					frame.setVisible(true);
					frame.setSize(600, 800);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public FormDosen() {
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("TAMPILKAN DATA");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kosonginTabel();
				getDataTabel();
				bersihkan();
				textfieldNip.enable();
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

				String nip=(String) tabelModel.getValueAt(i,0);
				String nama=(String) tabelModel.getValueAt(i,1);
				String alamat=(String) tabelModel.getValueAt(i,2);
				String pendidikan=(String) tabelModel.getValueAt(i,3);
				jenis=(String) tabelModel.getValueAt(i,4);
				
				textfieldNip.setText(nip);
				textfieldNip.disable();
				textFieldNama.setText(nama);
				comboboxPendidikan.setSelectedItem(pendidikan);
				textFieldAlamat.setText(alamat);
				if (jenis.equals("Perempuan") ) {
					rdbtnNewRadioButtonPerempuan.setSelected(true);
				} else {
					rdbtnNewRadioButtonLaki.setSelected(true);
				}
				
			}
		});
		table.setModel(tabelModel);
		scrollPane.setViewportView(table);
		
		textfieldNip = new JTextField();
		textfieldNip.setBounds(177, 31, 142, 20);
		getContentPane().add(textfieldNip);
		textfieldNip.setColumns(10);
		
		textFieldNama = new JTextField();
		textFieldNama.setColumns(10);
		textFieldNama.setBounds(177, 62, 142, 20);
		getContentPane().add(textFieldNama);
		
		textFieldAlamat = new JTextField();
		textFieldAlamat.setColumns(10);
		textFieldAlamat.setBounds(177, 107, 142, 20);
		getContentPane().add(textFieldAlamat);
		
		comboboxPendidikan = new JComboBox();
		comboboxPendidikan.setModel(new DefaultComboBoxModel(new String[] {"Sarjana", "Magister", "Doktor"}));
		comboboxPendidikan.setBounds(177, 163, 142, 20);
		getContentPane().add(comboboxPendidikan);
		
		JLabel lblNewLabel = new JLabel("NIP");
		lblNewLabel.setBounds(26, 34, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nama");
		lblNewLabel_1.setBounds(26, 71, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Pendidikan Terakhir");
		lblNewLabel_2.setBounds(26, 166, 117, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Alamat");
		lblNewLabel_3.setBounds(26, 107, 46, 14);
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
				textfieldNip.enable();
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
				textfieldNip.enable();
			}
		});
		btnNewButtonDelete.setBounds(230, 266, 89, 23);
		getContentPane().add(btnNewButtonDelete);
		
		JLabel lblNewLabel_4 = new JLabel("Jenis Kelamin");
		lblNewLabel_4.setBounds(26, 200, 89, 14);
		getContentPane().add(lblNewLabel_4);
		
		rdbtnNewRadioButtonLaki = new JRadioButton("Laki-Laki");
		buttonGroup.add(rdbtnNewRadioButtonLaki);
		rdbtnNewRadioButtonLaki.setBounds(187, 196, 109, 23);
		getContentPane().add(rdbtnNewRadioButtonLaki);
		
		rdbtnNewRadioButtonPerempuan = new JRadioButton("Perempuan");
		buttonGroup.add(rdbtnNewRadioButtonPerempuan);
		rdbtnNewRadioButtonPerempuan.setBounds(187, 222, 109, 23);
		getContentPane().add(rdbtnNewRadioButtonPerempuan);
	}
	
	public void getDataTabel() {
		try
		{
			Connection konek = Koneksi.getKoneksi();
			Statement state = konek.createStatement();
			String query = "SELECT * FROM dosen ORDER BY nip";
			ResultSet rs = state.executeQuery(query);
			while(rs.next())
			{
				Object obj[] = new Object[5];
				obj[0] = rs.getString(1);
				obj[1] = rs.getString(2);
				obj[2] = rs.getString(3);
				obj[3] = rs.getString(4);
				obj[4] = rs.getString(5);
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
		textfieldNip.setText("");
		textFieldNama.setText("");
		comboboxPendidikan.setSelectedIndex(0);
		textFieldAlamat.setText("");
		rdbtnNewRadioButtonLaki.setSelected(true);
		rdbtnNewRadioButtonPerempuan.setSelected(false);
	}
	
	public void menambah() {
		String nip=textfieldNip.getText();
		String nama=textFieldNama.getText();
		String pendidikan=comboboxPendidikan.getSelectedItem().toString();
		String alamat =textFieldAlamat.getText();
		String jeniskelamin=jenisk();
		
		try {
			Connection pengkonek = Koneksi.getKoneksi();
			String query = "INSERT INTO dosen VALUES(?,?,?,?,?)";
			PreparedStatement p = pengkonek.prepareStatement(query);
			p.setString(1, nip);
			p.setString(2, nama);
			p.setString(4, pendidikan);
			p.setString(3, alamat);
			p.setString(5, jeniskelamin);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}
	
	public void perubah() {
		String nip=textfieldNip.getText();
		String nama=textFieldNama.getText();
		String pendidikan=comboboxPendidikan.getSelectedItem().toString();
		String alamat =textFieldAlamat.getText();
		String jeniskelamin=jenisk();
		
		try{
			Connection konek=Koneksi.getKoneksi();
			 
			String query = "Update dosen set namaDosen=?,alamatDosen=?,pendidikanTerakhir=?,jenisKelamin=? where nip=?"; 
			PreparedStatement p = konek.prepareStatement(query);
			p.setString(1, nama);
			p.setString(2, pendidikan);
			p.setString(3, alamat);
			p.setString(4, jeniskelamin);
			p.setString(5, nip);
		
			p.executeUpdate();
			p.close();
			
		}
		catch(SQLException ex){
			System.out.println(ex);
		}
	}
	
	public void hapus() {
		String nim=textfieldNip.getText();
		try {
			Connection konek=Koneksi.getKoneksi();
			String query = "Delete From dosen where nip=?"; 
			PreparedStatement p = konek.prepareStatement(query);
			p.setString(1, nim);
			p.executeUpdate();
			p.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String jenisk() {
		String a="";
		
		if(rdbtnNewRadioButtonLaki.isSelected()) {
			a="Laki-Laki";
		}else if(rdbtnNewRadioButtonPerempuan.isSelected()) {
			a="Perempuan";
		}
		return a;
	}
}

