package src;

import java.awt.*;
import java.util.ArrayList;

// Add feature to have edge list of the polygon
public class MyPolygon {
    private Polygon polygon;
    public ArrayList<Integer> xCords = new ArrayList<Integer>();
    public ArrayList<Integer> yCords = new ArrayList<Integer>();
//    public ArrayList<String> valueLabel = new ArrayList<String>();

//    public ArrayList<Integer> xCordsPoint = new ArrayList<Integer>();
//    public ArrayList<Integer> yCordsPoint = new ArrayList<Integer>();

    private ArrayList<my_point> vertices = new ArrayList<my_point>();

    private int centerX, centerY;
    MyPolygon(){}

    MyPolygon(int x, int  y) {
     addPoint(x, y);
    }

    Polygon getPolygon() {
        return polygon;
    }

    ArrayList<my_point> getVertices() {
        return vertices;
    }
    public void addPoint(int x, int y) {
        xCords.add(x);
        yCords.add(y);
        polygon = new Polygon(xCords.stream().mapToInt(i -> i).toArray(), yCords.stream().mapToInt(i -> i).toArray(), xCords.size());
        vertices.add(new my_point(x, y));
        xCords.size();
    }
//    public void drawPoints(int x, int y){
////        System.out.println("printed drawpoints values");
//        xCordsPoint.add(x);
//        yCordsPoint.add(y);
////        System.out.println("after adding x and y coords");
////        System.out.println("x and y  " + xCordsPoint+" "+ yCordsPoint);
//    }
    public int getCenterX() {
//        if (centerX == 0) {

        if(xCords.isEmpty()) { return 0; }

        int sum = xCords.stream().mapToInt(a -> a).sum();
//            centerX = (Collections.max(xCords) + Collections.min(xCords))/2;
        centerX = sum/xCords.size();
//        }
//        System.out.println("X : Max: " + Collections.max(xCords) + " Min: " + Collections.min(xCords));
        return centerX;
    }

    public int getCenterY() {

        if(yCords.isEmpty()) { return 0; }
//        if (centerY == 0) {
//            centerY = (Collections.max(yCords) + Collections.min(yCords))/2;
        int sum = yCords.stream().mapToInt(a -> a).sum();
//            centerX = (Collections.max(xCords) + Collections.min(xCords))/2;
        centerY = sum/yCords.size();
//        System.out.println("Y : Max: " + Collections.max(yCords) + " Min: " + Collections.min(yCords));
//        }
        return centerY;
    }


}
