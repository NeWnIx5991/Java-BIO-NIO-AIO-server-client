package socket.BIO;

import java.io.*;
import java.net.Socket;

public class ClientNormal {
    private static final int DEFAULT_PORT = 12345;
    private static final String DEFAULT_IP_ADDRESS = "81.68.127.248";

    public static void send(String expression) {
        send(DEFAULT_PORT,expression);
    }

    public static void send(int port,String expression) {
        Socket socket = null;
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;

        try {
            socket = new Socket(DEFAULT_IP_ADDRESS,port);
            System.out.println(" [Client] : 客户已经启动，端口号为：" + port);

            printWriter = new PrintWriter(socket.getOutputStream(),true); //需要自动刷入，否则下面的print写入后无法刷新到缓冲区，服务端readline无法接收就会卡住
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            printWriter.println(expression);
            System.out.println(" [Client] : 发送消息 : " + expression);

            System.out.println(" [Client] : 接收到服务端消息 : " + bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bufferedReader = null;
            }

            if (printWriter != null) {
                printWriter.close();
                printWriter = null;
            }

            if(socket != null) {
                try {
                    socket.close();
                    System.out.println(" [Client] : 关闭");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
