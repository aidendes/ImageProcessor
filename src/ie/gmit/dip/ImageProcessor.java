package ie.gmit.dip;

/* ImageProcessor.class
 * 
 * This class exists to do the following two tasks:-
 * 
 * 1) Apply the Convolution Kernel / Filter per the Project Specification
 * 2) Convert an image to greyscale,
 * 
 * @author Aiden Desmond <G00398273@gmit.ie>
 */

public class ImageProcessor {

	/**
	 * Applies convolution kernel (filter) to a 2D Array representing a PNG image by
	 * iterating over the 2D array and the 2D representation of the kernel.
	 * 
	 * @param image  2D array representing a png image
	 * @param filter 2D array representation of the filter selected
	 * 
	 * @return a 2D array representing the image file after convolution.
	 * @throws Exception
	 */
	public double[][] applyFilter(double[][] image, double[][] filter) throws Exception {

		// create a new 2D array, of the same dimensions as the image's 2D array
		double[][] result = new double[image.length][image[0].length];
		int rows = result.length;
		int cols = result[0].length;

		for (int x = 0; x < rows; x++) { // Loop over the 2D array pixel-by-pixel
			for (int y = 0; y < cols; y++) {

				// Initialize some RGB variables for the pixel RGB values.
				float al = 0;
				float r = 0;
				float g = 0;
				float b = 0;

				// all convolution filters are _squares_, so length is a single value, 3 x 3, 5
				// x 5
				int fsize = filter.length;

				// start iterating over the kernel
				for (int row = 0; row < fsize; row++) {

					//
					for (int col = 0; col < fsize; col++) {

						// We need to assign an X and Y value for each element of the kernel
						// wrapping around for values in first row and column using modulo
						int elementX = (x - fsize / 2 + row + rows) % rows;
						int elementY = (y - fsize / 2 + col + cols) % cols;

						// from the project notes - sort of
						int element = (int) image[elementX][elementY];

						// we need to get the individual values for each colour channel
						int alpha = (element >> 24) & 0xff;
						int red = (element >> 16) & 0xff; // Red Value
						int green = (element >> 8) & 0xff; // Green Value
						int blue = (element) & 0xff; // Blue Value

						// the individual values are added to the running total of the RGB variables
						al += alpha; // alpha channel is left alone, not convoluted
						r += (red * filter[row][col]);
						g += (green * filter[row][col]);
						b += (blue * filter[row][col]);

					}
				}

				// Setting an outValue means we have to normalize to the range 1-255
				int outAlpha = Math.min(Math.max((int) al, 0), 255);
				int outRed = Math.min(Math.max((int) r, 0), 255);
				int outGreen = Math.min(Math.max((int) g, 0), 255);
				int outBlue = Math.min(Math.max((int) b, 0), 255);

				// putting the values back together, from the project notes
				int rgbOutput = 0;
				rgbOutput = rgbOutput | (outAlpha << 24);
				rgbOutput = rgbOutput | (outRed << 16);
				rgbOutput = rgbOutput | (outGreen << 8);
				rgbOutput = rgbOutput | outBlue;
				// and assigning the final RGB value to the pixel
				result[x][y] = rgbOutput;

			}
		}

		return result;

	}

	/**
	 * A simple extra, converting a colour image to Greyscale
	 * 
	 * @param image 2D array representing the image
	 * 
	 * @return a 2D array representing a greyscale version of the same image
	 * @throws Exception
	 */
	public double[][] greyScaleFilter(double[][] image) throws Exception {

		// Repeating the earlier process
		double[][] result = new double[image.length][image[0].length];
		int rows = result.length;
		int cols = result[0].length;

		for (int x = 0; x < rows; x++) { // Loop over the 2D array pixel-by-pixel
			for (int y = 0; y < cols; y++) {
				int pixel = (int) image[x][y];

				int alpha = (pixel >> 24) & 0xff;
				int red = (pixel >> 16) & 0xff; // Red Value
				int green = (pixel >> 8) & 0xff; // Green Value
				int blue = (pixel) & 0xff; // Blue Value

				// For greyscale, we just average the rgb values
				int greyVal = (red + green + blue) / 3;

				int rgbOutput = 0;

				// then we put them back to each colour channel
				rgbOutput = rgbOutput | (alpha << 24);
				rgbOutput = rgbOutput | (greyVal << 16);
				rgbOutput = rgbOutput | (greyVal << 8);
				rgbOutput = rgbOutput | greyVal;

				// and we put the new averaged values into the new array
				result[x][y] = rgbOutput;

			}
		}
		return result;
	}
}
