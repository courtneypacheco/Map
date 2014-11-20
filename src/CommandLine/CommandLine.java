package CommandLine;

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
		
        if (argsLen < 3) {
        	errorCheck(1);
        } else {
        	String x = args[0];
        	String y = args[1];
    		String k = args[2];
    		System.out.printf("Searching the %s nearest counties from point (%s, %s)...\n", k, x, y);
    		System.exit(0);
        }
    }
}
