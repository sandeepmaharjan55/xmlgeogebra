package src;

import java.awt.*;
import java.awt.Polygon;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class AppPanel extends JPanel implements MouseListener {
    private Graphics g;

    private boolean expand = false;
    private boolean clear = false;

    private Polygon pp;

//    public MyPointSan mPoint = new MyPointSan();
    //  private ArrayList<MyPointSan> mLineList = new ArrayList<MyPointSan>();
    // public MyLine mLine = new MyLine();

    public App.ControlPanel cp;


    private ArrayList<Poly> poly = new ArrayList<Poly>();
    // MyPoly Implementation
    private ArrayList<MyPolygon> polygonList = new ArrayList<MyPolygon>();
    private ArrayList<MyPoint> myPointList = new ArrayList<MyPoint>();
    //    private ArrayList<MouseDragVertices> mouseDragVertexList = new ArrayList<MouseDragVertices>();
    private ArrayList<MyLine> myLineList = new ArrayList<MyLine>();
    private ArrayList<MyLine> myLinePointLabel = new ArrayList<MyLine>();
//    private ArrayList<my_point[]> myLineSegment = new ArrayList<my_point[]>();
    private JLabel coorLabel = new JLabel("00", SwingConstants.LEFT);

    private Boundary b;

    public AppPanel() {
        setSize(520, 540);
        setBounds(0, 0, 520, 540);
        setMinimumSize(new Dimension(520, 540));
        setBorder(BorderFactory.createEtchedBorder());

        addMouseListener(this);
        addMouseMotionListener(new MyMouseListener());
        setLayout(new BorderLayout());
        coorLabel.setHorizontalAlignment(JLabel.RIGHT);
        coorLabel.setHorizontalTextPosition(JLabel.RIGHT);
        add(coorLabel, BorderLayout.NORTH);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        poly.add(new Poly());
        // set boundary
        b = new Boundary();
        setVisible(true);
    }

    int initialPointX, initialPointY;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEtchedBorder());
        Font currentFont = g.getFont();
        g.setFont(currentFont.deriveFont(50));

        // buildPolygon(g);
        drawPolygons(g);
        drawPoints(g);
        drawLines(g);
    }

    public void drawPolygons(Graphics g) {
        MyPolygon pp;
//        System.out.println("polygon size "+polygonList.size());
        for (int i = 0; i < polygonList.size(); i++) {
            pp = polygonList.get(i);
            g.drawPolygon(pp.getPolygon());
//            System.out.println(pp.coordLabel.size());
            //g.drawString(pp.coordLabel.get(i), pp.xCords.get(i) - 10, pp.yCords.get(i) - 10);
            // fill the polygon
            g.setColor(Color.CYAN);
            g.fillPolygon(pp.getPolygon());
            // Reset color
            g.setColor(Color.BLACK);
//System.out.println("polyc  "+pp.xCords.size());
            // Draw the vertices
            for (int j = 0; j < pp.xCords.size(); j++) {
                g.fillOval(pp.xCords.get(j) - 2, pp.yCords.get(j) - 2, 4, 4);
                g.drawString(pp.coordLabel.get(j), pp.xCords.get(j) - 10, pp.yCords.get(j) - 10);
            }
            if (pp.xCords.size() >= 1) {
                g.drawString("poly" + (i + 1) + "", pp.getCenterX() - 5, pp.getCenterY() + 5);
                cp.textboxUnclick.setText("Polygon " + (i + 1) + " being drawn.");
            }
        }
    }

    public void drawPoints(Graphics g) {

        MyPoint pp;
//        System.out.println("point size "+myPointList.size());
        for (int i = 0; i < myPointList.size(); i++) {
            pp = myPointList.get(i);
            // Draw the points
            //g.fillOval(pp.get_x() - 3, pp.get_y() - 3, 6, 6);

            pp.draw(g,myPointList.size());

//            //   Draw the vertices
           // System.out.println("here cord size"+pp.xCords.size());
//            for (int j = 0; j < myPointList.size(); j++) {
//                //System.out.println("i am here at paint points"+ pp.xCordsPoint +" "+pp.yCordsPoint);
//                //new my_point(pp.xCords.get(j), pp.yCords.get(j)).draw(g, Color.BLACK);
//                g.fillOval(pp.xCords.get(j) - 3, pp.yCords.get(j) - 3, 6, 6);
//            }
            if (myPointList.size() >= 1) {
                g.drawString("p" + (i + 1) + "", pp.get_x() - 20, pp.get_y() + 20);
                cp.textboxUnclick.setText("Point " + (i + 2) + " being drawn.");
            }
        }
    }

    public void drawLines(Graphics g) {
        MyLine line;
        my_point[] lineSegment;
        //System.out.println("line size "+myLinePoint.size());
//        for (int i = 0; i < myLinePoint.size(); i++) {
//            line = myLinePoint.get(i);
//            line.draw(g);
//            if (myLinePoint.size()/2 >= 1 && line.get_P2()!=null) {
//                g.drawString("p" + (i/2+1) + "", ((line.get_P1().get_x()+line.get_P2().get_x())/2)-20, ((line.get_P1().get_y()+line.get_P2().get_y())/2)+20);
//                cp.textboxUnclick.setText("Line "+(i/2 + 2)+" being drawn.");
//            }
//        }
//        System.out.println(myLineList);
        for (int i = 0; i < myLineList.size(); i++) {
            line = myLineList.get(i);
            line.draw(g);
//            if (myLineList.size() >= 1) {
            //line.getCenterX();
            if(line.complete) g.drawString("l " + (i+1) + "", line.getCenterX()+10, line.getCenterY()+10);
//            }
        }

        //System.out.println("line size "+myLineSegment.size());
//        for (int i = 0; i < myLineSegment.size(); i++) {
//
//            lineSegment = myLineSegment.get(i);
//            // g.fillOval(lineSegment[0].get_x() - 3, lineSegment[0].get_y() - 3, 6, 6);
//            //System.out.println(lineSegment[0].get_x());
//            for (int s = 0; s < lineSegment.length; s++) {
//                //System.out.println("lineSegment");
//                int x1 = lineSegment[0].get_x();
//                int y1 = lineSegment[0].get_y();
//                int x2 = lineSegment[1].get_x();
//                int y2 = lineSegment[1].get_y();
////                System.out.println("P1 label data: " + line.label);
////                System.out.println("P2 label data: " + line.label);
//                //System.out.println("sata xy"+x1+" "+y1+" "+x2+" "+y2);
////                if(s==0)System.out.println("source data: x "+lineSegment[s].get_x()+" y "+lineSegment[s].get_y());
////                else System.out.println("target data: x "+lineSegment[s].get_x()+" y "+lineSegment[s].get_y());
////                    g.fillOval(x1 - 3, y1 - 3, 6, 6);
//                g.fillOval(x1 - 3, y1 - 3, 6, 6);
//                g.fillOval(x2 - 3, y2 - 3, 6, 6);
//                g.drawLine(x1, y1, x2, y2);
//
////add label to points
//                int d = ((i + 1) * 2) + 1;
//                g.drawString("lp" + (d - 2) + "", x1 - 10, y1 - 10);
//                g.drawString("lp" + (d - 1) + "", x2 - 10, y2 - 10);
//
//
//                g.drawString("l" + (i + 1) + "", ((x1 + x2) / 2) - 20, ((y1 + y2) / 2) + 20);
//            }
//            cp.textboxUnclick.setText("Line " + (i + 2) + " being drawn.");
////            line.drawSegment(g,Arrays.stream(linePoint).toList().get(0).get_x(),Arrays.stream(linePoint).toList().get(0).get_y(),Arrays.stream(linePoint).toList().get(1).get_x(),Arrays.stream(linePoint).toList().get(1).get_y());
//
////            System.out.println("source "+line.get_P1().get_x()+" "+line.get_P1().get_y());
////            System.out.println("target "+line.get_P2().get_x()+" "+line.get_P2().get_y());
////            line.draw(g);
////            System.out.println(myLineSegment.size());
////            if (myLineSegment.size() >= 1 && line.get_P2()!=null) {
////                g.drawString("p" + (i+1) + "", ((line.get_P1().get_x()+line.get_P2().get_x())/2)-20, ((line.get_P1().get_y()+line.get_P2().get_y())/2)+20);
////                cp.textboxUnclick.setText("Line "+(i + 2)+" being drawn.");
////            }
//        }

    }


    public void saveData(String fn) {
        FileIO f = new FileIO(fn);
        f.write(polygonList);
        // f.writeTwo(myPointList);
        System.out.println("Data Saved");
    }

    public void readData(String fn) {
        FileIO f = new FileIO(fn);
        polygonList = f.read(fn);
        repaint();
    }

    int cx, cy;


    class MyMouseListener extends MouseAdapter {
        //        @Override

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            if (cx != x || cy != y) repaint();
            cx = x;
            cy = y;
            coorLabel.setText("" + x + ", " + y);
            //to make line draw dynamic
            if (myLineList.size() > 0) {
                MyLine line;
                line = myLineList.get(myLineList.size() - 1);
                if (line.get_P2() == null) {
                    line.tempDraw(getGraphics(), cx, cy);
                }
            }
        }
    }

    // Clear the panel
    public void actionPerformed(ActionEvent e) {
        //expand = true;
        clear = true;
        System.out.println("cleared");
        for (int p = 0; p < poly.size(); p++) {
            poly.get(p).xCords.clear();
            poly.get(p).yCords.clear();
        }
        polygonList.clear();
        myPointList.clear();
        myLineList.clear();
//        myLineSegment.clear();
        cp.textbox.setText("1");
        cp.textboxUnclick.setText("1");
        repaint();
        clear = false;
    }

    public void actionDeletePerformed(ActionEvent e) {
        if (cp.drawPoint.isSelected()) {
            int getElementId=Integer.parseInt(cp.textbox.getText())-1;
            if(myPointList.size()>0) myPointList.remove(getElementId);
            else System.out.println("selected element id doesnot exist!!");
        }
        if (cp.drawLine.isSelected()) {
            int getElementId=Integer.parseInt(cp.textbox.getText())-1;
            if(myLineList.size()>0) myLineList.remove(getElementId);
            else System.out.println("selected element id doesnot exist!!");

        }
        if (cp.drawPolygon.isSelected()) {
            int getElementId=Integer.parseInt(cp.textbox.getText())-1;

            if(polygonList.size()>0){
                //polygonList.set(polygonList.indexOf(getElementId),null);
               polygonList.remove(getElementId);
//                polygonList.set(polygonList.indexOf(getElementId),null);
            }
            else System.out.println("selected element id doesnot exist!!");
        }

//        clear = true;
//        System.out.println("cleared");
//        for (int p = 0; p < poly.size(); p++) {
//            poly.get(p).xCords.clear();
//            poly.get(p).yCords.clear();
//        }
//        polygonList.clear();
//        myPointList.clear();
//        myLineList.clear();
////        myLineSegment.clear();
//        cp.textbox.setText("1");
//        cp.textboxUnclick.setText("1");
        repaint();
//        clear = false;
    }


    private int GIClickCounter = 0;
    private boolean GIChanged = false;

    //Create new polygons
    private static final long CLICK_INTERVAL = 2000;
    private long lastClickTime = 0;

    @Override
    public void mouseClicked(MouseEvent e) {
//        if(cp.movePolygon.isSelected()) { return; }

        int x = e.getX();
        int y = e.getY();
        initialPointX = x;
        initialPointY = y;
        Graphics g = getGraphics();
        //System.out.println(x + " " + y);
        int ind = Integer.parseInt(cp.textbox.getText()) - 1;

        long currentTime = System.currentTimeMillis();

        if (e.getButton() == 3 && cp.drawPolygon.isSelected() && (currentTime - lastClickTime > CLICK_INTERVAL)) {
            int dada = Integer.parseInt(cp.textbox.getText()) + 1;
            cp.textbox.setText(String.valueOf(dada));
            lastClickTime = currentTime;
        }

        if (e.getButton() == 1) {
            if (cp.drawPolygon.isSelected()) {


                // clearMidPoints();
//                if (poly.size() < ind + 1) {
//                    poly.add(new Poly());
//
//                }
//
//                poly.get(ind).xCords.add(x);
//                poly.get(ind).yCords.add(y);
                //polyVertexLabel.get(ind).add("po"+ (i+1));

                // My Polygon implementation

                if (polygonList.size() < ind + 1) {
                    polygonList.add(new MyPolygon());

                }
//                System.out.println(" polugon lkist size "+polygonList.size());
//                System.out.println(" ind size "+ind+1);
                polygonList.get(ind).addCoordLabel(ind+1);
                polygonList.get(ind).addPoint(x, y);

            }
            //add points
            if (cp.drawPoint.isSelected()) {
                if (myPointList.size() < (ind + 1)) {
                    myPointList.add(new MyPoint(x, y));
                }
                //System.out.println(" mypointlist size "+myPointList.size());
//                System.out.println(" ind size "+ind+1);

                 myPointList.get(ind).addPoint(x,y);
                //System.out.println(" mypointlist size "+myPointList.size());
//                System.out.println(myPointList.get(ind).get_yCords());
            }

            if (cp.drawLine.isSelected()) {
//                System.out.println("I am inside draw line selected");
                MyLine line;
//                segment hawa;
                if (myLineList.size() == 0) {
                    //line = new MyLine(x, y); // can be moved to line method
                    line = new MyLine(x, y);
                    myLineList.add(line);
//                    myLineList.get(ind).addCoordLabel(ind+1);
                    //System.out.println("Cor labels "+line.coordLabel);

                } else {
                    line = myLineList.get(myLineList.size() - 1);
                    if (line.get_P2() != null) {
                        line = new MyLine(x, y);
                        myLineList.add(line);
//                        myLineList.get(ind).addCoordLabel(ind+1);
                        //System.out.println("Cor labels below "+line.coordLabel);
                    } else {
                        line.addPoint(x, y);
                    }
                }
            }
        }
        repaint();
//        buildPolygon(g);
//        g.drawRect(x, y, 2, 2);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        initialPointX = e.getX();
        initialPointY = e.getY();
    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

