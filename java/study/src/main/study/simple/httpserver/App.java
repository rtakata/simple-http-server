package study.simple.httpserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("start http server ->");
        try (
                ServerSocket server = new ServerSocket(11211); // https://docs.oracle.com/javase/jp/8/docs/api/java/net/ServerSocket.html#ServerSocket-int-
                // backlog : 50
                // InetAddress : localhost
                Socket socket = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        ) {
            String line = in.readLine();
            StringBuilder header = new StringBuilder();
            int contentLength = 0;
            while (line != null && !line.isEmpty()) {
                // Content-Lengthの取得
                if (line.startsWith("Content-Length: ")) {
                    contentLength = Integer.parseInt(line.split(": ")[1].trim());
                }
                header.append(line + '\n');
                line = in.readLine();
            }
            System.out.println(header);
            // Contentが存在するとき
            if (contentLength > 0) {
                char[] chars = new char[contentLength];
                in.read(chars);
                System.out.println(new String(chars));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("<- end http server");
    }
}
