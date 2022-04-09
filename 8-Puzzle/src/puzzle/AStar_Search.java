package puzzle;

import java.lang.Math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import javafx.util.Pair;

public class AStar_Search extends Search {

	public AStar_Search(int[][] matrix) {
		super(matrix);
	}

	private int[][] last;
	private PriorityQueue<Pair<Integer, int[][]>> frontier = new PriorityQueue<Pair<Integer, int[][]>>(
			Comparator.comparing(Pair::getKey));
	private Queue<int[][]> explored = new LinkedList<int[][]>();
	private int h_n = 0;
	private HashMap<int[][], Integer> hashmap = new HashMap<int[][], Integer>();
	private HashMap<int[][], Integer> Cost = new HashMap<int[][], Integer>();
	private HashMap<int[][], int[][]> trace_parent = new HashMap<int[][], int[][]>();

	protected boolean search(String heuristics) {
		// TODO Auto-generated method stub
		h_n = distance(heuristics, initial_state);
		frontier.add(new Pair<Integer, int[][]>(h_n, initial_state));
		hashmap.put(initial_state, h_n);
		Cost.put(initial_state, h_n);
		PriorityQueue<Pair<Integer, int[][]>> children = new PriorityQueue<Pair<Integer, int[][]>>(
				Comparator.comparing(Pair::getKey));
		while (!frontier.isEmpty()) {
			Pair<Integer, int[][]> p1 = frontier.poll();
			int[][] state = p1.getValue();
			explored.add(state);
			if (goalTest(state)) {
				last = state;
				return true;
			}
			children = get_children(state);
			while (!children.isEmpty()) {
				int[][] child = children.poll().getValue();
				h_n = distance(heuristics, child);
				if (!is_visited(child, explored) && !is_frontier(child)) {
					frontier.add(new Pair<Integer, int[][]>(hashmap.get(state) - Cost.get(state) + h_n + 1, child));
					hashmap.put(child, hashmap.get(state) - Cost.get(state) + h_n + 1);
					Cost.put(child, h_n);
					trace_parent.put(child, state);
				} else if (is_frontier(child)) {
					child = get_value(child);
					if (hashmap.get(state) - Cost.get(state) + h_n + 1 < hashmap.get(child)) {
						Pair<Integer, int[][]> p = new Pair<Integer, int[][]>(hashmap.get(child), child);
						frontier.remove(p);
						frontier.add(new Pair<Integer, int[][]>(hashmap.get(state) - Cost.get(state) + h_n + 1, child));
						hashmap.put(child, hashmap.get(state) - Cost.get(state) + h_n + 1);
						Cost.put(child, h_n);
						trace_parent.put(child, state);
					}
				}
			}
			hashmap.remove(state);
		}
		return false;
	}

	private int manhattan_distance(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	private int euclidean_distance(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	private int distance(String heuristics, int[][] state) {
		int h_n = 0;
		for (int element = 1; element <= 8; element++) {
			int x2 = element / 3;
			int y2 = element % 3;
			boolean found = false;
			for (int i = 0; i < state.length; i++) {
				for (int j = 0; j < state[0].length; j++) {
					if (state[i][j] == element) {
						int x1 = i;
						int y1 = j;
						if (heuristics.toLowerCase().equals("manhattan")) {
							h_n = h_n + manhattan_distance(x1, y1, x2, y2);
						} else {
							h_n = h_n + euclidean_distance(x1, y1, x2, y2);
						}
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
			}
		}
		return h_n;
	}

	private boolean is_frontier(int[][] state) {
		// TODO Auto-generated method stub
		for (int[][] c : hashmap.keySet()) {
			if (Arrays.deepEquals(c, state)) {
				return true;
			}
		}
		return false;
	}

	private int[][] get_value(int[][] state) {
		for (int[][] c : hashmap.keySet()) {
			if (Arrays.deepEquals(c, state)) {
				return c;
			}
		}
		return null;
	}

	protected int get_cost() {
		// TODO Auto-generated method stub
		if (last == null) {
			return 0;
		}
		return hashmap.get(last) - Cost.get(last);
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

	public void main(String[] args) {
		long startTime = System.nanoTime();
		System.out.println("Welcome to A* algorithm :)");
		boolean reach = search("manhattan");
		if (reach) {
			System.out.println("Congratulations! We reached to the goal.");
		} else {
			System.out.println("Ops! We codn't reach to the goal.");
		}
		System.out.println("The path:");
		ArrayList<int[][]> path = path();
		print_path(path);
		int cost = get_cost();
		int depth = search_depth(cost);
		System.out.println("The Cost = " + cost);
		System.out.println("The searsh depth = " + depth);
		get_expanded_nodes();
		long endTime = System.nanoTime();
		long totalTime = (endTime - startTime);
		System.out.println("A* Running time : " + totalTime / 1000000 + " ms");
		System.out.println("--------------------------------------");
	}
}
