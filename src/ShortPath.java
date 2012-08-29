import java.util.HashMap;
import java.util.Map;


public class ShortPath {
	int[][] mPath;
	int mMaxStatNum;
	
	public ShortPath(int n){
		mPath = new int[n][n];
		
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				mPath[i][j] = -1;
		
		mMaxStatNum = n;
	}
	
	public void addRoute(int from, int to, int distance) throws Exception{
		if(from>mMaxStatNum || to>mMaxStatNum || distance <=0)
			throw (new Exception("addRoute failed"));
	}
	
	private int getShortestFromNeighbor(int fromId){
		int id = -1;
		int distance = -1;
		for(int i=0; i<mMaxStatNum; i++){
			if(mPath[fromId][i] > 0){
				if(distance>0){
					if(distance > mPath[fromId][i]){
						distance = mPath[fromId][i];
						id = i;
					}
				}else{
					distance = mPath[fromId][i];
					id = i;
				}
			}
		}
		return id;
	}
	
	public void calcShortestPath(int from) throws Exception{
		boolean[] set_a = new boolean[mMaxStatNum];
		
		for(int i=0; i<mMaxStatNum; i++)
			set_a[i] = false;
		
	}
	
	public static void main(String[] args) {
		
	}
	
}
