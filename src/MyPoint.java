package src;

import java.awt.*;
import java.util.ArrayList;

public class MyPoint extends my_point{

    public ArrayList<Integer> xCords = new ArrayList<Integer>();
    public ArrayList<Integer> yCords = new ArrayList<Integer>();
//    public void drawPoints(int x, int y){
////        System.out.println("printed drawpoints values");
//        xCordsPoint.add(x);
//        yCordsPoint.add(y);
////        System.out.println("after adding x and y coords");
////        System.out.println("x and y  " + xCordsPoint+" "+ yCordsPoint);
//    }
    public String label = "";
    MyPoint() {

    }
    MyPoint(int x, int y) {
        super(x, y);
    }

    MyPoint(int x, int y, String l) {
        super(x, y);
        label = l;
    }
    public void addPoint(int x, int y) {
        //System.out.println("hello world");
        xCords.add(x);
        yCords.add(y);

    }
    public void draw(Graphics g,int x) {
        for (int i = 0; i < x; i++) {
            g.fillOval(xCords.get(i) - 3, yCords.get(i) - 3, 6, 6);
        }


    }
//    public ArrayList<Integer> get_xCords() {
//        return xCords;
//    }
//    public ArrayList<Integer> get_yCords() {
//        return yCords;
//    }

//    public void draw(g) { g.fill.....}
}

