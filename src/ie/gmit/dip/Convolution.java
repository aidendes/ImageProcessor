package ie.gmit.dip;

/* Convolution.class
 * 
 * This class exists to do the following tasks:-
 * 
 * 1) Determine what the user wants to do.
 * 2) Send the filename to the FileHandler, expecting a 2D Array in return
 * 3) Send that 2D array representation to the processor, for application
 *    of the chosen image kernel/filter
 * 4) Send the final 2D Array back to the FileHandler
 * 
 * @author Aiden Desmond <G00398273@gmit.ie>
 */

public class Convolution {

	private FileHandler fh = new FileHandler();
	private ImageProcessor ip = new ImageProcessor();

	/**
	 * Intermediate stage of Image Processing, sends the filename to be loaded and
	 * the image, as a 2D Array, to the Image Processor
	 * 
	 * @param filename   String representing the file to be used, including path
	 * @param filter     2D Array representing the filter
	 * @param filterName String identifying the filter
	 * 
	 * @return filename of the processed image saved to disk
	 * @throws Exception
	 */
	public String convolute(String filename, double[][] filter, String filterName) throws Exception {

		// First, we call the method from FileHandler which loads the image to a
		// 2D-Array
		double[][] image = fh.getImage(filename);

		// This strips the extension from the filename for later use
		filename = filename.substring(0, filename.lastIndexOf('.'));

		// Then we send the array to ImageProcessor to have the filter applied
		double[][] result = ip.applyFilter(image, filter);

		// Finally, we call the FileHandler to convert the 2D Array to an image, write
		// that to
		// a file, reporting any error.
		String fileOut = fh.writeImage(result, filename + "_" + filterName + ".png");
		return fileOut;
	}

	/**
	 * Intermediate stage of Image Processing, sends the filename to be loaded and
	 * the image, as a 2D Array, to a recursive function
	 * 
	 * @param filename   String representing the file to be used, including path
	 * @param filter     2D Array representing the filter
	 * @param filterName String identifying the filter
	 * @param passes     Number of times to repeat the filter
	 * 
	 * @return filename of the processed image saved to disk
	 * @throws Exception
	 */
	public String convolute(String filename, double[][] filter, String filterName, int passes) throws Exception {

		double[][] image = fh.getImage(filename);
		filename = filename.substring(0, filename.lastIndexOf('.'));
		double[][] result = repeatFilter(image, filter, 0, passes);

		// call the FileHandler to write the image, reporting any error
		String fileOut = fh.writeImage(result, filename + "_" + filterName + "_" + passes + ".png");
		return fileOut;

	}

	/**
	 * Intermediate stage of Image Processing, sends the filename to be loaded and
	 * repeatedly sends the image, as a 2D Array, for application of the filter
	 * 
	 * @param filename   String representing the file to be used, including path
	 * @param filterName String identifying the filters to be used
	 * @param filter1
	 * @param filter2    2D Arrays representing the filters to be applied
	 * @param filter3
	 * 
	 * @return filename of the processed image saved to disk
	 * @throws Exception
	 */
	public String convolute(String filename, String filterName, double[][] filter1, double[][] filter2,
			double[][] filter3) throws Exception {

		double[][] image = fh.getImage(filename);
		filename = filename.substring(0, filename.lastIndexOf('.'));
		// create a new array, with the filter applied
		double[][] result1 = ip.applyFilter(image, filter1);
		double[][] result2 = ip.applyFilter(result1, filter2);
		double[][] result3 = ip.applyFilter(result2, filter3);

		// call the FileHandler to write the image, reporting any error
		String fileOut = fh.writeImage(result3, filename + "_" + filterName + ".png");
		return fileOut;

	}

	/**
	 * Intermediate stage of Image Processing. Sends the filename to be loaded into
	 * a 2D array, and then sends that array to be coverted into greyscale.
	 * 
	 * @param filename  String representing the file to be used, including path
	 * @param grayScale Confirmation that this filter is correct one to apply
	 * 
	 * @return filename of the processed image saved to disk
	 * @throws Exception
	 */
	// Simpler method for the greyScale conversion.
	// Just read the file and send it to ip.greyScale
	public String convolute(String filename, boolean grayScale) throws Exception {
		double[][] image = fh.getImage(filename);
		filename = filename.substring(0, filename.lastIndexOf('.'));
		double[][] result = ip.greyScaleFilter(image);
		String fileOut = fh.writeImage(result, filename + "_GreyScale.png");
		return fileOut;

	}

	/**
	 * Recursive method to permit repeated application of a single filter to the
	 * image.
	 * 
	 * @param image    User selected image, converted to a 2D array
	 * @param filter   2D array of the filter to be applied
	 * @param thisPass Number of passes which have already been applied
	 * @param passes   Total number of passes which are to be applied
	 * 
	 * @return 2D array representing the image after application of all filters
	 * @throws Exception
	 */
	private double[][] repeatFilter(double[][] image, double[][] filter, int thisPass, int passes) throws Exception {

		double[][] procImage = ip.applyFilter(image, filter);

		if (thisPass == passes) {
			return procImage;

		} else {
			thisPass++;
			System.out.println("\tPasses Completed: " + thisPass);
			return repeatFilter(procImage, filter, thisPass, passes);
		}

	}

}
