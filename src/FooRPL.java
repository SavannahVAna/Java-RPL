import java.io.IOException;

public class FooRPL {//comme demandé la classe main va juste lancer la classe qui convient en fonction des arguments passés
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            CalcRPLImproved rpi = new CalcRPLImproved(false, false, false);
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
                        CalcRPLImproved rpi = new CalcRPLImproved(false, true, false);
                        try {
                            rpi.run();
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                        break;
                    case "-user=local":
                        CalcRPLImproved rp = new CalcRPLImproved(false, false, false);
                        try {
                            rp.run();
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                        break;
                    case "-log=rec":
                        CalcRPLImproved rp1 = new CalcRPLImproved(true, false, false);
                        try {
                            rp1.run();
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                        break;
                    case "-log=replay":

                        CalcRPLImproved log = new CalcRPLImproved(false, false, true);
                        log.run();
                        break;

                }

            }
            else if (args.length == 2) {
                if (args[0].equals("-user=remote")) {
                    if(args[1].equals("-log=rec")) {
                        CalcRPLImproved rpi = new CalcRPLImproved(true, true, false);
                        try {
                            rpi.run();
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                    }
                    else if(args[1].equals("-log=replay")) {
                        CalcRPLImproved lg = new CalcRPLImproved(false, true, true);
                        try {
                            lg.run();
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                    }
                    //utilité de replay les logs en utilisateur online : 0
                }
                else if (args[0].equals("-user=local")) {
                    if(args[1].equals("-log=rec")) {
                        CalcRPLImproved rp1 = new CalcRPLImproved(true, false, false);
                        try {
                            rp1.run();
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                    }
                    else if(args[1].equals("-log=replay")) {
                        CalcRPLImproved log = new CalcRPLImproved(false, false, true);
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
