import java.util.Scanner;
/*
 * 
 * This class provides a calculator interface which uses the number class
 * 
 * @author Vinny Lurgio
 * @Version 1.0
 */
public class Calculator {
	/*
	 * Displays a menu for the user
	 */
	public static void displayMenu()
	{
		System.out.println("enter a value: e	add: a");
		System.out.println("subtract: s		multiply: m");
		System.out.println("reverse sign: r		clear: c");
		System.out.println("quit: q");
		System.out.print("-->");
	}
	
	public static void main(String args[])
	{
		Number theNumber= new Number();
		String num2;
		Number Number2;
		theNumber.addLast(0);
		finalloop:
		while(true)
		{
			try
			{
				System.out.print("Welcome to vinny's calculator please input a value to start with:");
				Scanner s = new Scanner(System.in);
				String op = s.next();
				theNumber = new Number(op);
				System.out.println(theNumber.toString());
				while(op.compareTo("q") != 0)
					{
						displayMenu();
						op = s.next().toString();
						switch (op)
						{
							case "e":
								System.out.print("value ->");
								num2 = s.next();
								theNumber = new Number(num2);
								break;
							case "a":
								System.out.print("value ->");
								num2 = s.next();
								Number2 = new Number(num2);
								theNumber = theNumber.add(Number2);
								System.out.println(theNumber.toString());
								break;
							case "s":
								System.out.print("value ->");
								num2 = s.next();
								Number2 = new Number(num2);
								theNumber = theNumber.subtract(Number2);
								System.out.println(theNumber.toString());
								break;
							case "m":
								System.out.print("value ->");
								num2 = s.next();
								Number2 = new Number(num2);
								theNumber = theNumber.multiply(Number2);
								System.out.println(theNumber.toString());
								break;
							case "r":
								theNumber.reverseSign();
								System.out.println(theNumber.toString());
								break;
							case "c":
								theNumber = new Number("0");
								System.out.println(theNumber.toString());
								break;
							case "comp":
								System.out.print("value ->");
								num2 = s.next();
								System.out.println(theNumber.compareTo(new Number(num2)));
								break;
							case "q":
								break finalloop;
						}
						
					}
			}
			catch (BadNumberException e)
			{
				System.out.println("Please enter a valid number!");
			}
		}
	}
}
