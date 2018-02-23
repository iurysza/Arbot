package src;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientTest {

    public static void main(String argv[]) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 9000);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedInputStream bis = new BufferedInputStream(clientSocket.getInputStream());
        InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

        String sentence;
        int c = 0;
        while (clientSocket.isConnected() && c != -1) {
            StringBuffer instr = new StringBuffer();
            while ((c = isr.read()) != '\n')
                System.out.println((char) c);
                instr.append((char) c);
            System.out.println(instr.toString());
        }
//        outToServer.writeBytes(sentence + '\n');
//        modifiedSentence = inFromServer.readLine();
        clientSocket.close();
    }
}
