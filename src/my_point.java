package src;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

//Author: Laxmi  P  Gewali  Jan 31 2000

public class my_point extends Object
{
   private int x,y;

   // default is (0,0)
   public my_point()
   {
      x = 0;
      y = 0;
   }

   public my_point(int x1, int y1)
   {
      x = x1;
      y = y1;
   }

   // Returns x-coordinate.
   public int get_x()
   {
      return x;
   }

   // Returns y-coordinate.`
   public int get_y()
   {
      return y;
   }
   
   void set_x(int a) { x = a;}
   
   void set_y(int b) {y = b;}

   private int square(int i)
   {
      return i*i;
   }
   
   void print(){
	   //System.out.println("(" + get_x() + "," + get_y() + ")");
	   System.out.println(get_x() + " " + get_y());
   }

   // Returns distance from 'this' point to 'p'
   public double distance(my_point p)
   {
      return
         Math.sqrt(square(x-p.get_x())+
                  square(y-p.get_y()));
   }

   // Returns distance from 'this' point to 'p'
   public int distance_sq(my_point p)
   {
      return ( square(x-p.get_x()) + square(y-p.get_y()) );
   }
  

   // Returns the square of the distance from 'this' point to segment s
   public int distance_sq(segment s){
      line l1 = new line(s); // l1 is the line through segment s
      line l2 = l1.perp_line(this); // l2 is the line through 'this' and perp to l1
      int d1_sq = this.distance_sq(s.source());
      int d2_sq = this.distance_sq(s.target());
      if (l2.Are_onSame_side(s.source(), s.target())) {
         // return the smaller of d1_sq and d2_sq
         if (d1_sq < d2_sq) return d1_sq;
         else return d2_sq;
      }
      // l2 and s intersect
      my_point p1 = s.Compute_Intersect(l2);
      int d3_sq = this.distance_sq(p1);
      return d3_sq;
   }
  // Returns the distance from 'this' point to segment
  // writeen by Sun
  public double distance(segment s){
      line l1 = new line(s); // l1 is the line through segment s
      line l2 = l1.perp_line(this); // l2 is the line through 'this' and perp to l1
      double d1 = this.distance(s.source());
      double d2 = this.distance(s.target());
      if (l2.Are_onSame_side(s.source(), s.target())) {
         // return the smaller of d1_sq and d2_sq
         if (d1 < d2) return d1;
         else return d2;
      }
      // l2 and s intersect
      my_point p1 = s.Compute_Intersect(l2);
      double d3= this.distance(p1);
      return d3;
   }
  // Returns the distance from 'this' point to line l
  // writeen by Sun

  public double distance(line l){
     line l1=l.perp_line(this); //l1 is a line through 'this' and perp to l
     my_point p=l.Compute_Intersect(l1);
     double d=this.distance(p);
     return d;
  }

 public int distance_sq(line l){
     line l1=l.perp_line(this); //l1 is a line through 'this' and perp to l
     my_point p=l.Compute_Intersect(l1);
     int d=this.distance_sq(p);
     return d;
  }
   // Draws the point using 'g'
   public void draw(Graphics g, Color color)
   {
      g.setColor(color);
      g.fillRoundRect(x-1,y-1,2,2,2,2);
   }

   // size of the point to be drawn is also specified.
   public void draw(Graphics g, Color color, int size)
   {
      g.setColor(color);
      g.fillRoundRect(x-size,y-size,2*size,2*size,2*size,2*size);
   }


   // Update the point with new coordinates.
   public void update(int x1, int y1)
   {
      x = x1;
      y = y1;
   }

   // returns a clone of 'this'.
   public my_point my_clone()
   {
      my_point p = new my_point(x,y);
      return p;
   }

   // Checks this point is same as 'p'
   public boolean Is_sameAs(my_point p)
   {
      if((x == p.get_x()) && (y == p.get_y()))
         return true;
      return false;
   }

   // Returns twice the area the point 'this' forms with
   // the points p,q in the order p,q,this.
   public int Area2(my_point p, my_point q)
   {
      return
         (q.get_x() - p.get_x()) * (y - p.get_y()) -
         (x - p.get_x()) * (q.get_y() - p.get_y());
   }
   
