
/**
 * https://en.wikipedia.org/wiki/Binary_GCD_algorithm
 * https://en.wikipedia.org/wiki/Euclidean_algorithm
 * https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
 * https://en.wikipedia.org/wiki/Euler%27s_totient_function
 *  
 * @author Stéphan R.
 *
 */
public class GreatestCommonDivisor {

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	static long gcd(long a, long b) {
	    while (a * b != 0) {
	        if (a >= b) {
	        	a = a % b;
	        } else {
	        	b = b % a;
	        }
	    }
	    
	    return a + b;
	}
	
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
		long shift;
		
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
				long temp = a;
				a = b;
				b = temp;
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
	static int eulerTotient(int a, int b) {
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
	private static int phi(int n) {
        float result = n;
 
        for (int p = 2; p * p <= n; ++p) {
            if (n % p == 0) {
                while (n % p == 0) {
                	 n /= p;
                }                   
                
                result *= (1.0 - (1.0 / (float) p));
            }
        }
        
        if (n > 1) {
        	 result *= (1.0 - (1.0 / (float) n));
        }
 
        return (int) result;
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();		
		System.out.println("GCD => " + gcd(991776, 999982));
		System.out.println("Classical GCD took " + (System.currentTimeMillis() - start) + "ms\n");
		
		start = System.currentTimeMillis();
		System.out.println("GCD => " + euclidean(991776, 999982));
		System.out.println("Euclidean Algorithm took " + (System.currentTimeMillis() - start) + "ms\n");
		
		start = System.currentTimeMillis();
		System.out.println("GCD => " + extendedEuclidean(991776, 999982, 1, 1));		
		System.out.println("Extended Euclidean Algorithm took " + (System.currentTimeMillis() - start) + "ms\n");
		
		//start = System.currentTimeMillis();
		//System.out.println("GCD => " + eulerTotient(991776, 999982));
		//System.out.println("EUler's Totient took " + (System.currentTimeMillis() - start) + "ms\n");
		
		start = System.currentTimeMillis();
		System.out.println("GCD => " + binary(991776, 999982));
		System.out.println("Binary GCD took " + (System.currentTimeMillis() - start) + "ms\n");
	}
}
