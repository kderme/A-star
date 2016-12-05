import java.util.Comparator;

public class Point {
	public double x,y;

	Point(double a, double b){
		x=a;
		y=b;
	}

	public double distance(Point P){
		double dx=P.x-x;
		double dy=P.y-y;
		return (double)Math.sqrt(dx*dx+dy*dy);
	}

	public boolean eq_coord(Point two){
		return two.x==x && two.y==y;
	}
	
	public void print(){
		System.out.println("  "+x+" "+y);
	}
	
	static class xComparator implements Comparator<Point>
	 {
		public int compare(Point one, Point two){
			if(one.x-two.x>0)
				return 1;
			else if(one.x-two.x<0)
				return -1;
			
			else if(one.y-two.y>0)
				return 1;
			else if(one.y-two.y<0)
				return -1;
			
			return 0;
		}
	 }
}

