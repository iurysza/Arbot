package src;

import java.io.IOException;

public class WatcherServer {

    public static void main(String... args) throws IOException {
        //192.168.0.2:9000
        Server server = new Server(9000);
        server.start();
        try {
            for (int i = 0; i < 200; i++) {
                String message = "Data: " + i + "\n";
                System.out.println("Sending: " + message);
                server.broadcast(message);
                Thread.sleep(1 * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
    }

}