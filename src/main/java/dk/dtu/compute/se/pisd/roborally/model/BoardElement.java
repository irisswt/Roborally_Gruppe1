package dk.dtu.compute.se.pisd.roborally.model;

public abstract class BoardElement extends Space{
    private boolean canLandOn;
    private String description;
    /**
     * Constructor for Space.
     *
     * @param board The board the space is on.
     * @param x     the position on the horizontal axis.
     * @param y     the position on the vertical axis.
     */
    public BoardElement(Board board, int x, int y, String description) {
        super(board, x, y);
        this.description = description;
    }
    public boolean getcanLandOn(){
        return canLandOn;
    };
}
