
package pharmacy;
import java.util.ArrayList;
import java.util.Vector;

public class simulate {
    public int cur_1 = 0;
    public int cur_2 = 0;
    public int cur_3 = 0;
    public final int avarage_count = 10;
    public final double avarge_generate_count = 0.25;
    public final double shopping_time_only = 0.4;
    public final double prescription_time_only = 0.35;
    public final double both_shopping_prescription = 0.25;
    public final int stepCount = 180;//3 hours
    public final int stepforend = 30;
    public CustomerFiFo m_Shopper_Line;
    public CustomerFiFo m_Prescription_Line;
    public CustomerFiFo m_Pre_Pick_Line;
    public Vector<Customer> m_SWA;
    public Vector<Customer> m_PWA;
    public Vector<Customer> m_BusyCustomer;
    //output values
    public int cur_time = 0;
    public int all_only_shopping_time_customers = 0;
    public int all_only_prescription_time_customers = 0;
    public int both_shopping_presription_customers = 0;
    public double all_only_shopping_times = 0;
    public double all_only_prescription_times = 0;
    public double both_shopping_prescription_times = 0;
    public simulate()
    {
        m_Shopper_Line = new CustomerFiFo();
        m_Prescription_Line = new CustomerFiFo();
        m_Pre_Pick_Line = new CustomerFiFo();
        m_SWA = new Vector<>();
        m_PWA = new Vector<>();
        m_BusyCustomer = new Vector<>();
    }
   
    
    float fact(int k)
    {

        if(k==0)return 1;
        return (float)k*fact(k-1);
    }
    float Poisson(int k,double ramda)
    {
        double cur_val = Math.pow(ramda,k)/(fact(k)*Math.exp(ramda));
        return (float)cur_val;
    }
   
    boolean isComingCustomer()
    {
        double cur_sim_rand = Math.random();

	double cur_dst = 0;
	int cur_index = 0;
	do {

		cur_dst += Poisson(cur_index, avarge_generate_count);
		cur_index++;
		if (cur_index > 1)return true;
	} while (cur_sim_rand > cur_dst);
	return false;
        
    }
   
    Customer decideType()
    {
        double cur_sim_rand = Math.random();
        if(cur_sim_rand <= shopping_time_only)
        {
            cur_1 ++;
            return new Customer(true,false);
        }
        else if(cur_sim_rand > shopping_time_only && cur_sim_rand <= (shopping_time_only + prescription_time_only))
        {
            cur_2 ++;
            return new Customer(false, true);
        }
        else{
            cur_3++;
            return new Customer(true,true);
        }
    }
    
    void pushCustomer(Customer customer)
    {
        if(customer.isShopping_time && (!customer.isPrescription_time))
        {
            if(customer.select_shopping_item == 0)
            {
                
                m_Shopper_Line.push(customer);
            }
            else{
                m_SWA.add(customer);
            }
        }
        else if((!customer.isShopping_time) && customer.isPrescription_time)
        {
             m_PWA.add(customer);
        }
        else{
            
            switch(customer.SP_Type)
            {
                case 0:
                    m_PWA.add(customer);
                    break;
                case 1:
                    m_SWA.add(customer);
                    break;
                case 2:
                    m_BusyCustomer.add(customer);
                    break;
            }
        }
    }
    
