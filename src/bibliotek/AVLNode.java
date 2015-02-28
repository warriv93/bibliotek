package bibliotek;
/**
 * En nod som har ett key-värde och ett värde.
 * Denna nod håller reda på noderna den har till höger och vänster om sig.
 * @author Kim
 *
 * @param <K> key
 * @param <V> value
 */
class AVLNode<K,V> {
    K key;
    V value;
    AVLNode<K,V> left;
    AVLNode<K,V> right;
    int height=0;
    /**
     * Konstruktor för en nod
     * @param key - nyckel
     * @param value - värde
     * @param left - nod till vänster
     * @param right - nod till höger
     */
    public AVLNode( K key, V value, AVLNode<K,V> left, AVLNode<K,V> right ) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }
    /**
     * Metod som räknar ut höjden i ett träd
     * @return höjden
     */
    public int height() {
        int leftD = -1, rightD = -1;
        if( left != null )
            leftD = left.height();
        if( right != null )
            rightD = right.height();
        return 1 + Math.max( leftD, rightD );
    }
    /**
     * Metod som returnerar storleken på ett träd.
     * @return storleken på trädet.
     */
    public int size() {
        int leftS = 0, rightS = 0;
        if( left != null )
            leftS = left.size();
        if( right != null )
            rightS = right.size();
        return 1 + leftS + rightS;
    }
    /**
     * Metod som skriver ut noderna.
     */
    public void print() {
        if( left != null)
            left.print();
        System.out.println(key + ": " + value);
        if( right != null )
            right.print();
    }
    
//    public void showAVL() {
//        javax.swing.JOptionPane.showMessageDialog( null, new ShowAVL( this, 800,600 ), "Show tree", javax.swing.JOptionPane.PLAIN_MESSAGE );
//    }
}
