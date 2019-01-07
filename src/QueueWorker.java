import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueWorker implements Runnable
{
    private Queue<Client> clientList = new ConcurrentLinkedQueue<>();
    private static List<Client> sendedClient = new ArrayList<>();
    private boolean isWorking = true;
    private int delayTime = 15;

    public Queue<Client> getClientList()
    {
        return clientList;
    }

    public void setClientList(Queue<Client> clientList)
    {
        this.clientList = clientList;
    }

    public boolean isWorking()
    {
        return isWorking;
    }

    public void setWorking(boolean working)
    {
        isWorking = working;
    }

    @Override
    public void run()
    {
        Thread th;
        Client prioritetedClient;
        try
        {
            while (isWorking)
            {
                if(!clientList.isEmpty())
                {
                    prioritetedClient = clientList.poll();
                    th = new Thread(() -> {
                        try
                        {
                            while (true)
                            {
                                for (Client client : clientList)
                                {
                                    if (!sendedClient.contains(client))
                                    {
                                        client.sendMessage("Ты в очереди. Жди!");
                                        sendedClient.add(client);
                                    }
                                }
                                Thread.sleep(delayTime);
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    });
                    th.start();
                    prioritetedClient.run();
                    if(sendedClient.contains(prioritetedClient)) sendedClient.remove(prioritetedClient);
                }
                Thread.sleep(delayTime);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
