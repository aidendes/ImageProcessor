package ie.gmit.dip;

/**
 * 
 * @author Aiden Desmond  <G00398273@gmit.ie>
 * @author Dr. John Healy
 *
 */
public enum  Kernel {
	/*
	 * A Set of Convolution Kernels, or Filters, as enums.
	 * 
	 * Getter methods are at the end.
	 * 
	 * */
	
	
	IDENTITY(new double[][] {
		{0, 0, 0},
		{0, 1, 0},
		{0, 0, 0}
	}),
	
	
	EDGE_DETECTION_1(new double[][] {
		{-1, -1, -1},
		{-1,  8, -1},
		{-1, -1, -1}
	}),
	
	
	EDGE_DETECTION_2(new double[][]{
		{ 1, 0, -1},
		{ 0, 0,  0},
		{-1, 0,  1}
	}),
	
	
	LAPLACIAN(new double[][]{
		{ 0, -1,  0},
		{-1,  4, -1},
		{ 0, -1,  0}
	}),


	SHARPEN(new double[][]{
		{ 0, -1,  0},
		{-1,  5, -1},
		{ 0, -1,  0}
	}),


	VERTICAL_LINES(new double[][]{
		{-1, 2, -1},
		{-1, 2, -1},
		{-1, 2, -1}
	}),
	
	
	HORIZONTAL_LINES(new double[][]{
		{-1, -1, -1},
		{ 2,  2,  2},
		{-1, -1, -1}
	}),
	
	
	DIAGONAL_45_LINES(new double[][]{
		{-1, -1, 2},
		{-1, 2, -1},
		{2, -1, -1}
	}),

	
	BOX_BLUR(new double[][]{
		{0.111, 0.111, 0.111},
		{0.111, 0.111, 0.111},
		{0.111, 0.111, 0.111}
	}),
	
	SOBEL_HORIZONTAL(new double[][]{
		{-1, -2, -1},
		{ 0,  0,  0},
		{ 1,  2,  1}
	}),
	
	
	SOBEL_VERTICAL(new double[][]{
		{-1, 0, 1},
		{-2, 0, 2},
		{-1, 0, 1}
	}),
	
	EMBOSS(new double[][] {
		{-2, -1, 0},
		{-1, 1,  1},
		{0,  1,  2}
	}),
	
	GAUSSIAN(new double[][] {
		{0.003765, 	0.015019, 	0.023792, 	0.015019, 	0.003765},
		{0.015019, 	0.059912, 	0.094907, 	0.059912, 	0.015019},
		{0.023792, 	0.094907, 	0.150342, 	0.094907, 	0.023792},
		{0.015019, 	0.059912, 	0.094907, 	0.059912, 	0.015019},
		{0.003765, 	0.015019, 	0.023792, 	0.015019, 	0.003765}
	}),
	
	GAUSSIAN_LAPLACIAN(new double[][] {
		{ 0,  0, -1,  0,  0},
		{ 0, -1, -2, -1,  0},
		{-1, -2, 16, -2, -1},
		{ 0, -1, -2, -1,  0},
		{ 0,  0, -1,  0,  0}
	}),
	
	INTENSE_SHARP(new double[][] {
		{-1, -1, -1},
		{-1,  9, -1},
		{-1, -1, -1}
	});

	private final double[][] kernel;

	private Kernel(double[][] k) {
		this.kernel = k;
	}

	public double[][] getKernel() {
		return kernel;
	}

}