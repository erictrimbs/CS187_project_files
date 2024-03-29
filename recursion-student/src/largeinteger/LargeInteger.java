package largeinteger;

import largeinteger.LLNode;

/**
 * The LargeInteger class This class represents a large, non-negative integer
 * using a linked list. Each node stores a single digit. The nodes represent all
 * digits in *reverse* order: the least significant digit is the first node, and
 * the most significant the last node. For example, 135642 is represented as
 * 2->4->6->5->3->1 in that order.
 */
public class LargeInteger {
	private LLNode<Integer> head; // head of the list
	private int size; // size (i.e. number of digits)

	// Returns size
	public int size() {
		return size;
	}

	// Returns the linked list (used only for JUnit test purpose)
	public LLNode<Integer> getList() {
		return head;
	}

	public LargeInteger() {
		head = null;
		size = 0;
	}

//	public static void main(String[] args) {
//
//		LargeInteger a = new LargeInteger("99999");
//		LargeInteger b = new LargeInteger("10");
//		LargeInteger c = a.add(b);
//		LargeInteger m = a.multiply(Integer.parseInt(b.toString()));
//
//		System.out.println(a.toString() + " * " + b.toString() + " = " + m.toString() + ": "
//				+ (Integer.parseInt(a.toString()) * Integer.parseInt(b.toString()) == Integer.parseInt(m.toString())));
//
//		System.out.println(a.toString() + " + " + b.toString() + " = " + c.toString() + ": "
//				+ (Integer.parseInt(a.toString()) + Integer.parseInt(b.toString()) == Integer.parseInt(c.toString())));
//	}

	/**
	 * Constructor that takes a String as input and constructs the linked list. You
	 * can assume that the input is guaranteed to be valid: i.e. every character in
	 * the string is between '0' and '9', and the first character is never '0'
	 * (unless '0' is the only character in the string). You can use
	 * input.charAt(i)-'0' to convert the character at index i to the integer value
	 * of that digit. Remember: the list nodes must be in reverse order as the
	 * characters in the string.
	 */
	public LargeInteger(String input) {
		LLNode<Integer> current = new LLNode<Integer>();
		head = current;
		size = input.length();

		for (int i = input.length() - 1; i >= 0; i--) {
			current.data = toInt(input.charAt(i));
			if (i != 0) {
				current.link = new LLNode<Integer>();
				current = current.link;
			}
		}
	}

	private static int toInt(char a) {
		return (int) a - 48; // -48 for char conversion
	}

	/**
	 * Divide *this* large integer by 10 and return this. Assume integer division:
	 * for example, 23/10 = 2, 8/10 = 0 and so on.
	 */
	public LargeInteger divide10() { // works
		if (head.link == null)
			head.data = 0;
		else {
			size--;
			head = head.link;
		}
		return this;
	}

	/**
	 * Multiply *this* large integer by 10 and return this. For example, 23*10 =
	 * 230, 0*10 = 0 etc.
	 */
	public LargeInteger multiply10() { // works
		if (!(head.link == null && head.data == 0)) {
			size++;
			head = new LLNode<Integer>(0, head);
		}
		return this;
	}

	/**
	 * Returns a *new* LargeInteger object representing the sum of this large
	 * integer and another one (given by that). Your code must correctly handle
	 * cases such as the two input integers have different sizes (e.g. 2+1000=1002),
	 * or there is a carry over at the highest digit (e.g. 9999+2=10001).
	 */
	public LargeInteger add(LargeInteger that) { // works
		LargeInteger one = new LargeInteger(this.toString()), two = new LargeInteger(that.toString());
		String holder = "";

		int sumDigits, digits = (one.size() <= two.size() ? one.size() : two.size());
		boolean carry = false;

		for (int i = 0; i < digits || carry; i++) {
			sumDigits = one.getList().data + two.getList().data + (carry ? 1 : 0);

			if (sumDigits > 9) {
				carry = true;
				sumDigits %= 10;
			} else {
				carry = false;
			}

			holder = (sumDigits + "").concat(holder);

			one = one.divide10();
			two = two.divide10();
		}

		if (one.size() > 1 || one.getList().data != 0)
			return new LargeInteger(one.toString().concat(holder));

		if (two.size() > 1 || two.getList().data != 0)
			return new LargeInteger(two.toString().concat(holder));

		return new LargeInteger(holder);
	}

	/**
	 * Returns a new LargeInteger object representing the result of multiplying this
	 * large integer with a non-negative integer x. You can assume x is either a
	 * positive integer or 0. Hint: you can use a loop and call the 'add' method
	 * above to accomplish the 'multiply'.
	 */
	public LargeInteger multiply(int x) {
		if (x == 0)
			return new LargeInteger("0");

		LargeInteger out = new LargeInteger("0");

		while (x > 0) {
			out = out.add(this);
			x--;
		}

		return out;
	}

	/**
	 * Recursive method that converts the list referenced by curr_node back to a
	 * string representing the integer. Think about what's the base case and what it
	 * should return. Then think about what it should return in non-base case. Hint:
	 * refer to the 'printing a list backwards' example we covered in lectures.
	 */
	private String toString(LLNode<Integer> curr_node) {
		if (curr_node == null)
			return "";
		return toString(curr_node.link) + curr_node.data;
	}

	/**
	 * Convert this list back to a string representing the large integer. This is a
	 * public method that jump-starts the call to the recursive method above.
	 */
	public String toString() {
		return toString(head);
	}

	// Recursive method to compute factorial
	public static LargeInteger factorial(int n) {
		if (n == 0)
			return new LargeInteger("1");
		return factorial(n - 1).multiply(n);
	}

	// Recursive method to compute power
	public static LargeInteger pow(int x, int y) {
		if (y == 0)
			return new LargeInteger("1");
		return pow(x, y - 1).multiply(x);
	}
}
