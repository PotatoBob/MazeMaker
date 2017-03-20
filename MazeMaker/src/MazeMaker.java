import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// select a random cell to start
		Cell ex = maze.getCell(randGen.nextInt(width), randGen.nextInt(height));
		// call selectNextPath method with the randomly selected cell
		selectNextPath(ex);
		return maze;
	}

	private static void selectNextPath(Cell currentCell) {
		// mark current cell as visited
		currentCell.setBeenVisited(true);
		// check for unvisited neighbors
		ArrayList<Cell> unvisited = getUnvisitedNeighbors(currentCell);
		// if has unvisited neighbors,
		// select one at random.
		// push it to the stack
		// remove the wall between the two cells
		// make the new cell the current cell and mark it as visited
		// call the selectNextPath method with the current cell
		if (unvisited.size() > 0) {
			int cellNum1 = randGen.nextInt(unvisited.size());
			int cellNum2 = randGen.nextInt(unvisited.size());
			Cell exCell = maze.getCell(unvisited.get(cellNum1).getX(), unvisited.get(cellNum2).getY());
			uncheckedCells.push(exCell);
			removeWalls(currentCell, exCell);
			exCell.setBeenVisited(true);
			selectNextPath(exCell);
		}
		// if all neighbors are visited
		// if the stack is not empty
		// pop a cell from the stack
		// make that the current cell
		// call the selectNextPath method with the current cell
		else if (uncheckedCells.isEmpty() == false) {
			selectNextPath(uncheckedCells.pop());
		}
	}

	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getX() == c2.getX()) { // Xs are same
			if (c1.getY() > c2.getY()) { // if c1 is lower
				c2.setSouthWall(false);
				c1.setNorthWall(false);
			} else { // if c2 is lower
				c1.setSouthWall(false);
				c2.setNorthWall(false);
			}
		} else { // Ys are same
			if (c1.getX() > c2.getX()) { // if c1 is to the right
				c2.setEastWall(false);
				c1.setWestWall(false);
			} else { // if c2 is to the right
				c1.setEastWall(false);
				c2.setWestWall(false);
			}
		}
	}

	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> ans = new ArrayList<Cell>();
		if (c.getX() > 0 && maze.getCell(c.getX() - 1, c.getY()).hasBeenVisited() == false) {
			ans.add(maze.getCell(c.getX() - 1, c.getY()));
		}
		if (c.getX() < maze.getWidth() - 1 && maze.getCell(c.getX() + 1, c.getY()).hasBeenVisited() == false) {
			ans.add(maze.getCell(c.getX() + 1, c.getY()));
		}
		if (c.getY() > 0 && maze.getCell(c.getX(), c.getY() - 1).hasBeenVisited() == false) {
			ans.add(maze.getCell(c.getX(), c.getY() - 1));
		}
		if (c.getY() < maze.getHeight() - 1 && maze.getCell(c.getX(), c.getY() + 1).hasBeenVisited() == false) {
			ans.add(maze.getCell(c.getX(), c.getY() + 1));
		}
		return ans;
	}
}