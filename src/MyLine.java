package src;

import javax.swing.text.Segment;
import java.awt.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class MyLine extends segment {
    public segment innerSegment;
    private my_point P1, P2;
    //    public ArrayList<my_point[]> segmentList = new ArrayList<>();
//    public ArrayList<String> lineNameList = new ArrayList<String>();
//    public ArrayList<MyLine> lineList = new ArrayList<MyLine>();
    public boolean complete;
    private int centerX, centerY;

    MyLine() {
    }

    MyLine(int x, int y) {
        P1 = new my_point(x, y);
        complete = false;
    }

    public void addPoint(int x, int y) {
        P2 = new my_point(x, y);
        update(P1, P2);
        complete = true;
    }

    public my_point get_P2() {
        return P2;
    }

    public my_point get_P1() {
        return P1;
    }

    public void draw(Graphics g){
//        System.out.println("P1 is: " + P1.get_x());
        if(P1!=null) {
            g.fillOval(P1.get_x() - 3, P1.get_y() - 3, 6, 6);
            //g.drawString("line 1",  P1.get_x()- 10, P1.get_y() - 10);
        }
        if(P2!=null) {
            g.fillOval(P2.get_x() - 3, P2.get_y() - 3, 6, 6);
            g.drawLine(P1.get_x(), P1.get_y(), P2.get_x(), P2.get_y());
            //g.drawString("line 2",  P2.get_x()- 10, P2.get_y() - 10);
        }
    }
    public void tempDraw(Graphics g, int x, int y) {
        if (P1 != null) g.fillOval(P1.get_x() - 3, P1.get_y() - 3, 6, 6);
        g.fillOval(x - 3, y - 3, 6, 6);
        g.drawLine(P1.get_x(), P1.get_y(), x, y);
    }
//    public void drawSegment(Graphics g, int x1,int y1,int x2,int y2){
////        System.out.println("P1 is: " + P1.get_x());
//        g.fillOval(x1 - 3, y1 - 3, 6, 6);
//        if(complete==true) {
//            g.fillOval(x2 - 3, y2 - 3, 6, 6);
//            g.drawLine(x1, y1, x2, y2);
//        }
//    }
public int getCenterX() {
    if(P1.get_x()==0 || P2.get_x()==0) { return 0; }
    int sum = P1.get_x()+P2.get_x();
    centerX = sum/2;
    return centerX;
}


    public int getCenterY() {
        if(P1.get_y()==0 || P2.get_y()==0) { return 0; }
        int sum = P1.get_y()+P2.get_y();
        centerY = sum/2;
        return centerY;
    }
//    //to change double value to value upto 2 decimal
//    private static final DecimalFormat df = new DecimalFormat("0.00");

    //find midpoint and distance
    public void midPoint(Graphics g,int midX,int midY) {
        g.setColor(Color.red);
        g.fillOval(midX - 3, midY - 3, 6, 6);
        g.setColor(Color.black);
    }
}


//// Line 1
//    - Source -> my_point
//    - Target -> my_point
//
//line = line 1
//
//line.source -> p1
//            line.source.get_X, line.source.get_y
//line.target -> p2