import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CalcRemote2 {
    Socket _socket = null; // socket representing connecton to remote machine
    PrintWriter _send = null; // write to this to send data to remote server
    BufferedReader _receive = null;
    public void init() {
        int remoteSocketNumber = 12345;
        try {
            _socket = new Socket("127.0.0.1", remoteSocketNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(_socket !=null) {
            try {
                _send = new PrintWriter(_socket.getOutputStream(), true);
                _receive = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
                new Thread(() -> {
                    try {
                        String serverResponse;
                        while ((serverResponse = _receive.readLine()) != null) {
                            System.out.println(serverResponse);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                // Read messages from the console and send to the server
                Scanner scanner = new Scanner(System.in);
                String userInput;
                while (true) {
                    userInput = scanner.nextLine();
                    _send.println(userInput);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //tcp server client
    }
    public void run(){
        CalcRemote2 calcRemote = new CalcRemote2();
        calcRemote.init();
    }
}
