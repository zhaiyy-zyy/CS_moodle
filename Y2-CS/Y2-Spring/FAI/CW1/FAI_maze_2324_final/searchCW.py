#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import sys
from collections import deque

#from utils import *
import matplotlib.pyplot as plt
import random
import heapq
import math
import sys
from collections import defaultdict, deque, Counter
from itertools import combinations

# ______________________________________________________________________________
# Abstract class of problem and Node
# expand to generate successors; 
# path_actions and path_states to recover aspects of the path from the node.  

class Problem(object):
    """The abstract class for a formal problem. A new domain subclasses this,
    overriding `actions` and `results`, and perhaps other methods.
    The default heuristic is 0 and the default action cost is 1 for all states.
    When you create an instance of a subclass, specify `initial`, and `goal` states 
    (or give an `is_goal` method) and perhaps other keyword args for the subclass."""
    
    # **kwds Keywords arguments, a dictionary type 
    
    def __init__(self, initial=None, goal=None, **kwds):  #here self defining the problem itself
        self.__dict__.update(initial=initial, goal=goal, **kwds) 
        
    def actions(self, state):        raise NotImplementedError #return possible actions given state s, it regarded as sucessor functions in the slides
    def result(self, state, action): raise NotImplementedError # return result state given by start state and valid action
    def is_goal(self, state):        return state == self.goal
    def action_cost(self, s, a, s1): return 1 #travel from state s to s1 cost 1, s=>start state, a=>action, s1=>resulting state,
    def h(self, node):               return 0
    
    def __str__(self):
        return '{}({!r}, {!r})'.format(
            type(self).__name__, self.initial, self.goal) #define canonical representation of problem. Problemname(initial state, goal)   
    
class Node:
    "A Node in a search tree."
    def __init__(self, state, parent=None, action=None, path_cost=0): #here self defining the current node object
        self.__dict__.update(state=state, parent=parent, action=action, path_cost=path_cost)

    def __repr__(self): return '<{}>'.format(self.state) #print out format of node, <state name>
    def __len__(self): return 0 if self.parent is None else (1 + len(self.parent)) #depth of node
    def __lt__(self, other): return self.path_cost < other.path_cost #define the way of comparing two nodes.
    
    
failure = Node('failure', path_cost=math.inf) # Indicates an algorithm couldn't find a solution. state = 'failure'
cutoff  = Node('cutoff',  path_cost=math.inf) # Indicates iterative deepening search was cut off. state = 'cutoff'
    
    
def expand(problem, node):
    "Expand a node, generating the children nodes."
    s = node.state
    for action in problem.actions(s): #problem.actions(s) return all the possible actions on state s
        s1 = problem.result(s, action)
        cost = node.path_cost + problem.action_cost(s, action, s1)
        yield Node(s1, node, action, cost) #(yield)can be viewed as generator, expand node will yield several nodes (neighbours/frontier) reachable from the current node
        
def path_actions(node):
    "The sequence of actions to get to this node." #solution
    if node.parent is None:
        return []  
    return path_actions(node.parent) + [node.action] #list of actions from current node to root node

def path_states(node):
    "The sequence of states to get to this node."
    if node in (cutoff, failure, None): 
        return []
    return path_states(node.parent) + [node.state] #list of states from current node to root node


# ______________________________________________________________________________
# Queue implementation
# First-in-first-out and Last-in-first-out queues, and a `PriorityQueue`, which allows you to keep a collection of items, and continually remove from it the item with minimum `f(item)` score.

FIFOQueue = deque # check with help(deque), add to the right most, remove from the left most

LIFOQueue = list #add to the right most, remove the right most

class PriorityQueue: #used in best first search
    """A queue in which the item with minimum f(item) is always popped first."""

    def __init__(self, items=(), key=lambda x: x): 
        self.key = key
        self.items = [] # a heap of (score, item) pairs
        for item in items:
            self.add(item)
         
    def add(self, item):
        """Add item to the queuez."""
        pair = (self.key(item), item) #pair is a tuple type
        heapq.heappush(self.items, pair) #put the node to the sorted priority queue at the correct location w.r.t. value of key(item)

    def pop(self):
        """Pop and return the item with min f(item) value."""
        return heapq.heappop(self.items)[1]
    
    def top(self): return self.items[0][1]

    def __len__(self): return len(self.items)


