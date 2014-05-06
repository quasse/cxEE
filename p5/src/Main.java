public class Main {

    public static void main(String[] args) {
	SimpleHashMap<Integer, String> map = new SimpleHashMap<Integer, String>();
	map.put(1, "hi");
	System.out.println(map.get(1));
    }
}
