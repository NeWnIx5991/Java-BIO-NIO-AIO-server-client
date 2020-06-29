package socket.AIO.client;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel;
    public WriteHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        if (attachment.hasRemaining()) {
            channel.write(attachment,attachment,this);
        }else {
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            channel.read(readBuffer,readBuffer,new ReadHandler(channel));
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}