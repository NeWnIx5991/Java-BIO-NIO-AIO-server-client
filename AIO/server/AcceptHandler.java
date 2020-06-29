package socket.AIO.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, ServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel result, ServerHandler attachment) {
        ServerNormal.ClientCount++;
        System.out.println(" [Server] 连接的客户端数 : " + ServerNormal.ClientCount);
        attachment.serverSocketChannel.accept(attachment,this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer,buffer,new ReadHandler(result));
    }

    @Override
    public void failed(Throwable exc, ServerHandler attachment) {
        System.out.println(" [Server] 客户端连接失败 ");
        attachment.latch.countDown();
    }
}