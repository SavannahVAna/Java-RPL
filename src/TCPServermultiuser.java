import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


class Service2 extends Thread {


	Socket socket;


	public Service2(Socket socket ) {
		this.socket = socket;
		this.start();
	}


	public void run() {
		BufferedReader entree = null;
		PrintStream sortie = null;

		String texte;
		int compteur = 0;

		try {
			//while( !(texte = entree.readLine()).equals("xxxxxx"))
				//compteur += (new StringTokenizer( texte, " ,.;:_-+*/\\.;\"'{}()=><\t!\n")).countTokens();
			//sortie.println( "votre texte poss�de " + compteur + " mots" );
			CalcOnlineMulti calc = new CalcOnlineMulti(socket);
			calc.start();

			socket.close();
		} catch( IOException e ) {
			System.out.println( "probl�me dans run" );
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}




public class TCPServermultiuser {


	public static void main( String[] args ) {
		int port = 12345;
		ServerSocket receptionniste;
		Socket socket;

		try {
			receptionniste = new ServerSocket( port );
			while( true ) {
				System.out.println( "waiting..." );
				socket = receptionniste.accept();
				System.out.println( "connexion!" );
				new Service2( socket );
			}
		} catch( IOException e ) {
			System.out.println( "probleme de connexion" );
		}
	}
}

