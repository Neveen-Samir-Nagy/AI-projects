package puzzle;

public class Run_Algorithms {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] m = { { 8, 2, 1 }, { 5, 4, 6 }, { 0, 3, 7 } };
		BFS_Serach bfs = new BFS_Serach(m);
		//bfs.main(args);
		AStar_Search a = new AStar_Search(m);
		//a.main(args);
		DFS_Search dfs = new DFS_Search(m);
		dfs.main(args);
	}

}
