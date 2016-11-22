import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;

class UsageException extends Exception
{
   public UsageException()
   {
     super("Usage: java Main [path to input folder]\n"+
					 "If path is not defined ./TN2016-CW1 is the default input folder\n"+
					 "Input folder should have:\n client.csv\n nodes.csv\n taxis.csv");
   }
}

class Client{
	public float x,y;

	Client (float a, float b){
		x=a;
		y=b;
	}
}

class Taxi{
	public float x,y;
	public int id;

	Taxi(float a, float b, int d){
		x=a;
		y=b;
		d=id;
	}
}

class Node{
	public float x,y;
	public int id;
	public String name;

	Node(float a, float b, int d,String s){
		x=a;
		y=b;
		d=id;
		name=s;
	}
}

public class Main{

	private static String path;

	private static Client client;
	private static ArrayList<Taxi> taxis;
	private static ArrayList<Node> nodes;

	private static Scanner safe_open(String path) throws UsageException{
		Scanner sc = null;

  	try { sc = new Scanner(new File(path));}
  	catch(FileNotFoundException e) {
		System.out.println(path+":File Not Found");
		throw new UsageException();
		}
		return sc;
	}

	private static void read_data() throws UsageException{
		float x,y;
		int id;
		String name;
		String [] line;
		Scanner sc = null;
		Taxi taxi;
		Node node;

		sc=safe_open(path+"/client.csv");
		sc.next();
		line=sc.next().split(",");
		x=Float.parseFloat(line[0]);
		y=Float.parseFloat(line[1]);
		client=new Client(x,y);
		sc.close();

		taxis = new ArrayList<Taxi>();
		sc=safe_open(path+"/taxis.csv");
		sc.next();
		while(sc.hasNext()){
			line=sc.next().split(",");
			x=Float.parseFloat(line[0]);
			y=Float.parseFloat(line[1]);
			id=Integer.parseInt(line[2]);
			taxi = new Taxi(x,y,id);
			taxis.add(taxi);
		}
		sc.close();
		
		nodes = new ArrayList<Node>();
		sc=safe_open(path+"/nodes.csv");
		while(sc.hasNextLine()){
			sc.next();
			line=sc.next().split(",");
			x=Float.parseFloat(line[0]);
			y=Float.parseFloat(line[1]);
			id=Integer.parseInt(line[2]);
			name=line[3];
			node = new Node(x,y,id,name);
			nodes.add(node);
		}
		sc.close();

	}

	public static void main(String[] args)
	{
	try{
		if(args.length==0)
			path="TN2016-CW1";
		else if(args.length==1)
			path=args[0];
		else
			throw new UsageException();
		read_data();
		System.out.println(client.x);
		System.out.println(client.y);
	}
	catch (UsageException e){
			System.out.println(e.getMessage());
			System.exit(0);
	}
	
	}
}
