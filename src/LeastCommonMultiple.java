
/**
 * https://en.wikipedia.org/wiki/Least_common_multiple#Finding_least_common_multiples_by_prime_factorization
 * https://www.math.nmsu.edu/~pmorandi/CourseMaterials/LCM
 * 
 * @author Stéphan R.
 *
 */
public final class LeastCommonMultiple {

	/**
	 * Least Common Multiple: {@code a.b/gcd(a, b)}
	 * 
	 * @param a
	 * @param b
	 * 
	 * @return
	 */
	static long lcm(long a, long b) {
		return (a * b) / GreatestCommonDivisor.euclidean(a, b);
	}

	/**
	 * LCM on a set of numbers <br />
	 * 		{@code lcm(a, b, c) = lcm(a, lcm(b, c))}
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	static long lcm(long a, long b, long c) {
		return lcm(a, lcm(b, c));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		System.out.println(lcm(6, 20, 36));
		System.out.println(lcm(40, 45));
		System.out.println("Solution took " + (System.currentTimeMillis() - start) + "ms");
	}
}
