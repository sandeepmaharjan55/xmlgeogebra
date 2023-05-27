package src;

import java.awt.*;


//Author: Laxmi  P  Gewali


// Class segment

public class segment
{
    private my_point Source, Target;

    public segment()
    {
        // (0,0) => (0,0)
        Source = new my_point();
        Target = new my_point();
    }

    public segment(my_point p1, my_point p2)
    {
        Source = p1.my_clone();
        Target = p2.my_clone();
    }


    public segment(int x1, int y1, int x2, int y2)
    {
        Source = new my_point(x1, y1);
        Target = new my_point(x2, y2);
    }
    // Constructors are done.

    public void update(my_point p1, my_point p2)
    {
        Source.update(p1.get_x(),p1.get_y());
        Target.update(p2.get_x(),p2.get_y());
    }


    public void update(int x1, int y1, int x2, int y2)
    {
        Source.update(x1, y1);
        Target.update(x2, y2);
    }




    public double length()
    {
        return Source.distance(Target);
    }

    public double length_sq()
    {
        int x1 = Source.get_x();
        int y1 = Source.get_y();
        int x2 = Target.get_x();
        int y2 = Target.get_y();
        return ((double)((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
    }

    public segment my_clone()
    {
        return new segment(Source.my_clone(), Target.my_clone());
    }

    public void draw(Graphics g, Color color)
    {
        g.setColor(color);
        g.drawLine(Source.get_x(),Source.get_y(),
                Target.get_x(),Target.get_y());
    }

    public my_point source()
    {

        return Source.my_clone();
    }

    public my_point target()
    {
        return Target.my_clone();
    }

    public boolean Is_sameAs(segment sg)
    {
        if((Source.Is_sameAs(sg.source())) && (Target.Is_sameAs(sg.target())))
            return true;
        return false;
    }

    public boolean Left(my_point p)
    {
        return p.Left(Source,Target);
    }

    public boolean Right(my_point p)
    {
        return p.Right(Source,Target);
    }

    public boolean LeftOn(my_point p)
    {
        return p.LeftOn(Source,Target);
    }

    public boolean RightOn(my_point p)
    {
        return p.RightOn(Source,Target);
    }

    public double sinTheta()
    {
        double len = Source.distance(Target);
        return (double)(Target.get_y()-Source.get_y())/len;
    }

    public double cosTheta()
    {
        double len = Source.distance(Target);
        return (double)(Target.get_x()-Source.get_x())/len;
    }

    public segment reverse()
    {
        return new segment(Target.my_clone(),Source.my_clone());
    }


    // Angle made by 'this' segment with x-axis in anti-clockwise direction.
   /*
    E = 6.283185307179586
   NE= 5.497787143782138
   N = 4.71238898038469
   NW= 3.9269908169872414
   W = 3.141592653589793
   SW= 2.356194490192345
   S = 1.5707963267948966
   SE= 0.7853981633974484

    */
    public double dir()
    {
        double len = Source.distance(Target);
        double k = (Target.get_y()-Source.get_y())/len;

        int x2 = Target.get_x();
        int x1 = Source.get_x();

        // Because of Java's convention, ORIGIN is at the left most corner of the Canvas.
        // So 1,4  and 2,3  quadrant's are interchanged.

        if(k>=0)
        {
            if(x2 >= x1)  // First Quadrant.
                return (2.0*Math.PI - Math.asin(k));
            else          // Second Quadrant.
                return (Math.PI + Math.asin(k));
        }
        else
        {
            if(x2 >= x1)  // Fourth Quadrant.
                return Math.asin(-k);
            else          // Third Quadrant.
                return (Math.PI - Math.asin(-k));
        }
    }


    public double turn(my_point p1){
        double dir1, dir2, turn;
        segment s1 = this;
        segment s2 = new segment(this.Target,p1);
        dir1 = s1.dir();
        dir2 = s2.dir();
        if (this.Left(p1)) {
            if (dir2 < dir1) turn = dir2 - dir1;
            else turn = -2*(Math.PI) + dir2 - dir1;
        }
        else {
            if (dir2 > dir1) turn = dir2 - dir1;
            else turn = 2*(Math.PI)-dir1 + dir2;
        }
        return turn;
    }

    public void print(){
        my_point p1, p2;
        p1 = source();
        p2 = target();
        System.out.println("(" + p1.get_x() + "," + p1.get_y() +  "," + p2.get_x() + "," + p2.get_y());
    }


    // The angle from 'this' segment to 'sg', in anti-clockwise direction.
    // All angles are positive. Only anti-clockwise direction.
    public double angle(segment sg)
    {
        double a1 = dir();
        double a2 = sg.dir();
        if(a2>=a1)
            return a2-a1;
        else
            return 2.0*Math.PI-(a1-a2);
    }

    public boolean Intersect(my_point p1, my_point p2)
    {
        if(Source.Intersect(Target,p1,p2))
            return true;
        return false;
    }

    public boolean IntersectProp(my_point p1, my_point p2)
    {
        if(Source.IntersectProp(Target,p1,p2))
            return true;
        return false;
    }

    public boolean IntersectPropNonCollinear(my_point p1, my_point p2)
    {
        if(    (Source.IntersectProp(Target,p1,p2))
                && (!(Source.Collinear(p1, p2)))
        )
            return true;
        return false;
    }

    public boolean IntersectProp(segment sg)
    {
        if(Source.IntersectProp(Target,sg.source(),sg.target()))
            return true;
        return false;
    }

    public boolean IntersectPropNonCollinear(segment sg)
    {
        if(Source.IntersectPropNonCollinear(Target,sg.source(),sg.target()))
            return true;
        return false;
    }

    public boolean Intersect(segment sg)
    {
        if(Source.Intersect(Target,sg.source(),sg.target()))
            return true;
        return false;
    }


    public my_point Compute_Intersect(segment sg)
    {
        my_point p = new my_point();

        if(this.Intersect(sg) == true)
        {
            line l1 = new line(this);
            line l2 = new line(sg);
            p = l1.Compute_Intersect(l2);
        }
        return p;
    }

    public boolean Intersect(line l)  //  Testing should be done.
    {
        if(IntersectProp(l))
            return true;
        if(l.liesOn(Source) || l.liesOn(Target))
            return true;
        return false;
    }

    public boolean IntersectProp(line l)
    {
        // Check if this segment is completely on a single side.
        if(l.Are_onSame_side(Source,Target))
            return false;
        return true;
    }

    public my_point Compute_Intersect(line l)
    {
        if(Intersect(l))
        {
            line ln = new line(this);
            if(l.liesOn(Source))
                return Source;
            if(l.liesOn(Target))
                return Target;
            return ln.Compute_Intersect(l);
        }
        else
            return new my_point();
    }

    // This method returns a line parallel to 'this' segment
    // and at a distance 'dist' towards its left side.

    // Here left, right is designed for JAVA only. For Usual Coordinate-System
    // Interchange these two methods.
    public line parl_line_onLeft(double dist)
    {
        int newx = Source.get_x() + (int)Math.round(dist*sinTheta());
        int newy = Source.get_y() - (int)Math.round(dist*cosTheta());

        line ln = new line(this);
        my_point newp = new my_point(newx,newy);
        return ln.parl_line(newp);
    }

    public line parl_line_onRight(double dist)
    {
        int newx = Source.get_x() - (int)Math.round(dist*sinTheta());
        int newy = Source.get_y() + (int)Math.round(dist*cosTheta());

        line ln = new line(this);
        my_point newp = new my_point(newx,newy);
        return ln.parl_line(newp);
    }


    // returns the mid point of this line segment
    public my_point mid_point() {
        int x = (int)((source().get_x() + target().get_x())/2.0);
        int y = (int)((source().get_y() + target().get_y())/2.0);
        return new my_point(x, y);
    }

    //returns the target point of the segment formed
    //by stretching/contracting this segment "a" times
    public my_point stretchOrContract(double a) {
        double L = this.length();
        double cosTHETA = this.cosTheta();
        double sinTHETA = this.sinTheta();
        int x = (int)(a*L*(this.cosTheta()) + this.target().get_x());
        int y = (int)(a*L*(this.sinTheta()) + this.target().get_y());
        return new my_point(x, y);
    }

}



