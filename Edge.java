
public class Edge {
	public Node to;
	public double distance;
	public boolean dist_found;
	Edge twin;
	
	public Edge(Node t){
		to=t;
		dist_found=false;
	}
	
	public boolean add_distance(double dist){
		if (dist_found)
			return false;
		dist_found=true;
		distance=dist;
		return true;
	}
}

