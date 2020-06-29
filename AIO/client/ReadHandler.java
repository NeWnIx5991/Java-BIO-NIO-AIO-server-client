package socket.AIO.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel;

    public ReadHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);
        try {
            String msg = new String(bytes,"UTF-8");
            System.out.println(" [Client] : 收到的消息为 : " + msg);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.out.println(" [Client] : 数据读取失败... ");
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
