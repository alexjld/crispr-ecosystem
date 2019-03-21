import java.util.Arrays;
import java.util.List;

public class Mammal extends Animal {
    /*
     * Purpose: Constructor for mammal class.
     * 
     * @param species, subclass of animal.
     * 
     * @param gender, gender of mammal.
     * 
     * @param direction, right or left.
     * 
     * @return none
     */
	public Mammal(String species, String gender, String direction) {
		super(species, gender);

		right = direction;
		
		//	Define mammal's movement based on the direction
		//
		if (right.equalsIgnoreCase("right")) {
			steps = new RowCol[] {new RowCol(1, 0), new RowCol(0, 1)};
		} else {
			steps = new RowCol[] {new RowCol(-1, 0), new RowCol(0, -1)};
		}
		
		currStep = 0;
		nBabies = 5;
	}

    /*
     * Purpose: Checks if the species type is a valid mammal.
     * 
     * @param type, checks if argument is species of a mammal.
     * 
     * @return Boolean, true or false.
     */
    public static Boolean isMammal(String type) {
		List<String>	types = Arrays.asList("elephant",
						                      "rhinoceros",
						                      "lion",
						                      "giraffe",
						                      "zebra");
		
		return	types.contains(type.toLowerCase());
	}

    public String classification() {
		return	"mammal";
	}

    /*
     * Purpose: Executes move command for mammal. Mammal with the right option
     * alternates between
     * moving right and down, starting with down. Otherwise, it alternates
     * between moving left
     * and up, starting with up.
     * 
     * @param from, original position.
     * 
     * @param bounds, boundaries of grid.
     * 
     * @return to, new position.
     */
    public RowCol move(RowCol from, RowCol bounds) {
		RowCol	to = move(from, steps[currStep], bounds);
		
		currStep = (currStep + 1) % steps.length;

		return	to;
	}

    /*
     * Purpose: Creates new mammal. Mammals can each only reproduce 5 times.
     * Baby mammals should
     * be passed right.
     * 
     * @param mate, second mammal at same location of opposite gender.
     * 
     * @return new Mammal
     */
    public Animal reproduce(Animal mate) {
		if (nBabies == 0) {
			return	null;
		}
		
		--nBabies;
			
		return	new Mammal(species(), "female", "right");
	}

    public String toString() {
		return	String.format("%s.%s", super.toString(), right.toString());
	}

	private RowCol	steps[];
	private	String	right;
    private int currStep;
    private int nBabies;
}
