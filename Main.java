import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;
import java.io.Reader;

class UsageException extends Exception
{
   public UsageException()
   {
     super("Usage: java Main [path to input folder]\n"+
		"If path is not defined ./TN2016-CW1 is the default input folder\n"+
		"Input folder should have:\n client.csv\n nodes.csv\n taxis.csv");
   }
}

public class Main{

	private static String path;

	private static Client client;
	private static ArrayList<Taxi> taxis;
	private static ArrayList<Node> nodes;

	private static void read_data() throws UnsupportedEncodingException, IOException, UsageException{
		float x,y;
		int id;
		String name;
		String [] line;
		Scanner sc = null;
		Taxi taxi;
		Node node;

		sc=new Scanner(new File(path+"/client.csv"));
		sc.next();
		line=sc.next().split(",");
		x=Float.parseFloat(line[0]);
		y=Float.parseFloat(line[1]);
		client=new Client(x,y);
		sc.close();

		taxis = new ArrayList<Taxi>();
		sc=new Scanner(new File(path+"/taxis.csv"));
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
		
		int i;
		nodes = new ArrayList<Node>();
//		sc=safe_open(path+"/nodes.csv", "ISO-8859-1");
//		sc.next();
		String charset = "ISO-8859-1";
		InputStreamReader isr;
		isr=new InputStreamReader (new FileInputStream(path+"/nodes1.csv"), charset);

		BufferedReader in = new BufferedReader(isr);
		in.readLine();
		String ln;
		ln=in.readLine();

  	while( ln != null) { 
			line=ln.split(",");
			x=Float.parseFloat(line[0]);
			y=Float.parseFloat(line[1]);
			id=Integer.parseInt(line[2]);
			name="";
			/*	Just in case a name consists of ","		*/
			for(i=3; i<line.length;i++)
				name+=line[i];
			node=new Node(x,y,id,name);
			nodes.add(node);
	  		System.out.println(name);
			byte[] bb = name.getBytes("UTF-8");
			for(byte b :bb)
				System.out.print(b);
			System.out.println();

			name= new String(bb, "ASCII");
			bb = name.getBytes("ISO-8859-1");
			for(byte b :bb)
				System.out.print(b);
			System.out.println();
	  		System.out.println(name);
			ln=in.readLine();
		}
		in.close();
	}

	private static void _read_data_() throws UsageException{
	try {read_data();}
	catch(UnsupportedEncodingException e){
		System.out.println(e.getMessage());
		throw new UsageException();
	}
	catch(IOException e){
		System.out.println(e.getMessage());
		throw new UsageException();
	}

	}

	public static void main(String[] args)
	{
		System.out.println(new java.io.File("").getAbsolutePath());
	try{
		if(args.length==0)
			path="./TN2016-CW1";
		else if(args.length==1)
			path=args[0];
		else
			throw new UsageException();
		_read_data_();
		client.FindClosestNode(nodes);
		for(Taxi t : taxis){
			t.FindClosestNode(nodes);
		}
		Node.findAdj(nodes);
		for(Taxi t: taxis){
			A_star Astar = new A_star (t.closestN, client.closestN, nodes);
			Astar.ASearch();
		}
		System.out.println(client.x);
		System.out.println(client.y);
	}
	catch (UsageException e){
			System.out.println(e.getMessage());
			System.exit(0);
	}
	
	}
}
