/*
 * This class provides an exception in the case a String representing 
 * a number is invalid
 * 
 * @author Vinny Lurgio
 * @version 1.0
 */
public class BadNumberException extends Exception
	{
	char badChar;
		public BadNumberException(char c) {
			badChar = c;
		}
	}