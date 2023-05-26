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
    private ArrayList<MyPointSan> pointList = new ArrayList<MyPointSan>();
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
        for (int i = 0; i < polygonList.size(); i++) {
            pp = polygonList.get(i);
            g.drawPolygon(pp.getPolygon());
            // fill the polygon
            g.setColor(Color.CYAN);
            g.fillPolygon(pp.getPolygon());
            // Reset color
            g.setColor(Color.BLACK);

            // Draw the vertices
            for (int j = 0; j < pp.xCords.size(); j++) {
                g.fillOval(pp.xCords.get(j) - 2, pp.yCords.get(j) - 2, 4, 4);
            }
            if (pp.xCords.size() >= 1) {
                g.drawString("" + (i + 1) + "", pp.getCenterX() - 5, pp.getCenterY() + 5);
            }
        }
    }

    public void drawPoints(Graphics g) {

        MyPointSan pp;
        for (int i = 0; i < myPointList.size(); i++) {
            pp = myPointList.get(i);
            // Draw the points
            g.fillOval(pp.get_x() - 3, pp.get_y() - 3, 6, 6);

//            pp.draw(g, Color.red, 20);

//            //   Draw the vertices
//            for (int j = 0; j < pp.xCordsPoint.size(); j++) {
//                //System.out.println("i am here at paint points"+ pp.xCordsPoint +" "+pp.yCordsPoint);
//                new my_point(pp.xCordsPoint.get(j), pp.yCordsPoint.get(j)).draw(g, Color.BLACK);
//                g.fillOval(pp.xCordsPoint.get(j) - 3, pp.yCordsPoint.get(j) - 3, 6, 6);
//            }
        }
    }

    public void drawLines(Graphics g) {
        MyLine line;
        for (int i = 0; i < myLineList.size(); i++) {
            line = myLineList.get(i);
            if(i == (myLineList.size() - 1) && line.xCordsTemp.size() % 2 !=0) {
                g.fillOval(line.xCordsTemp.get(i) - 3, line.yCordsTemp.get(i) - 3, 6, 6);
            }
            for (int j = 0; j < line.xCords.size(); j += 2) {
                g.fillOval(line.xCords.get(j) - 3, line.yCords.get(j) - 3, 6, 6);
                g.fillOval(line.xCords.get(j + 1) - 3, line.yCords.get(j + 1) - 3, 6, 6);
                g.drawLine(line.xCords.get(j), line.yCords.get(j), line.xCords.get(j + 1), line.yCords.get(j + 1));
            }
        }


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

        for (int p = 0; p < myLineList.size(); p++) {
            myLineList.get(p).xCords.clear();
            myLineList.get(p).yCords.clear();
            myLineList.get(p).xCordsTemp.clear();
            myLineList.get(p).yCordsTemp.clear();
        }
        // myLineList.clear();
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

        if (e.getButton()==3 && cp.drawPolygon.isSelected()){
            int dada = Integer.parseInt(cp.textbox.getText()) + 1;
            cp.textbox.setText(String.valueOf(dada));
        }

        if (e.getButton()==1) {
            if (cp.drawPolygon.isSelected()) {


                // clearMidPoints();
                if (poly.size() < ind + 1) {
                    poly.add(new Poly());
                }

                poly.get(ind).xCords.add(x);
                poly.get(ind).yCords.add(y);

                // My Polygon implementation

                if (polygonList.size() < ind + 1) {
                    polygonList.add(new MyPolygon());
                }
                polygonList.get(ind).addPoint(x, y);

            }
            //add points
            if (cp.drawPoint.isSelected()) {
//                mPoint.drawPoints(x, y);
                myPointList.add(new MyPointSan(x, y));
                //polygonList.get(ind).addPoint(x,y);
                //new my_point(x,y).draw(g,Color.black,5);
//                System.out.println("indx 0 point " + myPointList.get(0).xCordsPoint);
            }

            if (cp.drawLine.isSelected()) {
                System.out.println("I am inside draw line selected");
                mLine.addPoint(x, y);
                myLineList.add(mLine);
                //polygonList.get(ind).addPoint(x,y);
                //new my_point(x,y).draw(g,Color.black,5);
                //System.out.println("indx 0 point "+myLineList.get(0).xCords);
                //repaint();
            }
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

