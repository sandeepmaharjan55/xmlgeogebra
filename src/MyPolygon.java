package src;

import java.awt.*;
import java.util.ArrayList;

// Add feature to have edge list of the polygon
public class MyPolygon {
    private Polygon polygon;
    public ArrayList<Integer> xCords = new ArrayList<Integer>();
    public ArrayList<Integer> yCords = new ArrayList<Integer>();
//    public ArrayList<String> coordLabel = new ArrayList<String>();
    private ArrayList<my_point> vertices = new ArrayList<my_point>();
    private ArrayList<segment> edges = new ArrayList<segment>();

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
        my_point newPoint = new my_point(x, y);
        vertices.add(newPoint);
        //xCords.size();
        if(vertices.size()>1){
            if (edges.size() > 1) edges.remove(edges.size()-1); // add if cond
            edges.add(new segment(vertices.get(vertices.size() - 2), newPoint)); // -2 because we need second last point
            edges.add(new segment(newPoint, vertices.get(0))); // add last edge that goes back to first point
        }
        //printEdges();
    }

    private void printEdges() {
        //System.out.println(edges);
        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).print();
        }
    }
//    public void addCoordLabel(int x) {
//        coordLabel.add("po"+x+(xCords.size()+1));
//        //System.out.println("coord ind data"+ x);
//    }
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
