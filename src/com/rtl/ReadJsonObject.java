/**
 * Created by caitlin.ye on 5/13/17.
 */
package com.rtl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.lang.String;

import org.json.JSONArray;
import org.json.JSONObject;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import java.awt.PageAttributes.MediaType;
import javax.xml.ws.Response;


public class ReadJsonObject {


        public HashMap<String, String> aptTesting(String authorize) throws Exception {

                    driver obj = new driver();
                    HashMap<String, String> map = new HashMap<>();

                    URL url = new URL("https://www.shopatron.com/api/system/v1/retailer/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Accept", "application/json");

                    //api keys?

                    //send request
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n\t\"address\": {\n\t\t\"address1\": \"1576 12th St.\",\n\t\t\"address2\": \"Suite A\",\n\t\t\"address3\": \"Building B\",\n\t\t\"city\": \"Los Osos\",\n\t\t\"state\": \"CA\",\n\t\t\"postalCode\": \"93402\",\n\t\t\"countryCode\": \"US\"\n\t},\n\t\"catalogID\": \"0\",\n\t\"contact1\": \"Caitlin Caitlin\",\n\t\"email\": \"contact1-987_1494740075@shopatron.com\",\n\t\"locale\": \"en-US\",\n\t\"localSalesTax\": \"0.0875\",\n\t\"locationActive\": true,\n\t\"name\": \"Test_CY_1\",\n\t\"manufacturerID\": \"16158\",\n\t\"phone\": \"(805) 269-5366\",\n\t\"sendNotifications\": true,\n\t\"shippingAddress\": {\n\t\t\"address1\": \"825 Buckley Rd.\",\n\t\t\"address2\": \"Suite\",\n\t\t\"address3\": \"200\",\n\t\t\"city\": \"San Luis Obispo\",\n\t\t\"state\": \"CA\",\n\t\t\"postalCode\": \"93405\",\n\t\t\"countryCode\": \"US\"\n\t},\n\t\"testRetailer\": true,\n\t\"user\": {\n\t\t\"firstName\": \"TestUser\",\n\t\t\"lastName\": \"LastName\",\n\t\t\"email\": \"user-Test_CY_987_1494740075@shopatron.com\",\n\t\t\"phone\": \"(805) 269-5293\",\n\t\t\"password\": \"Shopatron123\"\n\t}\n}");
                Request request = new Request.Builder()
                        .url("https://www.shopatron.com/api/system/v1/retailer/")
                        .post(body)
                        .addHeader("api-key", "ytxa9ppawphwyzraxqrn3uxg")
                        .addHeader("authorization", "Bearer " + authorize)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "449fe1b7-135a-1528-922f-e0e95a14f20a")
                        .build();

                //Response response = client.newCall(request).execute();


                    switch(conn.getResponseCode()) {
                        case 201: break;
                        case 400: break;
                        case 403: throw new RuntimeException(" HTTP error code : " + conn.getResponseCode() +
                                "- bad API credentials.");
                        default: throw new RuntimeException(" HTTP error code : " + conn.getResponseCode());
                    }

                    //read response
                    Scanner scan = new Scanner(url.openStream());
                    String entireResponse = new String();
                    while (scan.hasNext())
                        entireResponse += scan.nextLine(); //keeps scanning next line, concatenates

                    System.out.println("Response : " + entireResponse); //prints JSON response (1st line)

                    scan.close();

                    if(conn.getResponseCode() == 400) {  //duplicate rtl
                        String number = obj.parseRtlNum(entireResponse);
                    }

                    JSONObject object = new JSONObject(entireResponse);  //allows extraction from field name

                    String responseCode = object.getString("status");
                    System.out.println("status : " + responseCode);    //prints 2nd line

                    JSONArray arr = object.getJSONArray("results");
                        for (int i = 0; i < arr.length(); i++) {
                            String placeid = arr.getJSONObject(i).getString("place_id");
                            System.out.println("Place id : " + placeid);            //prints 3rd line
                            String formatAddress = arr.getJSONObject(i).getString("formatted_address");
                            System.out.println("Address : " + formatAddress);       //prints 4th line


                            //validating Address as per the requirement
                            if (formatAddress.equalsIgnoreCase("Chicago, IL, USA")) {
                                System.out.println("Address is as Expected");      //5th line
                            } else {
                                System.out.println("Address is not as Expected");
                            }

                            conn.disconnect();
                         }
                return map;


        }  //end test



}
