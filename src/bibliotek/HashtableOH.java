package bibliotek;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Hashtabellen använder öppen hashing
 * @author Kim Gustafsson
 */
public class HashtableOH<K,V> implements Map<K,V> { 
	private LinkedList<Entry<K,V>>[] table;
	private int size;

	/**
	 * Konstruktor som skapar en ny hash-tabell.
	 * @param size - storleken på tabellen.
	 */
	public HashtableOH( int size ) {
		table = (LinkedList<Entry<K,V>>[])new LinkedList[ size ];
		for( int i = 0; i < size; i++ ) {
			table[ i ] = new LinkedList<Entry<K,V>>();
		}
	}

	private int hashIndex( K key ) {
		int hashCode = key.hashCode();
		hashCode = hashCode % table.length;
		return ( hashCode < 0 ) ? -hashCode : hashCode;
	}
/**
 * Lägger till en nod i tabellen.
 */
	public V put( K key, V value ) {
		V res = null;
		int hashIndex = hashIndex( key );
		Entry<K,V> entry = new Entry<K,V>( key, value );
		int index = table[ hashIndex ].indexOf( entry );
		if( index == -1 ) {
			table[ hashIndex ].addFirst( entry );
			size++;
		}
		else {
			res = table[ hashIndex ].set( index, entry ).value;
		}
		return res;
	}
/**
 * Metod som skriver ut en lista på hash-tabellen.
 */
	public void list() {
		Entry<K,V> entry;
		for(int i=0; i<table.length; i++) {
			System.out.print( i + ":");
			for( int j = 0; j < table[ i ].size(); j++ ) {
				entry = table[ i ].get( j );
				System.out.print(" <" + entry.key +"," + entry.value + ">" );
			}
			System.out.println();
		}
	}
/**
 * Metod som returnerar värdet i en nod.
 * @param key
 * @return value
 */
	public V get(K key) {
		int hash = hashIndex(key);
		for(int i=0;i<table[hash].size();i++)
		{
			if(table[hash].get(i).key.equals(key))
				return table[hash].get(i).value;
		}
		return null;
	}
/**
 * Metod som tar bort en nod ur tabellen.
 */
	public V remove(K key) {
		int hash = hashIndex(key);
		V ret = null;
		for(int i=0;i<table[hash].size();i++)
		{
			if(table[hash].get(i).key.equals(key))
			{
				ret = table[hash].get(i).value;
				table[hash].remove(i);
				size--;
			}
		}
		return ret;
	}
/**
 * Metod som returnerar storleken på tabellen.
 * @return size - storleken på tabellen.
 */
	public int size() {
		return size;
	}
/**
 * Metod som returnerar true om tabellen är tom.
 * @return boolean true om tabellen är tom, annars false.
 */
	public boolean isEmpty() {
		return size==0;
	}
/**
 * Metod som returnerar om en viss nyckel finns i tabellen.
 * @param key - nyckel
 * @return boolean - true om nyckeln finns, annars false.
 */
	public boolean containsKey(K key) {
		return get(key)!=null;
	}
/**
 * Metod som tömmer tabellen.
 */
	public void clear() {
		for(int i=0;i<table.length;i++)
		{
			table[i].clear();
		}
	}
/**
 * Iterator som ittererar igenom alla nycklar i tabellen.
 */
	public Iterator<K> keys() {
		ArrayList<K> keys = new ArrayList<K>();
		for(int i=0;i<table.length;i++)
		{
			if(table[i].size()!=0)
			{
				for(int j=0;j<table[i].size();j++)
				{
					keys.add(table[i].get(j).key);
				}
			}
		}
		return keys.iterator();
	}
/**
 * 
 * Iterator som ittererar igenom alla värden i tabellen.
 */
	public Iterator<V> values() {
		ArrayList<V> values = new ArrayList<V>();
		for(int i=0;i<table.length;i++)
		{
			if(table[i].size()!=0)
			{
				for(int j=0;j<table[i].size();j++)
				{
					values.add(table[i].get(j).value);
				}
			}
		}
		return values.iterator();
	}

	public static void main(String[] args) {
		HashtableOH<String,String> table = new HashtableOH<String,String>(4);
		table.put("hej", "hello");      
		table.put("röd", "red");        
		table.put("vit", "white");      
		table.put("säng", "bed");       
		table.put("svart", "black");    
		table.put("gul", "yellow");     
		table.put("dator", "computer"); 
		table.put("snö", "snow");       
		table.put("blå", "blue");       
		table.put("grön", "green");     
		table.put("hus", "house");      
		table.list();
		//        System.out.println(table.get("dator"));
		//        System.out.println(table.remove("dator"));
		//        table.list();
		//        System.out.println(table.size());
		//        System.out.println(table.isEmpty());
		//        System.out.println(table.containsKey("grön"));
		//        table.clear();
		//        table.list();
		System.out.println("-----KEYS-------------------------");
		Iterator<String> keys = table.keys();
		while(keys.hasNext())
			System.out.println(keys.next());
		System.out.println("-----VALUES-------------------------");
		Iterator<String> values = table.values();
		while(values.hasNext())
			System.out.println(values.next());
	}
}
