package socket.BIO;

import java.util.Random;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        //server

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerBetter.recv();
            }
        }).start();

        Thread.sleep(2000);*/

        char[] operators = {'+','-','*','/'};
        Random random = new Random(System.currentTimeMillis());
        int iter = 10;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 0;
                while (true) {
                    if(index++ == 2) break;
                    System.out.println("迭代第 " + index + " 次 ");
                    String expression = String.valueOf(random.nextInt(10) );
                    for (int i = 0; i < 4; i++) {
                        expression += ""+operators[random.nextInt(4)] + (random.nextInt(10) + 1);
                    }
                    ClientNormal.send(expression);

                    try {
                        Thread.currentThread().sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}