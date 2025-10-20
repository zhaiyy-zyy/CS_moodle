import random
import sys
import os

import pygame
from searchCW import *

# define the size of the grid and the size of each cell
WIDTH, HEIGHT = 800, 800
ROWS, COLS = 40, 40
CELL_WIDTH, CELL_HEIGHT = WIDTH // COLS, HEIGHT // ROWS


# define colors
WHITE = (255, 255, 255)
GREEN = (0, 255, 0)
CYAN = (0, 255, 255)
BLACK = (0, 0, 0)
ORANGE = (255, 165, 0)
PURPLE = (128, 0, 128)
RED = (255, 0, 0)


class Cell:
    """The Cell class in the maze.py file represents a single cell in a maze. 
    Each cell has various properties and methods that are used to create and manipulate and draw the maze in pygame.
    
    Here are the usages of the Cell class in a maze:
    Position: Each Cell object has a row and col attribute that represents its position in the maze grid. 
    These attributes are initialized in the constructor (__init__) method.
    
    Visited Status: The visited attribute of a Cell object is a boolean value that indicates whether the cell has been visited during maze traversal or not. 
    It is initially set to False and can be updated during maze generation or solving algorithms.
    
    Path Status: The isPath attribute of a Cell object is a boolean value that indicates whether the cell is part of the solution path in the maze. 
    It is initially set to False and can be updated during maze solving algorithms.

    Start and End Cells: The isStart and isEnd attributes of a Cell object are boolean values that indicate whether the cell is the starting or ending point of the maze. 
    
    These attributes are used to visually distinguish the start and end cells in the draw method."""
    def __init__(self, row, col):
        self.row = row
        self.col = col
        self.visited = False
        self.isPath = False
        self.isStart = False
        self.isEnd = False
        # define whether there is a wall in each direction
        self.walls = {"top": True, "right": True, "bottom": True, "left": True}


    def draw(self, winDow):
        """ Draw the cell on the screen."""
        x = self.col * CELL_WIDTH #get the x-coordinate of the cell
        y = self.row * CELL_HEIGHT #get the y-coordinate of the cell

        if self.isStart:
            pygame.draw.rect(winDow, CYAN, (x, y, CELL_WIDTH, CELL_HEIGHT)) #draw the start cell in cyan color as a rectangle
        elif self.isEnd:
            pygame.draw.rect(winDow, PURPLE, (x, y, CELL_WIDTH, CELL_HEIGHT)) #draw the end cell in purple color as a rectangle   1
        elif self.isPath:
            pygame.draw.rect(winDow, RED, (x, y, CELL_WIDTH, CELL_HEIGHT)) #draw the path cells in red color as a rectangle
        elif self.visited:
            pygame.draw.rect(winDow, ORANGE, (x, y, CELL_WIDTH, CELL_HEIGHT)) #draw the visited cells in orange color as a rectangle

        if self.walls["top"]:
            pygame.draw.line(winDow, WHITE, (x, y), (x + CELL_WIDTH, y), 3) #draw the top wall of the cell
        if self.walls["right"]:
            pygame.draw.line(winDow, WHITE, (x + CELL_WIDTH, y),
                             (x + CELL_WIDTH, y + CELL_HEIGHT), 3) #draw the right wall of the cell
        if self.walls["bottom"]:
            pygame.draw.line(winDow, WHITE, (x, y + CELL_HEIGHT),
                             (x + CELL_WIDTH, y + CELL_HEIGHT), 3) #draw the bottom wall of the cell
        if self.walls["left"]:
            pygame.draw.line(winDow, WHITE, (x, y), (x, y + CELL_HEIGHT), 3) #draw the left wall of the cell


