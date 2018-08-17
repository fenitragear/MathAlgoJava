
/**
 * https://en.wikipedia.org/wiki/Digit_sum
 * 
 * @author Stéphan R.
 *
 */
public class DigitSum {
	
	/**
	 * @param x
	 * @return
	 */
	static int classicDigitSum(int x) {
		int sum = 0;
		
		while(x > 0) {				
			int n = x % 10;
            sum += n;
            x /= 10;
        }
		
		return sum;
	}
	
	/**
	 * Compute digit sum
	 * 
	 * <pre>{@code
	 * Digit sum formula:
	 * 	For n = 0 ... log10(n)
	 * 		1/b^n * (x mod b^(n + 1) - x mod b^n)
	 * 
	 * Where
	 * 	x is the number to calculate the digit
	 * 	b is the base
	 * }</pre>
	 * 
	 * @param x
	 * 
	 * @return
	 */
	static int digitSum(int x) {
		int sum = 0;
		
		for(int i = 0; i <= Math.sqrt(x); sum += (1 / Math.pow(10, i)) * ((x % Math.pow(10, i + 1)) - (x % Math.pow(10, i))), i++);
		
		return sum;
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		System.out.println(digitSum(154));
		System.out.println("Solution took " + (System.currentTimeMillis() - start) + "ms");
	}
}
