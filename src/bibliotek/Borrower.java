package bibliotek;
/**
 * Klass som representerar en lånetagare.
 * @author Grupp 1
 *
 */
public class Borrower 
{
	private String idNr;
	private String name;
	private String phoneNr;
	private HashtableOH myLoans = new HashtableOH(20);
	/**
	 * Konstruktor för att skapa en Lånetagare
	 * @param idNr - personnummer
	 * @param name - namn
	 * @param phoneNr - telefonnummer
	 */
	public Borrower(String idNr,String name,String phoneNr)
	{
		this.idNr = idNr;
		this.name = name;
		this.phoneNr = phoneNr;
	}
	public String getIdNr() {
		return idNr;
	}
	public void setIdNr(String idNr) {
		this.idNr = idNr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNr() {
		return phoneNr;
	}
	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
	}
	public HashtableOH getMyLoans() {
		return myLoans;
	}
	public void setMyLoans(HashtableOH myLoans) {
		this.myLoans = myLoans;
	}
}
