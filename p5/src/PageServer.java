/**
 * Created by Rain on 5/10/14.
 */
public class PageServer {
    private String serverIPAddress;
    private int accessTime;
    public PageServer(String serverIPAddress, int accessTime)
    {
        this.serverIPAddress = serverIPAddress;
        this.accessTime = accessTime;
    }
    public String getServerIPAddress()
    {
        return this.serverIPAddress;
    }
    public int getAccessTime()
    {
        return this.accessTime;
    }
}
