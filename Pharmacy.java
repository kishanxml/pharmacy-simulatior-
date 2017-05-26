
package pharmacy;

public class Pharmacy {

    
    public static void main(String[] args) {
        
        Pharmacy app = new Pharmacy();
        app.printWelcome();
        simulate sim_engine = new simulate();
        sim_engine.simulateStart();
        
        System.out.println("**************       print all            *************************");
        
        System.out.println("Customers for only do shopping-----> count:"+sim_engine.all_only_shopping_time_customers+" avarage time: "+sim_engine.all_only_shopping_times);
        System.out.println("Customers for only have prescriptions-----> count:"+sim_engine.all_only_prescription_time_customers+" avarage time: "+sim_engine.all_only_prescription_times);
        System.out.println("Customers for both shopping and prescriptions-----> count:"+sim_engine.both_shopping_presription_customers+" avarage time: "+sim_engine.both_shopping_prescription_times);
    }
    public void printWelcome()
    {
        System.out.println("*******************************************************************");
        System.out.println("***************                           *************************");
        System.out.println("***************           welcome         *************************");
        System.out.println("***************                           *************************");
        System.out.println("*******************************************************************");
    }
}
