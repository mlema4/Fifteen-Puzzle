import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Arrays;

public class BreadthFirstSearch {
    private Queue<Pair<String[], String[]>> queue;
    private Set<String[]> visited;
    private final String[] solvedArray = {"0","1", "2", "3","4", "5","6","7","8","9","10","11", "12", "13", "14", "16"};
    public String[] answer;

    public BreadthFirstSearch(String[] gridLayout) {

    queue = new LinkedList<>();
    visited = new HashSet<>();
    answer = BFS(gridLayout);
    }


    public String[] BFS(String[] gridLayout){
        queue.add(new Pair(gridLayout , null));
        visited.add(gridLayout);
        Pair<String[], String[]> currPair;
        String [] currGrid, currMoves;
        while(!queue.isEmpty()){
            currPair = queue.remove();
            currGrid = currPair.getGridLayout();
            printGrid(currGrid);
            currMoves = currPair.getMoves();
            if(checkIfSolved(currGrid))
                return currMoves;
            else{
                int emptyIndex = getEmptyIndex(gridLayout);
                checkMoves(emptyIndex,gridLayout, currMoves);

            }


        }

        if(!queue.isEmpty()){
            //TODO
            //Notify user no solution exists
        }

        return null;

    }

    public void printGrid(String[] gridLayout){
        for(int i =0; i< 16; i++){
            System.out.print(gridLayout[i]);
            if((i+1)%4 == 0)
                System.out.println();
        }
    }

    public boolean checkIfSolved(String[] gridLayout){
        return (Arrays.equals(gridLayout,solvedArray ));
    }

  /*  public String[] getMoves(String[] gridLayout){
        int index = getEmptyIndex(gridLayout);

    }*/

    public void updateQueueAndSet(int emptyIndex, int swapIndex, String[] gridLayout, String[] currMoves){
        String[] newGrid = new String[gridLayout.length];
        System.arraycopy( gridLayout, 0, newGrid, 0, gridLayout.length );
        if(!visited.contains(newGrid)) {
            newGrid[emptyIndex] = gridLayout[swapIndex];
            newGrid[swapIndex] = gridLayout[emptyIndex];
            String[] moves;
            if(currMoves == null)
                moves = new String[1];
            else
                moves = new String[currMoves.length + 1];
            moves[moves.length - 1] = gridLayout[swapIndex];
            Pair newPair = new Pair(newGrid, moves);
            queue.add(newPair);
            visited.add(newGrid);
        }
    }

    public void checkMoves(int index, String[] gridLayout, String[] currMoves){
        if(index + 4 <= 15 ) //check if able to move down
            updateQueueAndSet(index,index+4, gridLayout, currMoves);

        if(index -4 >=0)     //check if able to move top
            updateQueueAndSet(index,index-4, gridLayout, currMoves);

        if((index%4) != 0)   //check if able to move left
            updateQueueAndSet(index, index-1, gridLayout, currMoves);

        if(((index+1)%4)!= 0)//check if able to move right
            updateQueueAndSet(index,index+1, gridLayout, currMoves);

    }


    public int getEmptyIndex(String[] gridLayout){
        for(int i=0; i<16; i++) {
            if (gridLayout[i].equals("16") == true)
                return i;
        }
        return -1;
    }
}



