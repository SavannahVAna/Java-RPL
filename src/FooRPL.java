import java.io.IOException;

public class FooRPL {//comme demandé la classe main va juste lancer la classe qui convient en fonction des arguments passés
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            CalcRPLImproved rpi = new CalcRPLImproved(false);
            try {
                rpi.run();
            } catch (Exception e){
                System.out.println("une erreur s'est produite");
            }
        }

        else {
            if(args.length == 1) {
                switch (args[0]) {
                    case "-user=remote":
                        CalcRemote rpi = new CalcRemote();
                        try {
                            rpi.run(false, true);
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                        break;
                    case "-user=local":
                        CalcRemote rp = new CalcRemote();
                        try {
                            rp.run(false, false);
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                        break;
                    case "-log=rec":
                        CalcRPLImproved rp1 = new CalcRPLImproved(true);
                        try {
                            rp1.run();
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                        break;
                    case "-log=replay":

                        LogParser log = new LogParser("log.txt");
                        log.run();
                        break;

                }

            }
            else if (args.length == 2) {
                if (args[0].equals("-user=remote")) {
                    if(args[1].equals("-log=rec")) {
                        CalcRemote rpi = new CalcRemote();
                        try {
                            rpi.run(true, true);
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                    }
                    else if(args[1].equals("-log=replay")) {
                        LogParsserOnline lg = new LogParsserOnline();
                        try {
                            lg.run();
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                    }
                }
                else if (args[0].equals("-user=local")) {
                    if(args[1].equals("-log=rec")) {
                        CalcRemote rpi = new CalcRemote();
                        try {
                            rpi.run(true, false);
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                    }
                    else if(args[1].equals("-log=replay")) {
                        LogParser log = new LogParser("log.txt");
                        log.run();

                    }
                }
            }
            else {
                System.out.println("usage : <-user={[local],remote}> <-log={[none],rec,replay}>");
            }
        }

    }
}
