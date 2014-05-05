///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 3
// Files:           (HashTable.java)
//                    HashTable.java, Entry.java
// Semester:         CS302 Spring 2013
//
// Author:           Eric Smith
// Email:            ejsmith6@wisc.edu
// CS Login:         esmith
// Lecturer's Name:  Jim Skrentney
// Lab Section:      N/A
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
// STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR
// PARTNER
// Credits:          (list anyone who helped you write your program)
//////////////////////////// 80 columns wide /////////////////////////////////
import java.io.*;
import java.util.LinkedList;

/**
 * This class implements a hashtable that using chaining for
 * collision handling.
 * The chains are implemented using LinkedLists.  When a hashtable is
 * created,
 * its initial size and maximum load factor are specified. The hashtable
 * can
 * hold arbitrarily many items and resizes itself whenever it reaches 
 * its maximum load factor. Note that this hashtable allows duplicate
 * entries.
 */
public class HashTable<T> {
	/* Add private variables here as needed */
    //Variable for tableSiize
	private int tableSize;
    //Variable used for load factor
    private double loadFactor;
    //Variable used for maximum load factor
    private double maxLoadFactor;
    //Holds on to the number of items
    private int numItems;
    private LinkedList<T>[] arrayTable;
    /**
     * Constructs an empty hashtable with the given initial size and
     * maximum '
     * load factor. The load factor should be a real 
     * number greater than 0.0 (not a percentage).  For example, to
     * create a
     * hash table with an initial size of 10 and a load factor of 0.85, one 
     * would use:
     * <dir><tt>HashTable ht = new HashTable(10, 0.85);</tt></dir>
     *
     * @param initSize The initial size of the hashtable.  If the size is less
     * than or equal to 0, an IllegalArgumentException is thrown.
     * @param loadFactor The load factor expressed as a real number.
     *                   If the
     * load factor is less than or equal to 0.0, an
     *                   IllegalArgumentException is
     * thrown.
     **/
    public HashTable(int initSize, double loadFactor) {
    	if(initSize <= 0)
        {
            throw new IllegalArgumentException();
        }
        if(loadFactor <= 0.0)
        {
            throw new IllegalArgumentException();
        }
        this.tableSize = initSize;
        this.maxLoadFactor= loadFactor;
        this.arrayTable = (LinkedList<T>[])(new LinkedList[initSize]);
        for(int i = 0; i < initSize; i++)
        {
            arrayTable[i] = new LinkedList<T>();
        }
        this.numItems  = 0;
        this.loadFactor = 0.0;
    }
    
    
    /**
     * Determines if the given item is in the hashtable and returns it if 
     * present.  If more than one copy of the item is in the hashtable,
     * the
     * first copy encountered is returned.
     *
     * @param item the item to search for in the hashtable
     * @return the item if it is found and null if not found
     **/
    public T lookup(T item) {
        int k ;
        //Checks to see whether or not the hashcade will be positive
        if(item.hashCode()%this.tableSize < 0)
        {
            k = Math.abs(item.hashCode()%this.tableSize);
        }
        else
        {
            k = item.hashCode()%this.tableSize;
        }
        return arrayTable[k].get(arrayTable[k].indexOf(item));
    }
    
    
    /**
     * Inserts the given item into the hash table.  
     * 
     * If the load factor of the hashtable after the insert would exceed 
     * (not equal) the maximum load factor (given in the constructor),
     * hen the
     * hashtable is resized.
     * 
     * When resizing, to make sure the size of the table is good, the
     * new size
     * is always 2 x <i>old size</i> + 1.  For example, size 101 would
     * become
     * 203.  (This  guarantees that it will be an odd size.)
     * 
     * <p>Note that duplicates <b>are</b> allowed.</p>
     *
     * @param item the item to add to the hashtable
     **/
    public void insert(T item) {
        int k ;
        //hash is used and k is set to the index to use
        k = Math.abs(item.hashCode()%this.tableSize);
        //add item to the proper position in the linked list in the array
        //of linked lists
        arrayTable[k].add(item);
        this.numItems++;
        //Used to expand in the case that the loadfactor is greater than
        //the defined max load factor
        if((double)this.numItems/this.tableSize > this.maxLoadFactor)
        {
            //Variable for a tmp tableSize
            int tmpTableSize = 2 * tableSize+ 1;
            //Array of Linked List created to store the new tmp size
            LinkedList<T>[] tmpArrayTable
                    = (LinkedList<T>[])(new LinkedList[tmpTableSize]);
            //Loop used to initialize each Linked List
            for(int x = 0; x < tmpTableSize; x++)
            {
               tmpArrayTable[x] = new LinkedList<T>();
            }
            //Used to iteratate through the Array of Linked List, then the
            //Specific linked list to rehash
            for(int i = 0; i < arrayTable.length; i++)
            {
                int j = 0;
                //The process of rehashing is done here as the item is found
                //rehashedf from the old arrayTable using the new array
                // Table
                //table size
                if(arrayTable[i].size() != 0)
                {
                    while(j < arrayTable[i].size())
                    {
                        T itemReHash = arrayTable[i].get(j);
                        //Used to hash
                        int c = Math.abs(itemReHash.hashCode()
                                %tmpTableSize);
                        tmpArrayTable[c].add(itemReHash);
                        //Get the item and re hash on that item.
                        j++;
                    }
                }
            }
            //Set the table size and arrayTable to that of the new table size
            //and arrayTable
            this.tableSize =  tmpTableSize;
            arrayTable = tmpArrayTable;
        }

    }
    /**
     * Removes and returns the given item from the hashtable.  If the
     * item is
    * not in the hashtable, <tt>null</tt> is returned.  If more than one
     * copy
     * of the item is in the hashtable, only the first copy encountered is 
     * removed and returned.
     *
     * @param item the item to delete in the hashtable
     * @return the removed item if it was found and null if not found
     **/
    public T delete(T item) {
        //First lookup to determine if item is in the array of Linked Lists
        if( lookup(item) == null)
        {
            return null;
        }
        else
        {
            //Hash and set k to the index
            int k = Math.abs(item.hashCode()%tableSize);
            // tmp used for return of the item
            T tmp = lookup(item);
            //tmp is set equal to the item that will be removed
            tmp = arrayTable[k].get(arrayTable[k].indexOf(item));
            arrayTable[k].remove(arrayTable[k]
                    .get(arrayTable[k].indexOf(item)));
            numItems --;
            return tmp;
        }
    }
    
  
    /**
     * Prints statistics about the hashtable to the PrintStream supplied.
     * The statistics displayed are: 
     * <ul>
     * <li>the current table size
     * <li>the number of items currently in the table 
     * <li>the current load factor
     * <li>the length of the largest chain
     * <li>the number of chains of length 0
     * <li>the average length of the chains of length > 0
     * </ul>
     *
     * @param out the place to print all the output
     **/
    public void displayStats(PrintStream out) {
            out.println("Current table size:         "+  this.tableSize);
            out.println("# items in table:            "+ this.numItems);
            out.println("Current load factor:       "
                    +(double)this.numItems/this.tableSize);
        //Variable used for the largestLinkedList
            int largest = 0;
        //Variable to hold on to the number of zero Linked Lists
            int amntZero = 0;
        //Variable lto hold on to the number of non-zero Linked Lists
            int amntNonZeroList = 0;
        //Variable for the sum of Linked Lists
            int sum = 0;
        //This loop will count the number of Linked Lists
            for(int i = 0; i < arrayTable.length; i++)
            {
                int linkedListCount = 0;
                int j = 0;
               while(j <arrayTable[i].size())
               {
                    linkedListCount++;
                   j++;
               }
                //Increment if j is not equal to 0
               if(j!= 0)
               {
                   amntNonZeroList++;
               }
                if(linkedListCount == 0)
                {
                    amntZero++;
                }
                if (linkedListCount > largest)
                {
                    largest  = linkedListCount;
                }
                sum += linkedListCount;
            }
            out.println("Longest chain length:      " + largest);
            out.println("# 0-length chains:            " +       amntZero);
            out.println("Avg (non-0) chain length: "
                    +(double)sum/amntNonZeroList);
    }
}
