package CommandLine;

import Rectangle.RegionRectangle;

/* Command-line Interface
 * To test on Eclipse:
 * 		Run ---> Run Configurations ---> In the Arguments tab, enter arguments
 * 
 * Argument description:
 * 		x - latitude
 *		y - longitude
 *		k - number of nearest counties to dislay, must be between 1-10 inclusive
 * 
 */

public class CommandLine {
	/**
	 * Gets called when user provides incorrect commandline args
	 * @param errorCode: depends on the type of error from user-input
	 */
	static void errorCheck(int errorCode) {
		switch (errorCode) {
			// Incorrect number of arguments given
			case 1: System.out.println("Error:\tincorrect number of arguments entered");
					System.out.println("\tThis program requires three arguments in the following order:\n\t<latitude>   <longitude>   <number of results to display>\n\tExiting now.");
					System.exit(1);
			case 2: // out-of-bound points (ex. if user inputs 100 for the value for k)
			case 3: // incorrect input format (ex. if user inputs "abc", "17.3zre" as latitude, longitude)
		}
	}
	
	public static void main(String[] args) {
		int argsLen = args.length;
		
        if (argsLen != 3) {
        	errorCheck(1);
        } else {
        	Double x = Double.parseDouble(args[0]);
        	Double y = Double.parseDouble(args[1]);
    		int k = Integer.parseInt(args[2]);

    		/* If treating user-input coordinates as its own rectangle object
			double x1 = Double.parseDouble(x);
    		double x2 = Double.parseDouble(x);
    		double y1 = Double.parseDouble(y);
    		double y2 = Double.parseDouble(y);
    		RegionRectangle myRectangle = new RegionRectangle(x1, x2, y1, y2);
    		System.out.println(myRectangle.getHeight());
    		System.out.println(myRectangle.getWidth());
    		*/
    		
    		System.out.printf("Searching the %d nearest counties from point (%f, %f)...\n", k, x, y);
    		System.exit(0);
        }
    }
}
