package socket.AIO.server;

import socket.AIO.server.ServerHandler;

public class ServerNormal {
    private static final int DEFAULT_PORT = 12345;
    public volatile static long ClientCount = 0;
    public static void start() {
        start(DEFAULT_PORT);
    }

    private static void start(int port) {
        new Thread(new ServerHandler(port)).start();
    }
}
