package socket.AIO.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;

public class ClientHandler implements CompletionHandler<Void,ClientHandler> ,Runnable{
    private String host ;
    private int port;
    private AsynchronousSocketChannel clientChannel ;
    public ClientHandler(String host , int port ) {
        this.host = host;
        this.port = port;
        try {
            clientChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        clientChannel.connect(new InetSocketAddress(this.host,this.port),this,this);
    }

    @Override
    public void completed(Void result, ClientHandler attachment) {
        System.out.println("客户端成功连接到服务器...");
    }

    @Override
    public void failed(Throwable exc, ClientHandler attachment) {
        System.out.println("客户端连接到服务器失败...");
        exc.printStackTrace();
        try {
            clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String expression = scanner.next();
            if (expression.equals("quit")) {
                try {
                    clientChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return ;
            }
            handleOutput(expression);
        }
    }

    public void handleOutput(String expression) {
        byte[] bytes = expression.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        clientChannel.write(writeBuffer,writeBuffer,new WriteHandler(clientChannel));
    }
}
