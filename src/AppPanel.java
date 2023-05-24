package src;

import java.awt.*;
import java.awt.Polygon;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class AppPanel extends JPanel implements MouseListener, KeyListener {
    private Graphics g;

    private boolean expand = false;
    private boolean clear = false;
//    private PolygonCanvas pcanvas = new PolygonCanvas();

    private Polygon pp;

    public MyPolygon mPolygon = new MyPolygon();

    public App.ControlPanel cp;

    private int currentPolyIndex = 0;

    public boolean midPoint = false;
//    public ArrayList<int[][]> midPoints = new ArrayList<int[]>();
    public int midX, midY;

    public int pInd, vInd;
    public boolean editSelected=false;
    public boolean showNetwork=false;





    public ArrayList<int[]> midPoints = new ArrayList<int[]>();
    //public MyPoint[][] mPoints = new MyPoint[10][10];

    private ArrayList<Poly> poly = new ArrayList<Poly>();
    private my_point[] startEndObjects = new my_point[4];

    // MyPoly Implementation
    private ArrayList<MyPolygon> polygonList = new ArrayList<MyPolygon>();
    private ArrayList<MyPolygon> myPointList = new ArrayList<MyPolygon>();
    private JLabel coorLabel = new JLabel("00", SwingConstants.LEFT);

    private Boundary b;
    private int counter = 0;
    public AppPanel() {
        setSize(520, 540);
        setBounds(0,0,520,540);
        setMinimumSize(new Dimension(520, 540));
        setMaximumSize(new Dimension(520, 540));
        setBorder(BorderFactory.createEtchedBorder());
        addMouseListener(this);
        addMouseMotionListener(new MyMouseListener());
        addKeyListener(this);

//        coorLabel.setHorizontalAlignment(SwingConstants.LEFT);
//        coorLabel.setBackground(Color.red);
//        coorLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
//        coorLabel.setBorder(BorderFactory.createEtchedBorder());
        setLayout(new BorderLayout());
        coorLabel.setHorizontalAlignment(JLabel.RIGHT);
        coorLabel.setHorizontalTextPosition(JLabel.RIGHT);
        add(coorLabel, BorderLayout.NORTH);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        poly.add(new Poly());
        // set boundary
        b = new Boundary();
//        polygonList.add(b.getMyPolygon());

//        startEndObjects[0] = new my_point(50,50);
//        startEndObjects[1] = new my_point(1000,700);
//        startEndObjects[2] = new my_point(1100,200);
//        startEndObjects[3] = new my_point(210,600);

        setVisible(true);
    }

    private void buildPolygon(Graphics g) {
//        for (Poly value : poly) {
//        Poly value = new Poly();
        for(int p = 0; p < poly.size(); p++) {
            Poly value = poly.get(p);
            ArrayList<Integer> xCords = value.xCords;
            ArrayList<Integer> yCords = value.yCords;
            int[] xCo = xCords.stream().mapToInt(i -> i).toArray();
            int[] yCo = yCords.stream().mapToInt(i -> i).toArray();

            pp = new Polygon(xCords.stream().mapToInt(i -> i).toArray(), yCords.stream().mapToInt(i -> i).toArray(), xCords.size());

            // Draw the polygon
            g.drawPolygon(xCords.stream().mapToInt(i -> i).toArray(), yCords.stream().mapToInt(i -> i).toArray(), xCords.size());
            // Shade the polygon
            g.setColor(Color.LIGHT_GRAY);
            g.fillPolygon(pp);
            // Reset color
            g.setColor(Color.BLACK);

//            System.out.println("Center: " + value.getCenterX() + " " + value.getCenterY());
            Font currentFont = g.getFont();
            g.setFont(currentFont.deriveFont(20F));
            if (xCords.size() > 1) {g.drawString(""+(p+1)+"" , value.getCenterX()-5, value.getCenterY()+5);}
            // Draw the vertices
            for (int i = 0; i < xCords.size(); i++) {
                g.fillRect(xCords.get(i) - 1, yCords.get(i) - 1, 3, 3);
            }
        }
    }

    int initialPointX, initialPointY;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(clear) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            g.drawLine(midX, midY, midX, midY);
            g.drawOval(midX-2, midY-2, 4,4);
            g.setColor(new Color(0, 0, 0));
        }

