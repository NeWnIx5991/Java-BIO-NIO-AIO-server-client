package socket.NIO;

import socket.BIO.ClientNormal;

import java.io.IOException;
import java.util.Random;

public class TestClient {
    public static void main(String[] args) throws IOException {

        ClientHandler clientHandler = new ClientHandler("81.68.127.248",12345);
        new Thread(clientHandler).start();

        try {
            Thread.sleep(1000);
            clientHandler.sendMsg();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}