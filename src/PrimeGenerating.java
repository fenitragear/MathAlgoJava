import java.util.Arrays;

/**
 * https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
 * https://en.wikipedia.org/wiki/Sieve_of_Sundaram
 * https://en.wikipedia.org/wiki/Sieve_of_Atkin
 * http://compoasso.free.fr/primelistweb/page/prime/atkin_en.php
 * https://introcs.cs.princeton.edu/java/14array/PrimeSieve.java.html
 * https://www.geeksforgeeks.org/segmented-sieve/
 *  
 * @author Stéphan R.
 *
 */
public class PrimeGenerating {
		
	/**
	 * @param n
	 * @return
	 */
	static boolean[] primeSieve(int n) {
		boolean[] isPrime  = new boolean[n];
		
		Arrays.fill(isPrime, Boolean.TRUE);

        // mark non-primes <= n using Sieve of Eratosthenes
        for (int factor = 2; factor * factor < n; factor++) {
            if (isPrime[factor]) {
                for (int j = factor; factor * j < n; j++) {
                    isPrime[factor * j] = false;
                }
            }
        }
	    
	    return isPrime;
	}
	
	/**
	 * Sieve Of Eratosthenes Algorithm
	 * 
	 * Initialize an array of boolean default true
	 * Initialize an array of sum
	 * 
	 * 		For 2 ... sqrt(n)
	 * 			If a[i] is true then i is prime
	 * 				Mark to false all it's multiple, lesser or equal, than n (k * i ≤ n, k ≥ 2);
	 * 			Otherwise, if i is marked, then it is a composite number.
	 * 
	 * 		Output: all i such that A[i] is true.
	 * 
	 * @param n the limit
	 * 
	 * @return prime number below n
	 */
	static int[] sieveOfEratosthenes(int n) {
		boolean[] isPrime  = new boolean[n];
		int[] eratosthenes = new int[n + 1 >> 1];
		int lim = (int) Math.sqrt(n) + 1;
		int index = 0;
		
		Arrays.fill(isPrime, Boolean.TRUE);
		
	    for(int j = 2; j <= lim; j++) {	    	
	    	if(isPrime[j]) {
	    		for(int k = j * 2; k < n; k += j) {
		            isPrime[k] = false;
		        }
		    }
		}
	    	    
        for(int f = 2; f < n; f++) {        	
        	if(isPrime[f]) {
    			eratosthenes[index++] = f; 		
        	}
        }
        
        return eratosthenes;
	}
	
