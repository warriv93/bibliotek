package bibliotek;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
//import java.util.List;
import java.util.NoSuchElementException;


/**
 * Ett träd som är uppbyggt av AVLNodes.
 * @author Kim Gustafsson
 *
 * @param <K> - key
 * @param <V> - value
 */
public class AVLTree<K, V> implements SearchTree<K, V> {
	private Comparator<K> comparator;
	private AVLNode<K, V> tree;
	private int size=0;
/**
 * Konstruktor som skapar en comparator.
 */
	public AVLTree() {
		comparator = new Comp();
	}
/**
 * Konstruktor som får en comparator.
 * @param comp
 */
	public AVLTree(Comparator<K> comp) {
		comparator = comp;
	}

	private AVLNode<K, V> find(K key) {
		int res;
		AVLNode<K, V> node = tree;
		while ((node != null)
				&& ((res = comparator.compare(key, node.key)) != 0)) {
			if (res < 0)
				node = node.left;
			else
				node = node.right;
		}
		return node;
	}
/**
 * Metod som returnerar roten
 * @return roten
 */
	public AVLNode<K, V> root() {
		return tree;
	}
/**
 * Metod som returnerar värdet i en nod.
 * @return value - värdet
 */
	public V get(K key) {
		AVLNode<K, V> node = find(key);
		if (node != null)
			return node.value;
		return null;
	}
/**
 * Metod som returnerar true om det finns i trädet.
 * @return boolean true om key hittas.
 */
	public boolean contains(K key) {
		return find(key) != null;
	}
/**
 * Metod som returnerar trädets höjd.
 * @return height - trädets höjd
 */
	public int height() {
		return height(tree);
	}

	private int height(AVLNode<K, V> node) {
		if (node == null)
			return -1;
		return 1 + Math.max(height(node.left), height(node.right));
	}
/**
 * Metod som lägger till en nod i trädet.
 */
	public void put(K key, V value) {
		tree = put(tree, key, value);
		size++;
	}

	private AVLNode<K, V> put(AVLNode<K, V> node, K key, V value) {
		if (node == null) {
			node = new AVLNode<K, V>(key, value, null, null);
		} else {
			if (comparator.compare(key, node.key) < 0) {
				node.left = put(node.left, key, value);
				node = balanceLeft(node);
			} else if (comparator.compare(key, node.key) > 0) {
				node.right = put(node.right, key, value);
				node = balanceRight(node);
			}
		}
		return node;
	}
/** 
 * Metod som tar bort en nod i trädet.
 */
	public V remove(K key) {
		V value = get(key);
		if (value != null) {
			tree = remove(tree, key);
		}
		size--;
		return value;
	}

	private AVLNode<K, V> remove(AVLNode<K, V> node, K key) {
		int compare = comparator.compare(key, node.key);
		if (compare == 0) {
			if (node.left == null && node.right == null)
				node = null;
			else if (node.left != null && node.right == null)
				node = node.left;
			else if (node.left == null && node.right != null)
				node = node.right;
			else {
				AVLNode<K, V> min = getMin(node.right);
				min.height = node.height;
				min.right = remove(node.right, min.key);
				min.left = node.left;
				node = min;
			}
		} else if (compare < 0) {
			node.left = remove(node.left, key);
		} else {
			node.right = remove(node.right, key);
		}
		node = balanceNode(node);
		return node;
	}

	private AVLNode<K, V> getMin(AVLNode<K, V> node) {
		while (node.left != null)
			node = node.left;
		return node;
	}

	private AVLNode<K, V> balanceNode(AVLNode<K, V> node) {
		if (node != null) {
			node = balanceLeft(node);
			node = balanceRight(node);
		}
		return node;
	}

	private AVLNode<K, V> balanceLeft(AVLNode<K, V> node) {
		if (height(node.left) - height(node.right) == 2) {
			if (height(node.left.left) - height(node.left.right) == -1) { // LeftRight
				node.left = rotateLeft(node.left);
			}
			node = rotateRight(node);
		}
		return node;
	}

	private AVLNode<K, V> balanceRight(AVLNode<K, V> node) {
		if (height(node.left) - height(node.right) == -2) {
			if (height(node.right.left) - height(node.right.right) == 1) { // RightLeft
				node.right = rotateRight(node.right);
			}
			node = rotateLeft(node);
		}
		return node;
	}

	private AVLNode<K, V> rotateLeft(AVLNode<K, V> node) {
		AVLNode<K, V> rootNode = node.right;
		node.right = rootNode.left;
		rootNode.left = node;
		return rootNode;
	}

	private AVLNode<K, V> rotateRight(AVLNode<K, V> node) {
		AVLNode<K, V> rootNode = node.left;
		node.left = rootNode.right;
		rootNode.right = node;
		return rootNode;
	}

	private class Comp implements Comparator<K> {
		public int compare(K key1, K key2) {
			Comparable<K> k1 = (Comparable<K>) key1;
			return k1.compareTo(key2);
		}
	}

	// Laboration 13
	/**
	 * En iterator som ittererar genom trädet.
	 * @return iterator.
	 */
	public Iterator<V> iterator() {
		return new Iter();
	}
/**
 * Metod som returnerar storleken på trädet.
 * @return size
 */
	public int size() {
		return size;
	}
	
	public List<K> keys() {
		ArrayList<K> keys = new ArrayList<K>();
		Iterator<K> iter = IteratorKeys();
		while(iter.hasNext())
		{
			keys.add(iter.next());
		}
		return (List<K>) keys;
	}

	private Iterator<K> IteratorKeys() {
		// TODO Auto-generated method stub
		return (Iterator<K>) new Iter();
	}
	public List<V> values() {
		return null;
	}

	@Override
	public V first() {
		return null;
	}

	@Override
	public V last() {
		return null;
	}
	private class Iter implements Iterator<V> {
		ArrayList<V> list = new ArrayList<V>();
		int index = -1;

		public Iter() {
			inOrder(tree);
		}

		private void inOrder(AVLNode<K,V> node) {
			if(node!=null) {
				inOrder(node.left);
				list.add(node.value);
				inOrder(node.right);
			}
		}

		public boolean hasNext() {
			return index<list.size()-1;
		}

		public V next() {
			if(!hasNext())
				throw new NoSuchElementException();
			index++;
			return list.get(index);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
