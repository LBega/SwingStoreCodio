package cs2020.assignment2;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;

import java.util.*;
import java.util.List;

import javax.swing.*;

import com.opencsv.CSVWriter;
public class App 
{
    boolean allowItemListChange = false; // This variable is used to allow some code in the ItemList action listener to run on occasion
    Boolean itemSelected = false;
    ArrayList<Product> productList = new ArrayList<>();
    
    App(){
        
        JFrame ProductListGUI = new JFrame();
        ProductListGUI.setSize(800,600);
             
        ProductListGUI.setTitle("<Leonardo Bega>:");

        ProductListGUI.setLayout(new BorderLayout());
        JPanel LeftPanel = new JPanel();
        JPanel RightPanel = new JPanel();
        JPanel SouthPanel = new JPanel();
        JPanel NorthPanel = new JPanel();

        ProductListGUI.add(LeftPanel, BorderLayout.CENTER);
        LeftPanel.setSize(400,550);
        //LeftPanel.setBackground(Color.black);
        LeftPanel.setLayout(null);
        LeftPanel.setVisible(true);
        LeftPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));

        ProductListGUI.add(RightPanel, BorderLayout.EAST);
        RightPanel.setLayout(null);
        RightPanel.setVisible(true);
        //RightPanel.setBackground(Color.black);
        RightPanel.setPreferredSize(new Dimension(200,500));

        ProductListGUI.add(SouthPanel, BorderLayout.SOUTH); 
        SouthPanel.setLayout(null);
        SouthPanel.setVisible(true);
      
        SouthPanel.setPreferredSize(new Dimension(800,100));

    
        NorthPanel.setLayout(null);
        NorthPanel.setVisible(true);
    
        DefaultListModel listOfItem = new DefaultListModel<>();

        JList ItemList = new JList<>(listOfItem);
        JScrollPane scrollPane = new JScrollPane(ItemList);
        scrollPane.setBounds(0,0,200,500);
        RightPanel.add(scrollPane);
        ItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
      

        JLabel ProductIDLabel = new JLabel();
        ProductIDLabel.setText("ProductID");
        ProductIDLabel.setBounds(20,50, 80,20);
        LeftPanel.add(ProductIDLabel);
        
        JTextField ProductIDField = new JTextField();
        ProductIDField.setBounds(100 , 50, 230,20);
        ProductIDField.setText(generateUuid().toString());
        LeftPanel.add(ProductIDField);
        

        JLabel NameLabel = new JLabel();
        NameLabel.setText("Name");
        NameLabel.setBounds(20,85, 80,20);
        LeftPanel.add(NameLabel);
        //BorderLayout.se(Color.BLACK);

        JTextField nameField = new JTextField();
        nameField.setBounds(100 , 85, 120,20);
        LeftPanel.add(nameField);

        JLabel ItemTypeLabel = new JLabel();
        ItemTypeLabel.setText("Item type");
        ItemTypeLabel.setBounds(20, 120,80,20);
        LeftPanel.add(ItemTypeLabel);
        
        String[] ComboBoxText = {"Select Type", "Homeware", "Hobby", "Garden"};
        JComboBox ItemTypeField = new JComboBox<>(ComboBoxText);
        ItemTypeField.setBounds(100 , 120, 120,20);
        LeftPanel.add(ItemTypeField);


        JLabel QuantityLabel = new JLabel();
        QuantityLabel.setText("Quantity");
        QuantityLabel.setBounds(20,155, 80,20);
        LeftPanel.add(QuantityLabel);

        JTextField quantityField = new JTextField();
        quantityField.setBounds(100 , 155, 120,20);
        LeftPanel.add(quantityField);

        JCheckBox DeliveryCB = new JCheckBox();
        DeliveryCB.setText("Available for Next Day Delivery");
        DeliveryCB.setBounds(20,190,200,20);
        LeftPanel.add(DeliveryCB);

        JButton newItemButton = new JButton();
        newItemButton.setBounds(200,25, 100,20);
        newItemButton.setText("New Item");
        SouthPanel.add(newItemButton);
        newItemButton.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                ProductIDField.setText(generateUuid().toString());
                nameField.setText(null);
                ItemTypeField.setSelectedIndex(0);           
                DeliveryCB.setSelected(false);
                quantityField.setText(null);
                itemSelected = false;
            }
        });
       
        JButton SaveButton = new JButton();
        SaveButton.setBounds(320,25, 80 ,20);
        SaveButton.setText("Save");
        SouthPanel.add(SaveButton);
        
    
        JButton DeleteButton = new JButton();
        DeleteButton.setBounds(630,25,130,20);
        DeleteButton.setEnabled(false);
        DeleteButton.setText("Delete Selected");
        SouthPanel.add(DeleteButton);
        DeleteButton.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                int deleteIndex;
                deleteIndex = ItemList.getSelectedIndex();
                allowItemListChange = true;

                JFrame removeItemFrame = new JFrame();
                removeItemFrame.setVisible(true);
                removeItemFrame.setSize(400,250);
                removeItemFrame.setLocation(400, 200);
                removeItemFrame.setTitle("Select an Option");
                removeItemFrame.setLayout(null);  

                JLabel sureLabel = new JLabel();
                sureLabel.setText("Are you sure?");
                sureLabel.setBounds(150, 50, 100, 20 );
                removeItemFrame.add(sureLabel);

                JButton cancelButton = new JButton();
                cancelButton.setText("Cancel");
                cancelButton.setBounds(35, 150, 90, 20);
                removeItemFrame.add(cancelButton);
                cancelButton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        removeItemFrame.dispose();
                    }
                    });

                JButton noButton = new JButton();
                noButton.setText("No");
                noButton.setBounds(135, 150, 90, 20);
                removeItemFrame.add(noButton);
                noButton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                    removeItemFrame.dispose();
                    }
                    });

                JButton yesButton = new JButton();
                yesButton.setText("Yes");
                yesButton.setBounds(235, 150, 90, 20);
                removeItemFrame.add(yesButton);
                yesButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    ItemList.clearSelection();
                    productList.remove(deleteIndex);
                    listOfItem.remove(deleteIndex);
                    ProductIDField.setText("");
                    nameField.setText("");
                    ItemTypeField.setSelectedIndex(0);
                    DeliveryCB.setSelected(false);
                    quantityField.setText("");

                    removeItemFrame.dispose();
                    allowItemListChange = false;
                }
                });

                if(productList.size() == 0){
                    DeleteButton.setEnabled(false);
                }
                
            }
        });
        SaveButton.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                if(itemSelected.equals(true)){
                    try{
                    productList.get(ItemList.getSelectedIndex()).setquantity(Integer.parseInt(quantityField.getText()));
                    listOfItem.setElementAt(productList.get(ItemList.getSelectedIndex()).getname() + " (" + productList.get(ItemList.getSelectedIndex()).getquantity() +")", ItemList.getSelectedIndex());
                }
             catch (Exception f) {
                JOptionPane.showMessageDialog(ProductListGUI, "Quantity must be a whole non-negative number", "Error", JOptionPane.ERROR_MESSAGE);
           
            }
                }
                else{
         
                boolean sameName = false;
                for(int looper = 0; looper < productList.size(); looper++){
                    if(productList.get(looper).getname().equals(nameField.getText())){
                        sameName = true;
                    }
                }
                if(sameName == true){
                    JOptionPane.showMessageDialog(ProductListGUI, "The name of the item must not be the same as an existing item", "Error", JOptionPane.ERROR_MESSAGE);                 
                }
                else if(nameField.getText().equals("")){
                    JOptionPane.showMessageDialog(ProductListGUI, "The name of the item must not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(ItemTypeField.getSelectedItem().equals("Select Type")){
                    JOptionPane.showMessageDialog(ProductListGUI, "You must select an item type", "Error", JOptionPane.ERROR_MESSAGE);
                    
                }
                else{
                    try {
                        if(Integer.parseInt(quantityField.getText()) <= 0){
                            JOptionPane.showMessageDialog(ProductListGUI, "Quantity must be a whole non-negative number", "Error", JOptionPane.ERROR_MESSAGE);
                   
                        }
                        else{   
                        Product tempProduct = new Product(ProductIDField.getText(), nameField.getText() , ItemTypeField.getSelectedItem().toString(), Integer.parseInt(quantityField.getText()),DeliveryCB.isSelected() );
                        productList.add(tempProduct);
                        productList = SortList(productList);
                        //JList ItemList = new  JList(listOfItem);
                        allowItemListChange = true;
                        listOfItem.removeAllElements();
                        //handle when it goes to the thing evnet
                        for(int looper = 0; looper < productList.size(); looper++){
                           listOfItem.addElement(productList.get(looper).getname() + " (" + productList.get(looper).getquantity() + ")"); 
                         
                        }
                        allowItemListChange = false;
                        
                        DeleteButton.setEnabled(true);
                    }

                    } catch (Exception f) {
                        JOptionPane.showMessageDialog(ProductListGUI, "Quantity must be a whole non-negative number1", "Error", JOptionPane.ERROR_MESSAGE);
                   
                    }
                }
            }
                
            }
            
        });
    JPopupMenu popup = new JPopupMenu();
    JMenuItem deleteItem = new JMenuItem();
    deleteItem.setText("Delete");
    popup.add(deleteItem);
    //popup.show(LeftPanel,200,200);
    
        //ItemList Eventd

        ItemList.addMouseListener(new MouseInputAdapter() {
            
            public void mousePressed(MouseEvent e){
                allowItemListChange = true;
                if(SwingUtilities.isRightMouseButton(e)){
                    popup.setSize(100,100);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                    
                    deleteItem.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            
                            productList.remove(ItemList.getSelectedIndex());
                            listOfItem.remove(ItemList.getSelectedIndex());

                            ProductIDField.setText("");
                            nameField.setText("");
                            ItemTypeField.setSelectedIndex(0);
                            DeliveryCB.setSelected(false);
                            quantityField.setText("");
        
                            
                        }
                        });
                    
                }
                allowItemListChange = false;
            }
        });
        
        ItemList.addListSelectionListener(new ListSelectionListener() { // This action listener can be called by many events
            public void valueChanged(ListSelectionEvent le) {
                
                if(allowItemListChange == false){ // When this variable is set to be false it allows the list to update when an index is selected. When something else interacts with the list it is set to true and this part is not called

                ProductIDField.setText(productList.get(ItemList.getSelectedIndex()).getproductid());
                ItemTypeField.setSelectedIndex(productList.get(ItemList.getSelectedIndex()).getTypeAsInt());
                if(productList.get(ItemList.getSelectedIndex()).getnextday().equals("yes")){
                    DeliveryCB.setSelected(true);
                }
                else{
                    DeliveryCB.setSelected(false);
                }
                nameField.setText(productList.get(ItemList.getSelectedIndex()).getname());
                quantityField.setText(Integer.toString(productList.get(ItemList.getSelectedIndex()).getquantity()));
                itemSelected = true;
                }
            }
          });

        JMenu menu = new JMenu("Actions");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItem = new JMenuItem("About");
        JMenuItem menuItem2 = new JMenuItem("Import Data");
        JMenuItem menuItem3 = new JMenuItem("Inventory");
        JMenuItem menuItem4 = new JMenuItem("Export to CSV");
        menu.add(menuItem);
        menu.add(menuItem2);
        menu.add(menuItem3);
        menu.add(menuItem4);
        menuBar.add(menu);
        
        ProductListGUI.setJMenuBar(menuBar);
        menuBar.validate();

        menuItem.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                JFrame aboutFrame = new JFrame();
                JLabel aboutLabel = new JLabel();
                JButton aboutOkButton = new JButton();
                aboutFrame.setTitle("About");
                aboutLabel.setText("Assignment 2 App v.0.1");
                aboutLabel.setBounds(100, 100, 200, 20);
                aboutOkButton.setText("OK");
                aboutOkButton.setBounds(300, 230, 70, 20);
                
                aboutOkButton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        aboutFrame.dispose();
                    }
                });

                aboutFrame.add(aboutLabel);
                aboutFrame.add(aboutOkButton);
                aboutFrame.setSize(400,300);
                aboutFrame.setLayout(null);
                aboutFrame.setVisible(true);
                
            }
        }); 
     menuItem2.addActionListener(new ActionListener(){

        public void actionPerformed(ActionEvent e){
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException d) {
                d.printStackTrace();
                return;
            }
        
            Connection connection = null;
        
            try {
                connection = DriverManager
                .getConnection("jdbc:sqlite:products.db","","");
               
        
            } catch (SQLException m) {
                m.printStackTrace();
                return;
            }
        
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM products";
            ResultSet rs = stmt.executeQuery(query);
           
            String unmergableProducts = "";
             while ( rs.next()) {
                String dbProductID = rs.getString("productID");
                String dbName = rs.getString("name");
                int dbQuantity = rs.getInt("quantity");
                String dbType = rs.getString("type");
                String dbnextDaystr = rs.getString("nextDay");
                boolean convertNextdayToBoolean;
                if(dbnextDaystr.equals("yes")){
                    convertNextdayToBoolean = true;
                }
               else{
                convertNextdayToBoolean = false;
               }
               Product tempProduct = new Product(dbProductID, dbName, dbType, dbQuantity, convertNextdayToBoolean);
               int matchIndex = checkTypeMismatchList(productList,tempProduct);
               if(matchIndex == -1){
                productList.add(tempProduct);
               }
               else if(productList.get(matchIndex).gettype().equals(tempProduct.gettype())){
                productList.get(matchIndex).setquantity(tempProduct.getquantity() + productList.get(matchIndex).getquantity());
                if(productList.get(matchIndex).getnextday().equals("yes") || tempProduct.getnextday().equals(("yes")) ){ //checks if either of the identical products have next day delivery
                    productList.get(matchIndex).setnextday(true); //if either return true the product is set to have next day delivery
                }
               }
               else{
                unmergableProducts = unmergableProducts + " " + tempProduct.getname();
                
               }
                
            }
            allowItemListChange = true;
            productList = SortList(productList);
                        allowItemListChange = true;
                        listOfItem.removeAllElements();
                        //handle when it goes to the thing evnet
                        for(int looper = 0; looper < productList.size(); looper++){
                           listOfItem.addElement(productList.get(looper).getname() + " (" + productList.get(looper).getquantity() + ")"); 
                         
                        }
                        allowItemListChange = false;
            connection.close();
           menuItem2.setEnabled(false);
           
         
            if(unmergableProducts.length() != 0){ // checks that there were some entries that could not be added
                JOptionPane.showMessageDialog(ProductListGUI, "The following database entries could not be added" + "\n" + unmergableProducts, "Error", JOptionPane.ERROR_MESSAGE);
           
            }
               
            
        } catch (SQLException b) {
            System.out.println(b.getMessage());
        }
            
        }
     });
        menuItem3.addActionListener(new ActionListener(){ // opens inventory tab
            
            public void actionPerformed(ActionEvent e){
                if(productList.size() > 0) // makes sure there are some products on the list before showing the inventory page
                {int homewareCount = 0;
                    int hobbyCount = 0;
                    int gardenCount = 0;
                    int stockTotal = 0;
    
                int[] productCount = stockCounter(productList);
                homewareCount = productCount[0];
                hobbyCount = productCount[1];
                gardenCount = productCount[2];
                stockTotal = productCount[3];
                
                JFrame inventoryFrame = new JFrame();
                JTable inventoryTable = new JTable(4,2);
                JLabel inventoryTotal = new JLabel();
                JLabel tableLabel = new JLabel();
                JLabel firstItem = new JLabel();
                JLabel finalItem = new JLabel();
                JButton inventoryOk = new JButton();

                inventoryFrame.setTitle("Inventory");
                tableLabel.setText("Table of values");
                tableLabel.setBounds(20,10,200,20);
                inventoryTable.setBounds(20,30,250,64);
                inventoryTable.setValueAt("Item type", 0, 0);
                inventoryTable.setValueAt("Stock Count",0,1);
                inventoryTable.setValueAt("Homeware", 1, 0);
                inventoryTable.setValueAt(Integer.toString(homewareCount),1,1);
                inventoryTable.setValueAt("Hobby", 2, 0);
                inventoryTable.setValueAt(Integer.toString(hobbyCount),2,1);
                inventoryTable.setValueAt("Garden", 3, 0);
                inventoryTable.setValueAt(Integer.toString(gardenCount),3,1);
                
                
                inventoryTotal.setText("Total Inventory: " +Integer.toString(stockTotal));
                inventoryTotal.setBounds(20 , 100 , 200 , 20);

                firstItem.setText("The first item is " + productList.get(0).getname());
                firstItem.setBounds(20, 120,200,20);
                finalItem.setText("The final item is " + productList.get(productList.size() - 1).getname());
                finalItem.setBounds(20,140,200,20);


                inventoryOk.setText("OK");
                inventoryOk.setBounds(200, 140, 70, 20);
                
                inventoryOk.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        inventoryFrame.dispose();
                    }
                });

                inventoryFrame.add(tableLabel);
                inventoryFrame.add(inventoryTotal);
                inventoryFrame.add(firstItem);
                inventoryFrame.add(finalItem);
                inventoryFrame.add(inventoryTable);
                inventoryFrame.add(inventoryOk);
                inventoryFrame.setSize(300,200);
                inventoryFrame.setLayout(null);
                inventoryFrame.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(ProductListGUI, "Please enter a product before using this option.", "Error", JOptionPane.ERROR_MESSAGE);
                
            }
        }
     
        }); 
     
        menuItem4.addActionListener(new ActionListener(){ // menuItem4 is the export to csv
            
            public void actionPerformed(ActionEvent e){
                
                try {
                    File csvFile = new File("products.csv"); // this is where the file with exported products is
                    FileWriter outputfile = new FileWriter(csvFile);
                    CSVWriter writer = new CSVWriter(outputfile);
                    String[] header = {"productID","name","quantity","nextDay","type"};
                    writer.writeNext(header);
                    for(int looper = 0; looper < productList.size();looper++){
                        String[] tempStrings = {productList.get(looper).getproductid(),productList.get(looper).getname(),Integer.toString(productList.get(looper).getquantity()),(productList.get(looper).getnextday())};
                        writer.writeNext(tempStrings);
                    }
                    writer.close();
                    JFrame completedCSV = new JFrame();
                    JLabel csvLabel = new JLabel();
                JButton csvOkButton = new JButton();
                completedCSV.setTitle("CSV Export");
                csvLabel.setText("Export Completed");
                csvLabel.setBounds(125, 100, 200, 20);
                csvOkButton.setText("OK");
                csvOkButton.setBounds(300, 230, 70, 20);
                
                csvOkButton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        completedCSV.dispose();
                    }
                });
                completedCSV.add(csvLabel);
                completedCSV.add(csvOkButton);
                completedCSV.setSize(400,300);
                completedCSV.setLayout(null);
                completedCSV.setVisible(true);

                } catch (IOException e1) {
                    
                    e1.printStackTrace();
                }

                
            }
        }); 

        

        ProductListGUI.setVisible(true);
        
        SwingUtilities.updateComponentTreeUI(ProductListGUI); // updates the ProductListGUI so there are no issues when it is displayed
    }
    public UUID generateUuid(){ // generates the UUID
        UUID TempProductUuid = UUID.randomUUID();
        return TempProductUuid;
        
    }

    public static int checkTypeMismatchList(ArrayList<Product> listofProducts, Product prod){ // checks is there is a type mismatch
        int matchIndex = -1; // by default matchindex is set to -1. This is returned if there is no match with the names. 
        for(int looper = 0; looper < listofProducts.size(); looper++){ //loops through all products in listofProducts
            if(listofProducts.get(looper).getname().equals(prod.getname())){
                matchIndex = looper; // if there is a match found it is set to be equal to index that matches the conflict in the listofProducts
            }

        }

        return matchIndex; // value is returned
    }
    public static ArrayList<Product> SortList(ArrayList<Product> listOfProducts){ //takes in an arraylist of products and returns a sorted version of that list
        ArrayList<Product> tempListOfProducts = new ArrayList<Product>(listOfProducts.size());
        tempListOfProducts = listOfProducts;
        Product tempProduct = new Product(null, null, null, 0,false);
        Boolean sorted = false; //reduces the amount of loops that are completed
        do{
            sorted = true;
            for(int looper = 0;looper < tempListOfProducts.size() - 1;looper++){ //loops through full size of the list
                if(tempListOfProducts.get(looper).getname().compareTo(tempListOfProducts.get(looper + 1).getname()) > 0){ //if the first name is greater than the second they are switched using the tempProduct
                tempProduct = tempListOfProducts.get(looper);
                tempListOfProducts.set(looper, tempListOfProducts.get(looper + 1));
                tempListOfProducts.set(looper + 1, tempProduct);
                sorted = false;
                }
            }
        }
        while(!sorted);

        return tempListOfProducts;
    }
    
    public static int[] stockCounter(List<Product> productList){ // Calculates the individual totals of each of the stocks and the sum of all the stock
        int homewareCount = 0;
        int hobbyCount = 0;
        int gardenCount = 0;
        
        for(int looper = 0; looper<productList.size();looper++){ // loops through all products and increments the corresponding counter
            switch (productList.get(looper).gettype()) {
                case "Homeware":
                    homewareCount+=productList.get(looper).getquantity();
                
                    break;
                case "Hobby":
                hobbyCount+=productList.get(looper).getquantity();
                
                break;
                case "Garden":
                gardenCount+=productList.get(looper).getquantity();
                break;
                default:
                break;
            }
        }
        int[] totals = {homewareCount, hobbyCount, gardenCount, homewareCount + hobbyCount + gardenCount}; 
        

        return totals; // returns all the individual sums alongside the total sum
    }
    public static void main( String[] args )
    {
        new App();
    }
}
