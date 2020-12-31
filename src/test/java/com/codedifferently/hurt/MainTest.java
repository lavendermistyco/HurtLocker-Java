package com.codedifferently.hurt;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

public class MainTest {
    //static String sampleString = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##" +
           // "naME:BreaD;price:1.23;type:Food;expiration:1/02/2016";
    //static String sampleString2 = sampleString + "##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##" +
          //  "naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016";

    @Test
    public void convertToArrayTest() {
        String sampleString = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##" +
                "naME:BreaD;price:1.23;type:Food;expiration:1/02/2016";
        int expectedElements = 2;

        assertEquals(expectedElements, Main.convertToArray(sampleString).size());
    }

    @Test
    public void convertToArrayTest2() {
        String sampleString2 = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##" +
                "naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##" +
                "NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##" +
                "naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016";

        int expectedElements = 4;

        assertEquals(expectedElements, Main.convertToArray(sampleString2).size());
    }

    @Test
    public void trimListTest() {
        String fourElements = "naMe:MiLK;priCe:;type:Food;expiration:1/11/2016##" +
                "naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016##" +
                "naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##" +
                "naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016";

        int expectedElements = 3;

        assertEquals(expectedElements, Main.trimList(Main.convertToArray(fourElements)).size());
    }

    @Test
    public void trimListTest2() {
        String nineElements = "naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016##" +
                "naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##" +
                "naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##" +
                "naMe:COOkieS;Price:2.25;type:Food;expiration:1/25/2016##" +
                "NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##" +
                "naMe:MilK;priCe:;type:Food;expiration:4/25/2016##" +
                "naMe:apPles;prIce:0.25;type:Food;expiration:1/23/2016##" +
                "naMe:apPles;pRice:0.23;type:Food;expiration:5/02/2016##" +
                "NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##" +
                "naMe:;price:3.23;type:Food^expiration:1/04/2016";

        int expectedElements = 8;

        assertEquals(expectedElements, Main.trimList(Main.convertToArray(nineElements)).size());
    }

    @Test
    public void populateLinkedMapTest() {
        ArrayList<String> bigString = Main.trimList(Main.convertToArray
                ("naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016##" +
                "naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##" +
                "naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##" +
                "naMe:COOkieS;Price:2.25;type:Food;expiration:1/25/2016##" +
                "NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##" +
                "naMe:MilK;priCe:;type:Food;expiration:4/25/2016##" +
                "naMe:apPles;prIce:0.25;type:Food;expiration:1/23/2016##" +
                "naMe:apPles;pRice:0.23;type:Food;expiration:5/02/2016##" +
                "NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##" +
                "naMe:;price:3.23;type:Food^expiration:1/04/2016"));

        int expectedCookiesSeen = 4;
        Main.populateLinkedMap(bigString);
        int seenCount = 0;
        System.out.println(Main.products.values());
        for (Map.Entry<String, Integer> entry : Main.products.get("cookies").entrySet()) {
            seenCount += entry.getValue();
        }

        assertEquals(expectedCookiesSeen, seenCount);
    }
}
