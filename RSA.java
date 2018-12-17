package RSA;

public class RSA {
	private int e = 5; // öffentlicher Schlüssel
	private int N = 21; // öffentlicher Schlüssel
	private int d = 5; // privater Schlüssel

	// default constructor
	// Standartkonstruktor
	public RSA() {
		e = 5;
		N = 21;
		d = 5;
	}

	// custom constructor
	// Benutzerdefinierter Konstruktor
	public RSA(int e, int N, int d) {
		this.e = e;
		this.N = N;
		this.d = d;
	}

	public int encrypt(int k) {
		return (int) (Math.pow(k, e)) % N;
	}

	public int decrypt(int c) {
		return (int) (Math.pow(c, d)) % N;
	}
}