package cs2020.assignment2;
public class Product {
    String productid;
    String type;
    int quantity;
    String name;
    boolean nextday;
    public Product(String incProductId, String incName, String incType, int incQuantity, Boolean incNextDay){
        productid = incProductId;
        type = incType;
        quantity = incQuantity;
        name = incName;
        nextday = incNextDay;


    }
    public String getnextday(){
        if(nextday == true){
            return "yes";
        }
        else{
        return "no";
        }
    }

    public void setnextday(Boolean nextDay){
        nextday = nextDay;
    }
    public String getproductid(){
        return productid;
    }
    public void setproductid(String newproductid){
        productid = newproductid;
    }
    
    public String gettype(){
        return type;
    }
    public int getTypeAsInt(){
        if(type.equals("Select Type")){
            return 0;
        }
        else if(type.equals("Homeware")){
            return 1;
        }
        else if(type.equals("Hobby")){
            return 2;
        }
        else if(type.equals("Garden")){
            return 3;
        }
        else{
        return 0;
        }
    }
    public void settype(String newtype){
        type = newtype;
    }
    
    public int getquantity(){
        return quantity;
    }
    public void setquantity(int newquantity){
        quantity = newquantity;
    }
    
    public String getname(){
        return name;
    }
    public void setname(String newname){
        name = newname;
    }
    
}
