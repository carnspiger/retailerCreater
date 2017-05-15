package com.rtl;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by caitlin.ye on 5/13/17.
 */
public class driver {


    public static void main(String[] args) throws IOException {
        System.out.println("begin");
        xlsxManip xlsx = new xlsxManip();
        HashMap<String, String> map = new HashMap<>();
        int n = xlsx.fromXLSX();
        getInput obj = new getInput();

        int manID = obj.getManID();
        int catID = obj.getCatID();
        String apiKey = obj.getAPIkey();   //TODO use selenium web browser to automate
        String authorize = obj.getAuthorize();


        ReadJsonObject object = new ReadJsonObject();
        for(int i = 1; i<=n;i++) {
            try {
                object.readResponse(manID, catID, apiKey, authorize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //output
        //xlsx.toXLSX(map, (n-1));
    }
}
