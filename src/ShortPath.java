import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ShortPath {
	int[][] mPath;
	int mMaxStatNum;
	int[] mDist;
	int[] mPrev;
	Map<String, Integer> mMapStat;
	int mFrom;

	private final int MAXINT = 9999;
	
	public ShortPath(int num){
		mPath = new int[num][num];
		mDist = new int[num];
		mPrev = new int[num];
		
		for(int i=0; i<num; i++)
			for(int j=0; j<num; j++)
				mPath[i][j] = MAXINT;

		mMaxStatNum = num;
		mMapStat = new HashMap<String, Integer>();
	}
	
	private void addRoute(int from, int to, int distance) throws Exception{
		if(distance <=0 || distance >= MAXINT)
			throw (new Exception("Invalid route!"));
		
		mPath[from][to] = distance;
	}
	
	private void dijkstra(int from){
		mFrom = from;
		boolean[] visited = new boolean[mMaxStatNum];
		
		//init
		for(int i=0; i<mMaxStatNum; i++){
			visited[i] = false;
			mDist[i] = MAXINT;
			mPrev[i] = -1;
		}
		
		for(int i=0; i<mMaxStatNum; i++){
			if(mPath[from][i] > 0){
				mPrev[i] = from;
				mDist[i] = mPath[from][i];
			}
		}
		
		//start dijkstra
		visited[from] = true;
		mDist[from] = 0;

		for(int i=1; i<mMaxStatNum; i++){
			//1, find the nearest station
			int tmp = MAXINT;
			int u = from;
			
			for(int j=0; j<mMaxStatNum; j++){
				if(!visited[j] && (mDist[j]<tmp)){
					u = j;
					tmp = mDist[j];
				}
			}
			visited[u] = true;
			
			//2, update the distance
			for(int j=0; j<mMaxStatNum; j++){
				if(!visited[j] && (mPath[u][j]!=MAXINT)){
					int newDist = mDist[u] + mPath[u][j];
					if(newDist < mDist[j]){
						mDist[j] = newDist;
						mPrev[j] = u;
					}
				}
			}
		}
	}
	
	public String toString(){
		String output;
		output = "Number of Stations : " + mMaxStatNum;
		
		output += "\n--------------------------------------------------------------------------\n";
		output += "mPath:\n";
		for(int i=0; i<mMaxStatNum; i++){
			output += "\t" + i;
		}
		output += "\n";
		for(int i=0; i<mMaxStatNum; i++){
			output += i;
			for(int j=0; j<mMaxStatNum; j++)
				output += "\t" + mPath[i][j];
			output += "\n";
		}
		
		
		output += "\n--------------------------------------------------------------------------\n";
		output += "dist\t";
		for(int i=0; i<mMaxStatNum; i++)
			output += mDist[i] + "\t";
		
		output += "\n--------------------------------------------------------------------------\n";
		output += "prev\t";
		for(int i=0; i<mMaxStatNum; i++)
			output += mPrev[i] + "\t";
		
		
		return output;
	}
	
	//station name related
	private void dijkstra(String from) {
		dijkstra(getIdByName(from));
	}
	public void addRoute(String from, String to, int distance) throws Exception{
		int m, n;
		if(mMapStat.containsKey(from))
			m = mMapStat.get(from);
		else{
			m = mMapStat.size();
			mMapStat.put(from, m);
		}
		
		if(mMapStat.containsKey(to))
			n = mMapStat.get(to);
		else{
			n = mMapStat.size();
			mMapStat.put(to, n);
		}
		
		addRoute(m, n, distance);
	}
	
	private int getIdByName(String name){
		return mMapStat.get(name);
	}
	
	private String getNameById(int id) throws Exception{
		for(Map.Entry<String, Integer> m:mMapStat.entrySet()){
			if(m.getValue() == id)
				return m.getKey();
		}
		throw (new Exception("Invalid Id!"));
	}
	
	public void showRoute(String to) throws Exception{
		int id = getIdByName(to);
		String output = "shortest distance:" + mDist[id] + "\t";
		String path = "";
		while(id != mFrom){
			path = "->" + getNameById(id) + path;
			id = mPrev[id];
		}
		path = getNameById(id) + path;
		
		System.out.print(output + path + "\n");
	}
	
	public void showAllRoutes() throws Exception{
		for(Map.Entry<String, Integer> m:mMapStat.entrySet()){
			if(m.getValue() != mFrom)
				showRoute(m.getKey());
		}
		System.out.print("\n\n");
	}
	
	//testing
	public static void test1(){
		ShortPath t = new ShortPath(5);
		try {
			t.addRoute(0, 1, 5);
			t.addRoute(1, 2, 4);
			t.addRoute(2, 3, 8);
			t.addRoute(3, 2, 8);
			t.addRoute(3, 4, 6);
			t.addRoute(0, 3, 5);
			t.addRoute(2, 4, 2);
			t.addRoute(4, 1, 3);
			t.addRoute(0, 4, 7);
			t.dijkstra(3);
			
			System.out.print(t.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void test2(){
		ShortPath t = new ShortPath(5);
		try {
			t.addRoute(0, 1, 5);
			t.addRoute(1, 2, 4);
			t.addRoute(0, 4, 4);
			t.addRoute(3, 2, 18);
			t.dijkstra(4);
			System.out.print(t.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void test3(){
		ShortPath t = new ShortPath(5);
		try {
			//AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
			t.addRoute("A", "B", 5);
			t.addRoute("B", "C", 4);
			t.addRoute("C", "D", 8);
			t.addRoute("D", "C", 8);
			t.addRoute("D", "E", 6);
			t.addRoute("A", "D", 5);
			t.addRoute("C", "E", 2);
			t.addRoute("E", "B", 3);
			t.addRoute("A", "E", 7);
			
			t.dijkstra("A");
			t.showAllRoutes();
			
			t.dijkstra("B");
			t.showAllRoutes();
			
			t.dijkstra("C");
			t.showAllRoutes();
			
			t.dijkstra("D");
			t.showAllRoutes();
			
			t.dijkstra("E");
			t.showAllRoutes();
			
			//System.out.print(t.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	


	public static void main(String[] args) {
		//AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
		//015, 124, 238, 328, 346, 035, 242, 413, 047
		//test1();
		
		test3();
	}
	
}

