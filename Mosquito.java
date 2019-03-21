public class Mosquito extends Insect {
    /*
     * Purpose: Constructor for Mosquito class.
     * 
     * @param species, subclass of insects.
     * 
     * @param gender, gender of mosquito.
     * 
     * @param isClockwise, boolean for movement pattern.
     * 
     * @param g1, boolean for reproductive gene.
     * 
     * @param g2, boolean for reproductive gene.
     * 
     * @returns none
     */
	public Mosquito(String species, String gender, Boolean isClockwise, Boolean g1, Boolean g2) {
		super(species, gender, isClockwise);

		parent1 = g1;
		parent2 = g2;
	}

    /*
     * Purpose: Checks if the species type is a mosquito.
     * 
     * @param type, checks if argument is a mosquito.
     * 
     * @return Boolean, true or false.
     */
    public static Boolean isMosquito(String type) {
		return	type.equalsIgnoreCase("mosquito");
	}

    /*
     * Purpose: Creates new mosquito. Mosquitos carry two genes related to
     * reproduction, g1 and g2.
     * If these are both true, the mosquito cannot reproduce, sterile. Baby
     * mosquitos should be
     * created counter-clockwise and a boolean value indicating the sterility of
     * its parents.
     * 
     * @param mate, second mosquito of opposite gender at same location.
     * 
     * @return new Mosquito
     */
    public Animal reproduce(Animal mate) {
        Mosquito bug = (Mosquito) mate;

        if (isSterile() == false && bug.isSterile() == false) {
            return new Mosquito(species(), "female", false, isEdited(),
                    bug.isEdited());
        }
		return	null;
	}

	public Boolean	isSterile() {
		return	parent1 && parent2;
	}

	public Boolean	isEdited()	{
		return	parent1 || parent2;
	}

	public String	toString() {
		return	String.format("%s.%s.%s", super.toString(), parent1.toString(), parent2.toString());
	}

	private Boolean	parent1;
	private Boolean	parent2;
}
