import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements a generic map based on hash tables using chained
 * buckets for collision resolution.
 *
 * <p>A map is a data structure that creates a key-value mapping. Keys are
 * unique in the map. That is, there cannot be more than one value associated
 * with a same key. However, two keys can map to a same value.</p>
 *
 * <p>The <tt>SimpleHashMap</tt> class takes two generic parameters, <tt>K</tt>
 * and <tt>V</tt>, standing for the types of keys and values respectively. Items
 * are stored in a hash table. Hash values are computed from the
 * <tt>hashCode()</tt> method of the <tt>K</tt> type objects.</p>
 *
 * <p>The chained buckets are implemented using Java's <tt>LinkedList</tt>
 * class.  When a hash table is created, its initial table size and maximum
 * load factor is set to <tt>11</tt> and <tt>0.75</tt>. The hash table can hold
 * arbitrarily many key-value pairs and resizes itself whenever it reaches its
 * maximum load factor.</p>
 *
 * <p><tt>null</tt> values are not allowed in <tt>SimpleHashMap</tt> and a
 * NullPointerException is thrown if used. You can assume that <tt>equals()</tt>
 * and <tt>hashCode()</tt> on <tt>K</tt> are defined, and that, for two
 * non-<tt>null</tt> keys <tt>k1</tt> and <tt>k2</tt>, if <tt>k1.equals(k2)</tt>
 * then <tt>k1.hashCode() == k2.hashCode()</tt>. Do not assume that if the hash
 * codes are the same that the keys are equal since collisions are possible.</p>
 */
public class SimpleHashMap<K, V> {
	/**
	 * A map entry (key-value pair).
	 *
	 */
	public static class Entry<K, V> {
		private K key;
		private V value;

		/**
		 * Constructs the map entry with the specified key and value.
		 */
		public Entry(K k, V v) {
			// TODO
			key = k;
			value = v;
		}

		/**
		 * Returns the key corresponding to this entry.
		 *
		 * @return the key corresponding to this entry
		 */
		public K getKey() {
			// TODO
			return this.key;
		}

		/**
		 * Returns the value corresponding to this entry.
		 *
		 * @return the value corresponding to this entry
		 */
		public V getValue() {
			// TODO
			return this.value;
		}

		/**
		 * Replaces the value corresponding to this entry with the specified
		 * value.
		 *
		 * @param value new value to be stored in this entry
		 * @return old value corresponding to the entry
		 */
		public V setValue(V value) {
			// TODO
			V tmpValue = this.value;
			this.value = value;
			return tmpValue;
		}
	}

	// TODO You may add private fields here
	private int initialTableSize;
	private int tableSize;
	//Variable used for load factor
	private double loadFactor;
	//Variable used for maximum load factor
	private double maxLoadFactor;
	//Holds on to the number of items
	private int numItems;
	//Note there may be an error in which the hash map is defined
	private LinkedList<Entry>[] hashMap;
	/**
	 * Constructs an empty hash map with initial capacity <tt>11</tt> and
	 * maximum load factor <tt>0.75</tt>.
	 **/
	public SimpleHashMap() {
		// TODO
		tableSize = initialTableSize = 11;
		maxLoadFactor = .75;
		hashMap =  ( LinkedList<Entry>[])(new LinkedList[initialTableSize]);
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 *
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or <tt>null</tt>
	 * if this map contains no mapping for the key
	 * @throws NullPointerException if the specified key is <tt>null</tt>
	 */
	public V get(Object key) {

		int hashIndex = 0;
		if(key.hashCode()%tableSize > 0)
		{
			hashIndex = key.hashCode()%tableSize;
		}
		else
		{
			hashIndex = (key.hashCode()%tableSize) + tableSize;
		}

		Iterator<Entry> entryIterator = hashMap[hashIndex].iterator();
		while(entryIterator.hasNext()){
			Entry tmp = entryIterator.next();
			if (tmp.getKey().equals(key)){
				return (V) tmp.getValue();
			}
		}
		return null;
	}

	/**
	 * <p>Associates the specified value with the specified key in this map.
	 * Neither the key nor the value can be <tt>null</tt>. If the map
	 * previously contained a mapping for the key, the old value is replaced.</p>
	 *
	 * <p>If the load factor of the hash table after the insertion would exceed
	 * the maximum load factor <tt>0.75</tt>, then the resizing mechanism is
	 * triggered. The size of the table should grow at least by a constant
	 * factor in order to ensure the amortized constant complexity. You must also
	 * ensure that the new selected size is Prime. After that, all of the mappings 
	 * are rehashed to the new table.</p>
	 *
	 * @param key key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with <tt>key</tt>, or
	 * <tt>null</tt> if there was no mapping for <tt>key</tt>.
	 * @throws NullPointerException if the key or value is <tt>null</tt>
	 */
	public V put(K key, V value) {

		//********Still need to do something about duplicates****************
		//Converts key into hashIndex and checks if the hashIndex is below zero.
		int hashIndex = key.hashCode() % tableSize;
		if(hashIndex % tableSize < 0)
		{
			hashIndex = hashIndex + tableSize;
		}

		//Adds new Entry to the hashMap.
		Entry entry = new Entry(key,value);


		if (hashMap[hashIndex].equals(null)){
			LinkedList<Entry> list = new LinkedList<Entry>();
			list.add(entry);
		}else{
			hashMap[hashIndex].add(entry);
		}
		//Check to see if hashMap is too full.
		if (numItems/tableSize >= .75){
			rehash();
		}
		numItems++;
		return value;
	}

	/**
	 * Removes the mapping for the specified key from this map if present. This
	 * method does nothing if the key is not in the hash table.
	 *
	 * @param key key whose mapping is to be removed from the map
	 * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
	 * if there was no mapping for <tt>key</tt>.
	 * @throws NullPointerException if key is <tt>null</tt>
	 */
	public V remove(Object key) {
		Entry tmp = null;
		//Converts key into hashIndex and checks if the hashIndex is below zero.
		int hashIndex = key.hashCode();
		if(key.hashCode()%tableSize > 0)
		{
			hashIndex = key.hashCode() % tableSize;
		}
		else
		{
			hashIndex = (key.hashCode() % tableSize) + tableSize;
		}
		Iterator<Entry> itr = hashMap[hashIndex].iterator();
		while (itr.hasNext()){
			tmp = itr.next();
			if (tmp.getKey().equals(get(key))){
				hashMap[hashIndex].remove(key);
			}
		}
		numItems--;
		return (V) tmp.getValue();
	}

	/**
	 * Returns the number of key-value mappings in this map.
	 *
	 * @return the number of key-value mappings in this map
	 */
	public int size() {
		return numItems;
	}

	/**
	 * Returns a list of all the mappings contained in this map. This method
	 * will iterate over the hash table and collect all the entries into a new
	 * list. If the map is empty, return an empty list (not <tt>null</tt>).
	 * The order of entries in the list can be arbitrary.
	 *
	 * @return a list of mappings in this map
	 */
	public List<Entry<K, V>> entries() {
		List<Entry<K,V>> list = null;
		for(int i = 0; i < hashMap.length; i++){
			Iterator<Entry> itr = hashMap[i].iterator();
			while (itr.hasNext());
			list.add(itr.next());
		}
		return list;
	}

	private void rehash(){
		tableSize = tableSize*2;
		if (tableSize > maxLoadFactor){
			tableSize = (int) maxLoadFactor;
		}
	}
}
