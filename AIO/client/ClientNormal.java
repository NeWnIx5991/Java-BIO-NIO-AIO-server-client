package socket.AIO.client;

public class ClientNormal {
    private static String DEFAULT_HOST = "127.0.0.1";
    private static int DEFAULT_PORT = 12345;
    private static ClientHandler clientHandler ;
    public static void start() {
        start(DEFAULT_HOST,DEFAULT_PORT);
    }

    public static void start(String host,int port) {
        clientHandler = new ClientHandler(host,port);
        new Thread(clientHandler).start();
    }

    public static void sendMsg() {
        clientHandler.sendMsg();
    }
}