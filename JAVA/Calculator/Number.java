import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Scanner;
/*
 * This class provides a template for the linked list representation of the 
 * numbers obtained from calculator
 * 
 * @author Vinny Lurgio
 * @version 1.0
 */
public class Number implements Comparable<Number> {

	protected Node low, high;
	protected int digitCount;
	protected int decimalPlaces;
	protected boolean negative;

	/*
	 * Constructor to initialize fields
	 */
	public Number()
	{
		low = null;
		high = null;
		digitCount = 0;
		decimalPlaces = 0;
		negative = false;
	}
	/*
	 * Constructor to begin a Number with a value
	 * 
	 * @param String the string representation of a number
	 * @throws BadNumberException if the string is not a valid number
	 */
	public Number(String str) throws BadNumberException
	{
		accept(str);
	}
	
	/*
	 * Receives a string, validates it, then puts it into the linked list
	 * 
	 * @param String the number to be added
	 * @throws BadNumberException if the string is not a valid number
	 * @see validate
	 */
	public void accept(String str) throws BadNumberException
	{
		validate(str);
		char[] array = str.toCharArray();
		digitCount = array.length;
		Node firstAdd = null;
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == '-')
			{
				negative = true;
				digitCount--;
			}
			else if (array[i] == '.')
			{
				decimalPlaces = array.length -1 - i;
				digitCount--;
			}
			else
			{
				if (high == null)
				{
					int num = Character.getNumericValue(array[i]);
					firstAdd = new Node(num, null, null);
					high = firstAdd;
					low = firstAdd;
				}
				else
				{
					int num = Character.getNumericValue(array[i]);
					Node toAdd = new Node(num, firstAdd, null);
					firstAdd.next = toAdd;
					firstAdd = toAdd;
					low = toAdd;
				}	
			}
		}	
	}
	
	/*
	 * checks to make sure the string given represents a valid number
	 * 
	 * @param String the string to be checked
	 * @throws BadNumberException if the string is not a valid number
	 */
	public void validate(String str) throws BadNumberException
	{
		char[] array = str.toCharArray();
		boolean foundDot = false;
		boolean foundMinus = false;
		
		for(int i = 0; i < array.length; i++)
		{
			char current = array[i];
				if ((current < '0' || current > '9') && ((current != '.' || foundDot == true) && (current != '-' || foundMinus == true))) throw new BadNumberException(current);
				if (current == '.') foundDot = true;
				if (current == '-') foundMinus = true;
			
		}
	}
	
	/*
	 * adds the number n to the current number
	 * 
	 * @param Number the number to be added
	 * @thros BadNumberException if n is not a valid number
	 */
	public Number add(Number n)
	{
		try
		{
			int dec2 = n.decimalPlaces;
			int tempDec = decimalPlaces;
			int tempDig = digitCount;
			int dig2 = n.digitCount;
			int rem = 0;
			int cur;
			int d1;
			int d2;
			char[] thisArray = toStringNoDot().toCharArray();
			char[] nArray = n.toStringNoDot().toCharArray();
			StringBuilder answer = new StringBuilder();
			
			if (negative == true && n.negative == true)
			{
				Number answerN = addAbsolute(n);
				answerN.negative = true;
				return answerN;
			}
			if (negative != n.negative)
			{
				if (negative == true) 
				{
					return n.subtract(this);
				}
				else 
				{
					return subtract(n);
				}
			}
			
			if(decimalPlaces != dec2)
			{
				if (decimalPlaces > dec2)
				{
					for (int i = thisArray.length-1; i > (thisArray.length - 1) - (decimalPlaces - dec2); i--)
					{
						answer.append(Character.getNumericValue(thisArray[i]));
						tempDig--;
					}
					thisArray = Arrays.copyOfRange(thisArray, 0, digitCount - (decimalPlaces - dec2));
				}
				else
				{
					for (int i = nArray.length-1; i > (nArray.length - 1) - (dec2 - decimalPlaces); i--)
					{
						answer.append(Character.getNumericValue(nArray[i]));
						dig2--;
					}
					nArray = Arrays.copyOfRange(nArray, 0, n.digitCount - (n.decimalPlaces - tempDec));
				}
			}
			for (int j = (nArray.length < thisArray.length) ? nArray.length -1 : thisArray.length -1; j > -1; j--)
			{
				if (nArray.length -1 > thisArray.length -1)
				{
					d1 = Character.getNumericValue(thisArray[j]);
					d2 = Character.getNumericValue(nArray[j+1]);
				}
				else if(nArray.length == thisArray.length)
				{
					d1 = Character.getNumericValue(thisArray[j]);
					d2 = Character.getNumericValue(nArray[j]);
				}
				else 
				{
					d1 = Character.getNumericValue(thisArray[j+1]);
					d2 = Character.getNumericValue(nArray[j]);
				}
				cur = d1 + d2 + rem;
				if (cur > 9)
				{
					cur = cur - 10;
					rem = 1;
				}
				else
				{
					rem = 0;
				}
				answer.append(Integer.toString(cur));
				
				
			}
			if (thisArray.length != nArray.length)
			{
				if (thisArray.length > nArray.length)
				{
					for (int j = thisArray.length - nArray.length - 1; j > -1; j--)
					{
						if (rem != 0)
						{
							answer.append(Character.getNumericValue(thisArray[j])+1);
							rem = 0;
						}
						else
						{
							answer.append(Character.getNumericValue(thisArray[j]));
						}
					}
				}
				else
				{
					for (int j = nArray.length - thisArray.length - 1; j > -1; j--)
					{
						if (rem != 0)
						{
							answer.append(Character.getNumericValue(nArray[j])+1);
							rem = 0;
						}
						else
						{
							answer.append(Character.getNumericValue(nArray[j]));
						}
					}
				}
			}
			
			answer.append(Integer.toString(rem));
			Number finalAnswer = new Number(answer.reverse().toString());
			finalAnswer.decimalPlaces = (decimalPlaces > n.decimalPlaces) ? decimalPlaces : n.decimalPlaces;
			finalAnswer.trim();
			return finalAnswer;
		}
		catch (BadNumberException e)
		{
			System.out.println(e);
			return null;
		}
		
			
	}
	
	/*
	 * adds a new node with data field n to the beginning of the list
	 * 
	 * @param int the data to be added in the node
	 */
	public void addFirst(int n)
	{
		if (high == null)
		{
			Node toAdd = new Node(n, null, high);
			high = toAdd;
			digitCount++;
			low = high;
		}
		else 
		{
			Node toAdd = new Node(n, null, high);
			high = toAdd;
			digitCount++;
		}
	}
	/*
	 * adds a new node at the end of the linked list with data field n
	 * 
	 * @param int the data to be added to the node
	 */
	public void addLast(int n)
	{
		if (high == null)
		{
			Node toAdd = new Node(n, low, null);
			high = toAdd;
			low = toAdd;
			digitCount++;
		}
		else
		{
			Node toAdd = new Node(n, low, null);
			low.next = toAdd;
			digitCount++;
			low = toAdd;
		}
		
	}
	/*
	 * subtracts number n from the current number
	 * 
	 * @param Number the number to be subtracted
	 */
	public Number subtract(Number n)
	{
		try
		{
			this.trim();
			n.trim();
			int finalDigitCount = 0;
			int dec2 = n.decimalPlaces;
			int tempDec = decimalPlaces;
			int tempDig = digitCount;
			int dig2 = n.digitCount;
			char[] thisArray = toStringNoDot().toCharArray();
			char[] nArray = n.toStringNoDot().toCharArray();
			StringBuilder answer = new StringBuilder();
			
			if (compareTo(n) < 0)
			{
				Number answerN = n.subtract(this);
				answerN.negative = true;
				return answerN;
			}
			if (negative == true && n.negative == true)
			{
				Number answerN = subtractAbsolute(n);
				if (compareTo(n) > 0 || compareToAbsolute(n) == 0)
				{
					answerN.negative = false;
					return answerN;
				}
				else 
				{
					answerN.negative = true;
					return answerN;
				}
			}
			if(decimalPlaces != n.decimalPlaces)
			{
				if (decimalPlaces > n.decimalPlaces)
				{
					while(decimalPlaces != n.decimalPlaces)
					{
						n.addLast(0);
						n.decimalPlaces++;
					}
					nArray = n.toStringNoTrim().toCharArray();
				}
				else
				{
					while(decimalPlaces != n.decimalPlaces)
					{
						addLast(0);
						decimalPlaces++;
					}
					thisArray = toStringNoTrim().toCharArray();
				}
				
			}
			while((digitCount - decimalPlaces) != (n.digitCount - n.decimalPlaces))
			{
				
				if (digitCount - decimalPlaces > n.digitCount - n.decimalPlaces)
				{
					n.addFirst(0);
					nArray = n.toStringNoTrim().toCharArray();
				}
				else
				{
					this.addFirst(0);
					thisArray = toStringNoTrim().toCharArray();
				}
			}
			
			int borrow = 0;
			int thisDigit;
			int nDigit;
			int newDigit;
			for (int j = nArray.length-1; j > -1; j--)
			{
				finalDigitCount++;
				thisDigit = Character.getNumericValue(thisArray[j]);
				nDigit = Character.getNumericValue(nArray[j]);
				newDigit = thisDigit - nDigit - borrow;
				
				if (newDigit < 0)
				{
					newDigit += 10;
					borrow = 1;
				}
				else borrow = 0;
				answer.append(newDigit);
			}
				Number finalAnswer = new Number(answer.reverse().toString());
				finalAnswer.digitCount = finalDigitCount;
				finalAnswer.decimalPlaces = (decimalPlaces > n.decimalPlaces) ? decimalPlaces : n.decimalPlaces;
				return finalAnswer;
		}
		catch (BadNumberException e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	/*
	 * multiplies the number times the given n 
	 * 
	 * @param Number the number to be multiplied
	 */
	public Number multiply(Number n)
	{
		try
		{
			Number product = new Number("0");
			int nDigit;
			Node ptr = n.high;
			int newDigit = 0;
			while(ptr != null)
			{
				nDigit = ptr.data;
				Number partialProduct = new Number();
				int carry = 0;
				Node thisPtr = this.low; 
				while(thisPtr != null)
				{
					int thisDigit = thisPtr.data;
					newDigit = nDigit*thisDigit+carry;
					carry = newDigit/10;
					newDigit = newDigit % 10;
					partialProduct.addFirst(newDigit);
					thisPtr = thisPtr.prev;
				}
				if (carry != 0)
				{
					partialProduct.addFirst(carry);
				}
				product.digitCount++;
				if (product.decimalPlaces > product.digitCount) product.addFirst(0); 
				else product.addLast(0);
				product = product.addAbsolute(partialProduct);
				ptr = ptr.next;
			}
			product.decimalPlaces = decimalPlaces + n.decimalPlaces;
			if (negative != n.negative)
			{
				product.negative = true;
			}
			product.trim();
			return product;
		}
		catch (BadNumberException e)
		{
			System.out.println("An exception occured because of the character " +e.badChar );
			return null;
		}
		
	}
	/*
	 * adds the given n ignoring the signs of both n and this number
	 * 
	 * @param Number the number to be added
	 */
	public Number addAbsolute (Number n) throws BadNumberException
	{
		Number a;
		if (digitCount != 0)
		{
			a = new Number(toString());
		}
		else a = new Number("0");
		Number b = new Number(n.toString());
		a.negative = false;
		b.negative = false;
		return a.add(b);
	}
	
	
	/*
	 * subtracts the number n from the current one ignoring signs
	 * 
	 * @param Number the number to be subtracted
	 */
	public Number subtractAbsolute(Number n) throws BadNumberException
	{
		Number a = new Number(toString());
		Number b = new Number(n.toString());
		a.negative = false;
		b.negative = false;
		return a.subtract(b);
	}
	
	/*
	 * compares the given n with the current number ignoring signs
	 * 
	 * @param N the number to be compared to
	 */
	public int compareToAbsolute(Number n) throws BadNumberException
	{
		Number a = new Number(toString());
		Number b = new Number(n.toString());
		a.negative = false;
		b.negative = false;
		return a.compareTo(b);
	}
	
	/*
	 * reverses the sign of the current number
	 */
	public void reverseSign()
	{
		if (negative == false) negative = true;
		else negative = false;
	}
	/*
	 * Compares the current number to the number n
	 * 
	 * @param Number the number to be compared to
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Number n)
	{
		if (digitCount - decimalPlaces > n.digitCount - n.decimalPlaces)
		{
			return (negative == true && n.negative == true) ? -1 : 1;
		}
		else if (digitCount - decimalPlaces < n.digitCount - n.decimalPlaces) 
		{
			return (negative == true && n.negative == true) ? 1 : -1;
		}
		else if (negative == false && n.negative == true)
		{
			return 1;
		}
		else if (negative == true && n.negative == false)
		{
			return -1;
		}
		else if (toString().equals(n.toString()))
		{
			return 0;
		}
		else if (negative == true && n.negative == true)
		{
			NumberIterator it1 = new NumberIterator(0);
			NumberIterator it2 = n.getIteratorBeg();
			if(it1.current() > it2.current()) return -1;
			else if (it1.current() < it2.current()) return 1;
			
			while (it1.hasNext() && it2.hasNext())
			{
				if(it1.next() > it2.next()) return -1;
				it1.previous();
				it2.previous();
				if (it1.next() < it2.next()) return 1;
				else return 0;
			}
		}
		else 
		{
			NumberIterator it1 = new NumberIterator(0);
			NumberIterator it2 = n.getIteratorBeg();
			if(it1.current() > it2.current()) return 1;
			else if (it1.current() < it2.current()) return -1;
			
			while (it1.hasNext() && it2.hasNext())
			{
				if(it1.next() > it2.next()) return 1;
				it1.previous();
				it2.previous();
				if (it1.next() < it2.next()) return -1;
				else return 0;
			}
		}
		return -20;
	}
	/*
	 * returns a string representation of the current number adding in the decimal and negative sign
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		trim();
		StringBuffer finalString = new StringBuffer();
		NumberIterator it = new NumberIterator(0);
		if (negative == true) finalString.append('-');
		if (digitCount == decimalPlaces) finalString.append('.');
		finalString.append(it.current());
		int counter = 1;
		while (it.hasNext())
		{
			if(digitCount - decimalPlaces == counter) finalString.append('.');
			finalString.append(it.next());
			counter++;
		}
		return finalString.toString();
	}
	/*
	 * returns a string representation of the current number without trimming the number or adding
	 * the decimal or negative sign
	 */
	private String toStringNoTrim()
	{
		StringBuffer finalString = new StringBuffer();
		NumberIterator it = new NumberIterator(0);
		finalString.append(it.current());
		int counter = 1;
		while (it.hasNext())
		{
			finalString.append(it.next());
			counter++;
		}
		return finalString.toString();
	}
	/*
	 * returns a string representation of the current number without trimming the number
	 */
	private String toStringNoDot()
	{
		trim();
		StringBuffer finalString = new StringBuffer();
		NumberIterator it = new NumberIterator(0);
		finalString.append(it.current());
		int counter = 1;
		while (it.hasNext())
		{
			finalString.append(it.next());
			counter++;
		}
		return finalString.toString();
	}
	/*
	 * removes leading 0s before the decimal and trailing zeros after the decimal
	 */
	public void trim()
	{
		NumberIterator itFront = new NumberIterator(0);
		NumberIterator itBack = new NumberIterator();
		while(itFront.current() == 0 && digitCount != 1 && digitCount!= decimalPlaces && itFront.hasNext())
		{
			high = itFront.current;
			itFront.next();
			digitCount--;
		}
		high = itFront.current;
		while(itBack.current() == 0 && decimalPlaces > 0)
		{
			low = itBack.current;
			itBack.previous();
			digitCount--;
			decimalPlaces--;
		}
		low = itBack.current;
		
	}
	
	/*
	 * returns a new Number iterator object starting at the end of the linked list
	 */
	public NumberIterator getIterator()
	{
		return new NumberIterator();
	}
	/*
	 * returns a new NumberIterator object starting at the beginning of the list
	 */
	public NumberIterator getIteratorBeg()
	{
		return new NumberIterator(0);
	}
	/*
	 * This class allows the user to iterate through the linked list
	 */
	private class NumberIterator implements ListIterator<Integer>
	{
		Node current;
		
		public NumberIterator()
		{
			current = low;
		}
		
		public NumberIterator(int index)
		{
			current = high;
		}
		@Override
		public void add(Integer arg0) {
			// TODO Auto-generated method stub
			
		}

		public boolean hasNext() {
			if (current.next != null) return true;
			return false;
		}

		public boolean hasPrevious() {
			
			if (current.prev != null) return true;
			return false;
		}
		public int current()
		{
			return current.data;
		}

		@Override
		public Integer next() {
			current = current.next;
			return current.data;
		}

		@Override
		public int nextIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Integer previous() {
			// TODO Auto-generated method stu
			current = current.prev;
			return current.data;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void set(Integer arg0) {
			// TODO Auto-generated method stub
			
		}
		public void moveOver(int n)
		{
			while(n != 0 && hasNext())
			{
				current = current.next;
				n--;
			}
		}
		
	}
	
}
