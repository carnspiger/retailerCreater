/**
 * Created by caitlin.ye on 5/13/17.
 */
package com.rtl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;
import java.lang.String;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.Response;


public class ReadJsonObject {


        public String sendRequest(URL url, String apiKey, String authorize,int manID, int catID) throws Exception{
            //send request---------------------------------
            //TODO intialize from spreadsheet , getLocale, isTestrtl
            String street=" ", city = "", state = "", zip = "", country, contact1, contact2, locale, locationName, phone, shipaddr, shipcity, shipstate, shipzip, shipcountry;   //use contact 1's parsed name as user, email
            Random rand = new Random();


            OkHttpClient client = new OkHttpClient();

            //separate each json field
            MediaType mediaType = MediaType.parse("application/json");
            String content = "{\n\t\"address\": {\n\t\t\"address1\": \"1576 12th St.\",\n\t\t\"city\": \"Los Osos\",\n\t\t\"state\": \"CA\",\n\t\t\"postalCode\": \"93402\",\n\t\t\"countryCode\": \"US\"\n\t},\n\t\"catalogID\": \"" + catID + "\",\n\t\"contact1\": \"Caitlin Caitlin\",\n\t\"email\": \"contact1-"+ rand.nextInt(1000000) +"@shopatron.com\",\n\t\"locale\": \"en-US\",\n\t\"localSalesTax\": \"0.0875\",\n\t\"locationActive\": true,\n\t\"name\": \"TestRetailer_CY_" + rand.nextInt(1000000) + "\",\n\t\"manufacturerID\": \"" + manID + "\",\n\t\"phone\": \"(805) 269-5366\",\n\t\"sendNotifications\": true,\n\t\"shippingAddress\": {\n\t\t\"address1\": \"825 Buckley Rd.\",\n\t\t\"city\": \"San Luis Obispo\",\n\t\t\"state\": \"CA\",\n\t\t\"postalCode\": \"93405\",\n\t\t\"countryCode\": \"US\"\n\t},\n\t\"testRetailer\": true,\n\t\"user\": {\n\t\t\"firstName\": \"TestUser\",\n\t\t\"lastName\": \"LastName\",\n\t\t\"email\": \"user-Test_CY_" + rand.nextInt(1000000) + "@shopatron.com\",\n\t\t\"phone\": \"(805) 269-5293\",\n\t\t\"password\": \"Reset" + rand.nextInt(100) + "\"\n\t}\n}";
            //edited call body created
            RequestBody body = RequestBody.create(mediaType, content);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("api-key", apiKey)
                    .addHeader("authorization", "Bearer " + authorize)
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "449fe1b7-135a-1528-922f-e0e95a14f20a")    //TODO how is token calculated?
                    .build();

            Response response = client.newCall(request).execute();

            return response.body().string();

        }



        public HashMap<String, String> readResponse(int manID, int catID, String apiKey, String authorize) throws Exception {

                    HashMap<String, String> map = new HashMap<>();
                    URL url = new URL("https://www.shopatron.com/api/system/v1/retailer/");

            String entireResponse = sendRequest(url, apiKey, authorize, manID, catID);  //body response in a string
            System.out.println(entireResponse);

            //read response----------------------------------------------------------


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");


                    switch(conn.getResponseCode()) {
                        case 201: break;
                        case 400: break;
                        case 403: throw new RuntimeException(" HTTP error code : " + conn.getResponseCode() +
                                " - bad API credentials.");
                        default: throw new RuntimeException(" HTTP error code : " + conn.getResponseCode());
                    }


                    /*Scanner scan = new Scanner(url.openStream());
                    String entireResponse = new String();
                    while (scan.hasNext())
                        entireResponse += scan.nextLine(); //keeps scanning next line, concatenates

                    System.out.println("Response : " + entireResponse); //prints JSON response (1st line)

                    scan.close();
                    */
            System.out.println("Response : " + entireResponse); //prints JSON response (1st line)


            JSONObject object = new JSONObject(entireResponse);  //allows extraction from field name

            if(conn.getResponseCode() == 400) {  //duplicate rtl
                String additionalInfo = object.getString("additionalInfo");
                String lastWord = additionalInfo.substring(entireResponse.lastIndexOf(" ")+1); //prints rtl # (String)
                System.out.println("Retailer number : " + lastWord);    //prints 2nd line
                //TODO get locationName from inputXlsx :  map.put(lastWord, locationName);
            }


                    /*String responseCode = object.getString("status");
                    System.out.println("status : " + responseCode);    //prints 2nd line

                    //JSONArray arr = object.getJSONArray("results");
                       /* for (int i = 0; i < arr.length(); i++) {
                            //String location = arr.getJSONObject(i).getString("place_id");
                            String name = object.getString("status");
                            System.out.println("Retailer Name : " + placeid);            //prints 3rd line
                            String formatAddress = arr.getJSONObject(i).getString("formatted_address");
                            System.out.println("Address : " + formatAddress);       //prints 4th line


                            /*
                            //validating Address as per the requirement
                            if (formatAddress.equalsIgnoreCase("Chicago, IL, USA")) {
                                System.out.println("Address is as Expected");      //5th line
                            } else {
                                System.out.println("Address is not as Expected");
                            }*/

                            conn.disconnect();
                        // }
                return map;


        }  //end test



}
