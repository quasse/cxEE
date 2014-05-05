///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 3
// Files:           (Entry.java)
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

/**
 * Class that manipulates and overides the standard hash function
 * for use by the HashTable.java class
 */
public class Entry {
	
	public static final int STATIC = 0;
	public static final int STRING = 1;
	public static final int LONG = 2;
	public static final int BOTH = 3;
	
	private String name;
	private long phone;
	private int hashType;
	
	public Entry(String name, long phone, int hashType) {
		this.name = name;
		this.phone = phone;
		this.hashType = hashType;
	}
	
	@Override
	public String toString() {
		return name + ":" + phone;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Entry) {
			Entry that = (Entry) other;
			if (that.name.equals(this.name) && that.phone == this.phone)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Returns a hashCode for this object. You should complete the three
	 * different hash functions marked below.
	 * 
	 * Make note that when you write a hash function, it must always
     * return
	 * the same value for the same object. In other words, you should
     * not use
	 * any randomness to generate a hash code.
	 */
	@Override
	public int hashCode() {
		if (hashType == STRING) {
			/* Hash on the String name only. Java has a built-in hashing 
			 * function for Strings; see 
			 * http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/
			 * String.html#hashCode%28%29
			 * */
			 int k = name.hashCode();
			//Replace me with your hash function
			return k;
		}
		else if (hashType == LONG) {
			/* Hash on the phone number only. You may write whatever
			 hash
			 * function you like, as long as it involves the phone number 
			 * in some way. Mod and/or division both work well for this. */
                String phoneStr = Long.toString(phone);
               int sum = 0;
 			   for(int i = 6; i < phoneStr.length(); i++)
               {
                   sum += phoneStr.charAt(i);
               }
			//Replace me with your hash function
			return sum;
		}
		else if (hashType == BOTH) {
			/* Come up with your own hashing function. Your hashing
			 function
			 * should have better performance than each of the other
			  * functions
			 * on at least one of the given input files. 
			 * You may use the name, phone number, or both. */

            String phoneStr = Long.toString(phone);
            String lastFour = phoneStr.substring(5, 9);
            int digitsToHash = Integer.parseInt(lastFour);
            int nameToHash = phoneStr.charAt(0);

            //Replace me with your hash function
            return digitsToHash + nameToHash;
		}
		else {
			//Fixed hash function
			return 11;
		}
	}
}
