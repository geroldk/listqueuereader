package de.wmgruppe.ListQueueReader;

import java.awt.EventQueue;

import javax.resource.ResourceException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.ibm.vse.connector.VSEPowerEntry;
import com.ibm.vse.connector.VSEPowerQueue;
import com.ibm.vse.connector.VSEResource;
import com.ibm.vse.connector.VSEResourceEvent;
import com.ibm.vse.connector.VSEResourceListener;
import com.ibm.vse.connector.VSESystem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.security.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainApp {

	private final class TableModel extends AbstractTableModel implements
			VSEResourceListener {
		@Override
		public String getColumnName(int arg0) {
			switch (arg0) {
			case 0:
				return "Name";
			case 1:
				return "Nummer";
			case 2:
				return "Suffix";
			}
			return super.getColumnName(arg0);
		}

		LinkedList<VSEPowerEntry> list = new LinkedList<VSEPowerEntry>();

		@Override
		public void listStarted(VSEResourceEvent event) {
			// TODO Auto-generated method stub
			list.clear();
			fireTableDataChanged();
		}

		@Override
		public void listEnded(VSEResourceEvent event) {
			// TODO Auto-generated method stub
		}

		@Override
		public void listAdded(VSEResourceEvent event) {
			VSEResource resource = event.getData();
			if (resource instanceof VSEPowerEntry) {
				VSEPowerEntry entry = (VSEPowerEntry) resource;
				list.add(entry);
				fireTableRowsInserted(list.size() - 2, list.size() - 1);
			}
			// TODO Auto-generated method stub

		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			switch (arg1) {
			case 0:
				return list.get(arg0).getName();
			case 1:
				return list.get(arg0).getNumber();
			case 2:
				return list.get(arg0).getSuffix();
			}
			return null;
		}

		public void saveEntryTo(int i, String textzielverzeichnis)
				throws ResourceException, IOException {
			VSEPowerEntry entry = list.get(i);
			File file = new File(textzielverzeichnis + File.separator
					+ entry.getName() + "-" + entry.getNumber() + "-"
					+ entry.getSuffix() + ".txt");
			entry.get(file);
			// TODO Auto-generated method stub

		}
	}

	private JFrame frame;
	private JTable table;
	private JTextField txtName;
	private JButton btnDownload;
	VSESystem system;
	private VSEPowerQueue listQueue;
	private JTextField textzielverzeichnis;
	private JPopupMenu popupMenu;
	private JMenuItem mntmsave;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 463, 330);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		txtName = new JTextField();
		txtName.setToolTipText("filter expression");
		txtName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					getEventlist();
				}
			}
		});
		txtName.setColumns(10);

		JButton btnAnzeigen = new JButton("Anzeigen");
		btnAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getEventlist();
			}
		});

		btnDownload = new JButton("Connect");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							if (system != null) {
								system.disconnect();
							}
							system = new VSESystem();
							Login frame = new Login(system);
							frame.setVisible(true);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(MainApp.this.frame,
									e.getMessage(), "ERROR",
									JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					}
				});

			}
		});

		JScrollPane scrollPane = new JScrollPane();

		textzielverzeichnis = new JTextField();
		textzielverzeichnis.setColumns(10);
		textzielverzeichnis.setText(System.getProperty("user.home"));

		JButton btnAuswaehlen = new JButton("Ausw\u00E4hlen");
		btnAuswaehlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {

						JFileChooser fileChooser = new JFileChooser();
						fileChooser
								.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						fileChooser.showOpenDialog(MainApp.this.frame);
						MainApp.this.textzielverzeichnis.setText(fileChooser
								.getSelectedFile().getAbsolutePath());
					}
				});
			}
		});
		
		JLabel lblName = new JLabel("Name");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User");
		
		textField_1 = new JTextField();
		textField_1.setColumns(8);
		
		JLabel lblNewLabel_1 = new JLabel("Class");
		
		textField_2 = new JTextField();
		textField_2.setColumns(1);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(txtName, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnAnzeigen))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(textzielverzeichnis, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnAuswaehlen)
							.addGap(18)
							.addComponent(btnDownload))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblName)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel)
							.addGap(12)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAnzeigen))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textzielverzeichnis, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDownload)
						.addComponent(btnAuswaehlen))
					.addContainerGap())
		);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null }, { null, null, null },
				{ null, null, null }, }, new String[] { "Name", "Nummer",
				"Suffix" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);

		mntmsave = new JMenuItem("speichern");
		mntmsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i : table.getSelectedRows()) {
					i = table.convertRowIndexToModel(i);
					try {
						((TableModel) table.getModel()).saveEntryTo(i,
								textzielverzeichnis.getText());
					} catch (ResourceException e1) {
						JOptionPane.showMessageDialog(MainApp.this.frame,
								e1.getMessage(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainApp.this.frame,
								e1.getMessage(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			}
		});
		popupMenu.add(mntmsave);
		frame.getContentPane().setLayout(groupLayout);
	}

	protected void getEventlist() {
		try {
			if (system == null || !system.isExistent()) {
				JOptionPane.showMessageDialog(MainApp.this.frame,
						"NO CONNECTION", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;

			}
		} catch (ResourceException e1) {
			JOptionPane.showMessageDialog(MainApp.this.frame, e1.getMessage(),
					"ERROR", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(MainApp.this.frame, e1.getMessage(),
					"ERROR", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		if (listQueue == null) {
			listQueue = system.getVSEPower().getListQueue();
			TableModel tmodel = new TableModel();
			listQueue.addVSEResourceListener(tmodel);
			table.setModel(tmodel);
			TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(
					tmodel);
			rowSorter.setComparator(1, new Comparator<String>() {
				@Override
				public int compare(String arg0, String arg1) {
					int i0 = Integer.valueOf(arg0.trim());
					int i1 = Integer.valueOf(arg1.trim());
					return i0 - i1;
				}
			});
			rowSorter.setComparator(2, new Comparator<String>() {
				@Override
				public int compare(String arg0, String arg1) {
					int i0 = Integer.valueOf(arg0.trim());
					int i1 = Integer.valueOf(arg1.trim());
					return i0 - i1;
				}
			});
			table.setRowSorter(rowSorter);
		}

		try {
			String filter = MainApp.this.txtName.getText();
			if (filter == null || filter.equals("")) {
				filter = "*";
			}
			
			listQueue.getEntryList(filter);
		} catch (ResourceException e) {
			JOptionPane.showMessageDialog(MainApp.this.frame, e.getMessage(),
					"ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(MainApp.this.frame, e.getMessage(),
					"ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		// TODO Auto-generated method stub

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
