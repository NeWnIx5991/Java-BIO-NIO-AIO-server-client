package socket.AIO.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class ServerHandler implements Runnable{
    private int port;
    public AsynchronousServerSocketChannel serverSocketChannel;
    public CountDownLatch latch;

    public ServerHandler(int port) {
        this.port = port;
        try {
            this.serverSocketChannel = AsynchronousServerSocketChannel.open();
            this.serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println(" [Server] : 服务已经启动，端口号为：" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        serverSocketChannel.accept(this,new AcceptHandler());

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}