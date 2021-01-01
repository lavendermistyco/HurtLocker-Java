package com.codedifferently.hurt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class Main {
    //how are we going to store the products?  LinkedHashMap
    static Map<String , Map<String, Integer>> products = new LinkedHashMap<>();

    //how are we going to keep track of error? We need to keep track of initial length and final of the array
    static int initialSze = 0;
    static int finalSze = 0;

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
        formatOutput();
    }

    /* First method: Turn the product string into an arrayList seperated 
      by the delimiter [^. @....etc] */

    public static ArrayList<String> convertToArray(String str){
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
            list.set(i, list.get(i).substring(5));
            //COokIes price 2.25 type Food expiration 3/22/2016
        }

        //set the intialSze to the size of the arrayList (This will be the true count of products)
        initialSze = list.size(); 

        //return the arrayList
        return list; 
    }

    /* Second method:Remove the list elements that are not starting with the name of the product. This denotes an 
    error */

    public static ArrayList<String> trimList(ArrayList<String> arr){
        //Loop through each of the elements in arr

             //In each list element, create a string array split by the " " 
            //(This separates the element into smaller elements like price, type, expiration etc)
         
                //if the first element in the String array is equal to price remove it

                //else if the third element in the String array doesn't have a number but says "type" then remove it


        //set the finalSze to the size of the new list

        //return the list
    }


    /*Third method: Put the information into a LinkedHashmap, this will help us count everything */
    public static void populateLinkedMap(ArrayList<String> arr){
        //loop through each string in the list

           //split the list into categories 
           //Then check the c00kie input replace all names that have 0 with o

           //add the elements to the map
           // Map: 
                //category -> 
                           //3rd category (price)  : count 
                           //3rd category (price)  : count
        /* Example of what a String arr list element looks like: 
            arr[0] = "Co0kieS pRice 2.25"
        */

    }

    /* Fourth method: format output to match the output text*/
    public static void formatOutput(){

    }


}
