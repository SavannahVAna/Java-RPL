public class FooRPL {
    public static void main(String[] args) {
        CalcRPLImproved rpi = new CalcRPLImproved();
        try {
            rpi.run();
        } catch (Exception e){
            System.out.println("une erreur s'est produite");
        }
    }
}
