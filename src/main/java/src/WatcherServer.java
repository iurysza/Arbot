package src;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WatcherServer implements Runnable {

    public static void main(String... args) throws IOException, InterruptedException {
        WatcherServer server = new WatcherServer(9000);
        new Thread(server).start();

        try {
            for (int i = 0; i < 20; i++) {
                server.sendData("Data: " + i + "\n");
                Thread.sleep(1 * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        server.stop();
    }

    protected int serverPort = 18080;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected Thread runningThread = null;

    private ArrayList<Socket> clients = new ArrayList<>();

    public WatcherServer(int port) {
        this.serverPort = port;
    }

    private void sendData(String s) {
        for (Socket client : clients) {
            if (client.isClosed()) {
                continue;
            }
            try {
                Log.debug("Sending data: " + s);
                OutputStream output = client.getOutputStream();
                output.write(s.getBytes());
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
                new Thread(new WorkerRunnable(clientSocket, "Multithreaded Server")).start();
                clients.add(clientSocket);
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
        }
        System.out.println("Server Stopped.");
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

    public static class WorkerRunnable implements Runnable {

        protected Socket clientSocket = null;
        protected String serverText = null;

        public WorkerRunnable(Socket clientSocket, String serverText) {
            this.clientSocket = clientSocket;
            this.serverText = serverText;
        }

        public void run() {
//            try {
//                InputStream input = clientSocket.getInputStream();
//                OutputStream output = clientSocket.getOutputStream();
//                long time = System.currentTimeMillis();
//                output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +
//                        this.serverText + " - " +
//                        time +
//                        "").getBytes());
//                output.close();
//                input.close();
//                System.out.println("Request processed: " + time);
//            } catch (IOException e) {
//                //report exception somewhere.
//                e.printStackTrace();
//            }
        }
    }
}