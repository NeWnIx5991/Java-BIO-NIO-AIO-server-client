package socket.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ClientNormal {
    private static final int DEFAULT_PORT = 12345;
    private static final String DEFAULT_ADDRESS = "127.0.0.1";

    public static void send(String expression) {
        send(DEFAULT_ADDRESS,DEFAULT_PORT,expression);
    }

    public static void send(String address , int port , String expression) {
        Selector selector = null;
        SocketChannel socketChannel = null;

        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}