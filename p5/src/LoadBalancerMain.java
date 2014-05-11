import com.sun.corba.se.spi.activation.Server;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;


public class LoadBalancerMain
{

    public static void main(String[] args) throws IOException
    {
				Scanner scanner = null;
        //This program has three required command
        // line arguments and an optional fourth:

        if(args.length < 3 || args.length > 4)
        {
           System.out.println("Incorrect number of arguments accepts (3) or (4)");
           System.exit(0);
        }

        //the number of available hosting servers
        int maxServers = Integer.parseInt(args[0]);
        //the number of pages that each hosting server can cache
        int cacheSize = Integer.parseInt(args[1]);
		File file = new File(args[2]);
        if(!file.exists())
        {
            System.out.println("File does not exist");
            System.exit(0);
        }
        scanner = new Scanner(file);
        //Checks to see if the file is verbose
        boolean isVerbose = (args.length == 4) && (args[3].equals("-v"));
        
        String line;
       // SimpleHashMap<K, V> map = new SimpleHashMap<K, V>(); //TODO: Set the types for the HashMap
        SimpleHashMap<String, Server> map = new SimpleHashMap<String, Server>();


        
        
        while (scanner.hasNextLine()) {
						line = scanner.nextLine();
            //TODO: Handle each page request
        }
        scanner.close();
        
        //TODO: Output the number of requests routed to each server
        //TODO: Output the total number of evictions
    }
    
    

}
