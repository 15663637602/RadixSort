package impl;

import java.lang.reflect.Array;
import java.util.Random;

import core.List;

/**
 * 
 * @author yuqi li 2017/11/26
 */
public class SortingUtils {

	// 1. integer Bucket sort
	public static void integerBucketSort(List<Integer> list, int lower, int upper) {
		@SuppressWarnings("unchecked")
		List<Integer>[] buckets = (List<Integer>[]) Array.newInstance(List.class, upper - lower + 1); // list's capacity = range

		// Step 1: Copy the values from the list into the buckets.
		while (!list.isEmpty()) {
			int value = list.remove(list.first());
			if (value < lower || value > upper)
				throw new UnsortableException("The list contains an invalid value: " + value);
			if (buckets[value - lower] == null) {
				buckets[value - lower] = new LinkedList<Integer>();
			}
			buckets[value - lower].insertLast(value);
		}

		// Step 2: Copy the values from the buckets to the list.
		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i] != null) {
				while (!buckets[i].isEmpty()) {
					list.insertLast(buckets[i].remove(buckets[i].first()));
				}
			}
		}
	}

	// get the nth LSD of the given number.
	public static int getNthDigit(int num, int n) {
		int temp = (int) (num / (Math.pow(10, n - 1)));
		return temp % 10;
	}

	// integer Radix sort
	public static void integerRadixSort(List<Integer> list, int digits) {
		@SuppressWarnings("unchecked")
		List<Integer>[] buckets = (List<Integer>[]) Array.newInstance(List.class, 10);
		for (int j = 1; j <= digits; j++) {
			// Step 1: Copy the values from the list into the buckets based on jth LSD.
			while (!list.isEmpty()) {
				int value = list.remove(list.first());
				if (value < 0 || value > Math.pow(10, digits) - 1)
					throw new UnsortableException("The list contains an invalid value: " + value);
				if (buckets[SortingUtils.getNthDigit(value, j)] == null) {
					buckets[SortingUtils.getNthDigit(value, j)] = new LinkedList<Integer>();
				}
				buckets[SortingUtils.getNthDigit(value, j)].insertLast(value);
			}
			// Step 2: Copy the values from the buckets to the list.
			for (int i = 0; i < buckets.length; i++) {
				if (buckets[i] != null) {
					while (!buckets[i].isEmpty()) {
						list.insertLast(buckets[i].remove(buckets[i].first()));
					}
				}
			}
		}

	}

	// get the ASCII value of nth character of the given String
	// if the String has no nth character, then return 64 < 65('A')
	public static int getNthchar(String str, int n) {
		int length = str.length();
		if (n > length) {
			return 64;
		}
		return str.charAt(n - 1);
	}

	public static void stringRadixSort(List<String> list, int digits) {
		@SuppressWarnings("unchecked")
		List<String>[] buckets = (List<String>[]) Array.newInstance(List.class, 59); //list's capacity = 122('z')-64('@')+1 = 59
		
		//first sort the last character, then the penultimate character, then...until the first character.
		for (int j = digits; j >= 1; j--) {
			// Step 1: Copy the values from the list into the buckets based on jth character.
			while (!list.isEmpty()) {
				String value = list.remove(list.first());
				int length = value.length();
				if (length < 0 || length > digits)
					throw new UnsortableException("The list contains an invalid value: " + value);
				if (buckets[SortingUtils.getNthchar(value, j) - 64] == null) {
					buckets[SortingUtils.getNthchar(value, j) - 64] = new LinkedList<String>();
				}
				buckets[SortingUtils.getNthchar(value, j) - 64].insertLast(value);
			}
			// Step 2: Copy the values from the buckets to the list.
			for (int i = 0; i < buckets.length; i++) {
				if (buckets[i] != null) {
					while (!buckets[i].isEmpty()) {
						list.insertLast(buckets[i].remove(buckets[i].first()));
					}
				}
			}
		}
	}

	// test methods
	public static void ibsTest() {
		List<Integer> l = new ArrayList<Integer>(500);
		long seed = System.currentTimeMillis();
		Random r = new Random(seed);
		for (int i = 0; i < 500; i++) {
			int random = r.nextInt(201) + 50;
			l.insertFirst(random);
		}
		System.out.println("Integer Bucket Sort Test: ");
		System.out.println("before sort: \n" + l);
		integerBucketSort(l, 50, 250);
		System.out.println("after sort: \n" + l);
	}

	public static void irsTest() {
		List<Integer> l = new ArrayList<Integer>(500);
		long seed = System.currentTimeMillis();
		Random r = new Random(seed);
		for (int i = 0; i < 500; i++) {
			int random = r.nextInt(1000);
			l.insertFirst(random);
		}
		System.out.println("Integer Radix Sort Test: ");
		System.out.println("before sort: \n" + l);
		integerRadixSort(l, 3);
		System.out.println("after sort: \n" + l);
	}

	public static void srsTest() {
		String str = "the big black cat sat on the beautiful brown mat";
		String[] temp = str.split(" ");
		List<String> l = new ArrayList<String>(temp.length);
		for (String s : temp) {
			l.insertLast(s);
		}
		System.out.println("String Radix Sort Test: ");
		System.out.println("before sort: \n" + l);
		stringRadixSort(l, 9);
		System.out.println("after sort: \n" + l);

	}

	// main method
	public static void main(String[] args) {
		SortingUtils.ibsTest();
		System.out.println("---------------------------------------------------------------------");
		SortingUtils.irsTest();
		System.out.println("---------------------------------------------------------------------");
		SortingUtils.srsTest();
	}
}
