package src;

import java.util.ArrayList;
import java.util.Collections;

public class Poly {
    public ArrayList<Integer> xCords = new ArrayList<Integer>();
    public ArrayList<Integer> yCords = new ArrayList<Integer>();

    public int index;

    public int centerX, centerY;

    Poly() {

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
}
