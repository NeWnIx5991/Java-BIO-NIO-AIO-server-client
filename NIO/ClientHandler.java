package socket.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ClientHandler implements Runnable{
    private Selector selector ;
    private SocketChannel socketChannel;
    private String address;
    private int port;
    private volatile boolean stopped ;

    public ClientHandler(String address, int port) {
        this.address = address;
        this.port = port;

        try {
            this.selector = Selector.open();
            this.socketChannel = SocketChannel.open();
            this.socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(address,12345));
            while(!socketChannel.finishConnect()) {
                System.out.println("connect");
            }
            /*
            if (!conn) {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
                System.out.println(" [Client] : register connect");
            }*/
            this.stopped = false;
            System.out.println(" [Client] : 客户已经启动，端口号为：" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(!stopped){
            try {
                selector.select(1000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while(iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                try {
                    handleInput(selectionKey);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void handleInput(SelectionKey selectionKey) throws IOException {
        if(selectionKey.isValid()) {
            socketChannel = (SocketChannel) selectionKey.channel();
            if (selectionKey.isConnectable()) {
                if (!socketChannel.finishConnect())
                    return ;
            }

            if(selectionKey.isReadable()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(byteBuffer);
                if (readBytes > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String inStr = new String(bytes,"UTF-8");
                    System.out.println(" [Client] : 收到的消息为 : " + inStr);
                }
            }
        }
    }

    public void handleOutput(SocketChannel socketChannel , String expression) throws IOException {
        byte[] bytes = expression.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        socketChannel.write(buffer);
        System.out.println(" [Client] : 发送消息 : " + expression);
    }

    public void sendMsg() throws IOException {
        socketChannel.register(selector,SelectionKey.OP_READ);
        Scanner scanner = new Scanner(System.in);
        while(true){
            String expression = scanner.next();
            if (expression.equals("quit")) {
                this.stopped = true;
                socketChannel.close();
                selector.close();
                return;
            }
            handleOutput(socketChannel,expression);
        }
    }
}