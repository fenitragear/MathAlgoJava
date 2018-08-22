
/**
 * @see https://en.wikipedia.org/wiki/Binary_GCD_algorithm
 * @see https://en.wikipedia.org/wiki/Euclidean_algorithm
 * @see https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
 * @see https://en.wikipedia.org/wiki/Euler%27s_totient_function
 *  
 * @author Stéphan R.
 *
 */
public class GreatestCommonDivisor {

	/**
	 * Euclid Algorithm
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	static long euclidean(long a, long b) {
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
	 * Extended Euclid Algorithm
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	static int extendedEuclidean(int a, int b, int x, int y) {
        if (a == 0) {
            x = 0;
            y = 1;
            
            return b;
        }
        
        // To store results of recursive call
        int x1 = 1;
        int y1 = 1; 
        int gcd = extendedEuclidean(b % a, a, x1, y1);
 
        // Update x and y using results of recursive
        // call
        x = y1 - (b / a) * x1;
        y = x1;
 
        return gcd;
    }
	
	/**
	 * Stein's Algorithm or Binary GCD
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	static long binary(long a, long b) {
		int shift;
		
		if(a == 0) {
			return b;
		}
		
		if(b == 0) {
			return a;
		}
		
		for(shift = 0; ((a | b) & 1) == 0; ++shift) {
			a >>= 1;
			b >>= 1;
		}
		
		while((a & 1) == 0) {
			a >>= 1;
		}
		
		do {
			while((b & 1) == 0) {
				b >>= 1;
			}
			
			if(a > b) {
				a ^= b;
				b ^= a;
				a ^= b;
			}
			
			b = b - a;
		} while(b != 0);
		
		return a << shift;
	}
	
	/**
	 *  
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
	static long eulerTotient(long a, long b) {
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
	private static long phi(long n) {
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
		long start = System.currentTimeMillis();
		
		System.out.println(euclidean(1008, 3642));
		System.out.println(eulerTotient(1008, 3642));
		System.out.println(binary(1008, 3642));
		System.out.println(extendedEuclidean(1008, 3642, 1, 1));
		
		System.out.println("Solution took " + (System.currentTimeMillis() - start) + "ms");
	}
}
