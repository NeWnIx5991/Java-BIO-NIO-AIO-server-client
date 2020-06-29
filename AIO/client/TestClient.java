package socket.AIO.client;

public class TestClient {
    public static void main(String[] args) {
        ClientNormal.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ClientNormal.sendMsg();
    }
}