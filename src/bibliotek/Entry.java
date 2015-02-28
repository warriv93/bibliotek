package bibliotek;
/**
 * En nod som har en nyckel och ett värde.
 * @author Kim Gustafsson
 *
 * @param <K> - key
 * @param <V> - värde
 */
class Entry<K,V> {
    K key;
    V value;
    /**
     * Konstruktor som skapar en nod.
     * @param key - nyckel
     * @param value - värde
     */
    public Entry( K key, V value ) {
        this.key = key;
        this.value = value;
    }
    
    /**
     *  jämför två nycklar, returnerar true om lika
     */
    public boolean equals( Object obj ) {
        if( !(obj instanceof Entry) )
            return false;
        Entry<K,V> entry = ( Entry<K,V> )obj;
        return key.equals( entry.key );
    }
}
