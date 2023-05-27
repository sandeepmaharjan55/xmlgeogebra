package src;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;




// Class LINE

public class line extends Object
{
   // Only vertical lines are maintained as "x=my+c".
   // All the other lines are maintained as "y=mx+c".
   boolean vertical;
   boolean horizontal;

   private double Slope;
   private double Intercept;

   // Approximation error value to see if a point lies on the line.
   private final double Epsilon = 0.05;

   public line ()
   {
      // x-axis(by default)
      vertical = false;
      horizontal = true;
      Slope = 0;
      Intercept = 0;
   }

   // Slope and Intercept are the parameters.
   public line(double a, double b)
   {
      vertical = false;
      horizontal = true;
      Slope = a;
      Intercept = b;
   }


   // If p1 and p2 are the same, returns a vertical thru p1.
   public line (my_point p1, my_point p2)
   {
      int x1 = p1.get_x();
      int x2 = p2.get_x();
      int y1 = p1.get_y();
      int y2 = p2.get_y();

      if(x2 == x1)  // vertical line
      {
         vertical = true;
         horizontal = false;
         Slope = 0;
         Intercept = x1;
      }
      else        // Any line other than vertical.
      {
         vertical = false;
         if(y2 == y1)
            horizontal = true;
         else
            horizontal = false;

         Slope = (double)(y2-y1)/(double)(x2-x1);
         Intercept = (double)(x2*y1 - x1*y2)/(double)(x2-x1);
      }
   }



   // line passing thru the segment 'sg'
   public line(segment sg)
   {
      // We don't need to worry about object references because
      // sg.source() returns not the sg.Source, but a clone of it.
      my_point p1 = sg.source();
      my_point p2 = sg.target();
      int x1 = p1.get_x();
      int x2 = p2.get_x();
      int y1 = p1.get_y();
      int y2 = p2.get_y();

      if(x2 == x1)  // vertical line
      {
         vertical = true;
         horizontal = false;
         Slope = 0;
         Intercept = x1;
      }
      else        // Any other line
      {
         vertical = false;
         if(y2 == y1)
            horizontal = true;
         else
            horizontal = false;
         Slope = (double)(y2-y1)/(double)(x2-x1);
         Intercept = (double)(x2*y1 - x1*y2)/(double)(x2-x1);
      }
   }



   // constructors are done.

   public boolean Is_vertical()
   {
      return vertical;
   }

   public boolean Is_horizontal()
   {
      return horizontal;
   }

   // Returns the slope of this line.
   // for vertical lines misleading - should be careful.
   public double slope()
   {
      return Slope;
   }

   public double intercept()
   {
      return Intercept;
   }

   public line my_clone()
   {
      line ln = new line();
      ln.update(Slope, Intercept, vertical, horizontal);
      return ln;
   }

   // May not draw in the window - adapt another method.
   public void draw(Graphics g, Color color)
   {
      g.setColor(color);
      if(vertical)
         g.drawLine((int)Intercept,0,(int)Intercept,1000);
      else
         if(horizontal)
            g.drawLine(0,(int)Intercept,1000,(int)Intercept);
         else
            g.drawLine(0,(int)Intercept,(int)((200-Intercept)/Slope),200);
   }

   // Approximation is used to find whether a point lies on this line or not.
   public boolean liesOn(my_point p)
   {
      int x1 = p.get_x();
      int y1 = p.get_y();

      if(Math.abs((double)y1-(Slope*x1+Intercept)) < Epsilon)
         return true;
      return false;
   }

   // returns true iff p1 and p2 are on the same side(left or right).
   // Not a good idea to find two points on the line(because of rounding)-any other way ?
   public boolean Are_onSame_side(my_point p1, my_point p2)
   {
      my_point pt1,pt2;
      int I = (int)Math.round(Intercept);

      if(vertical)
      {
         pt1 = new my_point(I,0);
         pt2 = new my_point(I,100);
      }
      else
         if(horizontal)
         {
            pt1 = new my_point(0,I);
            pt2 = new my_point(100,I);
         }
         else
         {
            pt1 = new my_point(0,I);
            pt2 = new my_point((int)Math.round(-Intercept/Slope),0);
         }

      if((p1.Left(pt1,pt2) && p2.Left(pt1,pt2)) ||
         (p1.Right(pt1,pt2) && p2.Right(pt1,pt2)))
            return true;

      return false;
   }

