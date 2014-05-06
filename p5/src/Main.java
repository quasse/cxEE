public class Main {

    public static void main(String[] args) {
	SimpleHashMap<Integer, String> map = new SimpleHashMap<Integer, String>();
	map.put(1, "1");
	map.put(2, "2");
	System.out.println(map.get(1));
	System.out.println(map.get(2));
    }
}
