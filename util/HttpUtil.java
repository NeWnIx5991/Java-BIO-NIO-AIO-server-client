package socket.util;

public class HttpUtil {
    public static String getRequest(String host) {

        return "GET / HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "User-Agent: curl/7.43.0\r\n" +
                "Accept: */*\r\n\r\n";
    }
}
