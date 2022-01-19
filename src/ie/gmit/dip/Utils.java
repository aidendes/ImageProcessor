package ie.gmit.dip;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*
 * Utils.class
 * 
 * Methods which form part of the menu, but which are not primarily
 * comprised of informational data.
 * 
 * @author Aiden Desmond <G00398273@gmit.ie>
 */

public class Utils {

	private Scanner s = new Scanner(System.in);

	/**
	 * An object class which allows the creation of an Array which has as its
	 * elements filtername a string representing the name of a kernel/filter filter
	 * a 2D array containing the values of that filter
	 * 
	 * @author aidenmac
	 *
	 */
	public static class mixedArray {
		String filterName;
		double[][] filter;

		mixedArray(String krnl, double[][] filter) {
			this.filterName = krnl;
			this.filter = filter;
		}
	}

	/**
	 * Allows user to choose an image filter to apply to their image from a list of
	 * all filters available in Kernel.java
	 * 
	 * @return mixedArray containing the filter name and the filter as double[][]
	 */
	public mixedArray kernelChooser() throws Exception {
		mixedArray mix = null;
		notice("The available image filters are as follows:-\n\n");
		int val = 1; // Set a starting value to make the menu functional

		// iterate over the values in Kernel and list them for the user
		for (Kernel k : Kernel.values()) {
			System.out.println("\t" + val + "\t" + k);
			val++;
		}
		System.out.println("\t16\tExit Menu");
		System.out.print("\n\tPlease Enter a Number [1-16]" + ConsoleColour.WHITE_BOLD + " -> " + ConsoleColour.GREEN);
		// Using the new switch -> case to set a String _and_ set a kernel/matrix value,
		// using the class mixedArray to provide a name and matrix/kernel to the caller
		// idea from the Java documentation on the -> notation
		try {
			int filterChoice = Integer.parseInt(s.next());

			switch (filterChoice) {
			case 1 -> {
				mix = new mixedArray("IDENTITY", Kernel.IDENTITY.getKernel());
			}
			case 2 -> {
				mix = new mixedArray("EDGE_DETECTION_1", Kernel.EDGE_DETECTION_1.getKernel());
			}
			case 3 -> {
				mix = new mixedArray("EDGE_DETECTION_2", Kernel.EDGE_DETECTION_2.getKernel());
			}
			case 4 -> {
				mix = new mixedArray("LAPLACIAN", Kernel.LAPLACIAN.getKernel());
			}
			case 5 -> {
				mix = new mixedArray("SHARPEN", Kernel.SHARPEN.getKernel());
			}
			case 6 -> {
				mix = new mixedArray("VERTICAL_LINES", Kernel.VERTICAL_LINES.getKernel());
			}
			case 7 -> {
				mix = new mixedArray("HORIZONTAL_LINES", Kernel.HORIZONTAL_LINES.getKernel());
			}
			case 8 -> {
				mix = new mixedArray("DIAGONAL_45_LINES", Kernel.DIAGONAL_45_LINES.getKernel());
			}
			case 9 -> {
				mix = new mixedArray("BOX_BLUR", Kernel.BOX_BLUR.getKernel());
			}
			case 10 -> {
				mix = new mixedArray("SOBEL_HORIZONTAL", Kernel.SOBEL_HORIZONTAL.getKernel());
			}
			case 11 -> {
				mix = new mixedArray("SOBEL_VERTICAL", Kernel.SOBEL_VERTICAL.getKernel());
			}
			case 12 -> {
				mix = new mixedArray("EMBOSS", Kernel.EMBOSS.getKernel());
			}
			case 13 -> {
				mix = new mixedArray("GAUSSIAN", Kernel.GAUSSIAN.getKernel());
			}
			case 14 -> {
				mix = new mixedArray("GAUSSIAN_LAPLACIAN", Kernel.GAUSSIAN_LAPLACIAN.getKernel());
			}
			case 15 -> {
				mix = new mixedArray("INTENSE_SHARP", Kernel.INTENSE_SHARP.getKernel());
			}
			case 16 -> {
				mix = null;
			}
			default -> throw new IllegalArgumentException("Value not in range: " + filterChoice);
			}
		} catch (Exception e) {
			this.warning("[User Error] You must enter a number between 1 and 15");
		}

		return mix;

	}