//        drawBoundary(g);
        drawStartEndObjects(g);

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEtchedBorder());
        Font currentFont = g.getFont();
        g.setFont(currentFont.deriveFont(50));

       // buildPolygon(g);
        drawPolygons(g);
        drawPoints(g);
    }

    // Draws the rectangular boundary,
//    private void drawBoundary(Graphics g) {
//        g.drawRect(20,20, 1200, 700);
//        b.getPoints().forEach((n) -> g.fillRect(n.get_x() - 1, n.get_y() - 1, 3, 3) );
//    }
    private void drawStartEndObjects(Graphics g) {
//        System.out.println("size: " + startEndObjects[0]);
        if(startEndObjects[0] == null) {
            return;
        }
        g.fillOval(startEndObjects[0].get_x()-5, startEndObjects[0].get_y()-5, 10,10);
        g.drawString("S", startEndObjects[0].get_x()-10, startEndObjects[0].get_y()-10);
        for (int i = 1; i < 4; i++) {
            if(startEndObjects[i] != null) {
                g.fillOval(startEndObjects[i].get_x() - 5, startEndObjects[i].get_y() - 5, 10, 10);
                g.drawString("G"+i, startEndObjects[i].get_x() - 10, startEndObjects[i].get_y() - 10);
            }
        }

    }

    public void drawPolygons(Graphics g) {
        MyPolygon pp;
        for(int i =0; i<polygonList.size(); i++) {
            pp = polygonList.get(i);
            g.drawPolygon(pp.getPolygon());
            // Shade the polygon
//            if(i>0) {
                g.setColor(Color.CYAN);
                g.fillPolygon(pp.getPolygon());
//            }
            // Reset color
            g.setColor(Color.BLACK);

            // Draw the vertices
            for (int j = 0; j < pp.xCords.size(); j++) {
                g.fillRect(pp.xCords.get(j) - 1, pp.yCords.get(j) - 1, 3, 3);
//                if(cp.editPolygon.isSelected() && i==pInd && j==vInd) {
//                    g.setColor(Color.GREEN);
//                    g.drawOval(pp.xCords.get(j)-2, pp.yCords.get(j)-2, 4,4);
//                    g.setColor(Color.BLACK);
//                }
            }
            if (pp.xCords.size() > 1) {g.drawString(""+(i+1)+"" , pp.getCenterX()-5, pp.getCenterY()+5);}
        }
    }

    public void drawPoints(Graphics g) {

        MyPolygon pp;
        for(int i =0; i<myPointList.size(); i++) {
            pp = myPointList.get(i);
           // g.drawPolygon(pp.getPolygon());
           // g.setColor(Color.BLACK);
          //   Draw the vertices
            for (int j = 0; j < pp.xCordsPoint.size(); j++) {
                System.out.println("i am here at paint points"+ pp.xCordsPoint +" "+pp.yCordsPoint);
               new my_point(pp.xCordsPoint.get(j),pp.yCordsPoint.get(j)).draw(g,Color.BLACK);
               g.fillRect(pp.xCordsPoint.get(j) - 1, pp.yCordsPoint.get(j) - 1, 4, 4);
            }
            if (pp.xCordsPoint.size() > 1) {g.drawString(""+(i+1)+"" , pp.getCenterX()-5, pp.getCenterY()+5);}
        }
    }

    // Clear the panel
//    public void actionPerformed(ActionEvent e) {
//        expand = true;
//        clear = true;
//        System.out.println("cleared");
//        for (int p = 0; p < poly.size(); p++) {
//            poly.get(p).xCords.clear();
//            poly.get(p).yCords.clear();
//        }
//        polygonList.clear();
//        pcanvas.clear();
//        //clearMidPoints();
//        showNetwork=false;
//        cp.textbox.setText("1");
//        repaint();
//        clear = false;
//    }

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


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    class MyKeyListener extends KeyAdapter implements KeyListener{
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
//        mPolygon.drawPoints();
//        polygonList.add(mPolygon);
        if(cp.drawPoint.isSelected()) {
//            System.out.println("I am inside drawpoint selected");
////            mPolygon.drawPoints();
////            polygonList.add(mPolygon);
////            repaint();


            mPolygon.drawPoints(x, y);
            myPointList.add(mPolygon);
            //polygonList.get(ind).addPoint(x,y);
            //new my_point(x,y).draw(g,Color.black,5);
            System.out.println("indx 0 point "+myPointList.get(0).xCordsPoint);
            return;
        }

        if(cp.drawLine.isSelected()) {
            System.out.println("I am inside draw line selected");
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

