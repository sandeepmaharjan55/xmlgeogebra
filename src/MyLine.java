package src;

import java.awt.*;
import java.util.ArrayList;

public class MyLine extends segment{

//    public ArrayList<Integer> xCordsTemp = new ArrayList<Integer>();
//    public ArrayList<Integer> yCordsTemp = new ArrayList<Integer>();
//    public ArrayList<MyLine> source = new ArrayList<MyLine>();
//    public ArrayList<MyLine> dest = new ArrayList<MyLine>();

    // source = [x1, y1], dest = [x2, y2]
    // public double length() { return segment.distance(); }
    private my_point P1, P2;
    public boolean complete;
    MyLine() {

    }
    MyLine(int x, int y) {
        P1 = new my_point(x, y);
    }
    public void addPoint(int x, int y){
        P2 = new my_point(x, y);
    }
    public my_point get_P2() {
        return P2;
    }
    public void draw(Graphics g){
//        System.out.println("P1 is: " + P1.get_x());
        if(P1!=null) g.fillOval(P1.get_x() - 3, P1.get_y() - 3, 6, 6);
        if(P2!=null) {
            g.fillOval(P2.get_x() - 3, P2.get_y() - 3, 6, 6);
            g.drawLine(P1.get_x(), P1.get_y(), P2.get_x(), P2.get_y());
        }
    }
    public void tempDraw(Graphics g,int x,int y){
        //System.out.println("P1 is: " + P1.get_x());
        //System.out.println("print temp");

        if(P1!=null) g.fillOval(P1.get_x() - 3, P1.get_y() - 3, 6, 6);

            g.fillOval(x - 3, y - 3, 6, 6);
            g.drawLine(P1.get_x(), P1.get_y(), x, y);


    }
}
