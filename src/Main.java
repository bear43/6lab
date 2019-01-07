import java.net.ServerSocket;

/*
*
    19.
    «Ожидание  подключения».
    Организовать  взаимодействие  типа  клиент
    -
    сервер.  К  серверу  одновременно  может  подключиться  только  один  клиент.
    Остальные клиенты заносятся в очередь, и им высылается сообщение об ожидании
    освобождения сервера.
*
*/
public class Main
{
    private static ServerSocket serverSocket;//Серверный сокет, принимает соединения от клиентов
    private static int serverPort = 2020;//Порт, на котором запускается сервер
    private static boolean isWorking = true;//Когда true - программа работает(цикл while), иначе программа завершается
    private static int delayTime = 15;//15 миллисекунд спит основной поток(чтобы не нагружать процессор вечным циклом while
    private static QueueWorker worker = new QueueWorker();

    public static void main(String[] args) throws Exception//Точка входа в программу
    {
        serverSocket = new ServerSocket(serverPort);
        System.out.println("Сервер запущен на порте " + serverPort);
        new Thread(worker).start();//Запускаем отдельный поток обработки очереди
        Client currentClient;
        while(isWorking)
        {
            currentClient = new Client(serverSocket.accept());
            worker.getClientList().add(currentClient);
            Thread.sleep(delayTime);
        }
        worker.setWorking(false);
    }
}
