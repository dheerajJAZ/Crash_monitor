import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;


public class CPUMemCheck {

	private JFrame frmProcessCrashTracker;

	private JTable table_1;
	private JTable table_2;

	DefaultTableModel model;
	DefaultTableModel model2;

	Thread t1;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() { 
				try {
					CPUMemCheck window = new CPUMemCheck();
					window.frmProcessCrashTracker.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CPUMemCheck() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmProcessCrashTracker = new JFrame();

		frmProcessCrashTracker.getContentPane().setBackground(new Color(245, 245, 245));
		frmProcessCrashTracker.setResizable(false);
		frmProcessCrashTracker.setForeground(Color.BLACK);
		frmProcessCrashTracker.setBackground(Color.DARK_GRAY);
		frmProcessCrashTracker.setTitle("Process Crash Tracker");
		frmProcessCrashTracker.setBounds(100, 100, 627, 512);
		frmProcessCrashTracker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btnNewButton = new JButton("Start Check");

		btnNewButton.setBackground(Color.ORANGE);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startcheck stc=new startcheck();
				t1=new Thread(stc);

				t1.start();
			}
		});
		btnNewButton.setBounds(29, 52, 101, 23);
		JButton btnNewButton_1 = new JButton("End Check");
		btnNewButton_1.setBackground(Color.ORANGE);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {


				t1.stop();
				JOptionPane.showMessageDialog(null,"Stopped Sucessfully","Pass",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton_1.setBounds(29, 93, 101, 23);

		model= new DefaultTableModel();
		String[] columnNames = { "Pid", "Process"};
		model.setColumnIdentifiers(columnNames);

		table_2 = new JTable();
		table_2.setModel(model);
		table_2.setGridColor(Color.green);
		JTableHeader tableHeader = table_2.getTableHeader();
		tableHeader.setBackground(Color.LIGHT_GRAY);
		tableHeader.setForeground(Color.black);
		table_2.setDefaultEditor(Object.class, null);
		//table_2.setBackground(Color.LIGHT_GRAY);
		
		table_2.setForeground(Color.black);
		
		table_2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table_2.setFillsViewportHeight(true);
		JScrollPane scroll = new JScrollPane(table_2);
		scroll.setBounds(162, 30, 436, 195);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		model2= new DefaultTableModel();
		String[] columnNamescrash = { "Pid & Process Name","DateTime"};
		model2.setColumnIdentifiers(columnNamescrash);

		table_1 = new JTable();
		table_1.setModel(model2);
		table_1.setGridColor(Color.RED);
		JTableHeader tableHeader1 = table_1.getTableHeader();
		tableHeader1.setBackground(Color.LIGHT_GRAY);
		tableHeader1.setForeground(Color.black);
		table_1.setDefaultEditor(Object.class, null);
		//table_2.setBackground(Color.LIGHT_GRAY);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table_1.setFillsViewportHeight(true);
		JScrollPane scroll1 = new JScrollPane(table_1);
		scroll1.setBounds(163, 260, 436, 200);
		scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);



		JLabel lblNewLabel = new JLabel("Crash History");
		lblNewLabel.setBounds(163, 239, 78, 14);

		JLabel lblProcessPresent = new JLabel("Process Present");
		lblProcessPresent.setBounds(163, 14, 142, 14);
		frmProcessCrashTracker.getContentPane().setLayout(null);
		frmProcessCrashTracker.getContentPane().add(scroll);

		frmProcessCrashTracker.getContentPane().add(btnNewButton);
		frmProcessCrashTracker.getContentPane().add(btnNewButton_1);
		frmProcessCrashTracker.getContentPane().add(lblProcessPresent);
		frmProcessCrashTracker.getContentPane().add(lblNewLabel);
		frmProcessCrashTracker.getContentPane().add(scroll1);

	}

	public class startcheck implements Runnable
	{
		@Override

		public void run()
		{
			GetInfos g1 =new GetInfos();
			try {
				g1.csvreader(model ,model2);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
