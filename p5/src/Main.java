public class Main {

    public static void main(String[] args) {
	SimpleHashMap<Integer, String> map = new SimpleHashMap<Integer, String>();
	for(int i = 1; i < 30; i++){
		map.put(i, Integer.toString(i));
	}
	System.out.println(map.get(18));
    }
}
