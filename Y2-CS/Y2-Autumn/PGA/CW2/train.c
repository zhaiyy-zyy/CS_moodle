// 20514470 scyyz26 Yuyang Zhang

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <math.h>
#include <ctype.h>

#define INFINITY1 INT_MAX
#define MAX_STATIONS 5000
#define MAX_INPUT_LENGTH 500
#define COST_MULTIPLIER 1.2
#define STATION_PENALTY 25

//type the struct called NodeInfo
struct NodeInfo
{
    int distance;
    int cost;
    int prev;
    int stationsPassed;
};

//Calculate the cost 
int calculateCost(int distance, int stationsPassed) 
{
    return (int)(COST_MULTIPLIER * distance + 0.5) + 25 * stationsPassed;
}

//Dijkstra's Algorithm
void dijkstra(int n, int** graph, int src, int dest, struct NodeInfo* nodeInfo) 
{
    bool visited[MAX_STATIONS] = { false };

    //Initialize the NodeInfo index
    for (int i = 0; i < n; ++i) 
    {
        nodeInfo[i].distance = INFINITY1;
        nodeInfo[i].cost = INFINITY1;
        nodeInfo[i].prev = -1;
        nodeInfo[i].stationsPassed = 0;
    }

    nodeInfo[src].distance = 0;
    nodeInfo[src].cost = 0;
    
    // Find the closest unvisited station

    for (int count = 0; count < n - 1; ++count) 
    {
        int u = -1;
        int minDistance = INFINITY1;
          
        for (int v = 0; v < n; ++v) 
        {
            if (!visited[v] && nodeInfo[v].distance < minDistance) 
            {
                minDistance = nodeInfo[v].distance;
                u = v;
            }
        }

        if (u == -1 || u == dest) 
        {
            break;
        }

        visited[u] = true;

        for (int v = 0; v < n; ++v) 
        {
            if (!visited[v] && graph[u][v] != 0) 
            {
                int newDistance = nodeInfo[u].distance + graph[u][v];
                int newCost = calculateCost(newDistance, nodeInfo[u].stationsPassed);
                if (newDistance < nodeInfo[v].distance || (newDistance == nodeInfo[v].distance && newCost < nodeInfo[v].cost)) 
                {
                    nodeInfo[v].distance = newDistance;
                    nodeInfo[v].cost = newCost;
                    nodeInfo[v].prev = u;
                    nodeInfo[v].stationsPassed = nodeInfo[u].stationsPassed + 1;
                }
            }
        }
    }
}

//Print the Route
void printRoute(int n, struct NodeInfo nodeInfo[], int src, int dest, char** stationNames) 
{
    int totalCost = nodeInfo[dest].cost;
    printf("%d,%d\n", nodeInfo[dest].distance, totalCost);
    int path[MAX_STATIONS];
    int path_index = 0;
    int temp = dest;
    while (temp != -1) 
    {
        path[path_index++] = temp;
        temp = nodeInfo[temp].prev;
    }

    for (int i = path_index - 1; i >= 0; i--) 
    {
        printf("%s", stationNames[path[i]]);
        if (i > 0) 
        {
            printf(",");
        }
    }
    printf("\n");
}

int main() 
{
    int n = 0; //the number of stations
    int j = 0;

    // Allocate memory to store station names
    char line[5000];

    //read the station names
    fgets(line, sizeof(line), stdin);
    while (line[j] != '\n') 
    {
        if (line[j] == ',') 
        {
            n++;
        }
        j++;
    }
    n++;
    char** stationNames = malloc(n * sizeof(char*));
    char* token = strtok(line, ",\n");
    for (int i = 0; i < n; i++) 
    {
        stationNames[i] = malloc(strlen(token) + 1);
        if (stationNames[i] != NULL) 
        {
            strcpy(stationNames[i], token);
        }
        token = strtok(NULL, ",\n");
    }

    // Allocate memory to store the distance matrix
    int** graph = malloc(n * sizeof(int*));
    for (int i = 0; i < n; i++) 
    {
        graph[i] = malloc(n * sizeof(int));
        char distance[MAX_INPUT_LENGTH];
        int j;
        int countdot = 0;
        int a = 0;
        fgets(distance, MAX_INPUT_LENGTH, stdin);
        for (j = 0; j < n; j++) 
        {
            int b = 0;
            char dist[100];
            while (distance[a] != ',' && distance[a] != '\n') 
            {
                if (!isdigit(distance[a]) && distance[a] != '\0' && distance[a] != '\r') 
                {
                    printf("Invalid distance matrix.\n");
                    return 0;
                }
                dist[b++] = distance[a++];
            }
            if (distance[a] == ',') 
            {
                countdot++;
            }
            if ((distance[a] == '\n') && (countdot != n - 1)) 
            {
                printf("Invalid distance matrix.\n");
                return 0;
            }
            dist[b] = '\0';
            a++;
            graph[i][j] = atoi(dist);
            if (i == j) {
                if (graph[i][j] != 0) 
                {
                    printf("Invalid distance matrix.\n");
                    return 0;
                }
            }
        }
    }

    // read source and destination
    char start_end[500];
    char srcName[500], destName[500];
    fgets(start_end, sizeof(start_end), stdin);
    sscanf(start_end, "%[^,],%s", srcName, destName);
    int src = -1, dest = -1;
    for (int i = 0; i < n; i++) 
    {
        if (strcmp(srcName, stationNames[i]) == 0) src = i;
        if (strcmp(destName, stationNames[i]) == 0) dest = i;
    }

    // Check validity and calculate the shortest path
    if (src == -1) 
    {
        printf("Invalid source station.\n");
    }
    else if (dest == -1) 
    {
        printf("Invalid destination station.\n");
    }
    else if (src == dest) 
    {
        printf("No journey, same source and destination station.\n");
    }
    else 
    {
        struct NodeInfo* nodeInfo = malloc(n * sizeof(struct NodeInfo));
        dijkstra(n, graph, src, dest, nodeInfo);
        if (nodeInfo[dest].distance == INFINITY1) 
        {
            printf("No possible journey.\n");
        }
        else 
        {
            // Output total distance and fare
            printRoute(n, nodeInfo, src, dest, stationNames);
            free(nodeInfo);
        }
    }

    // free memory
    for (int i = 0; i < n; i++) 
    {
        free(stationNames[i]);
        free(graph[i]);
    }
    free(stationNames);
    free(graph);

    return 0;
}