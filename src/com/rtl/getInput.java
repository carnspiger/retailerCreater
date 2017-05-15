package com.rtl;

import java.util.Scanner;

/**
 * Created by caitlin.ye on 5/13/17.
 */
public class getInput {
    private Scanner scanner;

    //get user input
    /*
    public int numRetailers(){
        System.out.print("Enter the number of retailers: ");
        scanner = new Scanner(System.in);
        int nRtl = scanner.nextInt();  //gets num rtls from user
        if (nRtl <= 0 ) {
            throw new IllegalArgumentException("Must enter number greater than 0.");
        }
        return nRtl;
    }*/
    public String getAPIkey(){
        System.out.print("Enter your API-key: ");
        scanner = new Scanner(System.in);
        String APIkey = scanner.nextLine();
        return APIkey;
    }
    public String getAuthorize(){
        System.out.print("Enter your authorization: Bearer ");
        scanner = new Scanner(System.in);
        String authorization = scanner.nextLine();
        return authorization;
    }

    //TODO get excel workbook
    public String inputFilePath(){
        System.out.print("Enter input file path: ");
        scanner = new Scanner(System.in);
        String inputFile = scanner.nextLine();
        return inputFile;
    }


    public int getManID(){
        System.out.print("Enter manufacturer ID: ");
        scanner = new Scanner(System.in);
        String manufacturerID = scanner.nextLine();
        int manID = Integer.parseInt(manufacturerID);
        return manID;
    }

    public int getCatID(){
        System.out.print("Enter catalog ID: ");
        scanner = new Scanner(System.in);
        String catalogID = scanner.nextLine();
        int catID = Integer.parseInt(catalogID);
        return catID;
    }


}
