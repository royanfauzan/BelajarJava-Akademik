package uts;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class FormBuku extends JFrame {

	private JPanel contentPane;
	private JTextField textF1;
	private JTextField textF2;
	private JTextField textF3;
	private JTextField textF4;
	private JTextField textF5;
	private JScrollPane scrollPane;
	DefaultTableModel tableModel;
	String header[]= {"KodeBuku","Judul Buku","NoISBN","Jumlah Halaman","Penerbit"};
	private JTable table;



	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormBuku frame = new FormBuku();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public FormBuku() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 627, 548);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textF1 = new JTextField();
		textF1.setBounds(129, 36, 137, 20);
		contentPane.add(textF1);
		textF1.setColumns(10);
		
		textF2 = new JTextField();
		textF2.setColumns(10);
		textF2.setBounds(129, 67, 137, 20);
		contentPane.add(textF2);
		
		textF3 = new JTextField();
		textF3.setColumns(10);
		textF3.setBounds(129, 98, 137, 20);
		contentPane.add(textF3);
		
		textF4 = new JTextField();
		textF4.setBounds(129, 129, 137, 20);
		contentPane.add(textF4);
		textF4.setColumns(10);
		
		textF5 = new JTextField();
		textF5.setBounds(129, 161, 137, 20);
		contentPane.add(textF5);
		textF5.setColumns(10);
		
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 341, 541, 88);
		contentPane.add(scrollPane);
		
		tableModel = new DefaultTableModel(null,header);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				
				String kodeBuku = (String)tableModel.getValueAt(i, 0);
				String halaman = Integer.toString((int) tableModel.getValueAt(i, 3)) ;
				String judul = (String)tableModel.getValueAt(i, 1);
				String noisbn = (String)tableModel.getValueAt(i, 2);
				String penerbit = (String)tableModel.getValueAt(i, 4);
				
				textF1.setText(kodeBuku);
				textF1.disable();
				textF2.setText(judul);
				textF3.setText(noisbn);
				textF4.setText(halaman);
				textF5.setText(penerbit);
			}
		});
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		
		
		JButton tombolTampil = new JButton("TAMPILKAN DATA");
		tombolTampil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kosonginTabel();
				getDataTabel();	
				bersih();
				textF1.enable();
			}
		});
		tombolTampil.setBounds(52, 231, 143, 23);
		contentPane.add(tombolTampil);
		
		JButton tombolTambah = new JButton("TAMBAH");
		tombolTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				menambah();
				bersih();
				kosonginTabel();
				getDataTabel();
			}
		});
		tombolTambah.setBounds(205, 231, 89, 23);
		contentPane.add(tombolTambah);
		
		JButton tombolUpdate = new JButton("UPDATE");
		tombolUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mengubah();
				bersih();
				kosonginTabel();
				getDataTabel();
			}
		});
		tombolUpdate.setBounds(302, 231, 126, 23);
		contentPane.add(tombolUpdate);
		
		JButton btnNewButton = new JButton("DELETE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hapus();
				bersih();
				kosonginTabel();
				getDataTabel();
			}
		});
		btnNewButton.setBounds(437, 231, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("KodeBuku");
		lblNewLabel.setBounds(10, 39, 89, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblJudulBuku = new JLabel("Judul Buku");
		lblJudulBuku.setBounds(10, 70, 89, 14);
		contentPane.add(lblJudulBuku);
		
		JLabel lblNewLabel_1 = new JLabel("NoISBN");
		lblNewLabel_1.setBounds(10, 101, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Jumlah Halaman");
		lblNewLabel_2.setBounds(10, 132, 89, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Penerbit");
		lblNewLabel_3.setBounds(10, 164, 59, 14);
		contentPane.add(lblNewLabel_3);
		
	}
	
	
	public void getDataTabel() {
		try
		{
			Connection konek = KonekTester.getKoneksi();
			Statement state = konek.createStatement();
			String query = "SELECT * FROM buku";
			ResultSet rs = state.executeQuery(query);
			while(rs.next())
			{
				Object obj[] = new Object[5];
				obj[0] = rs.getString(1);
				obj[1] = rs.getString(2);
				obj[2] = rs.getString(3);
				obj[3] = rs.getInt(4);
				obj[4] = rs.getString(5);
				tableModel.addRow(obj);
			}
			rs.close();
			state.close();
		}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null, ex) ;
		}
		
	}
	
	public void menambah() {
		String kodeBuku=textF1.getText();
		int halaman = Integer.parseInt(textF4.getText());
		String judulBuku = textF2.getText();
		String noISBN = textF3.getText();
		String penerbit = textF5.getText();
		try {
			Connection konek = KonekTester.getKoneksi();
			String query="INSERT INTO buku VALUES(?,?,?,?,?)";
			PreparedStatement p = konek.prepareStatement(query);
			p.setString(1, kodeBuku);
			p.setString(2, judulBuku);
			p.setString(3, noISBN);
			p.setInt(4, halaman);
			p.setString(5, penerbit);
			p.executeUpdate();
			p.close();
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex) ;
		}
	}

	public void kosonginTabel() {
		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();
	}
	
	public void bersih() {
		textF1.enable();
		textF1.setText("");
		textF2.setText("");
		textF3.setText("");
		textF4.setText("");
		textF5.setText("");

	}
	
	public void Mengubah() {
		String kodeBuku=textF1.getText();
		int halaman = Integer.parseInt(textF4.getText());
		String judulBuku = textF2.getText();
		String noISBN = textF3.getText();
		String penerbit = textF5.getText();
		try {
			Connection konek = KonekTester.getKoneksi();
			String query="UPDATE buku set JudulBuku=?,NoISBN=?,JumlahHalaman=?,Penerbit=? where KodeBuku=? ";
			PreparedStatement p = konek.prepareStatement(query);
			p.setString(5, kodeBuku);
			p.setString(1, judulBuku);
			p.setString(2, noISBN);
			p.setInt(3, halaman);
			p.setString(4, penerbit);
			p.executeUpdate();
			p.close();
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex) ;
		}
	}
	
	public void hapus() {
		String kodeBuku=textF1.getText();

		try {
			Connection konek = KonekTester.getKoneksi();
			String query="DELETE FROM buku where KodeBuku=? ";
			PreparedStatement p = konek.prepareStatement(query);
			p.setString(1, kodeBuku);
			p.executeUpdate();
			p.close();
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex) ;
		}
	}
}

