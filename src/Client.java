import java.io.*;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client
{
    private Socket clientSocket;//Клиентский сокет
    private PrintWriter pw;
    private BufferedReader br;

    public Client(Socket clientSocket)
    {
        System.out.println("Новый клиент!");
        this.clientSocket = clientSocket;
    }


    public void closeConnection() throws Exception//Закрывает соединенние с клиентом.
    {
        if (!clientSocket.isClosed())
            clientSocket.close();
    }

    public void sendMessage(String msg) throws Exception
    {
        if(clientSocket.isConnected())
        {
            if(pw == null) pw = new PrintWriter(clientSocket.getOutputStream());
            pw.println(msg);
            pw.flush();
        }
    }

    public void run()
    {
        String buffer;
        try
        {
            pw = new PrintWriter(clientSocket.getOutputStream());//Через что пишем клиенту
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//Через что читаем от клиента
            while(true)//До тех пор, пока клиент желает держаться подключенным к серверу
            {
                sendMessage("Привет, как дела, как погода, азазаз, мне нраааавятся ноги твои и глаза");
                buffer = br.readLine();
                if(buffer.equals("exit"))
                {
                    sendMessage("Пока(");
                    closeConnection();
                    break;
                }
                else if(buffer.equals("flex"))
                {
                    sendMessage("ФЛЕКСИМ, БАДИ, ФЛЕКСИМ, ТУУУУУУУ ТУ ТУУУУУ ТУУУУ ТУУУУ ТУУУУ ТУ ДУ ТУ ДУ ТУУУУУУУ");
                }
                else if(buffer.equals("buyExams"))
                {
                    sendMessage("С утра деньги, вечером товар");
                }
                else
                {
                    sendMessage("ТЫЧО ТЫЧО, ВВОДИ КОМАНДЫ, А НЕ СВОЮ БРЕХНЮ, Я ВЕДЬ ЗНАЮ, ГДЕ ТЫ ЖИВЕШЬ");
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                closeConnection();
            }
            catch(Exception ignored)
            {

            }
        }
    }
}
