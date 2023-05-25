package src;

import java.awt.*;
import java.util.ArrayList;

public class MyPointSan {
    private Polygon polygon;

    public ArrayList<Integer> xCordsPoint = new ArrayList<Integer>();
    public ArrayList<Integer> yCordsPoint = new ArrayList<Integer>();

    public void drawPoints(int x, int y){
//        System.out.println("printed drawpoints values");
        xCordsPoint.add(x);
        yCordsPoint.add(y);
//        System.out.println("after adding x and y coords");
//        System.out.println("x and y  " + xCordsPoint+" "+ yCordsPoint);
    }
}

