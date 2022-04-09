package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javafx.util.Pair;

public class Search {
	public int[][] goal = new int[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } };
	public int[][] initial_state;
	private ArrayList<int[][]> path = new ArrayList<int[][]>();

	public Search(int[][] matrix) {
		initial_state = new int[matrix.length][matrix[0].length];
		initial_state = matrix;
	}

	public boolean goalTest(int[][] state) {
		if (Arrays.deepEquals(state, goal)) {
			return true;
		}
		return false;
	}

	public boolean is_visited(int[][] state, Queue<int[][]> explored) {
		for (int[][] visited : explored) {
			if (Arrays.deepEquals(state, visited)) {
				return true;
			}
		}
		return false;
	}

	public boolean is_frontier(int[][] state, LinkedList<int[][]> frontier) {
		for (int[][] visited : frontier) {
			if (Arrays.deepEquals(state, visited)) {
				return true;
			}
		}
		return false;
	}

	public PriorityQueue<Pair<Integer, int[][]>> get_children(int[][] state) {
		// TODO Auto-generated method stub
		PriorityQueue<Pair<Integer, int[][]>> children = new PriorityQueue<Pair<Integer, int[][]>>(
				Comparator.comparing(Pair::getKey));
		int[][] left = new int[state.length][state[0].length];
		int[][] right = new int[state.length][state[0].length];
		int[][] up = new int[state.length][state[0].length];
		int[][] down = new int[state.length][state[0].length];
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				up[i][j] = state[i][j];
				down[i][j] = state[i][j];
				left[i][j] = state[i][j];
				right[i][j] = state[i][j];
			}
		}
		int i = 0, j = 0;
		found: for (i = 0; i < state.length; i++) {
			for (j = 0; j < state[0].length; j++) {
				if (state[i][j] == 0) {
					break found;
				}
			}
		}
		// up
		if (i != 0) {
			up[i][j] = up[i - 1][j];
			up[i - 1][j] = 0;
			children.add(new Pair<Integer, int[][]>(up[i][j], up));
		}
		// down
		if (i != state.length - 1) {
			down[i][j] = down[i + 1][j];
			down[i + 1][j] = 0;
			children.add(new Pair<Integer, int[][]>(down[i][j], down));
		}
		// left
		if (j != 0) {
			left[i][j] = left[i][j - 1];
			left[i][j - 1] = 0;
			children.add(new Pair<Integer, int[][]>(left[i][j], left));
		}
		// right
		if (j != state[0].length - 1) {
			right[i][j] = right[i][j + 1];
			right[i][j + 1] = 0;
			children.add(new Pair<Integer, int[][]>(right[i][j], right));
		}
		return children;
	}

	public void track_path(HashMap<int[][], int[][]> trace, int[][] initial, int[][] goal) {
		if (!Arrays.deepEquals(initial, goal)) {
			int[][] parent;
			for (int[][] child : trace.keySet()) {
				if (Arrays.deepEquals(child, goal)) {
					parent = trace.get(child);
					path.add(parent);
					track_path(trace, initial, parent);
				}
			}
		}
	}

	public ArrayList<int[][]> get_path() {
		return path;
	}

	public void print_path(ArrayList<int[][]> path) {
		Collections.reverse(path);
		path.add(goal);
		
	}

	public void node_expanded(Queue<int[][]> explored) {
		// TODO Auto-generated method stub
		System.out.println("size:"+explored.size());
		//System.out.println("The expanded Nodes:");
		
	}

	public int search_depth(int cost) {
		// TODO Auto-generated method stub
		if (cost == 0) {
			return 0;
		}
		return cost - 1;
	}

}
