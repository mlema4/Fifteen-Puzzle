public class Pair<Elem1,Elem2 > {
    private Elem1 gridLayout;
    private Elem2 moves;

    public Pair(Elem1 gridLayout, Elem2 move){
        this.gridLayout = gridLayout;
        this.moves= move;
        }

    public Elem1 getGridLayout() {
        return gridLayout;
    }

    public Elem2 getMoves() {
        return moves;
    }
}