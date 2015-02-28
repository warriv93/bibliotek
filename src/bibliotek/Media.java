
package bibliotek;
/**
 * En klass som representerar alla media-objekt.
 * @author Kim
 *
 */
public class Media
{
	private String id;
	private String typ;
	private boolean status = true;
	/**
	 * Konstruktor f�r att skapa ett media-objekt.
	 * @param typ - typen p� media (bok/dvd)
	 * @param id - Mediats id.
	 */
	public Media( String typ, String id) 
	{
		this.id = id;
		this.typ = typ;
		this.status = status;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() 
	{
		return id;
	}
	public boolean equals( Object obj ) 
	{
		Media media = (Media)obj;
		return id.equals( media.getId() );
	}
}