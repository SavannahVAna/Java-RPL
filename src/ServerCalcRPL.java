import java.io.IOException;

public class ServerCalcRPL {//main du server de calcul, lance le bon mode en fonction de l'argument passé
    public static void main(String[] args) throws IOException {
        if (args.length ==0) {
            CalcOnlineStatic calcServer = new CalcOnlineStatic();
            calcServer.runSevrer();  // Lancer le serveur sur le port 12345
        }
        else if(args.length == 1){
            if(args[0].equals("-stack=multiple")){
                TCPServer server = new TCPServer();
                server.run();
            }
            else if(args[0].equals("-stack=shared")){
                CalcOnlineStatic calcServer = new CalcOnlineStatic();
                calcServer.runSevrer();  // Lancer le serveur sur le port 12345

            }
        }
        else {
            System.out.println("Usage: java ServerCalcRPL <-stack={[shared],multiple}>");
        }
    }
}