   //Returns the absolute value of the area of this with points p1, p2
   public int Area2Abs(my_point p, my_point q) {
	   int x1 = this.get_x(), y1 = this.get_y();
	   int x2 = p.get_x(), y2 = p.get_y();
	   int x3 = q.get_x(), y3 = q.get_y();
	   return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2)));
   }
   
   public boolean insideTriangle(my_point p1, my_point p2, my_point p3) {
	   //int x = this.get_x(), y = this.get_y();
	   int A = p1.Area2Abs(p2, p3);
	   int A3 = this.Area2Abs(p1,p2);
	   int A2 = this.Area2Abs(p1,p3);
	   int A1 = this.Area2Abs(p2,p3);
	   
	   return (A == A1 + A2 + A3);
	   /*
	    * bool isInside(int x1, int y1, int x2, int y2, int x3, int y3, int x, int y){  
   // Calculate area of triangle ABC 
   float A = area (x1, y1, x2, y2, x3, y3);
 
   // Calculate area of triangle PBC   
   float A1 = area (x, y, x2, y2, x3, y3);
 
   // Calculate area of triangle PAC   
   float A2 = area (x1, y1, x, y, x3, y3);
 
   // Calculate area of triangle PAB    
   float A3 = area (x1, y1, x2, y2, x, y);
   
   // Check if sum of A1, A2 and A3 is same as A 
   return (A == A1 + A2 + A3);
   }
	    */
	   
   }

   // Returns true iff the point 'this' is collinear with p,q.
   public boolean Collinear(my_point p, my_point q)
   {
      return Area2(p,q) == 0;
   }

   // returns true iff 'this' point is strictly to the left
   // of the line through points p,q.

   public boolean Left(my_point p, my_point q)
   {
      return Area2(p,q) > 0;
   }

   // returns true iff 'this' point is strictly to the right
   // of the line through points p,q.
   public boolean Right(my_point p, my_point q)
   {
      return Area2(p,q) < 0;
   }

   // returns true iff 'this' point is to the left or on
   // the line through points p,q.

   public boolean LeftOn(my_point p, my_point q)
   {
      return Area2(p,q) >= 0;
   }

   // returns true iff 'this' point is to the right or on
   // the line through points p,q.
   public boolean RightOn(my_point p, my_point q)
   {
      return Area2(p,q) <= 0;
   }


   //  Supposed to be a global function - where to put ?
   boolean Xor(boolean b1, boolean b2)
   {
      return (b1 || b2) && !(b1 && b2);
   }

   // returns true iff segments (this,p) and (q,r)
   // intersect properly (improper cases are eliminated).
   public boolean IntersectProp (my_point p, my_point q, my_point r)
   {
      // eliminate improper cases.

      if( this.Collinear(p,q) ||
         this.Collinear(p,r) ||
         q.Collinear(r,this) ||
         q.Collinear(r,p)
         )
         return false;

      return
         Xor(q.Left(this,p), r.Left(this,p)) &&
         Xor(this.Left(q,r), p.Left(q,r));
   }

   public boolean IntersectPropNonCollinear(my_point p, my_point q, my_point r)
   {
      return (    (this.IntersectProp(p,q,r))
               && (! (this.Collinear(q,r)))
             );
   }


   // Returns true iff (a,b,this) are collinear and point 'this' lies
   // on the closed segment ab.

   public boolean Between(my_point p, my_point q)
   {
      if(!Collinear(p,q))
         return false;
      // if 'ab' is vertical, check betweenness on y, else on x.
      if(p.get_x() == q.get_x())
         return ((p.get_y() <= y) && (y <= q.get_y())) ||
               ((p.get_y() >= y) && (y >= q.get_y()));
      else
         return ((p.get_x() <= x) && (x <= q.get_x())) ||
               ((p.get_x() >= x) && (x >= q.get_x()));
   }

   // Returns true iff segments (this,p) and (q,r) intersect,
   // properly or improperly.

   public boolean Intersect(my_point p, my_point q, my_point r)
   {
      if(this.IntersectProp(p,q,r))
         return true;
      if(q.Between(this,p) ||
         r.Between(this,p) ||
         this.Between(q,r) ||
         p.Between(q,r))
         return true;
      return false;
   }


   // returns true if 'this' point forms a convex quadrilateral
   // with the points p,q,r in that order.

   public boolean isConvex(my_point p, my_point q, my_point r)
   {
      if(this.IntersectProp(q,p,r))
         return true;
      return false;
   }
   
   public my_point midPoint(my_point p1, my_point p2) {
     return new my_point((p1.get_x()+p2.get_x())/2, (p1.get_y()+p2.get_y())/2);
   }
   
   public my_point circleCenter(my_point p1, my_point p2, my_point p3) {
     my_point m1 = midPoint(p1, p2);
     my_point m2 = midPoint(p2, p3);
     line L1 = new line(p1, p2);
     line L2 = new line(p2, p3);
     //equation of a line through m1 and perp to line thru p1 and p2
     line L3 = L1.perp_line(m1);
     //equation of a line through m2 and perp to line thru p2 and p3
     line L4 = L2.perp_line(m2);
     
     return L4.Compute_Intersect(L3);
   }
   
   public my_point circleCenter1(my_point p1, my_point p2, my_point p3) {
    // this formula is given on page 201
    // (Computational Geometry in C, First Edition, 1993, By O'Rrourke)
     int a0=p1.get_x(), a1=p1.get_y();
     int b0=p2.get_x(), b1=p2.get_y();
     int c0=p3.get_x(), c1=p3.get_y();
     int D=2*(a1*c0 + b1*a0 - b1*c0 -a1*b0 - c1*a0 + c1*b0);
     int cx = (b1*a0*a0 - c1*a0*a0 - b1*b1*a1 + c1*c1*a1 + b0*b0*c1 + a1*a1*b1
             + c0*c0*a1 - c1*c1*b1 - c0*c0*b1 - b0*b0*a1 + b1*b1*c1 - a1*a1*c1)/D;
     int cy = (a0*a0*c0 + a1*a1*c0 + b0*b0*a0 - b0*b0*c0 + b1*b1*a0 - b1*b1*c0
             - a0*a0*b0 - a1*a1*b0 - c0*c0*a0 + c0*c0*b0 - c1*c1*a0 + c1*c1*b0)/D;
     my_point cntr = new my_point(cx, cy);
     return cntr;
   }
     
     
   
}

