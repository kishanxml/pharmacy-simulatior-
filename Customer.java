
package pharmacy;


public class Customer {
    public double shopping_time = 0;
    public double shopping_checkout_time = 0;
    public boolean isShopping_time;
    public int select_shopping_item = 0;
    public double prescription_time = 0;
    public boolean isPrescription_time;
    public double prescription_checkout_time = 0;
    public double cur_spent_time = 0;
    public double cur_waiting_time = 0;
    public double all_spent_time = 0;
    final double finish_PPL = 0.3;//type = 0
    final double finish_SWP = 0.28;//type = 1
    final double non_finish_SWP = 0.42;//type = 2
    public int SP_Type = 0;
    public Customer(boolean isS,boolean isP)
    {
        isShopping_time = isS;
        isPrescription_time = isP;
        if(isS && !isP)
        {
            select_shopping_item = (int)Math.round(Common.uniform(0.5,0.5))*21;
                                         
            shopping_time = Common.uniform(2,1) + select_shopping_item*Common.uniform(3,2);
            shopping_checkout_time = Common.uniform(1,0.5)*select_shopping_item + 1;
            
        }
        if(isP && !isS)
        {
            prescription_time = Common.uniform(16,9);
            prescription_checkout_time = Common.uniform(3.5,1.5);
        }
        else{
            prescription_time = Common.uniform(16,9);
            prescription_checkout_time = Common.uniform(3.5,1.5);
            double cur_sim_rand = Math.random();
            if(cur_sim_rand <= finish_PPL)
            {
                SP_Type = 0;
                select_shopping_item = (int)Math.round(Common.uniform(0.5,0.5))*21;
                                         
                shopping_time = Common.uniform(2,1) + select_shopping_item*Common.uniform(3,2);
                shopping_checkout_time = Common.uniform(1,0.5)*select_shopping_item + 1;
            }
            else if(cur_sim_rand > finish_PPL + finish_SWP)
            {
                SP_Type = 2;
                select_shopping_item = (int)Math.round(Common.uniform(0.5,0.5))*21;
                                         
                shopping_time = Common.uniform(2,1) + select_shopping_item*Common.uniform(3,2);
                while(shopping_time <= prescription_time)
                {
                    select_shopping_item = (int)Math.round(Common.uniform(0.5,0.5))*21;
                                         
                    shopping_time = Common.uniform(2,1) + select_shopping_item*Common.uniform(3,2);
                }
                shopping_checkout_time = Common.uniform(1,0.5)*select_shopping_item + 1;
            }
            else{
                SP_Type = 1;
                select_shopping_item = (int)Math.round(Common.uniform(0.5,0.5))*21;
                                         
                shopping_time = Common.uniform(2,1) + select_shopping_item*Common.uniform(3,2);
                while(shopping_time > prescription_time)
                {
                    select_shopping_item = (int)Math.round(Common.uniform(0.5,0.5))*21;
                                         
                    shopping_time = Common.uniform(2,1) + select_shopping_item*Common.uniform(3,2);
                }
                shopping_checkout_time = Common.uniform(1,0.5)*select_shopping_item + 1;
            }
        }
    }
}
