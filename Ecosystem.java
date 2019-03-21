import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Ecosystem {
    /*
     * Purpose: Buidling ecosystem grid based on row and col dimmensions.
     * 
     * @param rows
     * 
     * @param cols
     * 
     * @return none
     */
	public Ecosystem(int rows, int cols) {
		bounds = new RowCol(rows, cols);
		grid = new ArrayList<List<List<Animal>>>();
		
		for (int r = 0; r < bounds.getRow(); ++r) {
			List<List<Animal>>	columns = new ArrayList<List<Animal>>();
			
			for (int c = 0; c < bounds.getCol(); ++c) {
				columns.add(new ArrayList<Animal>());
			}
			
			grid.add(columns);
		}
	}
	
    /*
     * Purpose: Creates animal object.
     * 
     * @param args, the command line.
     * CREATE (row,column) type gender [ arguments... ]
     * 
     * @returns none, adds animal to grid at given location once created.
     */
	public void	create(String[] args) {
		RowCol	origin = getRowCol(args[1].trim());
		
		if (origin != null) {
			String	type = args[2].trim();
			String	gender = args[3].trim();
			Animal	creature = null;
			
			if (Mammal.isMammal(type)) {
				creature = new Mammal(type, gender, args[4]);
			} else if (Bird.isBird(type)) {
				creature = new Bird(type, gender, Integer.parseInt(args[4]));
			} else if (Mosquito.isMosquito(type)) {
				creature = new Mosquito(type,
						                gender,
						                Boolean.parseBoolean(args[4]),
								        Boolean.parseBoolean(args[5]),
										Boolean.parseBoolean(args[6]));
			} else if (Insect.isInsect(type)) {
				creature = new Insect(type, gender, Boolean.parseBoolean(args[4]));
			} else {
				System.out.format("Error:  Can't create unknown animal type '%s'.", type);
			}
			
			if (creature != null) {
				place(creature, origin);
			}
		}
	}
	
    /*
     * Purpose: Moves animal.
     * 
     * @param args, command line.
     * MOVE - Move all animals
     * MOVE (row,column) - Move only animals at this location
     * MOVE type - Move only animals of the specified type
     * MOVE class - Move only animals of the specified class
     * 
     * @returns none, moves animal to new position.
     */
	public void	move(String[] args) {
		reset();

		if (args.length > 1) {
			String	type = args[1].trim();
			
            System.out.println("> MOVE " + args[1]);
			if (type.startsWith("(")) {
				move(getRowCol(type));
			} else {
				type = type.toLowerCase();
				
				move(type, Arrays.asList("mammal", "bird", "insect").contains(type));
			}
		} else {
            System.out.println("> MOVE");
			move("", false);
		}
		
		System.out.println();
	}
	
    /*
     * Purpose: Prints grid.
     */
	public void	print() {
		System.out.println("> PRINT");
		
		for (int r = 0; r < bounds.getRow(); ++r) {
			List<List<Animal>>	columns = grid.get(r);
			String				line = "";
			
			for (int c = 0; c < bounds.getCol(); ++c) {
				List<Animal>	animals = columns.get(c);
				
				if (! animals.isEmpty()) {
					line += animals.get(0).id();
					
					continue;
				}

				line += '.';
			}
			
            System.out.println(line);
		}
		
		System.out.println();
	}
	
    /*
     * Purpose: Creates baby.
     * 
     * @param args, command line.
     * REPRODUCE - Reproduce all animals
     * REPRODUCE (row,column) - Reproduce only animals at this location
     * REPRODUCE type - Reproduce only animals of the specified type
     * 
     * @return none, creates a new baby and plaeces it on grid.
     */
	public void	reproduce(String[] args) {
		if (args.length > 1) {
			String	type = args[1].trim();
			
            System.out.println("> REPRODUCE " + args[1]);
			if (type.startsWith("(")) {
				reproduce(getRowCol(type));
			} else {
				reproduce(type.toLowerCase());
			}
		} else {
			reproduce("");
            System.out.println("> REPRODUCE");
		}
		
		System.out.println();
	}

    /*
     * Purpose: Handles move with location.
     * 
     * @param from, location where all animals should be moved.
     * 
     * @return none, moves from old position and places animal at new position.
     */
    private void move(RowCol from) {
		List<List<Animal>>	columns = grid.get(from.getRow());
		Iterator<Animal>	iter = columns.get(from.getCol()).iterator();
		
		while (iter.hasNext()) {
			Animal	creature = iter.next();
			RowCol	to = creature.move(from, bounds);
			
			iter.remove();
			place(creature, to);
		}
	}
	
    /*
     * Purpose: Handles move with type and class. Searches grid for specific
     * speicies or classes.
     * 
     * @param type, string type or class of animal.
     * 
     * @param moveClass, whether animal is valid animal class.
     * 
     * @return none
     */
    private void move(String type, Boolean moveClass) {
		Boolean	moveAll = type.isEmpty();

		for (int r = 0; r < bounds.getRow(); ++r) {
			List<List<Animal>>	columns = grid.get(r);

			for (int c = 0; c < bounds.getCol(); ++c) {
				Iterator<Animal>	iter = columns.get(c).iterator();
				RowCol				from = new RowCol(r, c);

				while (iter.hasNext()) {
					Animal	creature = iter.next();

					if (creature.isPlaced() == false) {
						Boolean	moveCreature = moveAll;

						if (moveCreature == false) {
							if (moveClass) {
								moveCreature = (creature.classification().equals(type));
							} else {
								moveCreature = (creature.species().equals(type));
							}
						}
	
						if (moveCreature) {
							RowCol	to = creature.move(from, bounds);
	
							iter.remove();
							place(creature, to);
						}
					}
				}
			}
		}
	}

    /*
     * Purpose: Handles reproduction with location.
     * 
     * @param location, row and col.
     * 
     * @return none
     */
    private void reproduce(RowCol location) {
		List<List<Animal>>	columns = grid.get(location.getRow());
		
		reproduce(columns.get(location.getCol()), "");
	}
	
    /*
     * Purpose: Reproduces animal of specified type of speicies.
     * 
     * @param type, type of animal class.
     * 
     * @return none
     */
    private void reproduce(String type) {
		for (int r = 0; r < bounds.getRow(); ++r) {
			List<List<Animal>>	columns = grid.get(r);
			
			for (int c = 0; c < bounds.getCol(); ++c) {
				reproduce(columns.get(c), type);
			}
		}
	}
	
    /*
     * Purpose: Determines if the animals are able to reproduce.
     * 
     * @param animals, valid list of animals.
     * 
     * @param species, given speicies to filter on or none for all species.
     * 
     * @return none
     */
    private void reproduce(List<Animal> animals, String species) {
		if (animals.size() >= 2) {
			Animal	first = animals.get(0);
			
			if ((species.length() > 0) && (first.species().equals(species) == false)) {
				return;
			}

			Animal	second = animals.get(1);

			if (first.species().equals(second.species())) {					//	Same species
				if (first.gender() != second.gender()) {					//	Opposite sex
					//	Reproduce
					Animal	baby = first.reproduce(second);
					
					if (baby != null) {
						animals.add(baby);
					}
				}
			}
		}
	}
	
    /*
     * Purpose: Placing animal at given location. Appends animal to list of
     * animals at given location.
     * 
     * @param creature, the animal.
     * 
     * @param location, row and col.
     * 
     * @return none, appends animal to list of animals at given location.
     */
    private void place(Animal creature, RowCol location) {
		List<List<Animal>>	columns = grid.get(location.getRow());
		List<Animal>		animals = columns.get(location.getCol());
		
		animals.add(creature);
		creature.placed();
	}
	
    /*
     * Purpose: Resets all animals to defaults. Clears internal flags.
     * 
     * @param none
     * 
     * @return none
     */
    private void reset() {
		for (List<List<Animal>> columns : grid) {
			for (List<Animal> animals : columns) {
				for (Animal	creature : animals) {
					creature.reset();
				}
			}
		}
	}

    /*
     * Purpose: Parses out row and column and returns instance of a RowCol
     * class.
     * 
     * @param coord, row and col coordinates.
     * 
     * @returns new RowCol instance
     */
    private RowCol getRowCol(String coord) {
        int start = coord.indexOf('(');
        if (start < 0) {
            System.out.format("Error:  Invalid location '%s'.", coord);

            return null;
        }

        int end = coord.indexOf(',', start + 1);
        if (end < 0) {
            System.out.format("Error:  Invalid location '%s'.", coord);

            return null;
        }

        int row = 0;
        int col = 0;

        String digits = coord.substring(start + 1, end);
        try {
            row = Integer.parseInt(digits);
        } catch (Exception e) {
            System.out.format("Error:  Invalid row number '%s'.", digits);

            return null;
        }

        start = end + 1;
        end = coord.indexOf(')', start);
        if (end < 0) {
            System.out.format("Error:  Invalid location '%s'.", coord);

            return null;
        }

        digits = coord.substring(start, end);
        try {
            col = Integer.parseInt(digits);
        } catch (Exception e) {
            System.out.format("Error:  Invalid row number '%s'.", digits);

            return null;
        }

        return new RowCol(row, col);
    }

    private List<List<List<Animal>>> grid;
    private RowCol bounds;
}
	
	
