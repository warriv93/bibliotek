package bibliotek;
/**
 * Klass som representerar ett media-objekt.
 * @author Grupp 1
 *
 */
public class Book extends Media{
	
	private String id;
	private String typ;
	private String author;
	private String title;
	private String year;
	private boolean status = true;
	/**
	 * Konstruktor f�r att skapa ett media-Objekt.
	 * @param typ - typ av media
	 * @param id - mediats id
	 * @param author - f�rfattare
	 * @param title - bokens titel
	 * @param year - �ret d� boken sl�pptes
	 */
	public Book(String typ, String id, String author, String title, String year) 
	{
		super(id,typ);
		this.status = status;
		this.id = id;
		this.typ = typ;
		this.author = author;
		this.title = title;
		this.year = year;
	}
	/**
	 * Kollar om boken g�r att l�na eller inte.
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * S�tter bokens status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String toString(){
		return this.id + ", " + this.typ + ", " + this.title + ", " + this.author + ", " + this.year + ", Kan l�nas: " + this.status;
	}

}
