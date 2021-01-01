package com.codedifferently.hurt;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class Main {
    //how are we going to store the products?  LinkedHashMap
    static Map<String , Map<String, Integer>> products = new LinkedHashMap<>();

    //how are we going to keep track of error? We need to keep track of initial length and final of the array
    static int initialSze = 0;
    static int finalSze = 0;
    static PrintWriter txtFile ;
    static Integer fileCounter = 1;

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        System.out.println(output);
        //here we need an arrayList to use and where we will do the operations
        ArrayList<String> finalized = trimList(convertToArray(output));
        populateLinkedMap(finalized);
        printLinkedHashMap(products);
        //Trying to convert to output
        outputFileConverter(formatOutput());
    }

    /* First method: Turn the product string into an arrayList seperated 
      by the delimiter [^. @....etc] */

    private static ArrayList<String> convertToArray(String str){
        //What do we need to do to the String before we operate on it?
        //Remove the special characters except for the # , . and / (why? we need these
        // to determine the price and delimination and date) 
        //Replace them with a " "
        String string = str.replaceAll("[^0-9a-zA-Z-#./]+", " "); 


        //Convert everything into lowerCase

        string = string.toLowerCase();

        //Intialize return arrayList as a list splited by the ## 
        ArrayList<String> list = new ArrayList<>(Arrays.asList(string.split("##")));


        //Operate on the arrayList so that we remove the "name" signature from each element
        for(int i = 0; i < list.size(); i++){
            //naMe COokIes price 2.25 type Food expiration 3/22/2016
            System.out.println("Element " + i + " in list before edits is " + list.get(i).toString());
            list.set(i, list.get(i).substring(5));
            //COokIes price 2.25 type Food expiration 3/22/2016
            System.out.println("Element " + i + " in list is " + list.get(i).toString());
        }

        //set the intialSze to the size of the arrayList (This will be the true count of products)
        initialSze = list.size(); 

        //return the arrayList
        return list; 
    }

    /* Second method:Remove the list elements that are not starting with the name of the product. This denotes an 
    error */

    private static ArrayList<String> trimList(ArrayList<String> arr){
        int count = 0;
        //Loop through each of the elements in arr
        ArrayList<String> trimmedList = new ArrayList<>();
        for(int i = 0; i < arr.size(); i++){
             //In each list element, create a string array split by the " " 
            //(This separates the element into smaller elements like price, type, expiration etc)
            String[] items = arr.get(i).split(" ");
                //if the first element in the String array is equal to price remove it
                if(!items[0].equals("price") && !items[2].equals("type")){
                    trimmedList.add(arr.get(i));
                    System.out.println("Element " + count + " in trimmedList is " + trimmedList.get(count).toString());
                    count++;
                }  
        }
        //set the finalSze to the size of the new list
        finalSze = trimmedList.size();
        //return the list
        return trimmedList;
    }


    /*Third method: Put the information into a LinkedHashmap, this will help us count everything */
    private static void populateLinkedMap(ArrayList<String> arr){
        //loop through each string in the list
        for(String item : arr){
           //split the list into categories 
           String[] items = item.split(" ");
           //Then check the c00kie input replace all names that have 0 with o
           String productName = items[0].replaceAll("0", "o"); //eg cookies, milk bread etc
           //add the elements to the map
           // Map: 
                //category -> 
                           //3rd category (price)  : count 
                           //3rd category (price)  : count
        /* Example of what a String arr list element looks like: 
            arr[0] = "Co0kieS pRice 2.25"
        */
            String price = items[2];
            if(products.containsKey(productName)){
                } else {
                    products.put(productName, new LinkedHashMap<String, Integer>());
                }
            //adding to inner LinkedHashMap
            if(products.get(productName).containsKey(price)){
                    products.get(productName).put(price, products.get(productName).get(price) + 1);
                } else {
                    products.get(productName).put(price, 1);
                }
        }
    }

    /* Fourth method: format output to match the output text*/
    private static String formatOutput(){
        String divider1 = "=============";
        String divider2 = "-------------"; 

        String output = "";

        for(Map.Entry<String, Map<String, Integer>> entry : products.entrySet()){
            int seenCounter = 0;
            if(entry.getKey().length() > 5 ){
                output += "name: " + entry.getKey().substring(0,1).toUpperCase() + entry.getKey().substring(1);
            } else {
                output += "name: \t" + entry.getKey().substring(0,1).toUpperCase() + entry.getKey().substring(1);
            }
                for(Map.Entry<String, Integer> entrydets : entry.getValue().entrySet()){
                seenCounter += entrydets.getValue();
            }
            if(seenCounter > 1){
                output += "\t\t\t\tseen: " + seenCounter + " times";
            } else {
                output += "\t\t\t\tseen: " + seenCounter + " time";
            }
            output += "\n" +  divider1 + "\t\t\t\t" + divider1;

            //each of the different price outputs
            for(Map.Entry<String, Integer> entrydets :entry.getValue().entrySet()){
                output += "\nPrice:\t" + entrydets.getKey() + "\t\t\t\tseen: " + entrydets.getValue() + " times";
                output += "\n" + divider2 + "\t\t\t\t" + divider2;
            }
            
            output += "\n\n"; 
        }

        //account for the errors
        output += "Errors:\t\t\t\t\t\t\t" + "seen: "  + (initialSze - finalSze) + " times";
        return output;
    }

    /* Extra method to print the elements inside the LinkedHashMap */
    private static void printLinkedHashMap(Map<String, Map<String, Integer>> map){
        int count = 1;
        for(Map.Entry<String, Map<String, Integer>> entry : map.entrySet()){
            System.out.println("Entry #" + count + "\n" + 
                                "Product Name: " + entry.getKey() + "\n");
            for(Map.Entry<String, Integer> entrydets : entry.getValue().entrySet()){
                System.out.print("\t\t Price: " + entrydets.getKey() + " shows up " + entrydets.getValue() + " times.");
            }
            count ++;
            System.out.println();
        }
    }

    //converting to output txt file 
    public static void outputFileConverter(String output){
        try{
            txtFile = new PrintWriter("test"+fileCounter+".txt");
            txtFile.println(output);
            fileCounter ++;
            txtFile.close();
        } catch (FileNotFoundException e){
            System.err.println("File doesn't exist silly head. Try again");
            e.printStackTrace();
        }
    }

}