	/**
	 * The idea of segmented sieve is to divide the range [0..n-1] in different segments and compute primes in all segments one by one. 
	 * This algorithm first uses Simple Sieve to find primes smaller than or equal to √(n). Below are steps used in Segmented Sieve.
	 * 
	 * 		1/ Use Simple Sieve to find all primes upto square root of ‘n’ and store these primes in an array “prime[]”. Store the found primes 
	 * 		   in an array ‘prime[]’. 
	 * 		2/ We need all primes in range [0..n-1]. We divide this range in different segments such that size of every segment is at-most √n
	 * 		3/ Do following for every segment [low..high]
	 * 			Create an array mark[high-low+1]. Here we need only O(x) space where x is number of elements in given range.
	 * 			Iterate through all primes found in step 1. For every prime, mark its multiples in given range [low..high].
	 * 
	 * @param n
	 * @return
	 */
	static int[] segmentedSieve(int n) {
		// Compute all primes smaller than or equal
        // to square root of n using simple sieve
        int limit = (int) (Math.floor(Math.sqrt(n)) + 1);
        int[] segmentedSieve = new int[n + 1 >> 1];
        int[] prime = sieveOfEratosthenes(limit);
        int length = prime.length;
        int index = 0;
      
        // Divide the range [0..n-1] in different segments
        // We have chosen segment size as sqrt(n).
        int low = limit;
        int high = 2 * limit;
        
        for (int i = 0; i < prime.length; i++) {
        	int currentPrime = prime[i];
        	
        	if(currentPrime == 0) {
        		break;
        	}
        	
        	segmentedSieve[index++] = currentPrime;
        }
      
        // While all segments of range [0..n-1] are not processed,
        // process one segment at a time
        while (low < n) {
            if (high >= n) {
            	high = n;
            }                
 
            // To mark primes in current range. A value in mark[i]
            // will finally be false if 'i-low' is Not a prime,
            // else true.
            boolean mark[] = new boolean[limit + 1];
             
            Arrays.fill(mark, Boolean.TRUE);
            
            // Use the found primes by simpleSieve() to find
            // primes in current range
            for (int i = 0; i < length; i++) {
            	int currentPrime = prime[i];
            	
            	if(currentPrime == 0) {
            		break;
            	}
            	
                // Find the minimum number in [low..high] that is
                // a multiple of prime[i] (divisible by prime[i])
                // For example, if low is 31 and prime[i] is 3,
                // we start with 33.
                int loLim = (int) (Math.floor(low / currentPrime) * currentPrime);
                
                if (loLim < low) {
                	loLim += currentPrime;
                }                    
      
                /*  Mark multiples of prime.get(i) in [low..high]:
                    We are marking j - low for j, i.e. each number
                    in range [low, high] is mapped to [0, high-low]
                    so if range is [50, 100]  marking 50 corresponds
                    to marking 0, marking 51 corresponds to 1 and
                    so on. In this way we need to allocate space only
                    for range  */
                for (int j = loLim; j < high; j += currentPrime) {
                	mark[j - low] = false;
                }
            }
      
            // Numbers which are not marked as false are prime
            for (int i = low; i < high; i++) {
            	if (mark[i - low]) {
            		segmentedSieve[index++] = i;
            	}
            }
            
            // Update low and high for next segment
            low  += limit;
            high += limit;
        }
        
        return segmentedSieve;
	}
	
	/**
	 * Sieve Of Sundaram: algorithm steps for primes below 2n + 2.
	 * 
	 * Start with a list of the integers from 1 to n. From this list, remove all numbers of the form i + j + 2ij where:
	 * 		i, j € N, 1 <= i <= j
	 * 		i + j + 2ij <= n
	 * 
	 * The remaining numbers are doubled and incremented by one, giving a list of the odd prime numbers 
	 * (i.e, all primes except 2) below 2n + 2
	 * Sundaram's method crosses out i + j(2i + 1) for 1 <= j <= [k/2]
	 * 
	 * @param n the limit
	 * 
	 * @return prime number below n
	 */
	static int[] sieveOfSundaram(int n) {
		boolean[] isPrime = new boolean[n];
		int[] sundaram = new int[(((2 * n) + 2) >> 1)];
		int limit = (int) Math.sqrt(n) + 1;
		int index = 1;
		
		Arrays.fill(isPrime, Boolean.TRUE);
		
		for(int i = 1; i <= limit; i++) {
			if(isPrime[i]) {
				for(int j = 1; (i + j + (2 * i * j)) < n; j++) {
					isPrime[(i + j + (2 * i * j))] = false;
				}
			}
		}
		
		sundaram[0] = 2;
		
		for(int i = 1; i < n; i++) {
			if(isPrime[i]) {
				sundaram[index++] = (2 * i) + 1;
			}
		}
		
		return sundaram;
	}
	
