type State = [Char]
type Path = ([State], Int)

bfs :: State -> State -> [State] -> [Path]
bfs currentState goalState visited
    | currentState == goalState = [([goalState], 0)]
    | otherwise = [ (currentState : path, steps + 1) | x <- move currentState, x `notElem` visited',
                                                       (path, steps) <- bfs x goalState visited']
    where visited' = currentState : visited

isValidState :: State -> Bool
isValidState [f, w, g, c]
    | (w == g && g /= f) || (g == c && c /= f) = False
    | otherwise                                = True
    
switch :: Char -> Char
switch 'w' = 'e'
switch 'e' = 'w'

move :: State -> [State]
move [f, w, g, c] = filter isValidState [changeF, changeW, changeG, changeC]
  where
    changeF = [switch f, w, g, c]
    changeW = if f == w then [switch f, switch w, g, c] else [f, w, g, c]
    changeG = if f == g then [switch f, w, switch g, c] else [f, w, g, c]
    changeC = if f == c then [switch f, w, g, switch c] else [f, w, g, c]

nextSide :: Char -> Char  
nextSide 'w' = 'e'  
nextSide 'e' = 'w'
    
shortestPaths :: [[State]] -> [[State]]
shortestPaths paths = filter (\p -> length p == minLength) paths
    where minLength = minimum $ map length paths

solutionPath :: [State] -> IO ()
solutionPath [initialState, targetState]
    | not (isValidState initialState) = putStrLn "Invalid input"
    | otherwise = do
        putStrLn "All Possible Paths:"
        let allPaths = bfs initialState targetState []
        mapM_ (\(path, steps) -> do
            print path
            putStrLn "Number of Steps:"
            putStr $ " " ++ show steps 
            putStrLn "") allPaths
        putStrLn "Shortest Paths:"
        let shortest = shortestPaths (map fst allPaths)
        mapM_ (\path -> do
            print path
            putStrLn "Number of Steps: "
            putStr $ show (length path - 1)
            putStrLn "") shortest


