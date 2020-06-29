package socket.BIO;

import socket.util.CalculatorUtil;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable{
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
             bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             printWriter = new PrintWriter(socket.getOutputStream(),true);
             String inStr ;

             while((inStr = bufferedReader.readLine()) != null) {

                 System.out.println(" [Server] : 收到的消息为 : " + inStr);
                 int res = CalculatorUtil.cal(inStr);
                 System.out.println(" [Server] : 计算结果为 ： " + res);
                 printWriter.println(res);
             }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bufferedReader = null;
            }

            if (printWriter != null) {
                printWriter.close();
                printWriter = null;
            }

            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}