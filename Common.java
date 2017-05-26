
package pharmacy;


public class Common {
    public static final double avarage_count = 20;
     public static double uniform(double mean , double spread)
    {
        
        double cur_sum = 0;
        

        for(int i = 0 ; i< avarage_count ; i++)
        {
            cur_sum += mean + (Math.random()*2 - 1)*spread;
        }

        return cur_sum /avarage_count ;
    }
}
