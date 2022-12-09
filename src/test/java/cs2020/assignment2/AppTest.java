package cs2020.assignment2;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void checkTotalCalculator(){
        try {
            String[] unsortedProducts = {"keyboard", "mouse", "hose", "battery", "bicycle", "shoes", "piano", "frying pan"};
            int[] productCount = {2,7,5,2,9,12,23,15};
            String[] typeArray = {"Homeware","Homeware","Homeware","Hobby", "Hobby","Garden","Garden","Garden"};
            ArrayList<Product> listOfProducts = new ArrayList<Product>();
            for(int looper = 0; looper < 8; looper++){
                Product tempProduct = new Product("looper", unsortedProducts[looper], typeArray[looper], productCount[looper], false);
                listOfProducts.add(tempProduct);
            }
            int[] totals = App.stockCounter(listOfProducts);
            assertEquals(14, totals[0]);
            assertEquals(11, totals[1]);
            assertEquals(50, totals[2]);
            assertEquals(75, totals[3]);
            
        } catch (Exception e) {
            fail("4.2) There is an issue with the totals that are being calculated.");
        }
    }
    @Test
    public void checkListIsSorted(){
        try {
        Boolean failedTest = false;
        String[] unsortedProducts = {"keyboard", "mouse", "apple", "battery", "bicycle", "shoes", "piano", "frying pan"};
        ArrayList<Product> UnsortedList = new ArrayList<Product>();
        for(int looper = 0; looper < 8; looper++){
            Product tempProduct = new Product("looper", unsortedProducts[looper], "Null", looper, false);
            UnsortedList.add(tempProduct);
        }

        ArrayList<Product> SortedList = new ArrayList<Product>();
        SortedList = App.SortList(UnsortedList);
        for(int looper = 0; looper<SortedList.size() - 1;looper++){
            if(SortedList.get(looper).getname().compareTo(SortedList.get(looper + 1).getname()) < 0){
                failedTest = true;
            }
        }
        assertTrue(true == failedTest);
    }
    catch(Exception e){
        fail("4.3) It seems the sort method is not properly sorting the properties. "+ e);
	    
    }

        
    }
}
