import java.util.Arrays;
import java.util.List;

public class Insect extends Animal {
    /*
     * Purpose: Constructor for insect class and defines the insect's movement
     * based on clockwise or
     * counter-clockwise.
     * 
     * @param species, a type of animal.
     * 
     * @param gender, gender of insect.
     * 
     * @param, isClockwise, boolean for basis of insect's movement.
     * 
     * @return none
     */
	public Insect(String species, String gender, Boolean isClockwise) {
		super(species, gender);

		//	Define the insect's movement based on clockwise or counter-clockwise
		//
		clockwise = isClockwise;
		if (clockwise) {
			square = new RowCol[] {new RowCol( 0, -1),
		   			   			   new RowCol(-1,  0),
		   			   			   new RowCol( 0,  1),
		   			   			   new RowCol( 1,  0)};
		} else {
			square = new RowCol[] {new RowCol( 0, -1),
		   			   			   new RowCol( 1,  0),
		   			   			   new RowCol( 0,  1),
		   			   			   new RowCol(-1,  0)};
		}

		currSide = 0;
		currStep = 0;
		nSteps = 1;
	}

    /*
     * Purpose: Checks if the species type is a valid insect.
     * 
     * @param type, checks if argument is species of insect.
     * 
     * @return Boolean, true or false.
     */
    public static Boolean isInsect(String type) {
		List<String>	types = Arrays.asList("mosquito",
						                      "bee",
						                      "fly",
						                      "ant");
		
		return	types.contains(type.toLowerCase());
	}

    /*
     * Purpose: Identifies this class as a insect.
     * 
     * @param none
     * 
     * @return string, "insect"
     */
	public	String	classification() {
		return	"insect";
	}

    /*
     * Purpose: Executes move command for insect. Insects make either clockwise
     * or counterclockwise
     * squares, walking the same number of steps in each direction until a
     * complete square is made.
     * Both start by walking left. The initial square should have one step on
     * each side. The number
     * of steps should increase by one each time a square is completed.
     * 
     * @param from, original position.
     * 
     * @param bounds, boundaries of grid.
     * 
     * @return to, new position.
     */
    public RowCol move(RowCol from, RowCol bounds) {
		RowCol	to = move(from, square[currSide], bounds);

		++currStep;
		if (currStep >= nSteps) {
			//	Completed one side of the square, go to next side
			//
			currSide = (currSide + 1) % square.length;
			
			if (currSide == 0) {
				//	Completed a square, increase number of steps
				++nSteps;
			}
			
			currStep = 0;
		}

		return	to;
	}

    /*
     * Purpose: Creates new insect.
     * 
     * @param mate, second insect of opposite gender at same location .
     * 
     * @return new Insect
     */
    public Animal reproduce(Animal mate) {
		return	new Insect(species(), "female", false);
	}

	public String	toString() {
		return	String.format("%s.%s", super.toString(), clockwise.toString());
	}

	private Boolean	clockwise;
	private RowCol	square[];
    private int currSide;
    private int currStep;
    private int nSteps;
}
