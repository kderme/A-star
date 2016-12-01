import java.util.Comparator;
import java.util.Comparator;


public class Point {
	public float x,y;

	Point(float a, float b){
		x=a;
		y=b;
	}

	public float distance(Point P){
		float dx=P.x-x;
		float dy=P.y-y;
		return (float)Math.sqrt(dx*dx+dy*dy);
	}

	public boolean eq_coord(Point two){
		return two.x==x && two.y==y;
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

