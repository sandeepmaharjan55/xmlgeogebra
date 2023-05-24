import java.awt.*;
import java.util.ArrayList;

public class MyPolygon {
    private Polygon polygon;
    public ArrayList<Integer> xCords = new ArrayList<Integer>();
    public ArrayList<Integer> yCords = new ArrayList<Integer>();

    private ArrayList<segment> segments = new ArrayList<segment>();
    private ArrayList<my_point> vertices = new ArrayList<my_point>();

    private int index;
    private int centerX, centerY;

    MyPolygon(){}

    MyPolygon(int x, int  y) {
//        xCords.add(x);
//        yCords.add(y);
//
//        polygon = new Polygon(xCords.stream().mapToInt(i -> i).toArray(), yCords.stream().mapToInt(i -> i).toArray(), xCords.size());
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

    }

    public void move(int dx, int dy){
        for(int i=0; i<xCords.size(); i++) {
            xCords.set(i, xCords.get(i) + dx);
            yCords.set(i, yCords.get(i) + dy);
            vertices.set(i, new my_point(xCords.get(i) + dx, yCords.get(i) + dy));
        }
        polygon = new Polygon(xCords.stream().mapToInt(i -> i).toArray(), yCords.stream().mapToInt(i -> i).toArray(), xCords.size());

    }

    public void moveVertex(int ind, int dx, int dy) {
        xCords.set(ind, dx);
        yCords.set(ind, dy);
        vertices.set(ind, new my_point(dx, dy));
        polygon = new Polygon(xCords.stream().mapToInt(i -> i).toArray(), yCords.stream().mapToInt(i -> i).toArray(), xCords.size());
    }

    public void drawPoints(){
        System.out.println(xCords);
        System.out.println(yCords);
    }

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


    public double computeArea() {
        double sum = 0;
        int n = xCords.size();
        for(int i=0; i<n-1; i++) {
            sum += (xCords.get(i)*yCords.get(i+1)) - (yCords.get(i)*xCords.get(i+1));
        }
        sum += (xCords.get(n-1)*yCords.get(0)) - (yCords.get(n-1)*xCords.get(0));
        return Math.abs(sum)/2.0;
    }


    public double getDistanceFrom(my_point p) {
        double minDist = 999999, dist;
        my_point p1, p2;
        for(int i = 0; i < vertices.size()-1; i++) {
            p1 = getVertices().get(i);
            p2 = getVertices().get(i+1);
            dist = p.distance(new segment(p1, p2));
            if(dist < minDist) { minDist = dist; }
        }
        // last edge connecting last to first vertex
        p1 = getVertices().get(vertices.size()-1);
        p2 = getVertices().get(0);
        dist = p.distance(new segment(p1, p2));
        if(dist < minDist) { minDist = dist; }
        return minDist;
    }

    public double getDistanceFrom(MyPolygon poly){
        double minDist = 999999, dist;
        // Compute distance from all vertices of the source polygon(this) to the edges of target polygon(poly)
        for(int i = 0; i < getVertices().size(); i++) {
            dist = poly.getDistanceFrom(getVertices().get(i));
            if(dist < minDist) { minDist = dist; }
        }
        // Compute distance from all vertices of the target polygon(poly) to the edges of source polygon(this)
        for(int i = 0; i < poly.getVertices().size(); i++) {
            dist = getDistanceFrom(poly.getVertices().get(i));
            if(dist < minDist) { minDist = dist; }
        }
        // this double-sided check is necessary because the closest distance can be between a vertex and an edge,
        // and either of them can be on either of the polygons
        return minDist;
    }

    public static double minDistanceCalculator(ArrayList<MyPolygon> polygonList) {
        double minDist = 999999, dist;
        for(int i=0;i<polygonList.size()-1;i++) {
            MyPolygon poly1 = polygonList.get(i);
            for(int j =i+1;j<polygonList.size();j++) {
                MyPolygon poly2 = polygonList.get(j);
                dist = poly1.getDistanceFrom(poly2);
                if(dist < minDist) { minDist = dist; }
            }
        }

        return minDist;
    }
}
