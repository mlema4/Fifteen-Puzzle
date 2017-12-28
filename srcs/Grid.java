// Fig. 13.26: GridLayoutDemo.java
// Demonstrating GridLayout.    
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.util.Timer;
import java.util.TimerTask;

public class Grid extends JFrame implements ActionListener {
   private String[] gridLayoutArray = new String[16];
   private boolean toggle = true;
   private Container container;
   private GridLayout grid1;
   private ArrayList<PuzzlePiece> pieces = new ArrayList<>();
   private JMenuBar menuBar;
   private JMenu shuffle, undoMenu, helpMenu, exitMenu, movesSoFar, complexity;
   private JMenuItem undoLast, undoAll, solve,  about, help;
   private Stack <String[]> undo = new Stack<>();
   private int moves =0;

   // set up GUI
   public Grid() {
      //    super( "GridLayout Demo" );

      // set up layouts
      grid1 = new GridLayout(4, 4, 15, 15);
      // grid2 = new GridLayout( 3, 2 );

      // get content pane and set its layout
      container = getContentPane();
      container.setLayout(new GridBagLayout());
      GridBagConstraints layout = new GridBagConstraints();
      if(true) {
         layout.fill = GridBagConstraints.HORIZONTAL;
      }

      menuBar = new JMenuBar();

      // Create Menu objects to add to the MenuBar
      container.setLayout(grid1);

      shuffle = new JMenu("Shuffle");
      undoMenu = new JMenu("Undo");
      helpMenu = new JMenu("Help");
      exitMenu = new JMenu("Exit");
      movesSoFar = new JMenu("\tMoves so far: " +moves);
      exitMenu.addMenuListener(new MenuListener() {
         @Override
         public void menuSelected(MenuEvent e) {
            System.exit(0);
         }

         @Override
         public void menuDeselected(MenuEvent e) {
         }

         @Override
         public void menuCanceled(MenuEvent e) {
         }
      });

      shuffle.addMenuListener(new MenuListener() {
         @Override
         public void menuSelected(MenuEvent e) {
            shuffle();
            complexity.setText("Complexity "+ checkComplexity());
            moves =0;
            movesSoFar.setText("Moves so far: " + moves);
            while(!undo.empty()){
               undo.pop();
            }
         }

         @Override
         public void menuDeselected(MenuEvent e) {
         }

         @Override
         public void menuCanceled(MenuEvent e) {
         }
      });

      about = new JMenuItem("About");
      helpMenu.add(about);
      about.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(null, "Author: Manuel Lema\n " +
                    "Date Created: 10/5/17\n" +
                    "2nd programming assignment for CS 342\n" +
                    "No extra credit attempted");
         }
      });
      helpMenu.add(about);

      help = new JMenuItem("Help");
      helpMenu.add(help);
      help.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(null, "15 puzzle: click on a button to move it" +
                    "to an empty space in order to get all the numbers in order\n" + "Note that a button" +
                    "will not move if it is not next to an empty space\n" +
                    "Description of Menu:\n" +
                    "Shuffle: shuffles the current puzzleto a new puzzle\n" +
                    "Undo: UndoLast will undo your last move. UndoAll will undo all your moves\n" +
                    "Help: About provides description of project. Help provides a detailed" +
                    " description of the project. \n Solve will solve your puzzle");
         }
      });

      solve = new JMenuItem("solve");
      solve.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent actionEvent) {
            BreadthFirstSearch solve = new BreadthFirstSearch(gridLayoutArray);
            Stack<String> moves = new Stack<>();
            for(int i =solve.answer.length -1; i>=0;  i--){
               moves.push(solve.answer[i]);
            }
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                              @Override
                              public void run() {
                                 if(!moves.empty()){

                                    buttonClicked(moves.pop());

                                 } else
                                    timer.cancel();

                              }
                           }
                    ,0,500);
         }
      });

      helpMenu.add(about);
      helpMenu.add(solve);

      undoLast = new JMenuItem("Undo Last");
      undoLast.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent actionEvent) {
            if (!undo.empty()) {
               moves--;
               movesSoFar.setText("Moves So far: " + moves);
               gridLayoutArray = undo.pop();
               // printGridLayout();

               for (int i = 0; i < 16; i++) {
                  if (!gridLayoutArray[i].equals("16")) {
                     pieces.get(i).getButton().setText(gridLayoutArray[i]);
                  }
                  else
                     pieces.get(i).getButton().setText("");
               }
            }
         }
      });

      //Create undoAll JMenu item and add a actionListener
      undoAll = new JMenuItem("Undo All");
      undoAll.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent actionEvent) {
            Timer timer = new Timer();
            moves =0;
            movesSoFar.setText("Moves so far: "+moves);
            timer.schedule(new TimerTask() {

               @Override
               public void run() {

                  if (!undo.empty()) {
                     gridLayoutArray = undo.pop();
                     printGridLayout();

                     for (int i = 0; i < 16; i++) {
                        if (!gridLayoutArray[i].equals("16"))
                           pieces.get(i).getButton().setText(gridLayoutArray[i]);
                        else
                           pieces.get(i).getButton().setText("");
                     }

                  } else
                     timer.cancel();

               }
            }
            ,0,500);
         }
      });

      undoMenu.add(undoLast);
      undoMenu.add(undoAll);

     // movesSoFar = new JLabel("Moves so far: " + movesSoFAr);



      //initializes the Arraylist of pieces with values excluding 16
      for (int count = 0; count < 15; count++) {
         pieces.add(new PuzzlePiece(count, count));
      }

      //Shuffle the pieces and put them on the grid
      Collections.shuffle(pieces);

      for (int count = 0; count < 15; count++) {
         pieces.get(count).setIndex(count);
         pieces.get(count).getButton().addActionListener(this);
         gridLayoutArray[count] = Integer.toString(pieces.get(count).getVal()); //updates the gridlayout Array
         container.add(pieces.get(count).getButton());
      }

      while(checkComplexity()%2 == 1) {

         Collections.shuffle(pieces);

         for (int count = 0; count < 15; count++) {
            pieces.get(count).setIndex(count);
            //pieces.get(count).getButton().addActionListener(this);
            gridLayoutArray[count] = Integer.toString(pieces.get(count).getVal()); //updates the gridlayout Array
            container.add(pieces.get(count).getButton());
         }

      }

      complexity = new JMenu("Complexity " + checkComplexity());

      /*for (int count = 0; count < 15; count++) {
         //pieces.get(count).setIndex(count);
         pieces.get(count).setIndex(count);
         pieces.get(count).getButton().addActionListener(this);
         gridLayoutArray[count] = Integer.toString(pieces.get(count).getVal()); //updates the gridlayout Array
         container.add(pieces.get(count).getButton());
      }*/

      //Adds 16 to the array and a blank button
      gridLayoutArray[15] = "16";
      pieces.add(new PuzzlePiece());
      pieces.get(15).getButton().addActionListener(this);
      container.add(pieces.get(15).getButton());
      menuBar.add(movesSoFar);
      menuBar.add(shuffle);
      menuBar.add(undoMenu);
      menuBar.add(helpMenu);
      menuBar.add(exitMenu);
      menuBar.add(movesSoFar);
      menuBar.add(complexity);

      // Add MenuBar to the Frame
      setJMenuBar(menuBar);
      //container.add(movesSoFar);

      setSize(600, 600);
      setVisible(true);
   }

   public void actionPerformed(ActionEvent event) {
     String name  = event.getActionCommand();
     if(name.equals(""))
        return;
     else {
        buttonClicked(name);
        checkIfCompleted();
     }

     //pieces.get(14).getButton().setText("");

   System.out.println(name);


   }

   public void buttonClicked(String name){
     int index= getIndex(name);
     int swapIndex = getEmptyIndex(index);
     if(swapIndex > -1){
        String [] tmp = new String [16];
        System.arraycopy(gridLayoutArray,0, tmp,0, gridLayoutArray.length);
        undo.push(tmp);
        pieces.get(index).getButton().setText("");
        gridLayoutArray[index] = "16";
        pieces.get(swapIndex).getButton().setText(name);
        gridLayoutArray[swapIndex] = name;
        moves++;
        movesSoFar.setText("Moves so far: " + moves);
     }
   }

   public void setGridLayoutArray(){
      String [] TestGrid = {"0", "1","2","3","4","5","6","7","8","9","10","11","12","13","14","16"};
      gridLayoutArray = TestGrid;
   }

   public void setGridLayoutArrayTo(String[] newGridLayout){
      gridLayoutArray = newGridLayout;
   }

   public int getEmptyIndex(int index){
      if(index -4 >=0)
         if(checkTop(index))
            return index-4;

      if(index + 4 <= 15 )
         if(checkBottom(index))
            return index+4;

      if((index%4) != 0)
         if(checkLeft(index))
            return index-1;

      if(((index+1)%4)!= 0)
         if(checkRight(index))
            return index+1;

      return -1;
   }

   public boolean checkTop(int index){
      //System.out.println("Top for " + index + " is " + (index-4));
      return (gridLayoutArray[index-4].equals("16"));

   }

   public boolean checkBottom(int index){
      //System.out.println("Bottom for " + index + " is " + (index+4));
      return(gridLayoutArray[index+4].equals("16"));
   }

   public boolean checkLeft(int index){
      //System.out.println("Left for " + index + " is " + (index-1));
      return(gridLayoutArray[index-1].equals("16"));
   }

   public boolean checkRight(int index){
      //System.out.println("Right for " + index + " is " + (index+1));
      return(gridLayoutArray[index+1].equals("16"));
   }

   public int getIndex (String name){
      for(int i=0; i<16; i++) {
         if (gridLayoutArray[i].equals(name) == true)
            return i;
      }
      return -1;
   }

   public void printGridLayout(){
      for(int i=0; i<16; i++){
         System.out.print(gridLayoutArray[i] + " ");
         //if((i+1)%4 == 0)
            //System.out.println();
      }
      System.out.println();
   }
   public int checkComplexity() {
      int inversions = 0;
      for (int i = 0; i < 15; i++) {
         for (int j = i; j < 15; j++) {
            if (Integer.parseInt(gridLayoutArray[i]) > Integer.parseInt(gridLayoutArray[j]))
               inversions++;
         }
      }
      return inversions;
   }

   public boolean checkIfCompleted(){
      String[] solvedArray = {"0","1","2","3","4","5","6","7","8","9","10","11", "12","13","14","16"};
     if( Arrays.equals(gridLayoutArray,solvedArray )){
        JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle :)\n" +
                "Thank you for playing");
        return true;
     }
     return false;
   }

   public void shuffle(){
      int index = getIndex("16");
      Collections.shuffle(Arrays.asList(gridLayoutArray));

      while(checkComplexity()%2 == 1 || !gridLayoutArray[15].equals("16")) {

         Collections.shuffle(Arrays.asList(gridLayoutArray));
      }
         for (int count = 0; count < 15; count++) {
            //gridLayoutArray[count] = Integer.toString(pieces.get(count).getVal()); //updates the gridlayout Array
            pieces.get(pieces.get(count).getIndex()).getButton().setText(gridLayoutArray[count]);
         }
         pieces.get(15).getButton().setText("");




   }

   public String[] getGridLayoutArray(){
      return gridLayoutArray;
   }

}

