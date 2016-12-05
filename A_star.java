import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

public class A_star{
	private Node start,target;
	private static ArrayList<Node> nodes;
	
	public ArrayList<Node> path;
	public double path_length;
	
	static private HashSet<Node> closedSet;
	static private PriorityQueue<Node> openQ;
	
	public A_star(Node s, Node t, ArrayList<Node> ns){
//		System.out.println("NEW");
		start=s;
		target=t;
		nodes=ns;
	
		closedSet = new HashSet<Node>();
		openQ = new PriorityQueue<Node>(1,new Node.fComparator());
	
		init_nodes_distances();
		openQ.add(start);
	}
	
	public void ASearch(){
		while(!openQ.isEmpty()){
			Node current = openQ.poll();
			closedSet.add(current);
//			current.print();
			if(current==target){
				/*	TARGET FOUND	*/
				return;
			}
			for(Edge e : current.adj){
				Node neighbor=e.to;
//				System.out.println(neighbor.x+" "+neighbor.y+" "+neighbor.id);
				if (closedSet.contains(neighbor))
					/*	IGNORE CLOSED CASES		*/
					continue;
				double tentative_gScore = current.gScore + current.get_distance(e);
				if(!openQ.contains(neighbor)){
					openQ.add(neighbor);
					neighbor.parent=e.twin;
					neighbor.gScore=tentative_gScore;
					neighbor.fScore=tentative_gScore + neighbor.hEuclDist;
//					e.twin.to.print();
					continue;
				}
				else if (tentative_gScore >= neighbor.gScore)
					/*	IS DISCOVERED AND WE FOUND STH WORSE	*/
					continue;
				/*	IS DISCOVERED AND WE FOUND STH BETTER	*/
				openQ.remove(neighbor);
				openQ.add(neighbor);
				neighbor.parent=e.twin;
				neighbor.gScore=tentative_gScore;
				neighbor.fScore=neighbor.gScore+neighbor.hEuclDist;
//				e.twin.to.print();
			}
			 closedSet.add(current);
		}
	}
	
	private void init_nodes_distances(){
		for(Node n : nodes){
//			n.fScore = Double.MAX_VALUE;
//			n.gScore = Double.MAX_VALUE;
			n.parent=null;
		}
		start.fScore=start.hEuclDist;
		start.gScore=0.0f;
	}
	
	public void path(){
		path= new ArrayList<Node>();
		for (Node n=target; n!=start; n=n.parent.to){
			path_length+=n.parent.distance;
			path.add(n);
//			System.out.println(path.size());
		}
		path.add(start);
		Collections.reverse(path);
	}	
	
	public void print_path(PrintWriter writer, Client c, Taxi t){
		writer.println(t.x+" "+t.y);
		for (Node n: path){
			writer.println(n.x+" "+n.y);
		}
		writer.println(c.x+" "+c.y);
	}
	
	public void print_path(){
		for (Node n: path){
			System.out.println(n.x+" "+n.y);
		}
	}
}

