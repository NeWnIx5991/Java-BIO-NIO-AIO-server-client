package socket.AIO.server;

import socket.util.CalculatorUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel ;
    public ReadHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.channel = asynchronousSocketChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();

        byte[] message = new byte[attachment.remaining()];
        attachment.get(message);

        try {
            String expression = new String(message,"UTF-8");
            System.out.println(" [Server] : 收到的消息为 : " + expression);
            int res = CalculatorUtil.cal(expression);
            System.out.println(" [Server] : 计算结果为 ： " + res);
            handleOutput(res);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleOutput(int res) {
        byte[] bytes = String.valueOf(res).getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()) {
                    channel.write(buffer,buffer,this);
                }else {
                    ByteBuffer readbuffer = ByteBuffer.allocate(1024);
                    channel.read(readbuffer,readbuffer,new ReadHandler(channel));
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
        });
    }
}