import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * https://en.wikipedia.org/wiki/Trial_division
 * https://en.wikipedia.org/wiki/Pollard%27s_rho_algorithm
 * https://en.wikipedia.org/wiki/Wheel_factorization
 * https://en.wikipedia.org/wiki/Pollard%27s_p_%E2%88%92_1_algorithm
 * https://comeoncodeon.wordpress.com/2010/09/18/pollard-rho-brent-integer-factorization/
 * 
 * @author Stéphan R.
 *
 */
public class IntegerFactorization {

	private static boolean[] primes;
	
	/**
	 * @param n
	 * @return
	 */
	static int trialDivision(int n) {
		List<Integer> factors = new ArrayList<Integer>();
		int f = 2;
		
		while(n > 1) {
			if(n % f == 0) {
				factors.add(f);
				n /= f;
			} else {
				f++;
			}
		}
		
		return f;
	}
	
	/**
	 * @param n
	 * @return
	 */
	static int trialDivision2(int n) {
		List<Integer> factors = new ArrayList<Integer>();
		
		while(n % 2 == 0) {
			factors.add(2);
			n /= 2;
		}

		if(n == 1)
			return 2;
		
		int f = 3;

		while((f * f) <= n) {
			if(n % f == 0) {
				factors.add(f);
				n /= f;
			} else {
				f += 2;
			}
		}

		factors.add(n);
		
		return (n > 2) ? n : f;
	}
	
	/**
	 * @param n
	 * @param useRhoAlgorithm
	 * 
	 * @return
	 */
	static long factorize(int n, boolean useRhoAlgorithm) {
		List<Long> factors = new ArrayList<Long>();
		
		while(n != 1) {
			long divisor = (useRhoAlgorithm) ? pollardRho(n) : richardBrent(n);
			
			if(primes[(int) divisor]) {
				factors.add(divisor);
			}
			
			n /= divisor;
			
			if(primes[n]) {
				factors.add((long) n);
				break;
			}
		}
		
		return (n == 1) ? factors.get(0) : n;
	}
	
	/**
	 * fn is f(x) = (x * x) + c
	 * 
	 * @param n
	 * @return
	 */
	private static long pollardRho(long n) {
		if(n % 2 == 0) {
			return 2;
		}
		
		Random rand = new Random();

		long x = rand.nextInt((int) n) + 1;
		long y = x;
		long c = rand.nextInt((int) n) + 1;
		long g = 1;
		
		while(g == 1) {
			x = (((x * x) % n) + c) % n;
			y = (((y * y) % n) + c) % n;
			y = (((y * y) % n) + c) % n;
			g = GreatestCommonDivisor.binary(Math.abs(x - y), n);
		}
				
		return g;
	}
	
	/**
	 * @param n
	 * @return
	 */
	static long richardBrent(long n) {
		if(n % 2 == 0) {
			return 2;
		}
		
		Random rand = new Random();
		
		long y = rand.nextInt((int) n) + 1;
		long c = rand.nextInt((int) n) + 1;
		long m = rand.nextInt((int) n) + 1;
		long g = 1;
		long r = 1;
		long q = 1;
		long x = 0;
		long ys = 0;
		
		while(g == 1) {
			x = y;
			
			for(int i = 0; i < r; i++) {
				y = (((y * y) % n) + c) % n;
			}
			
			int k = 0;
			
			while(k < r && g == 1) {
				ys = y;
				
				for(int i = 0; i < Math.min(m, (r - k)); i++) {
					y = (((y * y) % n) + c) % n;
					q = (q * Math.abs(x - y)) % n;
				}
				
				g = GreatestCommonDivisor.binary(q, n);
				k += m;
			}
		}
		
		if(g == n) {
			while(true) {
				ys = (((ys * ys) % n) + c) % n;
				g = GreatestCommonDivisor.binary(Math.abs(x - ys), n);
				
				if(g > 1) {
					break;
				}
			}
		}
		
		return g;
	}
		
	/**
	 * 
	 * @param n
	 * @return
	 */
	static int wheelFactorization(int n) {
		List<Integer> factors = new ArrayList<Integer>();
		int[] inc = new int[] { 4, 2, 4, 2, 4, 6, 2, 6 };
		int k = 7;
		int i = 1;
		
		while(n % 2 == 0) {
			factors.add(2);
			n /= 2;
		}
		
		while(n % 3 == 0) {
			factors.add(3);
			n /= 3;
		}
		
		while(n % 5 == 0) {
			factors.add(5);
			n /= 5;
		}
				
		while(k * k <= n) {
			if(n % k == 0) {
				factors.add(k);
				n /= k;
			} else {
				k += inc[i];
				i = (i < 8) ? i++ : 1;
			}
		}
		
		factors.add(n);
		
		return n;
	}
	
	static int primeFactorization(int n) {
		List<Integer> factors = new ArrayList<Integer>();
		int total = 1;
		
		for (int p = 2; p <= n; p++) {
	        if (primes[p]) {
	        	if(p * p < n) {
	        		if (n % p == 0) {
		            	int count = 0;
		            	
		                while (n % p == 0) {
		                	factors.add(p);
		                    n /= p;
		                    count++;
		                }
		                
		                total = total * (count + 1);
		            }	
	        	} else {
	        		factors.add(n);
	        		total = n;
	        		break;
	        	}	            
	        }
	    }
		
		return total;
	}
		
	/**
	 * Driver main method use to compare the different algorithm proposed above
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		primes = PrimeGenerating.primeSieve(3642 + 1);
		System.out.println("Loading Prime number took " + (System.currentTimeMillis() - start) + "ms\n");
		
		start = System.currentTimeMillis();	
		System.out.println("Largest Prime Factor => " + trialDivision(3642));
		System.out.println("Trial Division took " + (System.currentTimeMillis() - start) + "ms\n");
		
		start = System.currentTimeMillis();
		System.out.println("Largest Prime Factor => " + trialDivision2(3642));
		System.out.println("Trial Division 2 took " + (System.currentTimeMillis() - start) + "ms\n");
		
		start = System.currentTimeMillis();
		System.out.println("Largest Prime Factor => " + factorize(3642, true));
		System.out.println("Pollard Rho took " + (System.currentTimeMillis() - start) + "ms\n");
		
		start = System.currentTimeMillis();
		System.out.println("Largest Prime Factor => " + factorize(3642, false));
		System.out.println("Richard Brent took " + (System.currentTimeMillis() - start) + "ms\n");
		
		start = System.currentTimeMillis();
		System.out.println("Largest Prime Factor => " + wheelFactorization(3642));
		System.out.println("Wheel Factorization took " + (System.currentTimeMillis() - start) + "ms\n");
		
		start = System.currentTimeMillis();
		System.out.println("Largest Prime Factor => " + primeFactorization(3642));
		System.out.println("Prime Factorization took " + (System.currentTimeMillis() - start) + "ms");
	}
}