   public boolean Are_onLeft_side(my_point p1, my_point p2)
   {
      my_point pt1,pt2;
      int I = (int)Math.round(Intercept);

      if(vertical)
      {
         pt1 = new my_point(I,0);
         pt2 = new my_point(I,100);
      }
      else
      if(horizontal)
      {
         pt1 = new my_point(0,I);
         pt2 = new my_point(100,I);
      }
      else
      {
         pt1 = new my_point(0,I);
         pt2 = new my_point((int)Math.round(-Intercept/Slope),0);
      }

      if(p1.Left(pt1,pt2) && p2.Left(pt1,pt2))
         return true;

      return false;
   }


   // updates the line with new info.
   private void update (double s, double cut, boolean bv, boolean bh)
   {
      Slope = s;
      Intercept = cut;
      vertical = bv;
      horizontal = bh;
   }

   // Returns a new line parallel to 'this' line and passing
   // through point p1.
   public line parl_line(my_point p1)
   {
      int x1 = p1.get_x();
      int y1 = p1.get_y();
      line ln = new line();

      if(vertical)
         ln.update(0,x1,true,false);
      else
      {
         double s = Slope;
         double cut = y1 - s*x1;
         ln.update(s,cut,false,horizontal);
      }

      return ln;
   }

   // Returns a new line perpendicular to 'this' line and passing
   // through point p1. ROTATE by 90 Degrees in Anti-CLK wise dir.
   public line perp_line(my_point p1)
   {
      int x1 = p1.get_x();
      int y1 = p1.get_y();
      line ln = new line();

      if(horizontal)
         ln.update(0,x1,true,false);
      else
      {
         double s;
         if(vertical)
            s = 0;
         else
            s = -(1.0/Slope);
         double cut = y1 - s*x1;
         ln.update(s,cut,false,vertical);
      }

      return ln;
   }


   public boolean Is_parallel(line l)
   {
      if(vertical && l.Is_horizontal())
         return false;
      if(horizontal && l.Is_vertical())
         return false;
      if(Slope == l.slope())
         return true;
      return false;
   }

   public boolean Is_perpendicular(line l)
   {
      // Will it be exactly -1 after rounding off?
      if(vertical && l.Is_horizontal())
         return true;
      if(horizontal && l.Is_vertical())
         return true;
      if(Slope * l.slope() == -1)
            return true;
      return false;
   }

   // returns intersection point of this line with 'l'
   // if both are parallel, returns (0,0) - can be misleading
   //  So PRECONDITION - lines are not parallel.

   public my_point Compute_Intersect(line l)
   {
     // Any problems with rounding off !?!

      my_point p = new my_point();
      if(Is_parallel(l) == false)
      {
         double m1,k1,m2,k2;
         int px, py;

         if(vertical) // this is vertical.
         {
            if(l.Is_horizontal())
            {
               px = (int)Math.round(Intercept);
               py = (int)Math.round(l.intercept());
            }
            else
            {
               // change 'l' into vertical format and then compute.
               m1 = Slope;
               k1 = Intercept;
               m2 = 1.0/(l.slope());
               k2 = -(l.intercept() * m2);
               py = (int)Math.round((k1-k2)/(m2-m1));
               px = (int)Math.round((k1*m2-k2*m1)/(m2-m1));
            }
         }
         else
         {
            if(l.Is_vertical()) // line 'l' is vertical.
            {
               if(horizontal)
               {
                  px = (int)Math.round(l.intercept());
                  py = (int)Math.round(Intercept);
               }
               else
               {
                  // change 'this' into vertical format
                  m1 = 1.0/Slope;
                  k1 = -(Intercept * m1);
                  m2 = l.slope();
                  k2 = l.intercept();
                  py = (int)Math.round((k2-k1)/(m2-m1));
                  px = (int)Math.round((k1*m2-k2*m1)/(m2-m1));

               }
            }
            else
            {
               // none of them is vertical. So just compute.
               m1 = Slope;
               k1 = Intercept;
               m2 = l.slope();
               k2 = l.intercept();
               px = (int)Math.round((k1-k2)/(m2-m1));
               py = (int)Math.round((k1*m2-k2*m1)/(m2-m1));
            }
         }

         p.update(px,py);
      }

      return p;
   }
}


