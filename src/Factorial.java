import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Stéphan R.
 *
 */
public class Factorial {
	
	
	/**
	 * Calculates factorial of an number<br /><br />
	 * 
	 * Classic formula <br />
	 * <pre>{@code 
	 * 0! = 1
	 * 1! = 1
	 * n! = n x (n - 1) x (n - 2) x ... x 3 x 2 x 1
	 * }</pre>
	 * 
	 * @param n
	 * 
	 * @return the factorial number
	 */
	static long classic(int n) {
		if(n == 0 || n == 1)
			return 1;
		
		return n * classic(n - 1);
	}

	/**
	 * Calculates factorial of an number using the prime factorization technic
	 * 
	 * <h3>Factorial Prime Factorization formula:</h3><br />
	 * {@code n! = product(s_i^r_i) = 2^r_1 * 3^r_2 * 5^r_3 * ... * p} <br /><br />
	 * 
	 * Where:<br />
	 * 		{@code p} denote the largest prime <br />
	 * 		{@code r} denote the power of each prime number
	 * 
	 * <h3>Sieve Of Eratosthenes <h3/>
	 * <pre>{@code
	 * Initialize an array of boolean default false
	 * 
	 * 	For 2 ... sqrt(n)
	 * 		If a[i] is true then i is prime
	 * 			Mark to true all it's multiple, lesser or equal, than n (k * i ≤ n, k ≥ 2);
	 * 		Otherwise, if i is marked, then it is a composite number.
	 * 
	 * 	Output: all i such that A[i] is false.
	 * 
	 * Compute r:
	 * 	for each prime number denoted by x
	 * 		val1 = the number that x can divide between 1 and n
	 * 			while val1 is greater than 1
	 * 				find the number that x can divide between 1 and val1
	 * }</pre>
	 * 
	 * @param n
	 * 
	 * @return the factorial number
	 */
	static BigInteger primeFactorization(int n) {
		BigInteger factorial = BigInteger.valueOf(1);
		boolean[] tab = new boolean[n + 1];
		
		for(int i = 2; i < Math.sqrt(tab.length); i++) {
			if(!tab[i]) {
				for(int j = (i * i); j < tab.length; j += i) {
					tab[j] = true;
				}
			}
		}
		
		for(int i = 2; i < tab.length; i++) {
			if(!tab[i]) {
				int r = n;
				int power = 0;
				
				while(r > 1) {
					int count = 0;
					
					for(int x = i; x <= r; x += i) {
						count++;
					}
					
					r = count++;
					power += r;
				}		
								
				factorial = factorial.multiply(BigDecimal.valueOf(i).pow(power).toBigInteger());
			}
		}
		
		return factorial;
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		System.out.println(classic(5));
		System.out.println(primeFactorization(1000));
		System.out.println("Solution took " + (System.currentTimeMillis() - start) + "ms");
	}
}