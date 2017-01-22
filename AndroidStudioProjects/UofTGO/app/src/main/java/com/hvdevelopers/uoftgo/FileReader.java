package com.hvdevelopers.uoftgo;

/**
 * Created by Harsh on 2017-01-21.
 */

import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    private static ArrayList<String> building = new ArrayList<>();

    public FileReader(){
    }

    public static void main(String [] args) {

        Scanner in;

        System.out.println("hello world");
        try {
            in = new Scanner(new File("C:\\Users\\Harsh\\AndroidStudioProjects\\UofTGO\\app\\src\\main\\java\\com\\hvdevelopers\\uoftgo\\buildings.txt"));
            while (in.hasNextLine()) {
                building.add(in.nextLine());
            }
        } catch (Exception e) {
            Log.e("nofile", "file not found");
        }
    }

    public ArrayList<String> getBuildings(){

        return this.building;
    }
}
