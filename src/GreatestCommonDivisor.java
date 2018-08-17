
/**
 * @author Stéphan R.
 *
 */
public class GreatestCommonDivisor {

	/**
	 * <pre>{@code
	 * gcd(a, b) = phi(a * b) == phi(a) * phi(b)
	 * 
	 * Where 
	 * 	phi(n) is the Euler's totient function
	 * }</pre>
	 * 
	 * @param a
	 * @param b
	 * 
	 * @return
	 */
	static long gcd(long a, long b) {
		return phi(a * b) / (phi(a) * phi(b));
	}
	
	/**
	 * Euler's totient function
	 * 
	 * Consider all prime factors of n and for every prime 
	 * factor p, multiply result with (1 - 1/p)
	 * 
	 * Check if p is a prime factor. If yes, then update n and result
	 * If n has a prime factor greater than sqrt(n)
	 * (There can be at-most one such prime factor)
	 * 
	 * @param n
	 * @return
	 */
	static long phi(long n) {
        float result = n;
 
        for (long p = 2; p * p <= n; ++p) {
            if (n % p == 0) {
                while (n % p == 0) {
                	 n /= p;
                }                   
                
                result *= (1.0 - (1.0 / (float) p));
            }
        }
        
        if (n > 1)
            result *= (1.0 - (1.0 / (float) n));
 
        return (int) result;
	}
	
	/**
	 * Test cases
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(gcd(11, 10));
	}
}
