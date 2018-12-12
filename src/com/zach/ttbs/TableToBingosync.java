package com.zach.ttbs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * The main class that holds the entire TableToBingosync program. Instructions
 * on how to use the program can be found on <a href="this.com">github</a>. Since
 * the program is so small, it only requires a single class which handles the
 * conversion process and GUI. The entire source folder is available on GitHub
 * in the case that someone has a great idea for an addition that requires
 * another class to be implemented.
 * 
 * @author Zach K
 */
public class TableToBingosync extends JFrame {
	
	/**
	 * Since it is highly unlikely that this class will ever be serialized, this
	 * ID was added here solely to stop Eclipse from yelling at me with those
	 * yellow squiggly lines.
	 * 
	 * @author Zach K
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The static object of the main class that holds the GUI created by the
	 * main method. It does basically all the work in its constructor.
	 */
	public static TableToBingosync mainframe;
	
	/**
	 * A non-static variable that holds this class for referencing in
	 * sub-classes unable to use the keyword "this" but are able to access
	 * fields.
	 */
	TableToBingosync thiz = this;
	/**
	 * The main GUI's main panel that holds all of the content.
	 */
	JPanel contentPane;
	
	/**
	 * Variable storing the current table size selected;<br>
	 * 3 means 3x3, 4 means 4x4, and 5 means 5x5.<br>
	 * 5x5 is selected by default.
	 */
	int size = 5;
	/**
	 * Variable storing the currently selected placeholder for empty values in
	 * the output table.
	 */
	char placeholder = '-';
	
	/**
	 * The main method that begins the program. Everything in the program starts
	 * from here. By default, this application uses the system look and feel. If
	 * you want to change what look and feel the system uses, launch the program
	 * with the argument <code>-SetLookAndFeel()</code> and put the name of the
	 * look and feel you would like inside of the parentheses. The name of the
	 * look and feel must be in quotes.
	 * 
	 * @param args
	 *        - Arguments with which to run the program with. The only supported
	 *        argument is -SetLookAndFeel(), which sets the look and feel based
	 *        upon what string is inside of the parentheses.
	 */
	public static void main(String[] args) {
		// Sets up the GUI inside of the EventQueue to avoid glitches and errors
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("Setting look and feel to the system default...");
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					// Check for arguments
					System.out.println("Found " + args.length + " arguments");
					for (String arg : args) {
						if (arg.startsWith("-SetLookAndFeel")) {
							String className = arg.substring(16, arg.length() - 1);
							System.out.println("Setting look and feel to \"" + className + "\"");
							UIManager.setLookAndFeel(className);
						}
					}
					
					System.out.println("Creating GUI");
					mainframe = new TableToBingosync();
				} catch (Exception e) {
					// Print any errors
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Mainframe constructor. Handles the creation of the entire GUI and event
	 * handlers. It also contains the code for the conversion process.
	 */
	public TableToBingosync() {
		// Set up JFrame properties
		setTitle("TableToBingosync - v1.0.1 - Zach K"); // Version follows Semantic Versioning
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setMinimumSize(new Dimension(600, 400));
		
		// Create the content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// Create the center content pane
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		// Create the left side (input) text pane
		JScrollPane scpInput = new JScrollPane();
		centerPanel.add(scpInput);
		
		JPanel panelInput = new JPanel();
		panelInput.setLayout(new BorderLayout(0, 0));
		scpInput.setViewportView(panelInput);
		
		JTextPane txtpnInput = new JTextPane();
		panelInput.add(txtpnInput);
		
		// Create the right side (output) text pane
		JScrollPane scpOutput = new JScrollPane();
		centerPanel.add(scpOutput);
		
		JPanel panelOutput = new JPanel();
		panelOutput.setLayout(new BorderLayout(0, 0));
		scpOutput.setViewportView(panelOutput);
		
		JTextPane txtpnOutput = new JTextPane();
		panelOutput.add(txtpnOutput);
		
		// Create the south panel (The one that holds the options)
		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		// Create the empty placeholder label
		JLabel lblPlaceholder = new JLabel("Placeholder:");
		southPanel.add(lblPlaceholder);
		
		JButton btnPlaceholder = new JButton("-");
		btnPlaceholder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Activating the button will change the placeholder and the text displayed on the button
				placeholder = e.getKeyChar();
				btnPlaceholder.setText(String.valueOf(placeholder));
			}
		});
		
		southPanel.add(btnPlaceholder);
		
		// Create the size radio buttons; clicking them changes the field "size" to its respective value
		ButtonGroup sizeGroup = new ButtonGroup();
		
		JRadioButton rdbtn3x3 = new JRadioButton("3x3");
		rdbtn3x3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				size = 3;
			}
		});
		southPanel.add(rdbtn3x3);
		sizeGroup.add(rdbtn3x3);
		
		JRadioButton rdbtn4x4 = new JRadioButton("4x4");
		rdbtn4x4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				size = 4;
			}
		});
		southPanel.add(rdbtn4x4);
		sizeGroup.add(rdbtn4x4);
		
		JRadioButton rdbtn5x5 = new JRadioButton("5x5");
		rdbtn5x5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				size = 5;
			}
		});
		rdbtn5x5.setSelected(true); // the 5x5 button is selected by default
		southPanel.add(rdbtn5x5);
		sizeGroup.add(rdbtn5x5);
		
		// Create the conversion button; this activates the conversion process from plain text to JSON format
		JButton btnConvert = new JButton("Convert");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Note that the conversion process to JSON does not use the google simple-json.jar file; this is intentional
				StringBuilder output = new StringBuilder(1250); // the StringBuilder has a capacity of 1250, which is usually enough to hold the entire output table
				output.append("[\n");
				
				String input = txtpnInput.getText();
				if (input.isEmpty()) {
					txtpnOutput.setText("");
					return;
				}
				String[] rowedTable = txtpnInput.getText().trim().split("\\n+|\\r+|;");
				String[][] table = new String[rowedTable.length][];
				int i = 0;
				for (String text : rowedTable) {
					if (!text.trim().isEmpty()) {
						table[i] = text.trim().split("\\t+|,");
						i++;
					}
				}
				for (int row = 0; row < 5; row++) {
					for (int col = 0; col < 5; col++) {
						if (row != 0 || col != 0) output.append(",\n"); // It would be nice if someone could replace this checker with something better
						output.append("    {\"name\": \"");
						if (row >= size || col >= size || row >= table.length || col >= table[row].length) output.append(placeholder);
						else output.append(table[row][col]);
						output.append("\"}");
					}
				}
				output.append("\n]");
				System.out.println("Converted table into " + output.length() + " chars");
				txtpnOutput.setText(output.toString());
			}
		});
		southPanel.add(btnConvert);
		
		setVisible(true); // Display the GUI
	}
	
}
