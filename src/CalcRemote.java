import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CalcRemote {
    Socket _socket = null; // socket representing connecton to remote machine
    PrintWriter _send = null; // write to this to send data to remote server
    BufferedReader _receive = null;
    public void init() {
        int remoteSocketNumber = 1234;
        try {
            _socket = new Socket("1.2.3.4", remoteSocketNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(_socket !=null) {
            try {
                _send = new PrintWriter(_socket.getOutputStream(), true);
                _receive = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //tcp server client
    }
}
