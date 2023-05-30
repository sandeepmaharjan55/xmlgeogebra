package src;

import java.awt.*;
import java.util.ArrayList;

public class MyLine extends segment{
    private my_point P1, P2;
//    public ArrayList<my_point[]> segmentList = new ArrayList<>();
    public boolean complete;
    MyLine() {

    }
    MyLine(int x, int y) {
        P1 = new my_point(x, y);
        complete=false;
    }
    public void addPoint(int x, int y){
        P2 = new my_point(x, y);
        complete=true;
    }

    public my_point get_P2() {
        return P2;
    }
    public my_point get_P1() {
        return P1;
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
    public void drawSegment(Graphics g, int x1,int y1,int x2,int y2){
//        System.out.println("P1 is: " + P1.get_x());
        g.fillOval(x1 - 3, y1 - 3, 6, 6);
        if(complete==true) {
            g.fillOval(x2 - 3, y2 - 3, 6, 6);
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