class MazeGrid:
    def __init__(self, rows, cols, seed=-1):
        # Initialize the grid with the given number of rows and columns, as well as the window to draw the maze.
        self.rows = rows #number of rows in the grid
        self.cols = cols #number of columns in the grid

        # set the random seed if provided
        if seed != -1:
            random.seed(seed)
        self.seed = seed    

        # create the grid and generate the maze
        self.grid = self.__make_grid(rows, cols)

        # generate the maze from the left upper cell
        self.__generate_maze()
        self.reset_all_unvisited_cells()

    # Create a grid of cells with the given number of rows and columns.
    def __make_grid(self, rows, cols):
        grid = []
        for i in range(rows):
            grid.append([])
            for j in range(cols):
                grid[i].append(Cell(i, j))
        return grid       

    def reset_all_unvisited_cells(self):
        for row in self.grid:
            for cell in row:
                cell.visited = False
                cell.isPath = False
                cell.isStart = False
                cell.isEnd = False

    def __generator_get_unvisited_neighbors(self, cell):
        neighbors = []
        row, col = cell.row, cell.col
        # check if the neighbor cells are unvisited and add them to the list of neighbors
        if row > 0 and not self.grid[row - 1][col].visited:   # top
            neighbors.append(self.grid[row - 1][col])
        if col < self.cols - 1 and not self.grid[row][col + 1].visited: # right
            neighbors.append(self.grid[row][col + 1])
        if row < self.rows - 1 and not self.grid[row + 1][col].visited:  # bottom
            neighbors.append(self.grid[row + 1][col])
        if col > 0 and not self.grid[row][col - 1].visited: # left
            neighbors.append(self.grid[row][col - 1])
        return neighbors
    
    # Remove the wall between the current cell and the next cell.
    def __remove_wall(self, current, next):
        dx = current.col - next.col
        if dx == 1:
            current.walls["left"] = False
            next.walls["right"] = False
        elif dx == -1:
            current.walls["right"] = False
            next.walls["left"] = False

        dy = current.row - next.row
        if dy == 1:
            current.walls["top"] = False
            next.walls["bottom"] = False
        elif dy == -1:
            current.walls["bottom"] = False
            next.walls["top"] = False


    def __generate_maze(self): #generate the maze using the depth-first search algorithm
    
        frontier = [self.grid[0][0]] #start from the left upper cell
        self.grid[0][0].visited = True

        while frontier:
            current = frontier[-1] #get the last cell in the frontier
            neighbors = self.__generator_get_unvisited_neighbors(current)
            
            # if there are unvisited neighbors, choose one randomly and remove the 
            # wall between the current cell and the next cell
            if len(neighbors) > 0:
                next_cell = random.choice(neighbors)
                self.__remove_wall(current, next_cell)
                next_cell.visited = True
                frontier.append(next_cell)
            else:
                frontier.pop()
           
        # remove some walls randomly to create loops in the maze
        for _ in range(int(self.rows * self.cols * 0.2)):  
            # remove 20% of the walls randomly to create loops
            x = random.randint(0, self.rows - 1)
            y = random.randint(0, self.cols - 1)
            directions = ["top", "right", "bottom", "left"]
            direction = random.choice(directions)
            # using __remove_wall
            if direction == "top" and x > 0: 
                self.__remove_wall(self.grid[x][y], self.grid[x - 1][y]) #remove the wall between the current and the top cell
            elif direction == "right" and y < self.cols - 1:
                self.__remove_wall(self.grid[x][y], self.grid[x][y + 1]) #remove the wall between the current and the right cell
            elif direction == "bottom" and x < self.rows - 1:
                self.__remove_wall(self.grid[x][y], self.grid[x + 1][y]) #remove the wall between the current and the bottom cell
            elif direction == "left" and y > 0:
                self.__remove_wall(self.grid[x][y], self.grid[x][y - 1]) #remove the wall between the current and the left cell

class Mazetemp(Problem):
    # the initial and goal states are tuple values specify the coordinate of starting and ending points in the mazegrid
    
    def __init__(self, initial, goal, mazegrid):
        self.initial = initial
        self.goal = goal
        self.maze = mazegrid
        self.grid = self.maze.grid
        self.cols = self.maze.cols
        self.rows = self.maze.rows

        initial_x, initial_y = initial
        goal_x, goal_y = goal
        self.grid[initial_x][initial_y].isStart = True
        self.grid[goal_x][goal_y].isEnd = True
        
        #setting up pygame and window to draw the maze
        pygame.init()
        winDow = pygame.display.set_mode((WIDTH, HEIGHT))
        pygame.display.set_caption("Maze")
        self.clock = pygame.time.Clock() #create a clock object to control the frame rate

        self.winDow = winDow #window to draw the maze

    def draw(self):
        self.clock.tick(120)

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                if os.name == 'nt':
                    pygame.quit() #if you are windows user, uncomment this line to close the window after the search is done
                else:
                    os._exit(0) #if you are Mac user, uncomment this line to close the window after the search is done

        self.winDow.fill((0, 0, 0))

        for row in self.grid:
            for cell in row:
                cell.draw(self.winDow)

        # draw the border
        pygame.draw.rect(self.winDow, GREEN, pygame.Rect(0, 0, WIDTH, HEIGHT), 2)
        pygame.display.update()

    def result(self, state, action):
        """Return the state that results from executing the given. Add the action to the path."""
        self.grid[state[0]][state[1]].visited = True
        return action

    def draw_visited_cell(self, node):
        """Draw the visited cell."""
        self.grid[node.state[0]][node.state[1]].visited = True
        self.draw()

    def draw_solution_path(self, node):
        """Draw the solution path."""
        while node.parent is not None:
            self.grid[node.state[0]][node.state[1]].isPath = True
            node = node.parent
            self.draw()

    def is_goal(self, state):
        """Return True if the state is the goal state."""
        return state == self.goal
    
    def h(self, node):
        """Return the heuristic for the given state. Default heuristic is Manhattan distance."""
        return abs(node.state[0] - self.goal[0]) + abs(node.state[1] - self.goal[1])
    
    def reset(self):
        """reset the grid."""
        self.maze.reset_all_unvisited_cells()
        self.grid[self.initial[0]][self.initial[1]].isStart = True
        self.grid[self.goal[0]][self.goal[1]].isEnd = True

    def __str__(self):
        """Return the string representation of the grid."""
        return f"Grid({self.rows}, {self.cols}, seed={self.maze.seed})"
    