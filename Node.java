import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Node extends Point{
	public int id;
	public String name;
	public ArrayList<Edge> adj;
	
	public double hEuclDist;
	public double gScore;
	public double fScore;
	Edge parent;

	
	public Node(double a, double b, int d,String s){
		super(a,b);
		id=d;
		name=s;
		parent=null;
		adj=new ArrayList<Edge>();
	}
	
	public Node next(int i,ArrayList<Node> nodes){
		/*	This function is not safe	*/
		return nodes.get(i+1);
	}
	
	public void add_next(int i,ArrayList<Node> nodes){
		if(i+1<nodes.size()){
			Node n=next(i,nodes);
			if(n.name.equals(name)){
				Edge e1=new Edge(n);
				Edge e2=new Edge(this);
				adj.add(e1);
				n.adj.add(e2);
				e1.twin=e2;
				e2.twin=e1;
			}
		}
	}
	
	public Node prev(int i,ArrayList<Node> nodes){
		/*	This function is not safe	*/
		return nodes.get(i-1);
	}
	
	public void add_prev(int i,ArrayList<Node> nodes){
		if(i-1>=0){
			Node n=prev(i,nodes);
			if(n.name.equals(name))
				adj.add(new Edge(n));
		}
	}
	
	public void add_same_coord(int i,ArrayList<Node> nodes){
		Node n;
		for (int j=i+1;j<nodes.size();j++){
			n=nodes.get(j);
			if(eq_coord(n)){
				Edge e1=new Edge(n);
				Edge e2=new Edge(this);
				adj.add(e1);
				n.adj.add(e2);
				e1.twin=e2;
				e2.twin=e1;
				e1.distance=0.0;
				e1.dist_found=true;
				e2.distance=0.0;
				e2.dist_found=true;
			}
			else 
				break;
		}
	}
	
	public boolean eq_noname(Node two){
		return two.name=="" && name=="";
	}
	
	public void add_same_cross(int i,ArrayList<Node> nodes){
		Node n;
		for (int j=i+1;j<nodes.size();j++){
			n=nodes.get(j);
			if(this.id==n.id && this.eq_noname(n)){
				Edge e1=new Edge(n);
				Edge e2=new Edge(this);
				adj.add(e1);
				n.adj.add(e2);
				e1.twin=e2;
				e2.twin=e1;
				
			}
			else
					break;
		}
	}
	
	public static void findAdj(ArrayList<Node> nodes){
		Node n;
		for(int i=0; i<nodes.size(); i++){
			n=nodes.get(i);
			n.add_next(i,nodes);	//this also adds the prev of the next which is this
			n.add_same_cross(i,nodes);
		}
		
		Collections.sort(nodes, new xComparator());
		for(int i=0; i<nodes.size(); i++){
			n=nodes.get(i);
			n.add_same_coord(i,nodes);
		}
	}
	
	public double get_distance(Edge e){
		if (e.dist_found)
			return e.distance;
		e.distance=distance(e.to);
		e.dist_found=true;
		e.twin.distance=e.distance;
		e.twin.dist_found=true;
		return e.distance;
	}
	
	static class fComparator implements Comparator<Node>
	 {
		public int compare(Node one, Node two){
			if(one.fScore-two.fScore>0)
				return 1;
			else if(one.fScore-two.fScore<0)
				return -1;
			return 0;
		}
	 }
}

