package src;

import java.awt.*;
import java.util.ArrayList;

// Add feature to have edge list of the polygon
public class MyPolygon {
    private Polygon polygon;
    public ArrayList<Integer> xCords = new ArrayList<Integer>();
    public ArrayList<Integer> yCords = new ArrayList<Integer>();
    public ArrayList<String> coordLabel = new ArrayList<String>();
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
        //if(xCords.size()!=0) coordLabel.add(xCords.size());
        vertices.add(new my_point(x, y));
        //xCords.size();
    }
    public void addCoordLabel(int x) {
        coordLabel.add("po"+x+(xCords.size()+1));
        //System.out.println("coord ind data"+ x);
    }
    public int getCenterX() {
        if(xCords.isEmpty()) { return 0; }
        int sum = xCords.stream().mapToInt(a -> a).sum();
        centerX = sum/xCords.size();
        return centerX;
    }

    public int getCenterY() {
        if(yCords.isEmpty()) { return 0; }
        int sum = yCords.stream().mapToInt(a -> a).sum();
        centerY = sum/yCords.size();
        return centerY;
    }


}