/**
 * Calculates the angle from centerPt to targetPt in degrees.
 * The return should range from [0,360), rotating CLOCKWISE, 
 * 0 and 360 degrees represents NORTH,
 * 90 degrees represents EAST, etc...
 *
 * Assumes all points are in the same coordinate space.  If they are not, 
 * you will need to call SwingUtilities.convertPointToScreen or equivalent 
 * on all arguments before passing them  to this function.
 *
 * @param centerPt   Point we are rotating around.
 * @param targetPt   Point we want to calcuate the angle to.  
 * @return angle in degrees.  This is the angle from centerPt to targetPt.
 */

//public static double calcRotationAngleInDegrees(Point centerPt, Point targetPt)
//{
    // calculate the angle theta from the deltaY and deltaX values
    // (atan2 returns radians values from [-PI,PI])
    // 0 currently points EAST.  
    // NOTE: By preserving Y and X param order to atan2,  we are expecting 
    // a CLOCKWISE angle direction.  
    //double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);

    // rotate the theta angle clockwise by 90 degrees 
    // (this makes 0 point NORTH)
    // NOTE: adding to an angle rotates it clockwise.  
    // subtracting would rotate it counter-clockwise
    //theta += Math.PI/2.0;

    // convert from radians to degrees
    // this will give you an angle from [0->270],[-180,0]
    //double angle = Math.toDegrees(theta);

    // convert to positive range [0-360)
    // since we want to prevent negative angles, adjust them now.
    // we can assume that atan2 will not return a negative value
    // greater than one partial rotation
    //if (angle < 0) {
    //    angle += 360;
    //}

    //return angle;
//}

