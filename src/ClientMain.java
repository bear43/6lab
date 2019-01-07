import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientMain
{
    public static void main(String[] args) throws Exception
    {
        Socket socket = new Socket();
        int delayTime = 15;
        String cmd;
        socket.connect(new InetSocketAddress("localhost", 2020));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while(true)
        {
            System.out.println(br.readLine());
            BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Команда серверу: ");
            cmd = brConsole.readLine();
            pw.println(cmd);
            pw.flush();
            if(cmd.equals("exit"))
            {
                socket.close();
                return;
            }
            System.out.println(br.readLine());
            Thread.sleep(delayTime);
        }
    }
}
