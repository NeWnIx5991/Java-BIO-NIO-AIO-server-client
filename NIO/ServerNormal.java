package socket.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class ServerNormal {
    private static final int DEFAULT_PORT = 12345;

    public static void start() {
        start(DEFAULT_PORT);
    }

    private static void start(int port) {
        ServerSocketChannel serverSocketChannel = null;
        Selector selector = null;
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println(" [Server] : 服务已经启动，端口号为：" + port);

            new Thread(new ServerHandler(selector,serverSocketChannel,port)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}