package akademik;

import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.sql.PreparedStatement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class FormTest extends JFrame {
	private JPanel contentPane;
	String header[] = {"NIM","Nama","Jurusan","Alamat"};
	
	DefaultTableModel mouseClicked;
	DefaultTableModel tabelModel;

	private JTable table;
	private JTextField textField_nim;
	private JTextField textField_nama;
	private JTextField textField_alamat;
	private String nim,nama,alamat,jurusan;
	private JComboBox comboBox_jurusan ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormTest frame = new FormTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnTampilkanData = new JButton("TAMPILKAN DATA");
		btnTampilkanData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emptyTable();
				getDataTable();
			}
		});
		btnTampilkanData.setBounds(426, 183, 138, 23);
		contentPane.add(btnTampilkanData);
		
		JScrollPane scrollPane = new JScrollPane();
//		tabelModel = new DefaultTableModel(null,header);
//		table = new JTable();
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int i= table.getSelectedRow();
//				
//				if (i==0){
//					i=1;
//				}
//				
//					String nim=(String) tabelModel.getValueAt(i,0);
//					String nama=(String) tabelModel.getValueAt(i,1);
//					String jurusan=(String) tabelModel.getValueAt(i,2);
//					String alamat=(String) tabelModel.getValueAt(i,3);
//					
//					textField_nim.setText(nim);
//					textField_nama.setText(nama);
//					comboBox_jurusan.setSelectedItem(jurusan);
//					textField_alamat.setText(alamat);
//			}
//		});
		scrollPane.setBounds(31, 217, 533, 167);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("NIM");
		lblNewLabel.setBounds(31, 14, 105, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nama");
		lblNewLabel_1.setBounds(31, 45, 105, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Jurusan");
		lblNewLabel_2.setBounds(31, 75, 105, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Alamat");
		lblNewLabel_3.setBounds(31, 112, 105, 14);
		contentPane.add(lblNewLabel_3);
		
		tabelModel = new DefaultTableModel(null,header);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i= table.getSelectedRow();
//				
//				if (i==0){
//					i=1;
//				}
				
					String nim=(String) tabelModel.getValueAt(i,0);
					String nama=(String) tabelModel.getValueAt(i,1);
					String jurusan=(String) tabelModel.getValueAt(i,2);
					String alamat=(String) tabelModel.getValueAt(i,3);
					
					textField_nim.setText(nim);
					textField_nama.setText(nama);
					comboBox_jurusan.setSelectedItem(jurusan);
					textField_alamat.setText(alamat);
			}
		});
		table.setModel(tabelModel);
		scrollPane.setViewportView(table);
		
		textField_nim = new JTextField();
		textField_nim.setBounds(174, 11, 176, 20);
		contentPane.add(textField_nim);
		textField_nim.setColumns(10);
		
		textField_nama = new JTextField();
		textField_nama.setBounds(174, 42, 176, 20);
		contentPane.add(textField_nama);
		textField_nama.setColumns(10);
		
		comboBox_jurusan = new JComboBox();
		comboBox_jurusan.setModel(new DefaultComboBoxModel(new String[] {"Teknik Mesin", "Teknik Sipil", "Teknik Elektro", "Administrasi Niaga", "Akuntansi", "Pariwisata"}));
		comboBox_jurusan.setBounds(174, 75, 176, 20);
		contentPane.add(comboBox_jurusan);
		
		textField_alamat = new JTextField();
		textField_alamat.setBounds(174, 109, 176, 20);
		contentPane.add(textField_alamat);
		textField_alamat.setColumns(10);
		
		JButton btnNewButton_tambah = new JButton("TAMBAH DATA");
		btnNewButton_tambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertDataTable();
				emptyTable();
				getDataTable();
				clearInput();
			}
		});
		btnNewButton_tambah.setBounds(31, 183, 125, 23);
		contentPane.add(btnNewButton_tambah);
		btnNewButton_tambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateDataTable();
				emptyTable();
				getDataTable();
			}
		});
		
		JButton btnNewButton = new JButton("DELETE DATA");
		btnNewButton_tambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteDataTable();
				emptyTable();
				getDataTable();
			}
		});
		btnNewButton.setBounds(301, 183, 115, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("UPDATE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateDataTable();
				clearInput();
				emptyTable();
				getDataTable();
			}
		});
		btnNewButton_1.setBounds(167, 183, 89, 23);
		contentPane.add(btnNewButton_1);
}
		
		public void getDataTable() {
	try
	{
		Connection konek = Koneksi.getKoneksi();
		Statement statement = konek.createStatement();
		String query = "SELECT * FROM mahasiswa2";
		ResultSet rs = statement.executeQuery(query);
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
		statement.close();
	}
	catch(Exception ex)
	{
	}
}
		
		public void InsertDataTable(){
	String nim=textField_nim.getText();
	String nama=textField_nama.getText();
	String jurusan=comboBox_jurusan.getSelectedItem().toString();
	String alamat=textField_alamat.getText();
	try{
		Connection konek=Koneksi.getKoneksi();
		String query = "INSERT INTO mahasiswa2 values (?,?,?,?)"; 
		PreparedStatement p = konek.prepareStatement(query);
		p.setString(1, nim);
		p.setString(2, nama);
		p.setString(3, jurusan);
		p.setString(4, alamat);
		p.executeUpdate();
		p.close();
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
		}
	public void clearInput(){
		textField_nim.setText("");
		textField_nama.setText("");
		comboBox_jurusan.setSelectedIndex(0);
		textField_alamat.setText("");
	}
	public void UpdateDataTable(){
		String nim=textField_nim.getText();
		String nama=textField_nama.getText();
		String jurusan=comboBox_jurusan.getSelectedItem().toString();
		String alamat=textField_alamat.getText();
		try{
			Connection konek=Koneksi.getKoneksi();
			String query = "Update mahasiswa2 set Nama=?,Jurusan=?,Alamat=? where Nim=?"; 
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
	public void emptyTable () {
		tabelModel.getDataVector().removeAllElements();
		tabelModel.fireTableDataChanged();
	}

	public void DeleteDataTable(){
	String nim=textField_nim.getText();
	try{
		Connection konek=Koneksi.getKoneksi();
		String query = "Delete From mahasiswa2 where Nim=?"; 
		PreparedStatement p = konek.prepareStatement(query);
		p.setString(1, nim);
		p.executeUpdate();
		p.close();
	}
	catch(Exception ex){
		System.out.println(ex);
	}
}
}		