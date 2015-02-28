package bibliotek;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * GUI-klassen i programmet. 
 * Använder sig av JFrame, ActionListener och ListSelectionListener.
 * Här visas alla listor upp i en JList där man kan välja direkt ur listan vad man vill låna/returnera.
 * Om ett objekt är lånat så kan man ej använda "låna"-knappen. Man kan välja att visa upp mina-lån listan 
 * och där ifrån kan man då välja att returnera objektet. Man kan också välja att bara se dvder eller bara böcker.
 * @author Grupp 1
 *
 */
public class GUI extends JFrame implements ActionListener, ListSelectionListener
{
	// Login frame
	private JFrame loginFrame = new JFrame();
	private JPanel loginpnl = new JPanel();
	private JPanel btnpnl =  new JPanel();
	private JButton loginbtn = new JButton("Login");
	private JButton cancelbtn = new JButton("Cancel");
	private JTextField user = new JTextField();
	private JLabel loginlbl = new JLabel("ID: ");

	// Main frame
	private JPanel panel = new JPanel();
	private JPanel panelsearch = new JPanel();
	private JPanel panelbtn = new JPanel();
	private JPanel panelbtn2 = new JPanel();
	private JLabel lblsearch = new JLabel("Sök id: ");
	private JTextField stf = new JTextField();
	private JButton btnDVD = new JButton("DVD");
	private JButton btnLoan = new JButton("Låna");
	private JButton btnBOOK = new JButton("Böcker");
	private JButton btnMyLoan = new JButton("Mina Lån");
	private JButton btnReturn = new JButton("Returnera");
	private JButton btnSearch = new JButton("Sök");
	private JButton btnlogout = new JButton("Logga ut");
	private JList alt = new JList();
	private boolean loggIn = false;
	private JScrollPane listScrollPane = new JScrollPane();
	private Controller controller = new Controller();
	
	
	private String id;
	/**
	 * Konstruktor för att skapa GUI:t
	 */
	public GUI()
	{
		loginFrame.setLayout(new BorderLayout());
		loginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		loginFrame.setLocation(500, 350);
		loginFrame.setPreferredSize(new Dimension(200,80));
		loginFrame.pack();
		loginFrame.setResizable(false);
		loginFrame.setVisible(true);
		loginFrame.add(loginpnl, BorderLayout.NORTH);
		loginFrame.add(btnpnl, BorderLayout.SOUTH);
		btnpnl.setLayout(new GridLayout(1,2));
		loginpnl.setLayout(new BorderLayout());
		btnpnl.add(loginbtn);
		btnpnl.add(cancelbtn);
		loginpnl.add(loginlbl, BorderLayout.WEST);
		loginpnl.add(user, BorderLayout.CENTER);

		loginbtn.addActionListener(this);
		cancelbtn.addActionListener(this);
	}
	public void paintMainFrame()
	{
		alt.setListData(controller.getAllMedia());

		listScrollPane.setViewportView(alt);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(500, 350);
		setPreferredSize(new Dimension(800,400));
		pack();
		setResizable(false);
		add(panel);
		panel.setLayout(new BorderLayout());
		panel.add(panelsearch, BorderLayout.NORTH);
		panelsearch.setLayout(new BorderLayout());
		panelsearch.add(lblsearch, BorderLayout.WEST);
		panelsearch.add(stf, BorderLayout.CENTER);
		panelsearch.add(btnSearch, BorderLayout.EAST);
		panel.add(listScrollPane, BorderLayout.CENTER);
		panel.add(panelbtn, BorderLayout.SOUTH);
		panelbtn.setLayout(new BorderLayout());
		panelbtn2.setLayout(new GridLayout(3,2));
		panelbtn.add(panelbtn2, BorderLayout.CENTER);
		panelbtn2.add(btnReturn);
		panelbtn2.add(btnlogout);
		panelbtn2.add(btnDVD);
		panelbtn2.add(btnBOOK);
		panelbtn2.add(btnLoan);
		panelbtn2.add(btnMyLoan);

		btnBOOK.addActionListener(this);
		btnDVD.addActionListener(this);
		btnLoan.addActionListener(this);
		btnMyLoan.addActionListener(this);
		btnReturn.addActionListener(this);
		btnSearch.addActionListener(this);
		alt.addListSelectionListener(this);
		btnlogout.addActionListener(this);

		setVisible(true);
	}
	/**
	 * Listerners till alla knappar.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getSource()==loginbtn)
		{
			id = user.getText().toString();
			
			if(controller.searchUser(id))
			{
				paintMainFrame();
				loginFrame.setVisible(false);
				
				}
			else{
				JOptionPane.showMessageDialog(null, "Fel id!");
				user.setText("");
		}
			
			
//			controller.setid(id);
			
//			controller.login(id);
			
		}
		if(e.getSource()==btnlogout)
		{
			setVisible(false);
			loginFrame.setVisible(true);
			user.setText("");
		}
		if(e.getSource()==cancelbtn)
		{
			System.exit(0);
		}
		if(e.getSource()==btnBOOK)
		{
			alt.setListData(controller.getBookList());
		}
		if(e.getSource()==btnDVD)
		{
			alt.setListData(controller.getDvdList());
		}
		if(e.getSource()==btnLoan)
		{
			controller.loan(id, controller.current);
		}
		if(e.getSource()==btnMyLoan)
		{
			alt.setListData(controller.getMyLoanList(id));
		}
		if(e.getSource()==btnReturn)
		{
			controller.returnMedia(id, controller.current);
			alt.setListData(controller.getMyLoanList(id));
		}
		if(e.getSource()==btnSearch)
		{
			if(stf.getText().isEmpty())
			{
				alt.setListData(controller.getAllMedia());
			}
			else
				alt.setListData(controller.searchMedia(stf.getText()));
		}
	}
	/**
	 * Listerner till JList så att man kan se vilket objekt som är markerat i listan.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		controller.current = (Media) alt.getSelectedValue();
		try{
			if(controller.current.isStatus()==false)
				btnLoan.setEnabled(false);
			if((controller.current.getId() == null))
			{
				btnLoan.setEnabled(false);
				btnReturn.setEnabled(false);
			}
			else
				btnLoan.setEnabled(true);

			// Fångar Nullpointerexception som händer om man markerat ett objekt i listan och sedan trycker på Dvd / Book knapparna.
		}catch (NullPointerException ex){}	
	}
}