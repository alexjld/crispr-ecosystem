import java.util.Arrays;

public class Bird extends Animal {
    /*
     * Purpose: Constructor for bird class.
     * 
     * @param species, subclass of mammal.
     * 
     * @param gender, gender of bird.
     * 
     * @param n, number of steps to move.
     * 
     * @return none
     */
	public Bird(String species, String gender, int n) {
		super(species, gender);

		uShape = new RowCol[] {new RowCol(1, 0), new RowCol(0, 1), new RowCol(-1, 0)};
		
		nSteps = n;
		currSide = 0;
		steps = 0;
	}

    /*
     * Purpose: Checks if the species type is a valid bird.
     * 
     * @param type, checks if argument is species of a bird.
     * 
     * @return Boolean, true or false.
     */
    public static Boolean isBird(String type) {
		return	Arrays.asList("thrush", "owl", "warbler", "shrike").contains(type.toLowerCase());
	}

    /*
     * Purpose: Identifies this class as a bird.
     * 
     * @param none
     * 
     * @return string, "bird"
     */
    public String classification() {
		return	"bird";
	}

    /*
     * Purpose: Executes move command for bird.
     * 
     * @param from, original position.
     * 
     * @param bounds, boundaries of grid.
     * 
     * @return to, new position.
     */
    public RowCol move(RowCol from, RowCol bounds) {
		RowCol	to = move(from, uShape[currSide], bounds);

		++steps;
		if (steps >= nSteps) {
			//	Completed one side of the u-shape, go to next side
			//
			currSide = (currSide + 1) % uShape.length;
			
			steps = 0;
		}

		return	to;
	}

    /*
     * Purpose: Creates new bird.
     * 
     * @param mate, second bird of opposite gender at same location.
     * 
     * @return new Bird
     */
    public Animal reproduce(Animal mate) {
		return	new Bird(species(), "female", 5);
	}

    public String toString() {
		return	String.format("%s.%d", super.toString(), steps);
	}

    private RowCol uShape[];
    private int currSide;
    private int nSteps;
    private int steps;
}
