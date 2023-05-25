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

    public MyPointSan mPoint = new MyPointSan();
    public MyLine mLine = new MyLine();

    public App.ControlPanel cp;


    private ArrayList<Poly> poly = new ArrayList<Poly>();

    // MyPoly Implementation
    private ArrayList<MyPolygon> polygonList = new ArrayList<MyPolygon>();
    private ArrayList<MyPointSan> myPointList = new ArrayList<MyPointSan>();
    //private ArrayList<MyLine> myLineList = new ArrayList<MyLine>();
    public ArrayList<MyLine> myLineList = new ArrayList<MyLine>();
    private JLabel coorLabel = new JLabel("00", SwingConstants.LEFT);

    private Boundary b;
    public AppPanel() {
        setSize(520, 540);
        setBounds(0,0,520,540);
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

//    private void buildPolygon(Graphics g) {
//        for(int p = 0; p < poly.size(); p++) {
//            Poly value = poly.get(p);
//            ArrayList<Integer> xCords = value.xCords;
//            ArrayList<Integer> yCords = value.yCords;
//            int[] xCo = xCords.stream().mapToInt(i -> i).toArray();
//            int[] yCo = yCords.stream().mapToInt(i -> i).toArray();
//
//            pp = new Polygon(xCords.stream().mapToInt(i -> i).toArray(), yCords.stream().mapToInt(i -> i).toArray(), xCords.size());
//
//            // Draw the polygon
//            g.drawPolygon(xCords.stream().mapToInt(i -> i).toArray(), yCords.stream().mapToInt(i -> i).toArray(), xCords.size());
//            // Shade the polygon
//            g.setColor(Color.LIGHT_GRAY);
//            g.fillPolygon(pp);
//            // Reset color
//            g.setColor(Color.BLACK);
//
//            Font currentFont = g.getFont();
//            g.setFont(currentFont.deriveFont(20F));
//            if (xCords.size() > 1) {g.drawString(""+(p+1)+"" , value.getCenterX()-5, value.getCenterY()+5);}
//            // Draw the vertices
//            for (int i = 0; i < xCords.size(); i++) {
//                g.fillRect(xCords.get(i) - 1, yCords.get(i) - 1, 3, 3);
//            }
//        }
//    }

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
        for(int i =0; i<polygonList.size(); i++) {
            pp = polygonList.get(i);
            g.drawPolygon(pp.getPolygon());
            // fill the polygon
                g.setColor(Color.CYAN);
                g.fillPolygon(pp.getPolygon());
            // Reset color
            g.setColor(Color.BLACK);

            // Draw the vertices
            for (int j = 0; j < pp.xCords.size(); j++) {
                g.fillOval(pp.xCords.get(j) - 2, pp.yCords.get(j) - 2, 4,4);
            }
            if (pp.xCords.size() >= 1) {g.drawString(""+(i+1)+"" , pp.getCenterX()-5, pp.getCenterY()+5);}
        }
    }

    public void drawPoints(Graphics g) {

        MyPointSan pp;
        for(int i =0; i<myPointList.size(); i++) {
            pp = myPointList.get(i);

          //   Draw the vertices
            for (int j = 0; j < pp.xCordsPoint.size(); j++) {
                //System.out.println("i am here at paint points"+ pp.xCordsPoint +" "+pp.yCordsPoint);
               new my_point(pp.xCordsPoint.get(j),pp.yCordsPoint.get(j)).draw(g,Color.BLACK);
               g.fillOval(pp.xCordsPoint.get(j) - 3, pp.yCordsPoint.get(j) - 3, 6, 6);
            }
        }
    }
    public void drawLines(Graphics g) {

        MyLine pp;



            for (int i = 0; i < myLineList.size(); i++) {
                pp = myLineList.get(i);
//                System.out.println(" display y corrds here "+pp.yCords.get(i));
//                System.out.println("one is here "+pp.xCords.get(i)+" "+pp.yCords.get(i));
               // System.out.println("two is here  "+pp.xCords.get(i+1)+" "+pp.xCords.get(i+1));
//                if(pp.xCords.size() % 2 == 0)
                for (int j = 0; j < pp.xCords.size(); j+=2) {
                    g.fillOval(pp.xCords.get(j) - 3, pp.yCords.get(j) - 3, 6, 6);
                    g.fillOval(pp.xCords.get(j + 1) - 3, pp.yCords.get(j + 1) - 3, 6, 6);
                    g.drawLine(pp.xCords.get(j), pp.yCords.get(j), pp.xCords.get(j + 1), pp.yCords.get(j + 1));
                }
            }



    }


    public void saveData(String fn) {
        FileIO f = new FileIO(fn);
        f.write(polygonList);
        System.out.println("Data Saved");
    }

    public void readData(String fn) {
        FileIO f = new FileIO(fn);
        polygonList = f.read(fn);
        repaint();
    }

    class MyMouseListener extends MouseAdapter {
        //        @Override

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            coorLabel.setText("" + x + ", " + y);
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
        cp.textbox.setText("1");
        repaint();
        clear = false;
    }

    private int GIClickCounter = 0;
    private boolean GIChanged = false;
    //Create new polygons
    @Override
    public void mouseClicked(MouseEvent e) {
//        if(cp.movePolygon.isSelected()) { return; }

        int x = e.getX();
        int y = e.getY();
        initialPointX = x;
        initialPointY = y;
        Graphics g = getGraphics();
        System.out.println(x + " " + y);
        int ind = Integer.parseInt(cp.textbox.getText()) - 1;

        if(cp.drawPolygon.isSelected()) {


            // clearMidPoints();
            if (poly.size() < ind + 1) {
                poly.add(new Poly());
            }

//        System.out.println("Adding to poly" + ind);
            poly.get(ind).xCords.add(x);
            poly.get(ind).yCords.add(y);

            // My Polygon implementation

            if (polygonList.size() < ind + 1) {
                polygonList.add(new MyPolygon());
            }
            polygonList.get(ind).addPoint(x, y);

        }
        //add points
//
//        mPoint.drawPoints();
//        polygonList.add(mPoint);
        if(cp.drawPoint.isSelected()) {
//            System.out.println("I am inside drawpoint selected");
////            mPoint.drawPoints();
////            polygonList.add(mPoint);
////            repaint();


            mPoint.drawPoints(x, y);
            myPointList.add(mPoint);
            //polygonList.get(ind).addPoint(x,y);
            //new my_point(x,y).draw(g,Color.black,5);
            System.out.println("indx 0 point "+myPointList.get(0).xCordsPoint);
           // repaint();
            //return;
        }

        if(cp.drawLine.isSelected()) {
            System.out.println("I am inside draw line selected");
            mLine.drawLines(x, y);
            myLineList.add(mLine);
            //polygonList.get(ind).addPoint(x,y);
            //new my_point(x,y).draw(g,Color.black,5);
            //System.out.println("indx 0 point "+myLineList.get(0).xCords);
            //repaint();
        }
        repaint();
//        buildPolygon(g);
        g.drawRect(x, y, 2, 2);
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