def g(n): return n.path_cost    

def is_cycle(node, k=30):
    "Does this node form a cycle of length k or less?"
    def find_cycle(ancestor, k):
        return (ancestor is not None and k > 0 and
                (ancestor.state == node.state or find_cycle(ancestor.parent, k - 1)))
    return find_cycle(node.parent, k)

# ______________________________________________________________________________
# report method implementation adjust from lab3 tutorial

class CountCalls:
    """Delegate all attribute gets to the object, and count them in ._counts"""
    def __init__(self, obj):
        self._object = obj
        self._counts = Counter() #the self._counts will count number of times each methods has been used.
    def __getattr__(self, attr):
        "Delegate to the original object, after incrementing a counter."
        self._counts[attr] += 1
        return getattr(self._object, attr)

        
def report(searchers, problems, verbose=True):
    """Show summary statistics for each searcher (and on each problem unless verbose is false)."""
    show_stat = {}
    for searcher in searchers:
        print(searcher.__name__ + ':')
        total_counts = Counter()
        for p in problems:
            p.reset() # reset the problem makes the problem to its initial state
            
            prob   = CountCalls(p)
            soln   = searcher(prob) # run problem p with current searcher, 
            counts = prob._counts; 
            counts.update(path_actions=len(soln), path_cost=soln.path_cost) 
            total_counts += counts
            if verbose: report_counts(counts, str(p)[:40])
        report_counts(total_counts, 'TOTAL\n')

        show_stat[searcher.__name__] = [total_counts['result'],total_counts['is_goal'],total_counts['path_actions']]
    return show_stat
        
def report_counts(counts, name):
    """Print one line of the counts report."""
    print('{:9,d} nodes |{:9,d} goal |{:8.0f} path cost |{:8,d} path actions | {}'.format(
          counts['result'], counts['is_goal'], counts['path_cost'], counts['path_actions'], name)) 


# ______________________________________________________________________________
# show_bar method implementation copy from lab3 tutorial
   
#matplotlib inline
import numpy as np
import matplotlib.pyplot as plt


def show_bar(show_stat):
    columns = ('nodes','goal','actions')
    rows = ['%s' % x for x in show_stat.keys() ]
    
    values = np.arange(0, 100, 25)
    value_increment = 5
   
    # Get some pastel shades for the colors
    colors = plt.cm.BuPu(np.linspace(0.5, 1, len(show_stat)))
    n_rows = len(show_stat)

    index = np.arange(len(columns)) + 0.3
    bar_width = 0.1

    # Initialize the vertical-offset for the stacked bar chart.
    y_offset = np.zeros(len(columns))

    # Plot bars and create text labels for the table
    cell_text = []
    for row in range(n_rows):
        index = index+0.1
        plt.bar(index, show_stat[rows[row]], bar_width, bottom=0, color=colors[row],edgecolor = 'white')
    
        #y_offset = y_offset + show_stat[rows[row]]
        y_offset =show_stat[rows[row]]
        cell_text.append(['%1.1f' % (x ) for x in y_offset])
    
    # Reverse colors and text labels to display the last value at the top.
    colors = colors[::1]
   # cell_text.reverse()
    
    # Add a table at the bottom of the axes
    the_table = plt.table(cellText=cell_text,
                      rowLabels=rows,
                      rowColours=colors,
                      colLabels=columns,
                      loc='bottom')
    
    # Adjust layout to make room for the table:
    plt.subplots_adjust(left=0.2, bottom=0.2)

    plt.ylabel("search comparison")
 #   plt.yticks(values * value_increment, ['%d' % val for val in values])
    plt.xticks([])
    plt.title('search criteria')

    plt.show()
