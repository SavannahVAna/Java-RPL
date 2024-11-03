import java.net.*;
import java.io.*;

//server pour lancer calconline, la classe qui va servir a calculer et ensuite envoyer le resultat au client
class Service extends Thread {


	Socket socket;


	public Service( Socket socket ) {
		this.socket = socket;
		this.start();
	}


	public void run() {
		BufferedReader entree = null;
		PrintStream sortie = null;
		try {
			entree = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			sortie = new PrintStream( socket.getOutputStream() );
		} catch( IOException e ) {
			try {
				socket.close();
			} catch( IOException e2 ) {
				System.out.println( "probl�me en fermant socket" );
			}
		}

		String texte;
		int compteur = 0;

		try {
			//while( !(texte = entree.readLine()).equals("xxxxxx"))
				//compteur += (new StringTokenizer( texte, " ,.;:_-+*/\\.;\"'{}()=><\t!\n")).countTokens();
			//sortie.println( "votre texte poss�de " + compteur + " mots" );
			CalcOnline calc = new CalcOnline(entree, sortie);
			calc.run();

			socket.close();
		} catch( IOException e ) {
			System.out.println( "probl�me dans run" );
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}




public class TCPServer {


	public void run() {
		int port = 12345;
		ServerSocket receptionniste;
		Socket socket;

		try {
			receptionniste = new ServerSocket( port );
			while( true ) {
				System.out.println( "waiting..." );
				socket = receptionniste.accept();
				System.out.println( "connexion!" );
				new Service( socket );
			}
		} catch( IOException e ) {
			System.out.println( "probleme de connexion" );
		}
	}
}