	/**
	 * Sieve Of Atkin Optimized
	 * 
	 * Basci algorithm:
	 * 
	 * Create a results list, filled with 2, 3, and 5.
	 * Create a sieve list with an entry for each positive integer; all entries of this list should initially be marked non prime.
	 * 		
	 * 		For each entry number n in the sieve list, with modulo-sixty remainder r:
	 * 			If r is 1, 13, 17, 29, 37, 41, 49, or 53, flip the entry for each possible solution to 4x2 + y2 = n.
	 * 			If r is 7, 19, 31, or 43, flip the entry for each possible solution to 3x2 + y2 = n.
	 * 			If r is 11, 23, 47, or 59, flip the entry for each possible solution to 3x2 – y2 = n when x > y.
	 * 			If r is something else, ignore it completely.
	 * 
	 * Start with the lowest number in the sieve list.
	 * Take the next number in the sieve list still marked prime.
	 * Include the number in the results list.
	 * Square the number and mark all multiples of that square as non prime. Note that the multiples that can be factored by 2, 3, or 5 
	 * need not be marked, as these will be ignored in the final enumeration of primes.
	 * 
	 * Repeat steps four through seven.
	 * 
	 * @param n the limit
	 * 
	 * @return prime number below n
	 */
	static int[] sieveOfAtkin(int n) {
		boolean isPrime[] = new boolean[n];
		int[] atkin = new int[n + 1 >> 1];
        
		// Find prime
		int[] sequence = { 2, 4 };
		int index = 0;
		int k = 0;
		int k1 = 0;

		double xUpper = Math.sqrt(n / 4) + 1;
		int x = 1;
		int y = 0;

		while (x < xUpper) {
			boolean isXMod3 = (x % 3 == 0);
			index = 0; 
			k1 = 4 * (x * x);
			y = 1;
			
			while (true) {
				k = k1 + (y * y);
				
				if (k >= n) {
					break;
				}
				
				isPrime[k] = !isPrime[k];
				y += (isXMod3) ? sequence[(++index & 1)] : 2;
			}
			
			x++;
		}

		xUpper = Math.sqrt(n / 3) + 1;
		x = 1;
		y = 0;

		while (x < xUpper) {
			index = 1;
			k1 = 3 * (x * x);
			y = 2;
			
			while (true) {
				k = k1 + (y * y);
				
				if (k >= n) {
					break;
				}
				
				isPrime[k] = !isPrime[k];
				y += sequence[(++index & 1)];
			}
			
			x += 2;
		}

		xUpper = (int) Math.sqrt(n);
		x = 1;
		y = 0;

		while (x < xUpper) {
			boolean isEven = ((x & 1) == 0);
			k1 = 3 * (x * x);
			y = (isEven) ? 1 : 2;
			index = (isEven) ? 0 : 1;
						
			while (y < x) {
				k = k1 - (y * y);
				
				if (k < n) {
					isPrime[k] = !isPrime[k];
				}
				
				y += sequence[(++index & 1)];
			}
			
			x++;
		}

		isPrime[2] = true;
		isPrime[3] = true;
		
		int lim = (int) Math.sqrt(n) + 1;
		
		for (int r = 5; r <= lim; r++) {
			if (isPrime[r]) {
				int r2 = r * r;
				
				for (k = r2; k < n; k += r2) {
					isPrime[k] = false;
				}
			}
		}

		int pos = 0;
		
		for (int i = 2; i < n; i++) {
			if (isPrime[i]) {
				atkin[pos++] = i;
			}
		}
		
		return atkin;
	}
		
	/**
	 * Driver main method use to compare the different algorithm proposed above
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		int n = (int) Math.pow(10, 8);
		int twoNPlus2 = (2 * n) + 2;
		
		long start = System.currentTimeMillis();		
		primeSieve(twoNPlus2);
		System.out.println("Prime Sieve took " + (System.currentTimeMillis() - start) + "ms");
		
		start = System.currentTimeMillis();		
		sieveOfEratosthenes(twoNPlus2);
		System.out.println("Sieve Of Eratosthenes took " + (System.currentTimeMillis() - start) + "ms");
		
		start = System.currentTimeMillis();
		sieveOfSundaram(n);		
		System.out.println("Sieve Of Sundaram took " + (System.currentTimeMillis() - start) + "ms");
		
		start = System.currentTimeMillis();
		sieveOfAtkin(twoNPlus2);		
		System.out.println("Sieve Of Atkin (optimized) took " + (System.currentTimeMillis() - start) + "ms");
		
		start = System.currentTimeMillis();		
		segmentedSieve(twoNPlus2);
		System.out.println("Segmented Sieve took " + (System.currentTimeMillis() - start) + "ms");
	}
}
