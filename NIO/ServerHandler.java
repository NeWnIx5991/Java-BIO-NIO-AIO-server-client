package socket.NIO;

import socket.util.CalculatorUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerHandler implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private int port;

    public ServerHandler(Selector selector, ServerSocketChannel serverSocketChannel,int port) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
        this.port = port;
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select(1000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterable = selectionKeys.iterator();
            SelectionKey selectionKey = null;
            while (iterable.hasNext()) {
                selectionKey = iterable.next();
                iterable.remove();
                try {
                    handleInput(selectionKey);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleInput(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isValid()) {
            if (selectionKey.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(this.selector,SelectionKey.OP_READ);
            }

            if (selectionKey.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(buffer);
                if (readBytes > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String inStr = new String(bytes,"UTF-8");
                    System.out.println(" [Server] : 收到的消息为 : " + inStr);
                    int res = CalculatorUtil.cal(inStr);
                    System.out.println(" [Server] : 计算结果为 ： " + res);
                    handleOutput(socketChannel,res);
                }
            }
        }
    }

    private void handleOutput(SocketChannel socketChannel,int res) {
        String resStr = String.valueOf(res);
        byte[] bytes = resStr.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        try {
            buffer.put(bytes);
            buffer.flip();
            socketChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
