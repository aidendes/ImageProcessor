package ie.gmit.dip;

import java.util.Scanner;

/*
 * Menu.class
 * 
 * This is a long class, because menus are hard, and making the
 * menu look pretty at the same time the code looks simple is
 * not something I could manage.
 * 
 * 
 * @author Aiden Desmond <G00398273@gmit.ie>
 */

public class Menu {

	/**
	 * A utility class consisting of all the necessary variables for the menu to
	 * function, including getter and setter methods
	 * 
	 * @author aidenmac
	 *
	 */
	private class settings {
		public String getFilterName() {
			return filterName;
		}

		public void setFilterName(String filterName) {
			this.filterName = filterName;
		}

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public int getPassChoice() {
			return passChoice;
		}

		public void setPassChoice(int passChoice) {
			this.passChoice = passChoice;
		}

		public boolean isGreyScale() {
			return greyScale;
		}

		public void setGreyScale(boolean greyScale) {
			this.greyScale = greyScale;
		}

		public boolean isMulti() {
			return multi;
		}

		public void setMulti(boolean multi) {
			this.multi = multi;
		}

		public double[][] getFilter() {
			return filter;
		}

		public void setFilter(double[][] filter) {
			this.filter = filter;
		}

		public double[][] getFilter2() {
			return filter2;
		}

		public void setFilter2(double[][] filter2) {
			this.filter2 = filter2;
		}

		public double[][] getFilter3() {
			return filter3;
		}

		public void setFilter3(double[][] filter3) {
			this.filter3 = filter3;
		}

		// When class is instantiated, return the default/empty values
		String filterName = "";
		String filename = "";
		int passChoice;
		boolean greyScale = false;
		boolean multi = false;
		double[][] filter = null;
		double[][] filter2 = null;
		double[][] filter3 = null;
	}

	// set up a scanner for user input at the menu
	private Scanner s;

	// Instantiate the Utils class
	Utils u = new Utils();

	// boolean flag to keep the menu operational until exit is called
	private boolean keepRunning = true;
	public settings g;

	public Menu() throws InterruptedException {
		s = new Scanner(System.in);
	}

	/*
	 * Starts up the menu
	 */
	public void start() throws Exception {
		// instantiates a new set of empty settings using the class above
		this.g = new settings();

		// set a variable to keep the menu alive until user exits
		while (keepRunning) {
			// This calls the menu the user sees on the screen
			showOptions();

			try {
				int choice = Integer.parseInt(s.next());
				// Each option from the menu needs a method to do something.
				switch (choice) {
				case 1 -> fileChooser();
				case 2 -> filterChooser();
				case 3 -> passPicker();
				case 4 -> multiChooser();
				case 5 -> greyScaleToggle();
				case 6 -> execute();
				case 7 -> shutDown();
				default -> throw new IllegalArgumentException("User Error: Value not in range: " + choice);
				}
			} catch (Exception e) {
				u.warning("User Error: You must enter a number between 1 and 7");
			}
		}
	}

	/**
	 * The main menu the user sees
	 * 
	 * @throws Exception
	 */
	private void showOptions() throws Exception {
		// Allows a cleaner presentation
		System.out.println();
		System.out.flush();
		// Dynamic menu adds a little complexity here.
		imagePickerMenu();
		filterPickerMenu();
		passPickerMenu();
		multiPickerMenu();
		greyScaleMenu();

		// These are non-dynamic, so remain here.
		System.out.println("\t6) Execute the Program....");
		System.out.println("\t7) Quit"); // Terminate
		System.out.println("\n\t[Options Marked * are optional and exclusive]");
		System.out.print("\tSelect Option [1-7] -> ");

		System.out.print(ConsoleColour.GREEN);

	}

	/**
	 * Each menu changes colour to CYAN when that option has been set and remains
	 * active.
	 */
	private void imagePickerMenu() {
		if (g.getFilename().isEmpty()) {
			System.out.println("\t1) Enter Image Path and Name");
		} else {
			// if the user has set a choice, then they are shown the filename.
			u.menuItem("1) Image Selected:\t" + g.getFilename());
		}
	}

	private void filterPickerMenu() {
		if (g.getFilterName().isEmpty()) {
			System.out.println("\t2) Select a Filter");
		} else {
			// Once a kernel is selected, the name of the kernel, or kernels, is shown
			u.menuItem("2) Filter Selected:\t" + g.getFilterName());
		}
	}

