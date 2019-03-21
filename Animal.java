
public class Animal {
    /*
     * Purpose: Initializes common attributes for all animals.
     * 
     * @param type, common attribute of animals.
     * 
     * @param gender, common attribute of animals.
     * 
     * @return none
     */
    public Animal(String type, String gender) {
		name = type;
		male = gender.equalsIgnoreCase("male");
		moved = false;
	}
	
    public RowCol move(RowCol from, RowCol bounds) {
		return	null;
	}

    /*
     * Purpose:
     * 
     * @param from, original row and column.
     * 
     * @param step, movement.
     * 
     * @param bounds, boundaries of grid.
     * 
     * @return new RowCol, new position.
     */
    public RowCol move(RowCol from, RowCol step, RowCol bounds) {
		int	r = from.getRow();
		int	c = from.getCol();
		
		int	n = step.getRow();
		if (n != 0) {
			r += n;
			if (r < 0) {
				r = bounds.getRow() - 1;
			} else if (r >= bounds.getRow()) {
				r = 0;
			}
		}
		
		n = step.getCol();
		if (n != 0) {
			c += n;
			if (c < 0) {
				c = bounds.getCol() - 1;
			} else if (c >= bounds.getCol()) {
				c = 0;
			}
		}
		
		return	new RowCol(r, c);
	}

    public Animal reproduce(Animal mate) {
		return	null;
	}

	public void	placed() {
		moved = true;
	}

	public char	id() {
		return	name.toLowerCase().charAt(0);
	}

    public String classification() {
		return	"animal";
	}

    public Boolean gender() {
		return	male;
	}

    public Boolean isPlaced() {
		return	moved;
	}

    public String species() {
		return	name;
	}

	public void	reset() {
		moved = false;
	}

    public String toString() {
		return	String.format("%s:%s", name, male.toString());
	}

	private Boolean	male;
	private Boolean	moved;
	private String	name;
}
