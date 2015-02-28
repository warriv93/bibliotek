package bibliotek;
/**
 * Klass som representerar ett dvd-objekt.
 * @author Grupp 1
 *
 */
public class Dvd extends Media{
	
	private String id;
	private String typ;
	private String title;
	private String year;
	private String actors;
	private boolean status = true;
	/**
	 * Konstruktor för att skapa ett dvd-Objekt.
	 * @param typ - typ av media
	 * @param id - mediats id
	 * @param actors - namn på skådespelare
	 * @param title - titel
	 * @param year - året då dvdn släpptes
	 */
	public Dvd(String typ, String id, String title, String year, String actors) 
	{
		super(id,typ);
		this.status = status;
		this.id = id;
		this.typ = typ;
		this.title = title;
		this.year = year;
		this.actors = actors;
	}
	/**
	 * Kollar om dvdn är lånad eller ej.
	 */
	public boolean isStatus() {
		return status;
	}
	
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

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String toString(){
		return this.id + ", " + this.typ + ", " +  this.title + ", " + this.actors + ", " + this.year + ", Kan lånas: " + this.status;
	}
}
