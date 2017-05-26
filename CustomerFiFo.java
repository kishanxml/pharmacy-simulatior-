
package pharmacy;

import java.util.List;
import java.util.Vector;



public class CustomerFiFo {
    public int count;
    public Vector<Customer> m_FiFo;
    @SuppressWarnings("UseOfObsoleteCollectionType")
    public CustomerFiFo()
    {
        
        m_FiFo = new Vector<>();
        count = 0;
    }
    public void push(Customer ctm)
    {
        count ++ ;
        m_FiFo.add(ctm);
    }
    public Customer pop()
    {
        count--;
        return m_FiFo.remove(0);
       
    }
    public Customer getFirst()
    {
        return m_FiFo.get(0);
    }
    public void all_increase_time()
    {
        for(int i =0;i<m_FiFo.size();i++)
        {
            m_FiFo.elementAt(i).cur_waiting_time++;
            m_FiFo.elementAt(i).all_spent_time ++;
        }
        
    }
    public void increase_first_spent_time()
    {
        m_FiFo.elementAt(0).cur_spent_time++;
    }
}
