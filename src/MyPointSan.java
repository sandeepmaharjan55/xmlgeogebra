package src;

import java.awt.*;
import java.util.ArrayList;

public class MyPointSan extends my_point{

//    public ArrayList<Integer> xCordsPoint = new ArrayList<Integer>();
//    public ArrayList<Integer> yCordsPoint = new ArrayList<Integer>();

//    public void drawPoints(int x, int y){
////        System.out.println("printed drawpoints values");
//        xCordsPoint.add(x);
//        yCordsPoint.add(y);
////        System.out.println("after adding x and y coords");
////        System.out.println("x and y  " + xCordsPoint+" "+ yCordsPoint);
//    }
    public String label = "";
    MyPointSan(int x, int y) {
        super(x, y);
    }

    MyPointSan(int x, int y, String l) {
        super(x, y);
        label = l;
    }

//    public void draw(g) { g.fill.....}
}

