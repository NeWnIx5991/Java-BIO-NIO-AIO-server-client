package socket.BIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerBetter {

    private static final int DEFAULT_PORT = 12345;
    private static ServerSocket serverSocket;

    public static void recv() {
        start(DEFAULT_PORT);
    }

    public static synchronized void start(int port) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(" [Server] : 服务已经启动，端口号为：" + port);

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new ServerHandler(socket));
                //new Thread(new ServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(" [Server] : 关闭");
                serverSocket = null;
            }
        }
    }
}