	private void passPickerMenu() {
		if (g.getPassChoice() == 0) {
			System.out.println("\t3) Choose Number of Passes\t\t*");
		} else {
			// If multiple passes are required, show the number set
			u.menuItem("3) Passes Chosen:\t" + g.getPassChoice());
		}
	}

	private void multiPickerMenu() {
		if (!g.isMulti()) {
			System.out.println("\t4) Choose a Set of Three Filters\t*");
		} else {
			u.menuItem("4) Filter Set Chosen");
		}
	}

	private void greyScaleMenu() {
		if (!g.isGreyScale()) {
			System.out.println("\t5) Convert Colour Image to GreyScale\t*");
		} else {
			// Show greyscale is selected.
			u.menuItem("5) GreyScale Conversion Only");
		}
	}

	/**
	 * This method shows the user a list of possible values, and asks them to enter
	 * a filename
	 * 
	 * @throws Exception
	 */
	private void fileChooser() throws Exception {
		// Gets filename & path from the Utils class.
		g.setFilename(u.fileChooser());

		if (!g.getFilename().isEmpty()) {
			u.notice("\tYour chosen file is " + g.getFilename());
		}
	}

	/**
	 * This method shows the user a list of possible filters to choose from
	 * 
	 * @throws Exception
	 */
	private void filterChooser() throws Exception {

		// set up a flag to keep this sub-menu alive until we are done
		boolean filterMenu = true;
		while (filterMenu) {
			// This gets a mixed array of [String kernelName, double[][] kernel]
			// by a call to the Utils class
			Utils.mixedArray mix = u.kernelChooser();
			// check that values are set before continuing
			if (mix != null) {
				// separate those out and into variables.
				g.setFilterName(mix.filterName);
				g.setFilter(mix.filter);
				filterMenu = false;
				// Tell the user what they've selected, and back to main menu.
				// Errors are dealt with in the Utility class.
				if (!g.getFilterName().isEmpty()) {
					u.notice("\tYour chosen filter is " + g.getFilterName());
				}
			} else {
				u.notice("No filter selected. Exiting");
				filterMenu = false;
			}
		}
	}

	/**
	 * This repeats a similar process, except it does it three times.
	 * 
	 * @throws Exception
	 */
	private void multiChooser() throws Exception {

		boolean multiMenu = true;
		while (multiMenu) {

			Utils.mixedArray mix = u.kernelChooser();
			// Check the user hasn't just exited the filterChooser, setting IDENTITY if they
			// have
			mix = u.nullCheck(mix);
			g.setFilterName(mix.filterName);
			g.setFilter(mix.filter);
			u.notice("\tYour first chosen filter is " + mix.filterName);

			Utils.mixedArray mix2 = u.kernelChooser();
			mix2 = u.nullCheck(mix2);
			g.setFilterName(g.getFilterName() + "_" + mix2.filterName);
			g.setFilter2(mix2.filter);
			u.notice("Your second chosen filter is " + mix2.filterName);

			Utils.mixedArray mix3 = u.kernelChooser();
			mix3 = u.nullCheck(mix3);
			g.setFilterName(g.getFilterName() + "_" + mix3.filterName);
			g.setFilter3(mix3.filter);
			u.notice("Your third chosen filter is " + mix3.filterName);

			multiMenu = false;
			if (!g.getFilterName().isEmpty()) {
				u.notice("\tYour chosen filters are " + g.getFilterName());
				g.setGreyScale(false);
				g.setPassChoice(0);
				g.setMulti(true);
			}
		}
	}

