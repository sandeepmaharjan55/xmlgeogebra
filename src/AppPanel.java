import java.awt.*;
import java.awt.Polygon;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class AppPanel extends JPanel implements MouseListener, KeyListener {
    private Graphics g;

    private boolean expand = false;
    private boolean clear = false;
    private PolygonCanvas pcanvas = new PolygonCanvas();

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

    @Override
    public void keyTyped(KeyEvent e) {
        if(cp.editPolygon.isSelected()){
            System.out.println(e.getKeyChar());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(cp.editPolygon.isSelected()){
            System.out.println(e.getKeyChar());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(cp.editPolygon.isSelected()){
            System.out.println(e.getKeyChar());
        }
    }

    public ArrayList<int[]> midPoints = new ArrayList<int[]>();
    public MyPoint[][] mPoints = new MyPoint[10][10];

    private ArrayList<Poly> poly = new ArrayList<Poly>();
    private my_point[] startEndObjects = new my_point[4];

    // MyPoly Implementation
    private ArrayList<MyPolygon> polygonList = new ArrayList<MyPolygon>();
    private ArrayList<MyMidPoint> myMidPoints = new ArrayList<MyMidPoint>();
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

        drawBoundary(g);
        drawStartEndObjects(g);

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEtchedBorder());
        Font currentFont = g.getFont();
        g.setFont(currentFont.deriveFont(50));

//        buildPolygon(g);
        drawPolygons(g);
        lcThreshold = Integer.parseInt(cp.lcThreshold.getText());

        // This implementation was not pursued due to time limitation
//         if(midPoint) {
//             expandPoly(g);
//         }
        for(int m=0; m<myMidPoints.size(); m++) {
//            System.out.println("inside mp drawing");
            MyMidPoint mmp = myMidPoints.get(m);
            my_point mp = mmp.point;
            g.drawLine(mp.get_x(), mp.get_y(), mp.get_x(), mp.get_y());
            g.drawOval(mp.get_x()-1, mp.get_y()-1, 3,3);
//            Font currentFont = g.getFont();
            g.setFont(currentFont.deriveFont(10F));
            // display node index/number
            if(cp.showLCNodes.isSelected() && lcNodes.contains(m)) {
                g.setColor(Color.RED);
                g.drawOval(mp.get_x()-lcThreshold, mp.get_y()-lcThreshold, lcThreshold*2,lcThreshold*2);
                g.setColor(Color.BLACK);
            }
            if(cp.showNodeIndex.isSelected()) {
                g.drawString("" + mmp.n, mp.get_x() - 5, mp.get_y() - 5);
            }
        }
        if(showNetwork && myMidPoints.size() > 0) {
            emptyCircleTest(g);
        }
    }

    // Draws the rectangular boundary, and the Steiner points
    private void drawBoundary(Graphics g) {
        g.drawRect(20,20, 1200, 700);
        b.getPoints().forEach((n) -> g.fillRect(n.get_x() - 1, n.get_y() - 1, 3, 3) );
    }
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
                g.setColor(Color.LIGHT_GRAY);
                g.fillPolygon(pp.getPolygon());
//            }
            // Reset color
            g.setColor(Color.BLACK);

            // Draw the vertices
            for (int j = 0; j < pp.xCords.size(); j++) {
                g.fillRect(pp.xCords.get(j) - 1, pp.yCords.get(j) - 1, 3, 3);
                if(cp.editPolygon.isSelected() && i==pInd && j==vInd) {
                    g.setColor(Color.GREEN);
                    g.drawOval(pp.xCords.get(j)-2, pp.yCords.get(j)-2, 4,4);
                    g.setColor(Color.BLACK);
                }
            }
            if (pp.xCords.size() > 1) {g.drawString(""+(i+1)+"" , pp.getCenterX()-5, pp.getCenterY()+5);}

            // Print polygon area
//            System.out.println("Area of Polygon " + (i+1) + " : " + pp.computeArea());
        }
    }

    // Clear the panel
    public void actionPerformed(ActionEvent e) {
        expand = true;
        clear = true;
        System.out.println("cleared");
        for (int p = 0; p < poly.size(); p++) {
            poly.get(p).xCords.clear();
            poly.get(p).yCords.clear();
        }
        polygonList.clear();
        pcanvas.clear();
        clearMidPoints();
        showNetwork=false;
        cp.textbox.setText("1");
        repaint();
        clear = false;
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

    private void expandPoly(Graphics g) {
        segment sg;
        my_point p1, p2;
        for (int k = 0; k < polygonList.size(); k++) {
            MyPolygon po = polygonList.get(k);
            // Check which direction to draw the line at
            boolean isLeft = true;
            for (int poi = 0; poi < po.xCords.size(); poi++) {
                p1 = po.getVertices().get(poi);
                if (poi == po.xCords.size() - 1) {
                    // line segment between first and last vertices
                    p2 = po.getVertices().get(0);
                } else {
                    p2 = po.getVertices().get(poi + 1);
                }
                sg = new segment(po.getVertices().get(0), po.getVertices().get(1));
                line vLineLeft = sg.parl_line_onLeft(5);
//                if (!sg.IntersectProp(vLineLeft)) {
                if (!vLineLeft.Are_onLeft_side(p1, p2)) {
                    isLeft = false;
                    break;
                }
            }
            // Now we know which side to expand on, create lines and then also create cutoff points to get segment
            for (int poi = 0; poi < po.xCords.size(); poi++) {
                p1 = po.getVertices().get(poi);

                if (poi == po.xCords.size() - 1) {
                    // line segment between first and last vertices
                    p2 = po.getVertices().get(0);
                } else {
                    p2 = po.getVertices().get(poi + 1);
                }
                sg = new segment(p1, p2);

                line vLine;
                if(isLeft) {
                    vLine = sg.parl_line_onLeft(Integer.parseInt(cp.expandBy.getText()));
                } else {
                    vLine = sg.parl_line_onRight(Integer.parseInt(cp.expandBy.getText()));
                }
//              // compute perpendicular lines from the vertices
                line pLine1 = vLine.perp_line(p1);
                line pLine2 = vLine.perp_line(p2);

                // the take those two points to compute whether the vertices of the polygon are on the same side(this might have a catch)
                // dont need to do for all
                // if yes, then use the vlineleft and perpline functions to compute the new expanded polygons
                // if no then use right

                my_point p11 = pLine1.Compute_Intersect(vLine);
                my_point p22 = pLine2.Compute_Intersect(vLine);

//                segment sg2 = new segment(p11, p22);
                g.drawLine(p11.get_x(), p11.get_y(), p22.get_x(), p22.get_y());
//                g.drawOval();

            }
        }
    }

    public void findMidPointsAll(ActionEvent e) {
//        System.out.println("find midpoint all");
        midPoint = true;
        findAllVisibleMidPoints();
//        System.out.println("midpoint all found");
        repaint();
    }

    // find out mid points of the polygons
    public void findAllMidPoints(){
        double shortestDistance=1000000;
        double dist;
        boolean isInside=false;
        for (int i = 0; i < polygonList.size()-1; i++) {
            MyPolygon myPolyI = polygonList.get(i);
            int myPolyISize = myPolyI.getVertices().size();
            for (int j = i+1; j < polygonList.size(); j++) {
                my_point mp = new my_point();
                shortestDistance=1000000;
                MyPolygon myPolyJ = polygonList.get(j);
                for(int ii=0; ii< myPolyISize; ii++) {
                    my_point vx = myPolyI.getVertices().get(ii);
                    for (int jj = 0; jj < myPolyJ.getVertices().size(); jj++) {
                        my_point vy = myPolyJ.getVertices().get(jj);
                        dist = vx.distance(vy);
                        if (dist < shortestDistance) {
                            shortestDistance = dist;
                            mp = vx.midPoint(vx, vy);
                        }
                    }
                }
                if(polygonList.size() <= 2) { myMidPoints.add(new MyMidPoint(mp, i+1, j+1)); continue; }
                isInside = false;
                for (int k = 0; k < polygonList.size(); k++) {
                    if((k != i) && (k != j) && polygonList.get(k).getPolygon().contains(mp.get_x(), mp.get_y())) {
                        isInside = true;
//                        break;
                    }
                }
                if (!isInside) { myMidPoints.add(new MyMidPoint(mp, i+1, j+1)); }
            }
        }
    }

    public void findAllVisibleMidPoints(){
        lcNodes.clear();
        // min clearance distance
        double md = MyPolygon.minDistanceCalculator(polygonList);
        cp.lcThreshold.setText(String.valueOf((int)(md * 0.5)));
        // Move this to parent method
        if(cp.useRandom.isSelected()) {
            if (showNetwork) { return; }
            myMidPoints.clear();
            randomPointGeneration();
            return;
        }
        double dist;
        boolean isInside=false, visible=true;
        segment sg1, sg2;
        counter=1; // counter to index the mid points as a node in graph structure
        myMidPoints.clear();
        if(startEndObjects[0] != null ) { myMidPoints.add(new MyMidPoint(startEndObjects[0],0 )); }
        for (int i = 0; i < polygonList.size()-1; i++) {
            MyPolygon myPolyI = polygonList.get(i);
            int myPolyISize = myPolyI.getVertices().size();
            for (int j = i+1; j < polygonList.size(); j++) {
                my_point mp = new my_point();
                MyPolygon myPolyJ = polygonList.get(j);
                for(int ii=0; ii< myPolyISize; ii++) {
                    my_point vx = myPolyI.getVertices().get(ii);
                    for (int jj = 0; jj < myPolyJ.getVertices().size(); jj++) {
                        my_point vy = myPolyJ.getVertices().get(jj);

                        mp = vx.midPoint(vx, vy);
                        if(polygonList.size() <= 0) {
                            myMidPoints.add(new MyMidPoint(mp, counter)); counter+=1; continue;
                        } else {
                            isInside = false;
                            visible = true;
                            sg1 = new segment(vx, vy);
                            for (int k = 0; k < polygonList.size(); k++) {
                                //check visibility
                                MyPolygon po = polygonList.get(k);
                                for(int poi=0;poi<po.xCords.size();poi++) {
                                    my_point p1 = po.getVertices().get(poi);
                                    my_point p2;
                                    if(poi == po.xCords.size()-1) {
                                        // line segment between first and last vertices
                                        p2 = po.getVertices().get(0);
                                    } else {
                                        p2 = po.getVertices().get(poi + 1);
                                    }
//                                    System.out.println("checking intersection between " + i + ":" + ii + "|" + j + ":" + jj);
//                                    System.out.println("and " + k + ":" + poi + "|" + k + ":" + (poi+1));
                                    if(vx.Is_sameAs(p1) || vx.Is_sameAs(p2) || vy.Is_sameAs(p1) || vy.Is_sameAs(p2)) {
//                                        System.out.println("Same point overlap");
                                        continue;
                                    }
                                    sg2 = new segment(p1, p2);
                                    if(sg1.Intersect(sg2)) {
                                        visible = false;
//                                        System.out.println("not visible " + sg1.source().toString() + ":" + sg1.target().toString() + "|" + sg2.source().toString() + ":" + sg2.target().toString());
                                        break;
                                    };
//                                    System.out.println("is visible");
                                }
                                // line segment between first and last vertices
//                                sg2 = new segment(po.getVertices().get(po.getVertices().size()-1), po.getVertices().get(0));
//                                if(sg1.Intersect(sg2)) {
//                                    visible = false;
//                                    System.out.println("not visible " + i + ":" + j + "|" + 0);
//                                    break;
//                                };
//                                if(!visible) { continue; }
                                // this may not be required, need to verify
                                if (polygonList.get(k).getPolygon().contains(mp.get_x(), mp.get_y())) {
                                    isInside = true;
                                    break;
                                }
                            }
                            if (!isInside && visible) {
//                                System.out.println("added mp");
                                myMidPoints.add(new MyMidPoint(mp, counter, i + 1, j + 1));
                                counter+=1;
                            }
//                            myMidPoints.add(new MyMidPoint(mp, i + 1, j + 1));
                        }
                    }
                }

            }
        }

        // with the boundary
        for (int i = 0; i < polygonList.size(); i++) {
            MyPolygon myPolyI = polygonList.get(i);
//            int myPolyISize = myPolyI.getVertices().size();
            for (int j = 0; j < myPolyI.getVertices().size(); j++) {
                my_point vp = myPolyI.getVertices().get(j); // polygon vertex
                for(int bi=0;bi<b.getPoints().size();bi++) {
                    my_point bp = b.getPoints().get(bi); // boundary vertex
                    // do visibility test
                    sg1 = new segment(vp, bp);
                    visible = true;
                    for (int k = 0; k < polygonList.size(); k++) {
                        //check visibility
                        MyPolygon po = polygonList.get(k);
                        for (int poi = 0; poi < po.xCords.size(); poi++) {
                            my_point p1 = po.getVertices().get(poi);
                            my_point p2;
                            if (poi == po.xCords.size() - 1) {
                                // line segment between first and last vertices
                                p2 = po.getVertices().get(0);
                            } else {
                                p2 = po.getVertices().get(poi + 1);
                            }
//                            System.out.println("checking intersectio between " + i + ":" + ii + "|" + j + ":" + jj);
//                            System.out.println("and " + k + ":" + poi + "|" + k + ":" + (poi+1));
//                            if(vx.Is_sameAs(p1) || vx.Is_sameAs(p2) || vy.Is_sameAs(p1) || vy.Is_sameAs(p2)) {
//                                System.out.println("Same point overlap");
//                                continue;
//                            }
                            // to avoid intersection test on common vertex point
                            if(vp.Is_sameAs(p1) || vp.Is_sameAs(p2)) {
//                                System.out.println("Same point overlap");
                                continue;
                            }
                            sg2 = new segment(p1, p2);
                            if (sg1.Intersect(sg2)) {
                                visible = false;
//                                System.out.println("not visible " + sg1.source().toString() + ":" + sg1.target().toString() + "|" + sg2.source().toString() + ":" + sg2.target().toString());
                                break;
                            }
                        }
                    }
                    if(visible) {
                        myMidPoints.add(new MyMidPoint(vp.midPoint(vp, bp), counter, 0, i+1));
                        counter+=1;
                    }
                }
            }
        }

        // Add the start and end points as midpoints to connect
//        myMidPoints.add(new MyMidPoint(startEndObjects[0],0 ));
        if(startEndObjects[1] != null ) { myMidPoints.add(new MyMidPoint(startEndObjects[1],counter )); }
        if(startEndObjects[2] != null ) { myMidPoints.add(new MyMidPoint(startEndObjects[2],counter+1 )); }
        if(startEndObjects[3] != null ) { myMidPoints.add(new MyMidPoint(startEndObjects[3],counter+2 )); }
//        System.out.println("Goal node: "+counter+2);
//        identifyLowClearanceNodes();
    }

    public int frnCount = 0;
    public int invNodeCount = 0;
    public void randomPointGeneration() {
        int n = (int) Integer.parseInt(cp.numOfRandomPoints.getText());
        int x1 = 0, y1 = 0;
        my_point p1 = new my_point();
        boolean invalidPoint = true;
        myMidPoints.clear();
        if(startEndObjects[0] != null ) { myMidPoints.add(new MyMidPoint(startEndObjects[0],0 )); }
        frnCount = 0; invNodeCount = 0;
        int buffer  = 20; // buffer to add some pre-defined clearance from the boundary
        for (int i=0; i<n; i++){
            while(invalidPoint) {
//                x1 = (int) (1150 * Math.random()) + buffer;
//                y1 = (int) (650 * Math.random()) + buffer;
                x1 = (int) (1200 * Math.random()) + buffer;
                y1 = (int) (700 * Math.random()) + buffer;
                p1 = new my_point(x1, y1);
                invalidPoint = pointInsidePolygon(p1);
            }
//            System.out.println("Point: " + x1 + ", " + y1);
            myMidPoints.add(new MyMidPoint(p1, i+1));
            invalidPoint = true;
            frnCount += 1;
        }
        counter = n+1;
        if(startEndObjects[1] != null ) { myMidPoints.add(new MyMidPoint(startEndObjects[1],n+1 )); }
        if(startEndObjects[2] != null ) { myMidPoints.add(new MyMidPoint(startEndObjects[2],n+2 )); }
        if(startEndObjects[3] != null ) { myMidPoints.add(new MyMidPoint(startEndObjects[3],n+3 )); }
    }

    public boolean pointInsidePolygon(my_point p) {
        for(int i=0; i<polygonList.size();i++) {
            if (polygonList.get(i).getPolygon().contains(p.get_x(), p.get_y())) {
//                System.out.println("Invalid : " + p.get_x() + ", " + p.get_y());
                invNodeCount += 1;
                return true;
            }
        }
        return false;
    }
    public ArrayList<Integer> lcNodes = new ArrayList<Integer>();
    int lcThreshold;
    public void identifyLowClearanceNodes(){
        // with the boundary
        segment sg;
        boolean isLCN = false;
        lcThreshold = Integer.parseInt(cp.lcThreshold.getText());
        lcNodes.clear(); // clear the nodes as the threshold changes
        for (int i = 0; i < myMidPoints.size(); i++) {
            isLCN = false;
            my_point node = myMidPoints.get(i).point;
            for (int k = 0; k < polygonList.size(); k++) {
                //check visibility
                MyPolygon po = polygonList.get(k);
                for (int poi = 0; poi < po.xCords.size(); poi++) {
                    my_point p1 = po.getVertices().get(poi);
                    my_point p2;
                    if (poi == po.xCords.size() - 1) {
                        // line segment between first and last vertices
                        p2 = po.getVertices().get(0);
                    } else {
                        p2 = po.getVertices().get(poi + 1);
                    }
                    sg = new segment(p1, p2);
                    if (node.distance(sg) < lcThreshold) {
                        lcNodes.add(i);
                        isLCN = true;
                        break;
                    }
                }
                if(isLCN) { break; }
            }
        }
    }


    public void movePolygon(int x, int y) {
        int ind = Integer.parseInt(cp.textbox.getText()) - 1;
        if (polygonList.size() < ind || polygonList.size() == 0) {
            return;
        }
        MyPolygon mpo = null;
        for(int i=0; i<polygonList.size(); i++) {
            if(polygonList.get(i).getPolygon().contains(initialPointX, initialPointY)) {
                mpo = polygonList.get(i);
            }
        }
        
        if (mpo == null) { return; }
        
//        mpo = polygonList.get(ind);
        mpo.move(x - initialPointX, y - initialPointY);
//        System.out.println("initial point " + initialPointX + ", " + initialPointY);
//        System.out.println("end point " + x + ", " + y);
//        System.out.println("diff point " + (x-initialPointX) + ", " + (y-initialPointY));
        initialPointX = x ;
        initialPointY = y ;
        clearMidPoints();
        repaint();
    }

    public void moveVertex(int x, int y) {
        MyPolygon mpo = polygonList.get(pInd);
        mpo.moveVertex(vInd,x,y);
        clearMidPoints();
        repaint();
    }

    public void clearMidPoints() {
        showNetwork=false; myMidPoints.clear(); counter=0; lcNodes.clear();
    }

    public void createNetwork() {
        showNetwork = true;
        findAllVisibleMidPoints();
        identifyLowClearanceNodes();
        repaint();
//        showNetwork = false;
    }

    public void emptyCircleTest(Graphics g) {
        //find the closest gap
        MyMidPoint imp, jmp;
//        my_point ip, jp;
//        double shortestDistance=999999;
//        for (int i =0; i<myMidPoints.size()-1; i++) {
//            imp = myMidPoints.get(i);
//            for (int j = i+1; j < myMidPoints.size(); j++) {
//                // System.out.println("inside mp drawing");
//                jmp = myMidPoints.get(j);
//                if (imp.point.distance(jmp.point) < shortestDistance ) {
//                    shortestDistance = imp.point.distance(jmp.point);
//                }
//            }
//        }
        // now we have the shortest distance
//        int gridRadius = (int)shortestDistance*3; //grid square size

        BFSTraversal graph = new BFSTraversal(counter+3);
        int gridSize = Integer.parseInt(cp.gridSize.getText()); //grid square size
        int gridRadius = Integer.parseInt(cp.circleSize.getText()); //grid circle size
        // draw grid
        int width = this.getWidth();
        int height = this.getHeight();
//        for (int i = 0; i<this.getWidth(); i=i+gridRadius) {
//            g.drawLine(i,0,i,height);
//        }
//        for (int i = 0; i<height; i=i+gridRadius) {
//            g.drawLine(0,i,width,i);
//        }
        //connect points in circle
        ArrayList<segment> MPLines = new ArrayList<segment>();
        ArrayList<MyMidPoint> MPoints = new ArrayList<MyMidPoint>();
        for(int w=0;w<width;w+=gridSize){
            for(int h=0;h<height;h+=gridSize) {
//                g.drawOval(w,h,gridRadius*2, gridRadius*2);

                for(int i=0;i<myMidPoints.size();i++) {
                    if(cp.avoidLCNodes.isSelected() && lcNodes.contains(i)) { continue; } // Avoid low clearance nodes
                    my_point p = myMidPoints.get(i).point;
                    if (isInside(w+gridRadius,h+gridRadius,gridRadius, p.get_x(), p.get_y())){
                        MPoints.add(myMidPoints.get(i));
                    }
                }

                for (int i=0; i<MPoints.size()-1; i++) {
                    imp = MPoints.get(i);
                    for (int j = i+1; j < MPoints.size(); j++) {
                        // System.out.println("inside mp drawing");
                        jmp = MPoints.get(j);
                        if(isVisible(imp.point, jmp.point)) {
                            graph.insertEdge(imp.n, jmp.n);
                            graph.insertEdge(jmp.n, imp.n);
//                            System.out.println("" + imp.n + " -> " + jmp.n);
                            if(cp.showFullNetwork.isSelected()) {
                                g.setColor(new Color(51, 153, 255));
                                g.drawLine(imp.point.get_x(),imp.point.get_y(),jmp.point.get_x(),jmp.point.get_y());
                                g.setColor(Color.BLACK);
                            }
                        }
                    }
                }
                MPoints.clear();
            }
        }
//        if (cp.showBFSTree.isSelected()) {
            graph.drawBFS(0, g, myMidPoints, cp.showBFSTree.isSelected());
//        }

        int goalIndex = counter;
//        System.out.print(goalIndex);
//        boolean noPath = false;
        if(cp.showBFSPath.isSelected() && !GIChanged) {
            for (int i = 0; i < 3; i++) {
                goalIndex = counter + i;
                int nodeCounter = 0;
                int pIndex;
                double totalDistance = 0;
                double smallestDistance = 99999; // Set a dummy value to mimic infinity
                while (goalIndex != 0 && startEndObjects[i+1] != null) {
//                    System.out.print(" <- " + goalIndex);
                    pIndex = myMidPoints.get(goalIndex).parentIndex;
//                    System.out.println(goalIndex);
//                    System.out.println("parent: " + myMidPoints.get(goalIndex).getParent());
//                    System.out.println("parent index: " + myMidPoints.get(goalIndex).parentIndex);
//                    System.out.println("parent index: " + pIndex);
                    if (myMidPoints.get(goalIndex).getParent() == null) { break; }
                    g.setColor(Color.RED);
                    g.drawLine(myMidPoints.get(goalIndex).point.get_x(), myMidPoints.get(goalIndex).point.get_y(), myMidPoints.get(pIndex).point.get_x(), myMidPoints.get(pIndex).point.get_y());
                    g.setColor(Color.BLACK);

                    totalDistance += myMidPoints.get(goalIndex).point.distance(myMidPoints.get(pIndex).point);

                    // Also compute the lowest clearance
                    double minDistance = computeMinDistance(myMidPoints.get(goalIndex));
                    if (smallestDistance > minDistance ) {
                        smallestDistance = minDistance;
                    }
                    nodeCounter += 1;
                    goalIndex = pIndex;
                }
                System.out.println("Min Clearance for " + (i+1) + " : " + smallestDistance);
                if (smallestDistance > 9999) { continue; }
//                // Print out the stat table for documentation
//                System.out.println("Total nodes : FRN : LC nodes : nodesInPath : Euc Dist : Min Clearance ::: " + invNodeCount);
//                System.out.println("" + (int) Integer.parseInt(cp.numOfRandomPoints.getText())
//                                    + " : " + (frnCount - invNodeCount)
//                                    + " : " + (lcNodes.size())
//                                    + " : " + nodeCounter
//                                    + " : " + totalDistance
//                                    + " : " + smallestDistance);
                // For mid point based
                System.out.println("Total nodes ; LC nodes ; nodesInPath ; Euc Dist ; Min Clearance");
                System.out.println("" + myMidPoints.size()
                        + " ; " + (lcNodes.size())
                        + " ; " + nodeCounter
                        + " ; " + totalDistance
                        + " ; " + smallestDistance);
            }
        }
        // Compute the area
        // The area of the total boundary

        System.out.println("Total area: " + (1200*700));
        MyPolygon pp;
        double sum = 0;
        for(int i =0; i<polygonList.size(); i++) {
            pp = polygonList.get(i);
//            System.out.println("Polygon " + (i + 1) + " area: " + pp.computeArea());
            sum += pp.computeArea();
        }
        System.out.println("Total polygon area: " + sum);
        System.out.println("Total mid points count: " + myMidPoints.size());
        GIChanged = false;
        showNetwork = false;
//        System.out.println("Total counter: " + counter);

        double mp_dist, mp_min_dist = 999999;
        double maxMin = 0;
        for (int i = 0; i < myMidPoints.size()-1; i++) {
            for (int j = i+1; j < myMidPoints.size(); j++) {
                mp_dist = myMidPoints.get(i).point.distance(myMidPoints.get(j).point);
                if(mp_dist < mp_min_dist) {
                    mp_min_dist = mp_dist;
                    System.out.println( mp_dist);
                    System.out.println("" + i + " " + myMidPoints.get(i).point.get_x() + ", " + myMidPoints.get(i).point.get_y());
                    System.out.println("" + j + " " + myMidPoints.get(j).point.get_x() + ", " + myMidPoints.get(j).point.get_y());
                    System.out.println(myMidPoints.get(i).polygon1);
                    System.out.println(myMidPoints.get(i).polygon2);
                    System.out.println(myMidPoints.get(j).polygon1);
                    System.out.println(myMidPoints.get(j).polygon2);
                }
            }
            if (mp_min_dist > maxMin) {
                maxMin = mp_min_dist;
            }
        }
        System.out.println("min mid point gap: " + mp_min_dist);
        System.out.println("max min mid point gap: " + maxMin);
        System.out.println(myMidPoints);

    }

    private double computeMinDistance(MyMidPoint mp) {
//        return 0;
        double minD=99999, dist;
        for (int i = 0; i < polygonList.size(); i++) {
            MyPolygon poly = polygonList.get(i);
            double newDist = 99999;
            for (int j = 0; j < poly.getVertices().size(); j++) {
                my_point p1 = poly.getVertices().get(j);
                my_point p2;
                if (j == poly.getVertices().size() - 1) {
                    // line segment between first and last vertices
                    p2 = poly.getVertices().get(0);
                } else {
                    p2 = poly.getVertices().get(j + 1);
                }
                // Now calculate the distance between the vertices and the path point
                dist = mp.point.distance(p1);
                if (minD > dist) {
                    minD = dist;
                }

                // Calculate the distance between the edges and the path point
                dist = mp.point.distance(new segment(p1, p2));
                if (minD > dist) {
                    minD = dist;
                }
            }
        }
        return minD;
    }

    private boolean isVisible(my_point firstPoint, my_point secondPoint) {
        boolean visible = true;
        segment sg1 = new segment(firstPoint, secondPoint);
        for (int k = 0; k < polygonList.size(); k++) {
            //check visibility
            MyPolygon po = polygonList.get(k);
            for(int poi=0;poi<po.xCords.size();poi++) { // number of vertices in the polygon
                my_point p1 = po.getVertices().get(poi);
                my_point p2;
                if(poi == po.xCords.size()-1) {
                    // line segment between first and last vertices
                    p2 = po.getVertices().get(0);
                } else {
                    p2 = po.getVertices().get(poi + 1);
                }
//                System.out.println("and " + k + ":" + poi + "|" + k + ":" + (poi+1));
                if(firstPoint.Is_sameAs(p1) || firstPoint.Is_sameAs(p2) || secondPoint.Is_sameAs(p1) || secondPoint.Is_sameAs(p2)) {
//                    System.out.println("Same point overlap");
                    continue;
                }
                segment sg2 = new segment(p1, p2);
                if(sg1.Intersect(sg2)) {
                    visible = false;
//                    System.out.println("not visible " + sg1.source().toString() + ":" + sg1.target().toString() + "|" + sg2.source().toString() + ":" + sg2.target().toString());
                    return false;
                };
//                System.out.println("is visible");
            }

            // line segment between first and last vertices
//                                sg2 = new segment(po.getVertices().get(po.getVertices().size()-1), po.getVertices().get(0));
//                                if(sg1.Intersect(sg2)) {
//                                    visible = false;
//                                    System.out.println("not visible " + i + ":" + j + "|" + 0);
//                                    break;
//                                };
//                                if(!visible) { continue; }
//            // this may not be required, need to verify
//            if (polygonList.get(k).getPolygon().contains(mp.get_x(), mp.get_y())) {
//                isInside = true;
//                break;
//            }
        }
        return true;
//        if (!isInside && visible) {
//            System.out.println("added mp");
//            myMidPoints.add(new MyMidPoint(mp, i + 1, j + 1));
//        }
    }

    static boolean isInside(int circle_x, int circle_y,
                            int rad, int x, int y)
    {
        // Compare radius of circle with
        // distance of its center from
        // given point
        if ((x - circle_x) * (x - circle_x) +
                (y - circle_y) * (y - circle_y) <= rad * rad)
            return true;
        else
            return false;
    }

    class MyKeyListener extends KeyAdapter implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
            if(cp.editPolygon.isSelected()){
//                System.out.println(e.getKeyChar());
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(cp.editPolygon.isSelected()){
//                System.out.println(e.getKeyChar());
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(cp.editPolygon.isSelected()){
//                System.out.println(e.getKeyChar());
            }
        }

    }
    class MyMouseListener extends MouseAdapter {
        //        @Override
        public void mouseDragged(MouseEvent e){
//            System.out.println("" + e.getX() + ", " + e.getY());
            if(cp.movePolygon.isSelected()) {
                movePolygon(e.getX(), e.getY());
            }

            if(cp.editPolygon.isSelected()) {
                moveVertex(e.getX(), e.getY());
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            coorLabel.setText("" + x + ", " + y);
            if(cp.editPolygon.isSelected()) {
                my_point cursor = new my_point(x,y);
                System.out.print("\r" + x + ", " + y);
                double closeDist = 999999;
                MyPolygon mp;
//                int pind, vind;
                for(int i=0;i<polygonList.size();i++) {
                    int vsize = polygonList.get(i).getVertices().size();
                    for(int j=0;j<vsize;j++){
                        my_point mpp=polygonList.get(i).getVertices().get(j);
                        if (cursor.distance(mpp) < closeDist) {
                            closeDist = cursor.distance(mpp);
                            pInd = i;
                            vInd = j;
                        }
                    }
                }
                repaint();
            }
        }
    }

    private int GIClickCounter = 0;
    private boolean GIChanged = false;
    //Create new polygons
    @Override
    public void mouseClicked(MouseEvent e) {
        if(cp.movePolygon.isSelected()) { return; }

        int x = e.getX();
        int y = e.getY();
        initialPointX = x;
        initialPointY = y;
        Graphics g = getGraphics();
        System.out.println(x + " " + y);

        if(cp.editPolygon.isSelected()) {
//            System.out.println("selected point is "+x+","+y+"of poly "+pInd + ":" +vInd);
            editSelected = true;
            return;
        }

        if(cp.sourceNodeCheck.isSelected()) {
            if(startEndObjects[0] != null) { GIChanged = true; }
            startEndObjects[0] = new my_point(x, y);
            repaint();
            return;
        } else if(cp.goalNodeCheck.isSelected()) {

//            for(int i =1;i<=3;i++) {
//                if(startEndObjects[i] == null) {
//                    startEndObjects[i] = new my_point(x, y);
//                    break;
//                }
//            }
            int i = (GIClickCounter % 3) + 1;
            startEndObjects[i] = new my_point(x, y);
            GIClickCounter += 1;
            if (GIClickCounter > 3) { GIChanged = true; }
            repaint();
            return;
        }

        int ind = Integer.parseInt(cp.textbox.getText()) - 1;
        clearMidPoints();
        if(poly.size() < ind + 1) { poly.add(new Poly()); }

//        System.out.println("Adding to poly" + ind);
        poly.get(ind).xCords.add(x);
        poly.get(ind).yCords.add(y);

        // My Polygon implementation
        if(polygonList.size() < ind+1) { polygonList.add(new MyPolygon()); }
        polygonList.get(ind).addPoint(x, y);
//        mPolygon.drawPoints();
//        polygonList.add(mPolygon);
        ///
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

