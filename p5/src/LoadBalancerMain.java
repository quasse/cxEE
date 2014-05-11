import com.sun.corba.se.spi.activation.Server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        //Increase the counter by 1 each time you add a server
        SimpleHashMap<String, PageServer> map = new SimpleHashMap<String, PageServer>();

        int serverNumber = 0;

        long[] accessTimeArray = new long[maxServers];
        int[] pagesForServer = new int[maxServers];
        //PageServer pageServer;
        while (scanner.hasNextLine())
        {
            //TODO: Handle each page request
            line = scanner.nextLine().trim();
            String pageNumber = line;
            //Need to use the round robin technique.. but without the queue
            if(serverNumber > maxServers - 1)
            {
                serverNumber = 0;
            }
            //Incremenet accessTime
             accessTimeArray[serverNumber]++;
            //Might have to change how this is put
            map.put(pageNumber, new PageServer("192.168.0." + serverNumber, accessTimeArray[serverNumber]));
            pagesForServer[serverNumber]++;
            //System.out.println(map.get(pageNumber).getAccessTime());
            //Deal with page eviction
            SimpleHashMap.Entry pageToEvict = null;
            long leastUsedAccessTime = 100000000;
            if(pagesForServer[serverNumber] > cacheSize)
            {
               //The hunt for the least used page begins
               for(int i = 0; i < map.entries().size(); i++)
               {
                 if(map.entries().get(i).getValue().getServerIPAddress().equals("192.168.0" + serverNumber))
                 {
                      if(map.entries().get(i).getValue().getAccessTime() <  leastUsedAccessTime)
                      {
                          leastUsedAccessTime = map.entries().get(i).getValue().getAccessTime();
                          pageToEvict = map.entries().get(i);
                      }
                 }
               }
                map.remove(pageToEvict);
            }
            //Increment server
            serverNumber++;
        }
        scanner.close();
        
        //TODO: Output the number of requests routed to each server
        //TODO: Output the total number of evictions
    }
    
    

}
