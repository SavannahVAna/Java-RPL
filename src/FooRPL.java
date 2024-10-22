import java.io.IOException;

public class FooRPL {
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
                            rpi.run();
                        } catch (Exception e){
                            System.out.println("une erreur s'est produite");
                        }
                        break;
                    case "-user=local":
                        CalcRPLImproved rp = new CalcRPLImproved(false);
                        try {
                            rp.run();
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
                        //TODO replay log
                        LogParser log = new LogParser("log.txt");
                        log.run();
                        break;

                }

            }
            else if (args.length == 2) {
                if (args[0].equals("-user=remote")) {
                    if(args[1].equals("log=rec")) {

                    }
                    else if(args[1].equals("log=replay")) {

                    }
                }
                else if (args[0].equals("-user=local")) {
                    if(args[1].equals("log=rec")) {

                    }
                    else if(args[1].equals("log=replay")) {

                    }
                }
            }
            else {
                System.out.println("usage : <-user={[local],remote}> <-log={[none],rec,replay}>");
            }
        }

    }
}
