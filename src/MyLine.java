package src;

import java.awt.*;
import java.util.ArrayList;

public class MyLine {

    public ArrayList<Integer> xCordsTemp = new ArrayList<Integer>();
    public ArrayList<Integer> yCordsTemp = new ArrayList<Integer>();
    public ArrayList<Integer> xCords = new ArrayList<Integer>();
    public ArrayList<Integer> yCords = new ArrayList<Integer>();
    //public ArrayList<int[]> pointyList = new ArrayList<int[]>();
    public void drawLines(int x, int y){
//        xCords.add(x);
//        yCords.add(y);

        xCordsTemp.add(x);
        yCordsTemp.add(y);
        if(xCordsTemp.size() % 2==0) {
            System.out.println("m here sasaa");
            for(int i=0;i<xCordsTemp.size();i++) {
                System.out.println("X AND Y "+ xCordsTemp.get(i)+" "+xCordsTemp.get(i));
                xCords.add(xCordsTemp.get(i));
                yCords.add(yCordsTemp.get(i));
            }
        }

    }
}
