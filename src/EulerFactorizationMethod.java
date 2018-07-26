
/**
 * https://en.wikipedia.org/wiki/Euler%27s_factorization_method
 * TODO
 * 
 * @author Stéphan R.
 *
 */
public class EulerFactorizationMethod {

	/**
	 * 
	 * @param a
	 * @param b
	 * 
	 * @return
	 */
	static long gcd(long a, long b) {
		if (a < 0 || b < 0)
			throw new IllegalArgumentException("Negative number");
		
		while (b != 0) {
			long z = a % b;
			a = b;
			b = z;
		}
		
		return a;
	}
	
	/**
	 * A = (0,0)
	 * B = (1,1)
	 * C = (2,2)
	 * D = (3,3)
	 * 
	 * @param n
	 * 
	 * @return
	 */
	static long[][] fermatSumOfTwoSquare(long n) {
		long[][] sumOfTwoSquare = new long[4][4];
		
		return sumOfTwoSquare;
	}
	
	/**
	 * @param a
	 * @return
	 */
	static long eulerFractionMethod(long n) {
		long[][] twoSquare = fermatSumOfTwoSquare(n);
		long a = twoSquare[0][0] - twoSquare[2][2];
		long b = twoSquare[0][0] + twoSquare[2][2];
		long c = twoSquare[3][3] - twoSquare[1][1];
		long d = twoSquare[3][3] + twoSquare[1][1];
				
		return (long) (Math.pow(gcd(a, c) / 2, 2) + Math.pow(gcd(b, d) / 2, 2));
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		System.out.println(eulerFractionMethod(Long.parseLong("1000009")));
		System.out.println("Solution took " + (System.currentTimeMillis() - start) + "ms");
	}
}
