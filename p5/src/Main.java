public class Main {

    public static void main(String[] args) {
	SimpleHashMap<Integer, String> map = new SimpleHashMap<Integer, String>();
	map.put(1, "one");
	map.put(2, "two");
	System.out.println(map.get(1));
	System.out.println(map.get(2));
	map.remove(2);
	System.out.println(map.get(2));
    }
}
