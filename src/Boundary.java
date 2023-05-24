import java.util.ArrayList;

public class Boundary{
    public int width = 1220;
    public int height = 720;

    public ArrayList<Integer> xCords = new ArrayList<Integer>();
    public ArrayList<Integer> yCords = new ArrayList<Integer>();

    private ArrayList<segment> segments = new ArrayList<segment>();
    private ArrayList<my_point> vertices = new ArrayList<my_point>();
    private ArrayList<my_point> points = new ArrayList<my_point>();

    MyPolygon polygon;

    Boundary() {
        polygon = new MyPolygon();
        polygon.addPoint(20, 20);
        polygon.addPoint(width, 20);
        polygon.addPoint(width, height);
        polygon.addPoint(20, height);
        setExtraPoints(); // Steiner's point
    }

    public MyPolygon getMyPolygon() {
        return polygon;
    }

    private void setExtraPoints() {
        int delta = 100;
        for (int i =20;i<=width; i+=delta) {
            points.add(new my_point(i, 20));
            points.add(new my_point(i, height));
        }
        for(int j =20;j<=height;j+=delta) {
            points.add(new my_point(20, j));
            points.add(new my_point(width, j));
        }
    }

    public ArrayList<my_point> getPoints() {
        return points;
    }

}
