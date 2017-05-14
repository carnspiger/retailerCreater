package com.rtl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * Created by caitlin.ye on 5/13/17.
 */
public class driver {

    //already created retailer, parse last word in str, convert to int
    public String parseRtlNum (String response){
        String lastWord = response.substring(response.lastIndexOf(" ")+1);
        //int rtlNum = Integer.parseInt(lastWord);
        return lastWord;
    }


    public void main(String[] args) throws IOException {
        xlsxManip xlsx = new xlsxManip();
        HashMap<String, String> map = new HashMap<>();
        int[] sheetDimensions = xlsx.fromXLSX();  //0 contains # rows, 1 contains # col
        //TODO do i need # of col?
        getInput obj = new getInput();
        int n = sheetDimensions[0]; //num rows;
        //TODO api-key and authorization  --> use them to make the json call?
        //String apiKey = obj.getAPIkey();   //TODO use selenium web browser to automate
        String authorize = obj.getAuthorize();


        ReadJsonObject object = new ReadJsonObject();
        for(int i = 1; i<=(n-1);i++) {
            try {
                object.aptTesting(authorize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //output
        xlsx.toXLSX(map, (n-1));
    }
}