	/**
	 * Asks the user to select a file path, then shows a list of all files in that
	 * path of type PNG, and asks the user to type the name of their chosen file.
	 * 
	 * @return String of the filename, including path.
	 * @throws Exception
	 */
	public String fileChooser() throws Exception {

		boolean fileMenu = true;

		String[] filenames = null;
		String filePath = "";
		String filename = "";

		while (fileMenu) {
			boolean pathMenu = true;
			while (pathMenu) {
				String[] thisMenu = { "Please enter a path where your image files can be found.\n",
						"This should be a directory, not a path to a single file,",
						"and should be given relative to the program folder (e.g. ../),",
						"or an absolute system path (e.g. /home/user/ or D:\\folder\\)"};
				this.menuActive(thisMenu, "Path -> ");
				filePath = s.next();
				
				// Quick check for a file separator character
				if (!filePath.endsWith(File.separator)) {
					filePath += File.separator;
				}
				this.notice("\tYour images are located at: " + filePath);

				try {
					// This process taken from
					// https://stackabuse.com/java-list-files-in-a-directory/

					File f = new File(filePath);

					FilenameFilter filter = new FilenameFilter() {
						@Override
						public boolean accept(File f, String name) {
							return name.endsWith(".png");
						}
					};

					filenames = f.list(filter);
					if (filenames.length != 0) {
						this.notice("The following files are available:\n");
						for (String fname : filenames) {
							// Print the names of matching files
							System.out.println("\t" + fname);
							pathMenu = false;
						}
					} else {
						this.warning("[User Error] There are no png files at that location.");
					}
				} catch (Exception e) {
					this.warning("[User Error] Path is either wrong or not available.");
				}
			}

			// End of researched solution
			// Asks the user to type the name of the file they want to process
			System.out.print("\n\tPlease enter the name of your file " + ConsoleColour.WHITE_BOLD + " -> "
					+ ConsoleColour.GREEN);

			// TRUST BUT VERIFY! OK, maybe don't trust the user at all...
			// get the user's choice, but check it exists!!
			String fileChoice = s.next();

			// Is the users selection in the list of files? If so, back to the main menu.
			if (Arrays.asList(filenames).contains(fileChoice)) {
				filename = filePath + fileChoice;
				fileMenu = false;
			} else {
				// naughty user must be censured.
				this.warning("[User Error] You have entered a filename which does not exist!");
			}

		}
		
		return filename;
	}

	/**
	 * Simple check that a number is between bounds source in bibliography
	 * 
	 * @param i                 Integer which is being queried
	 * @param minValueInclusive The lower bound permitted
	 * @param maxValueInclusive The upper bound permitted
	 * 
	 * @return true/false for if the integer is within bounds
	 */
	public static boolean between(int i, int minValueInclusive, int maxValueInclusive) {
		return (i >= minValueInclusive && i <= maxValueInclusive);
	}

	/**
	 * Checks a mixedArray and checks it contains values. Sets both values to
	 * represent the IDENTITY kernel if empty.
	 * 
	 * @param mix The mixedArray to be checked
	 * @return a mixedArray with values filled in
	 */
	public mixedArray nullCheck(mixedArray mix) {
		if (mix != null) {
			return mix;
		} else {
			mix = new mixedArray("IDENTITY", Kernel.IDENTITY.getKernel());
			return mix;
		}
	}

	/**
	 * Menu Cleaner Method Sets some colours and spacing for a standard notice
	 *
	 * @param notice String to display on screen
	 */
	public void notice(String notice) {
		try {
			System.out.println();
			System.out.println("\t" + ConsoleColour.GREEN + notice + ConsoleColour.RESET);
			TimeUnit.SECONDS.sleep(1);
			System.out.print("\033[H\033[2J");
			System.out.flush();
		} catch (InterruptedException e) {
			System.out.println("Got Interrupted!");
			e.printStackTrace();
		}
	}

	/**
	 * Menu Cleaner Method Prints a warning text in bright red on screen
	 * 
	 * @param warning Text of the warning to send the user
	 */
	public void warning(String warning) {
		try {
			System.out.println(ConsoleColour.RED_BOLD_BRIGHT);
			System.out.println("\t" + warning);
			System.out.println(ConsoleColour.RESET);
			TimeUnit.SECONDS.sleep(2);
			System.out.print("\033[H\033[2J");
			System.out.flush();
		} catch (InterruptedException e) {
			System.out.println("Got Interrupted!");
			e.printStackTrace();
		}
	}

	/**
	 * Menu Cleaner Method Shows the user a menu and a place to enter a choice
	 * 
	 * @param menu   Array of strings to display to the user, one per line
	 * @param choice String to indicate the program awaits user input
	 */
	public void menuActive(String[] menu, String choice) {
		System.out.print("\033[H\033[2J");
		System.out.flush();
		for (String string : menu) {
			System.out.println(ConsoleColour.RESET + "\t" + ConsoleColour.GREEN + string);
		}
		System.out.print("\t" + choice + ConsoleColour.RESET);

	}

	/**
	 * Menu Cleaner Method Displays the given string in Bright Cyan
	 * 
	 * @param item Text to display
	 */
	public void menuItem(String item) {
		System.out.print(ConsoleColour.CYAN_BRIGHT);
		System.out.print("\t" + item);
		System.out.println(ConsoleColour.RESET);
	}

}
