package bibliotek;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.swing.JOptionPane;
/**
 * Programmets controller-klass. Har hand om all logik.
 * @author Grupp 1
 *
 */
public class Controller 
{
	private GUI gui;
	public Media current;
	private Dvd dvd;
	private Book book;
	private Borrower borrower;
	private HashtableOH<String, Media> mediaTable = new HashtableOH<String, Media>(1000);
	private HashtableOH<String, Dvd> dvdTable = new HashtableOH<String, Dvd>(1000);
	private AVLTree<String, Borrower> userTree = new AVLTree<String, Borrower>();
	private HashtableOH<String, Book> bookTable = new HashtableOH<String, Book>(1000);

	
	
	private String id;
//	private String loginId = " ";
//	private String id;
	/**
	 * Metod som l�ser in all media fr�n Media.txt och lagrar all media i en HashtabelOH,
	 * alla dvder i en egen HashtableOH och alla b�cker i en egen HashtableOH.
	 * @return - returnerar listan med all media.
	 */
	public HashtableOH<String, Media> readMedia()
	{
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("src/bibliotek/Media.txt"), "ISO-8859-1"));
			String[] parts;
			String mediaType,  mediaId;
			String bookAuthor, bookTitle, bookYear;
			String dvdTitle, dvdYear, dvdActors; 

			String text = br.readLine();
			while (text != null) {

				parts = text.split(";");
				mediaType = parts[0];
				mediaId = parts[1];

				if (mediaType.matches("Bok")){
					bookAuthor = parts[2];
					bookTitle = parts[3];
					bookYear = parts[4];

					book = new Book(mediaType, mediaId, bookAuthor, bookTitle, bookYear);
					mediaTable.put(mediaId, book);
					bookTable.put(mediaId, book);

				} else {
					dvdTitle = parts[2];
					dvdYear = parts[3];
					dvdActors = parts[4] + ", ";

					for(int i=5;i<parts.length;i++) {
						dvdActors += parts[i];
						if(i < parts.length-1) {
							dvdActors += ", ";
						}
					}
					
					
					dvd = new Dvd(mediaType, mediaId, dvdTitle, dvdYear, dvdActors);
					mediaTable.put(mediaId, dvd);
					dvdTable.put(mediaId, dvd);
				}

				text = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			System.out.println(e);
		}

		
		return mediaTable;	
	}
	/**
	 * Metod som l�ser in alla l�netagare fr�n Lantagare.txt. Metoden skapar borrower objekt och 
	 * lagrar alla anv�ndarna i ett AVLTree med personnummret som key och resten som value.
	 * @return - returnerar lista p� alla l�netagare.
	 */
	public AVLTree<String, Borrower> readUsers()
	{
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream( "src/bibliotek/Lantagare.txt/"), "ISO-8859-1"));
			String[] parts;

			String personNr, name, phone;

			String text = br.readLine();
			while (text != null) 
			{

				parts = text.split(";");
				personNr = parts[0];
				name = parts[1];
				phone = parts[2];
				
				borrower = new Borrower(personNr, name, phone);
				userTree.put(personNr, borrower);
				
				text = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			System.out.println(e);
		}

		return userTree;
	}
	/**
	 * Metod som tar emot en HashtableOH och g�r om den till en Media-array.
	 * Beh�vs f�r att kunna visa upp objekten i JList i GUI.
	 * @param table - HashtableOH som man vill konvertera till en array.
	 * @return - returnerar en Media-array.
	 */
	public Media[] toArray(HashtableOH table)
	{
		Media[] array = new Media[100];
		int counter = 0;
		Iterator iter;

		iter = table.values();
		while(iter.hasNext())
		{
			array[counter] = (Media) iter.next();
			counter++;
		}
		return array;
	}
	/**
	 * Metod som returnerar listan med alla b�cker.
	 * @return - bookTable
	 */
	public Media[] getBookList() 
	{

		return toArray(bookTable);
	}
	/**
	 * Metod som returnerar listan med alla dvder.
	 * @return - dvdTable
	 */
	public Media[] getDvdList() 
	{

		return toArray(dvdTable);
	}
	/**
	 * Metod som returnerar listan med all media.
	 * @return - mediaTable
	 */
	public Media[] getAllMedia()
	{
		return toArray(readMedia());
	}
	/**
	 * Metod som h�mtar en l�netagares l�ne-lista.
	 * @param id - personnummer
	 * @return - returnerar l�netagarens l�ne-lista
	 */
	public Media[] getMyLoanList(String id)
	{
		Borrower currentBorrower = userTree.get(id);
		HashtableOH currentLoanList = currentBorrower.getMyLoans();
		return toArray(currentLoanList);
	}
	/**
	 * Metod som s�ker efter ett specifikt media-objekt med hj�lp av 
	 * nyckeln.
	 * @param id - nyckel
	 * @return - returnerar en lista med objektet man letade efter.
	 */
	public Media[] searchMedia(String id)
	{
		Media media = mediaTable.get(id);
		Media[] res = new Media[1];
		res[0] = media;
		return  res;	
	}
	/**
	 * Metod som letar upp en specifik l�netagare med personnummret som nyckel.
	 * @param id - personnummer
	 * @return - returnerar en borrower
	 */
	public boolean searchUser(String id)
	{
		readUsers();
		return userTree.contains(id);
	}
	/**
	 * Metod som g�r ett l�n �t en l�netagare. 
	 * F�rst h�mtas r�tt l�netagare med hj�lp av personnummer,
	 * sedan h�mtar man r�tt media-objekt.
	 * Sedan s�tter metoden mediats status till false s� att den �r "utl�nad".
	 * Sedan l�ggs mediat till i l�netagarens l�ne-lista.
	 * @param id - personnummer
	 * @param media - media man vill l�na.
	 */
	public void loan(String id, Media media)
	{
		try{
			Borrower currentBorrower = userTree.get(id);
			HashtableOH currentLoanList = currentBorrower.getMyLoans();
			Media currentMedia = mediaTable.get(media.getId());
			currentMedia.setStatus(false);
			currentLoanList.put(media.getId(), media);
			JOptionPane.showMessageDialog(null, "Du har nu l�nat: " + media.toString());
		}catch(NullPointerException e)
		{
			JOptionPane.showMessageDialog(null, "Du m�ste v�lja media att l�na!");
		}
	}
	/**
	 * Metod som returnerar ett media-objekt.
	 * F�rst h�mtas r�tt l�netagare med hj�lp av personnummer,
	 * sedan h�mtar man r�tt media-objekt.
	 * Sedan s�tter metoden mediats status till true s� att den inte �r "utl�nad".
	 * Sedan tas mediat bort fr�n l�netagarens l�ne-lista.
	 * @param id - personnummer
	 * @param media - media man vill returnera.
	 */
	public void returnMedia(String id, Media media)
	{
		try{
			Borrower currentBorrower = userTree.get(id);
			HashtableOH currentLoanList = currentBorrower.getMyLoans();
			Media currentMedia = mediaTable.get(media.getId());
			currentMedia.setStatus(true);
			currentLoanList.remove(media.getId());
			JOptionPane.showMessageDialog(null, "Du har nu returnerat: " + media.toString());
		}catch(NullPointerException e)
		{
			JOptionPane.showMessageDialog(null, "Du m�ste v�lja media att returnera!");
		}
	}
//	public void setid(String id2) {
//		// TODO Auto-generated method stub
//		id = id2;
//		System.out.println(id);
//	}
	
	
	
//	public void login(String id) {
//		// TODO Auto-generated method stub
//		
//		if(searchUser(id))
//		{
//			gui.paintMainFrame();
//			
//		}
//		else
//			JOptionPane.showMessageDialog(null, "Fel id!");
//		

//	}
//	
//	public void logout(){
//		
//		id = JOptionPane.showInputDialog("Logga in :");
//		if(searchUser(id)) {
//			GUI gui = new GUI();
//		}else {
//			
//			login();
//		}
//
//	}
//	
//	
//	public void login () {
//		
//	
////		id = JOptionPane.showInputDialog("Logga in :");
////		gui.logingui();
//		
//			if(searchUser(loginId)) {
//				GUI gui = new GUI();
//			}else {
//				login();
//			}
//			
//			
//	}
//	public void setid(String loginid) {
//		 loginId  = gui.loginid;
//		
//	}
	
}
