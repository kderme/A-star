import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Node extends Point{
	public int id;
	public String name;
	public ArrayList<Node> adj;
	
	public float EuclDist;
	public float gScore;
	public float fScore;
	Node parent;
	boolean isStart;
	boolean isTarget;
	
	public Node(float a, float b, int d,String s){
		super(a,b);
		d=id;
		name=s;
		isStart=false;
		isTarget=false;
		parent=null;
	}
	
	public static void findAdj(ArrayList<Node> nodes){
		Collections.sort(nodes, new xComparator());
	}
}

