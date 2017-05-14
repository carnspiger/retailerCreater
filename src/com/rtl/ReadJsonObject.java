/**
 * Created by caitlin.ye on 5/13/17.
 */
package com.rtl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.lang.String;

import org.json.JSONArray;
import org.json.JSONObject;
//import org.testng.Reporter;
import org.testng.annotations.Test;

public class ReadJsonObject {

       @Test
        public void aptTesting() throws Exception {
            try {
                URL url = new URL("https://www.shopatron.com/api/system/v1/retailer/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 201) {  //201 is created rtl
                    if(conn.getResponseCode() == 403) throw new RuntimeException(" HTTP error code : " + conn.getResponseCode() + "- bad API credentials.");
                    else if(conn.getResponseCode() != 400) { //rtl already created - 400
                        throw new RuntimeException(" HTTP error code : " + conn.getResponseCode()); //error if not 403 or 400 or 201
                    }
                }

                Scanner scan = new Scanner(url.openStream());
                String entireResponse = new String();
                while (scan.hasNext())
                    entireResponse += scan.nextLine(); //keeps scanning next line, concatenates

                System.out.println("Response : "+entireResponse); //prints JSON response

                scan.close();

                //parse last word in string, get rtl number
                if(conn.getResponseCode() == 400){
                    ReadJsonObject object = new ReadJsonObject();
                   //TODO int rtlNumber = object.parseIntFromStr(entireResponse);
                }


                JSONObject obj = new JSONObject(entireResponse );
                String responseCode = obj.getString("status");
                System.out.println("status : " + responseCode);

                JSONArray arr = obj.getJSONArray("results");
                for (int i = 0; i < arr.length(); i++) {
                    String placeid = arr.getJSONObject(i).getString("place_id");
                    System.out.println("Place id : " + placeid);
                    String formatAddress = arr.getJSONObject(i).getString(
                            "formatted_address");
                    System.out.println("Address : " + formatAddress);

                //validating Address as per the requirement
                    if(formatAddress.equalsIgnoreCase("Chicago, IL, USA"))
                    {
                        System.out.println("Address is as Expected");
                    }
                    else
                    {
                        System.out.println("Address is not as Expected");
                    }
                }

                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }



}
