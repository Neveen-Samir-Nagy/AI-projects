package puzzle;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javafx.util.Pair;

public class BFS_Serach extends Search {

	public BFS_Serach(int[][] matrix) {
		super(matrix);
	}

	private Queue<int[][]> explored = new LinkedList<int[][]>();
	private HashMap<int[][], int[][]> trace_parent = new HashMap<int[][], int[][]>();
	private LinkedList<int[][]> frontier = new LinkedList<int[][]>();
	private int cost = 0;

	private boolean is_frontier(int[][] state) {
		for (int[][] visited : frontier) {
			if (Arrays.deepEquals(state, visited)) {
				return true;
			}
		}
		return false;
	}

	protected boolean search() {
		PriorityQueue<Pair<Integer, int[][]>> children = new PriorityQueue<Pair<Integer, int[][]>>(
				Comparator.comparing(Pair::getKey));
		int[][] state;
		frontier.add(initial_state);
		while (!frontier.isEmpty()) {
			state = frontier.poll();
			explored.add(state);
			if (goalTest(state)) {
				return true;
			}
			children = get_children(state);
			while (!children.isEmpty()) {
				int[][] child = children.poll().getValue();
				if (!(is_visited(child, explored) && !(is_frontier(child)))) {
					frontier.add(child);
					trace_parent.put(child, state);

				}
			}
			cost++;
		}
		return false;
	}

	protected ArrayList<int[][]> path() {
		ArrayList<int[][]> path = new ArrayList<int[][]>();
		;
		track_path(trace_parent, initial_state, goal);
		path = get_path();
		return path;
	}

	protected void get_expanded_nodes() {
		node_expanded(explored);
	}

	protected int get_cost(ArrayList<int[][]> path) {
		cost = path.size() - 1;
		return cost;
	}

	public void main(String[] args) {
		long startTime = System.nanoTime();
		System.out.println("Welcome to BFS algorithm :)");
		boolean reach = search();
		if (reach) {
			System.out.println("Congratulations! We reached to the goal.");
		} else {
			System.out.println("Ops! We codn't reach to the goal.");
		}
		System.out.println("The path:");
		ArrayList<int[][]> path = path();
		print_path(path);
		int cost = get_cost(path);
		int depth = search_depth(cost);
		System.out.println("The Cost = " + cost);
		System.out.println("The searsh depth = " + depth);
		get_expanded_nodes();
		long endTime = System.nanoTime();
		long totalTime = (endTime - startTime);
		System.out.println("BFS Running time : " + totalTime / 1000000 + " ms");
		System.out.println("--------------------------------------");
	}
}
