package ie.gmit.dip;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/* FileHandler.class
 * 
 * This class exists to do the following two related tasks:-
 * 
 * 1) Load an image from a file into a 2D Array and send it back.
 * 2) Receive a 2D Array, convert to an Image and save it.
 * 
 * @author Aiden Desmond <G00398273@gmit.ie>
 */

public class FileHandler {

	// This needs to be in full scope for the class.
	private int imageType;

	/**
	 * Loads an image file from the disk and reads the ARGB values from the image
	 * file into a 2D array representation. Does not use the BufferedImage class as
	 * the representation, because documentation suggests this is not a "pure" 2D
	 * array, and is thus out of scope for this project.
	 * 
	 * @param file Name of file to load from disk.
	 * 
	 * @return 2D representation of the RGB values
	 * @throws IOException where errors on loading file
	 */
	public double[][] getImage(String file) throws IOException {
		// We create a Buffered Image from the file
		try {
			BufferedImage image = ImageIO.read(new File(file));

			// We determine the filetype, and save that for later.
			imageType = image.getType();

			/*
			 * While BufferedImage is a type of 2D Array, it is a very complex beast. This
			 * process creates a new 2D Array which consists _Just_ of the RGB values from
			 * each pixel, and returns that Array to the caller.
			 */

			double[][] imageArray = new double[image.getHeight()][image.getWidth()];

			// Iterating over the new simple 2D Array, and setting values from
			// BufferedImage data.

			for (int col = 0; col < imageArray[0].length; col++) {
				for (int row = 0; row < imageArray.length; row++) {
					double pixelRGB = image.getRGB(col, row);
					imageArray[row][col] = pixelRGB;
				}
			}
			// Finally, returning the imageArray to the caller.
			return imageArray;
		} catch (IOException e) {
			System.out.println("\t" + ConsoleColour.RED_BOLD_BRIGHT + "Oops... I can't read that file.");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Converts a 2D Array of ARGB values into an image file, saving that file to
	 * disk
	 * 
	 * @param outImage 2D array representing the ARGB values of an image.
	 * @param outFile  Name of the file (including path) to save the image.
	 * 
	 * @return filename as confirmation that the file has been saved
	 * @throws IOException in case of any errors in writing
	 */
	public String writeImage(double[][] outImage, String outFile) throws IOException {
		try {
			int imageHeight = outImage.length;
			int imageWidth = outImage[0].length;

			// Here's why we need that imageType from above.
			BufferedImage output = new BufferedImage(imageWidth, imageHeight, imageType);

			// Again, iterating over BufferedImage, reading values in from the 2D Array
			for (int y = 0; y < imageHeight; y++) {
				for (int x = 0; x < imageWidth; x++) {
					int val = (int) outImage[y][x];
					output.setRGB(x, y, val);
				}
			}

			ImageIO.write(output, "png", new File(outFile));
			return outFile;
			// if this fails, then we need to tell the user.
		} catch (IOException e) {
			System.out.println("Uh-oh... there appears to be a problem saving the file...");
			e.printStackTrace();
			return null;
		}
	}
}