    void checkArrays()
    {
        
        for(int i = 0; i<m_SWA.size();i++)
        {
            if(m_SWA.get(i).cur_spent_time >= m_SWA.get(i).shopping_time)
            {
                if(m_SWA.get(i).isShopping_time && !m_SWA.get(i).isPrescription_time)
                {
                    m_SWA.elementAt(i).cur_spent_time = 0;
                    m_SWA.elementAt(i).cur_waiting_time = 0;
                    m_SWA.elementAt(i).all_spent_time++;
                    m_Shopper_Line.push(m_SWA.get(i));
                    m_SWA.removeElementAt(i);
                }
                else if(m_SWA.get(i).SP_Type == 0)
                {
                    m_SWA.elementAt(i).cur_spent_time = 0;
                    m_SWA.elementAt(i).cur_waiting_time = 0;
                    m_SWA.elementAt(i).all_spent_time++;
                    m_Shopper_Line.push(m_SWA.get(i));
                    m_SWA.remove(i);
                }
                else
                {
                    m_SWA.elementAt(i).cur_spent_time = 0;
                    m_SWA.elementAt(i).cur_waiting_time = 0;
                    m_SWA.elementAt(i).all_spent_time++;
                    m_Pre_Pick_Line.push(m_SWA.elementAt(i));
                    m_SWA.remove(i);
                }
            }
            else{
                m_SWA.elementAt(i).all_spent_time++;
                m_SWA.elementAt(i).cur_spent_time++;
               
            }
        }
        
        for(int i = 0;i<m_PWA.size();i++)
        {
            m_PWA.elementAt(i).all_spent_time ++;
            if(m_PWA.elementAt(i).cur_spent_time >= m_PWA.elementAt(i).prescription_time)
            {
                m_PWA.elementAt(i).cur_spent_time = 0;
                m_Pre_Pick_Line.push(m_PWA.elementAt(i));
                m_PWA.remove(i);
            }
            else
            {
                m_PWA.elementAt(i).cur_spent_time ++;
            }
        }
        
        for(int i = 0;i<m_BusyCustomer.size();i++)
        {
            m_BusyCustomer.get(i).cur_waiting_time ++;
            if(m_BusyCustomer.get(i).cur_spent_time >= m_BusyCustomer.get(i).prescription_time)
            {
                m_BusyCustomer.elementAt(i).SP_Type = 0;
                m_BusyCustomer.elementAt(i).shopping_time = m_BusyCustomer.elementAt(i).shopping_time - m_BusyCustomer.elementAt(i).prescription_time;
                m_BusyCustomer.elementAt(i).cur_spent_time = 0;
                m_Pre_Pick_Line.push(m_BusyCustomer.elementAt(i));
                m_BusyCustomer.remove(i);
            }
            else
            {
                m_BusyCustomer.get(i).cur_spent_time ++;
            }
                
        }
        
        if(m_Shopper_Line.count > 0)
        {
            if(m_Shopper_Line.getFirst().cur_spent_time >= m_Shopper_Line.getFirst().shopping_checkout_time)
            {
                increaseAvarage(m_Shopper_Line.pop());
                m_Shopper_Line.all_increase_time();
            }
            else
            {
                m_Shopper_Line.all_increase_time();
                m_Shopper_Line.increase_first_spent_time();
            }
                
        }
        
        
        if(m_Pre_Pick_Line.count>0)
        {
            m_Pre_Pick_Line.all_increase_time();
            if(m_Pre_Pick_Line.getFirst().cur_spent_time >= m_Pre_Pick_Line.getFirst().prescription_checkout_time )
            {
             if(!m_Pre_Pick_Line.getFirst().isShopping_time && m_Pre_Pick_Line.getFirst().isPrescription_time)
                {
                    increaseAvarage(m_Pre_Pick_Line.pop());
                }
                else if(m_Pre_Pick_Line.getFirst().SP_Type == 0)
                {
                    Customer cur_customer = m_Pre_Pick_Line.getFirst();
                    cur_customer.cur_spent_time = 0;
                    cur_customer.cur_waiting_time = 0;
                    m_SWA.add(cur_customer);
                    m_Pre_Pick_Line.pop();
                }
                else{
                    Customer cur_customer = m_Pre_Pick_Line.getFirst();
                    cur_customer.cur_spent_time = 0;
                    cur_customer.cur_waiting_time = 0;
                    m_Shopper_Line.push(cur_customer);
                    m_Pre_Pick_Line.pop();
                }
            }
            else{
                m_Pre_Pick_Line.increase_first_spent_time();
            }
        }
        
    }
    
    void increaseAvarage(Customer customer)
    {
        if(customer.isShopping_time && !customer.isPrescription_time)
        {
            all_only_shopping_time_customers ++;
            all_only_shopping_times += customer.all_spent_time;
        }
        else if(!customer.isShopping_time && customer.isPrescription_time)
        {
            all_only_prescription_time_customers++;
            all_only_prescription_times+= customer.all_spent_time;
        }
        else
        {
            both_shopping_presription_customers++;
            both_shopping_prescription_times+= customer.all_spent_time;
        }
    }
    public void step()
    {
        
       
        checkArrays();
        
        if(isComingCustomer()){
            Customer new_customer = decideType();
            pushCustomer(new_customer);
        }
        
    }
    void StepforEnd()
    {
        checkArrays();
    }
    public void simulateStart()
    {
        for(int i = 0;i<stepCount;i++)
        {
            step();
        }
        for(int i = 0;i<stepCount;i++)
        {
            StepforEnd();
        }
        try{
            all_only_shopping_times = all_only_shopping_times/all_only_shopping_time_customers;
            all_only_prescription_times = all_only_prescription_times/all_only_prescription_time_customers;
            both_shopping_prescription_times = both_shopping_prescription_times/both_shopping_presription_customers;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
    }
}
