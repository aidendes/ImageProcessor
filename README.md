# ImageProcessing - Filtering an Image with a Convolution Kernel

Project by Aiden Desmond - student @gmit.ie

The project is contained in the src folder. Compiled by entering the `src` directory and entering the       
commands:-                                                         
`$javac ie/gmit/dip/*.java`

The compiled program is called with:-
`$java ie.gmit.dip.Runner`


## User Experience

On loading the user sees the following menu structure:-

```
	***************************************************
	* GMIT - Dept. Computer Science & Applied Physics *
	*                                                 *
	*           Image Filtering System V0.1           *
	*     H.Dip in Science (Software Development)     *
	*                                                 *
    *        Aiden Desmond - G00398273@gmit.ie        *
    *                                                 *
	***************************************************


	1) Enter Image Path and Name
	2) Select a Filter
	3) Choose Number of Passes				*
	4) Choose a Set of Three Filters		*
	5) Convert Colour Image to GreyScale	*
	6) Execute the Program....
	7) Quit

	[Options Marked * are optional and exclusive]
	Select Option [1-7] ->

Menu choices are made by entering a number in the range 1-7.
```

Choice 1 asks the user for the location of the directory which holds the image files they wish to process. It is *recommended* that the path be given relative to the `src/` directory, but it is not required.

User is then given a listing of files in their chosen directory which meet the specification, i.e. PNG image files, and is then asked to type the name of their chosen file. After validation, user is returned to the main menu. Choosing an image file is a *required* step.

Choice 2 loads a list of available filters (or convolution kernels) and asks the user to supply a choice by entering a number. Again, following validation, the choice is confirmed and the user returns to the main menu. Choosing a filter is a semi-required step.

If the user wishes, they can then proceed to execute the program (choice 6) and the program will perform a convolution filter on the chosen image file, and report the file to which the filtered image has been saved. This completes the mandatory assignment, and further details are given below under "Technical Details."

Choices 3, 4 and 5 are all *optional*, so the program will run, with just steps 1 and 2. Choices 3, 4 and 5 are all, moreover *exclusive*, you can only choose one of them, and completing one of them will cancel out the others.

Choice 3 allows the user to set a number for repeated applications of a filter. The program allows up to ten passes, but in testing, anything above three leads to unpleasant, not to say disquieting, results. This is an additional extra which was fun to program, and which forced me to rethink the layout of the application.

Choice 4 allows the user to set three different filters to be applied as a set. This is an additional Extra, and it led to a complete rewrite of the loading and convolution functions.

Choice 5 allows the user to convert a colour image to greyscale, instead of applying a filter. Once 3 and 4 had been implemented, this was a fun additional extra to provide to the user.

Choice 6 executes the program, doing whatever the user has selected. If the user has chosen a multipass function, then each pass is confirmed. Finally, the program confirms completion and exits to the main menu. For user convenience, the menu is already populated with the image file which has just been written, should the user want to carry out additional filtering. Of course, the user can just select the first menu option and choose a new file to work on.

## Technical Details

All classes and methods are commented using what I think is a conformant style, including `@param` and `@return` information, as well as a brief description.

`Runner.java` is a stub, which exists only to present the program information, and to call `Menu.java`.

`Menu.java` is quite complex to read, but is essentially quite simple. First the program instantiates a set of empty variables, which are used throughout the menu, and begins process of calling the different elements which allow the user to choose what type of filtering function they wish to carry out.

Where the method consisted primarily of constructor elements, these were abstracted to `Utils.java`. This includes the file picker, and the filter chooser, as well as an Object class consisting of a String and a 2D Array. It also provides some utility classes designed to made the Menu more readable, both front- and back-end.

When the user executes the program, all matters are handed off to `Convolution.java`. This consists primarily of an overloaded method, `convolute()`, which handles the different types of filter application.

`convolute()` uses the final two primary classes for functionality `ImageProcessor.java` contains the convolution filter application, as well as the greyscale converter. `FileHandler.java` handles IO operations exclusively.

All convolution kernels are provided as enums in `Kernel.java`.  Please note that apart from Kernel.java, I have tended to refer to these values as _filter_ throughout the project.

Bibliography


In creating the project, I have relied primarily on the Java Platform, Standard Edition & Java Development Kit Version 13 API Specification, September 17, 2019, (located at https://docs.oracle.com/en/java/javase/13/docs/api/index.html) and https://docs.oracle.com/en/java generally.


[Alvin Alexander's Java Blog](https://alvinalexander.com/java/) was a valuable resource, particularly for [A Java method to determine if an integer is between a range](https://alvinalexander.com/java/java-method-integer-is-between-a-range/)which is implemented in `Utils.java@223`.

I did refer to StackOverflow and other similar resources for inspiration, in particular [How to use hasNext() from the Scanner class?](https://stackoverflow.com/questions/34355062/how-to-use-hasnext-from-the-scanner-class), which various examples led to the logic of the Y/N inputs in `Menu.java@310`

Finally, the design of the kernel application logic is based on the method illustrated on Naushad Karim's blogpost, [Image Processing and Computer vision in java (Image Filtering part1)](https://naushadsblog.wordpress.com/2014/04/25/image-processing-and-computer-vision-in-java-image-filtering-part1/) although I do wish he'd written the second part.