	/**
	 * Allow the user to choose to repeat the filter application.
	 * 
	 * @throws Exception
	 */
	private void passPicker() throws Exception {
		// set up a flag to keep this sub-menu alive until we are done
		boolean passMenu = true;

		while (passMenu) {
			String[] thisMenu = { "How many passes, or repeats, of your chosen filter would you like?",
					"You can pick from 1 - 10, but anything above 5 is usually pointless." };
			u.menuActive(thisMenu, "[1-10] -> ");

			try {
				g.setPassChoice(Integer.parseInt(s.next()));
				// Java doesn't offer a simple between number function, I copied one.
				// Checks the number passed is a valid choice, if so, back to main menu.
				if (Utils.between(g.getPassChoice(), 1, 10)) {
					u.notice("You have chosen to repeat the filter " + g.getPassChoice() + " times.");

					// Greyscale, FilterSets and Passes are Exclusive
					passMenu = false;
					g.setGreyScale(false);
					g.setMulti(false);
				} else {
					// If it isn't, tell the user and go again.
					u.warning("You must enter a number in the range [1 - 10]");
				}
				// If the user doesn't even enter a number, they are told to do so.
			} catch (java.lang.NumberFormatException e) {
				u.warning("You must enter a number in the range [1 - 10]");
			}
		}
	}

	/**
	 * Allow the user to choose to just convert their image to Greyscale. This also
	 * unsets any filters or passes, and the menu reflects that.
	 * 
	 * @throws Exception
	 */
	private void greyScaleToggle() throws Exception {
		// set up a flag to keep this sub-menu alive until we are done
		boolean greyMenu = true;

		while (greyMenu) {
			String[] greyMenu1 = { "Warning: This will unset all filter options!",
					"Convert your chosen image to GreyScale?" };
			u.menuActive(greyMenu1, "[Y / N] -> ");

			// Using Scanner's regex function to detect a valid answer.
			while (!s.hasNext("[YNyn]")) {
				String[] greySubMenu = { "Y or N, please." };
				u.menuActive(greySubMenu, "[Y / N] -> ");
				// asks again.
				s.next();
			}
			char greyToggle = s.next().charAt(0);
			// If the user changes their mind, back to the main menu
			// preserving their previous filter & pass choices
			if (greyToggle == 'N' || greyToggle == 'n') {
				u.notice("Okay, leaving menu...");
				greyMenu = false;
				g.setGreyScale(false);
				// If they proceed, wipe those values and go back to the main menu
			} else {
				u.notice("\tOkay, Greyscale Option Chosen.");
				g.setGreyScale(true);
				g.setFilterName("");
				g.setMulti(false);
				g.setFilter(null);
				g.setPassChoice(0);
				greyMenu = false;
			}
		}
	}

	private void shutDown() {
		u.warning("User has told us to shut down.");
		u.warning("Shutting down now...\nAiden Desmond - G00398273@gmit.ie");
		keepRunning = false;
	}

	/**
	 * Brings everything together and sends all information off to be processed
	 * 
	 * @throws Exception
	 */
	private void execute() throws Exception {
		// Clearing the display
		u.notice("Preparing to execute your instructions...");
		;
		String allDone = "";

		// Sets up the Convolution class
		Convolution con = new Convolution();

		// first level of check is if a filename _and_ a kernel are set.
		if (!g.getFilterName().isEmpty() && !g.getFilename().isEmpty()) {

			// if a kernel is set, are we doing any passes?
			if (g.getPassChoice() != 0) {
				allDone = con.convolute(g.getFilename(), g.getFilter(), g.getFilterName(), g.getPassChoice());
			} else {
				// Are we being asked to apply a set of filters?
				if (g.isMulti()) {
					allDone = con.convolute(g.getFilename(), g.getFilterName(), g.getFilter(), g.getFilter2(),
							g.getFilter3());
				} else {
					// If neither is true, just do a single convolution application
					allDone = con.convolute(g.getFilename(), g.getFilter(), g.getFilterName());
				}

			}
			// If all has worked well, and only if all has worked well, complete the program
			if (!allDone.isEmpty()) {
				u.notice("Processing complete.\n\tYour image has been saved as: " + allDone);
				// reset all setttings
				this.g = new settings();
				// set the filename to the just-created image before returning to the main menu
				g.setFilename(allDone);
			}
		} else {
			// If a kernel has not been set, are we doing a greyscale conversion?
			if (g.isGreyScale()) {
				allDone = con.convolute(g.getFilename(), true);
			}
			if (!allDone.isEmpty()) {
				u.notice("Processing complete.\n\tYour image has been saved as: " + allDone);
				// reset all settings
				this.g = new settings();
				g.setFilename(allDone);
			} else {
				// with all other options accounted for, tell the user they're doing it wrong
				// and reset
				u.warning("You have not selected a file and something to do with it.\n\tPlease do so.");
			}
		}
	}
}