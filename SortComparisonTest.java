import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
/**
 *  Test class for SortComparison.java
 *
 *  @author Zuomin Xie 17328867
 *  @version HT 2019
 *  
 *  
 *  (microseconds)
 *  								Insert   Quick   Merge Recursive   Merge Iterative   Selection
	10 random						1290     1288    1244              1504				 1371
	100 random						1513	 1394	 1684			   1822				 2683
	1000 random						12337	 2752	 3620			   3956				 13864
	1000 few unique					9360	 3181	 4617			   4867				 14082
	1000 nearly ordered				5162	 2866	 3074			   3710			     13384 
	1000 reverse order				13200	 2290	 13742			   3422				 12556
	1000 sorted					    1751	 2233	 2137			   3304				 13895
	
	a. Which of the sorting algorithms does the order of input have an impact on? Why?
		the order of inputs have an impact on insertion, quick and merge sort, as these algorithms require less swapping
		or shifting operation when the input is closer to being sorted. Whereas selection sort always has to check
		every element to find the smallest for every position with a run time of N^2, input order has little to no impact
		on selection sorts performance
	b. Which algorithm has the biggest difference between the best and worst performance, based
	on the type of input, for the input of size 1000? Why?
		insertion sort has the biggest difference, it performs poorly with inputs in reverse order but performes 
		extremely well when the inputs are already sorted. This is because insertion sort shifts an element back 
		until a smaller element in found, when an array is already sorted, insertion sort does not shift anything,
		however when an array is in reverse order, insertion sort always has to shift an element up as many times as
		possible
	c. Which algorithm has the best/worst scalability, i.e., the difference in performance time
	based on the input size? Please consider only input files with random order for this answer.
		quick sort seems to have the best scalability with little difference in run time between 10 and 100 inputs,
		and relatively small increase in run time with 1000 inputs
		selection sort has the worst scalability as it always has the run time of N^2
	d. Did you observe any difference between iterative and recursive implementations of merge
	sort?
		The iterative version of merge sort seems to be slightly slower than the recursive version in most cases,
		except when the inputs are in reverse order, this causes merge sort recursive to perform poorly, probably due
		to the use of insertion sort for sections of length less than 10, as insertion sort performs poorly with 
		reversed inputs.
	e. Which algorithm is the fastest for each of the 7 input files? 
		insertion sort is the fastest when the inputs are already sorted, merge sort recursive appears to be slightly
		faster than quick sort with 10 random inputs. Otherwise, quick sort is the fastest sorting algorithm.
 */
@RunWith(JUnit4.class)
public class SortComparisonTest
{
    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
        new SortComparison();
    }

    //~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Check that the methods work for empty arrays
     */
    @Test
    public void testEmpty()
    {
    	double[] a = new double[0];
    	assertEquals("check that the methods work for empty arrays", a, SortComparison.insertionSort(a));
    	assertEquals("check that the methods work for empty arrays", a, SortComparison.mergeSortIterative(a));
    	assertEquals("check that the methods work for empty arrays", a, SortComparison.mergeSortRecursive(a));
    	assertEquals("check that the methods work for empty arrays", a, SortComparison.selectionSort(a));
    	assertEquals("check that the methods work for empty arrays", a, SortComparison.quickSort(a));
    }

    /**
     * Check that the methods work for other arrays
     */
    @Test
    public void testArray1() {
    	double[] a1 = {113,3.32,33,91.3,55,63.1,1,0,24.8};
    	double[] sortedArr = {0, 1, 3.32, 24.8, 33, 55, 63.1, 91.3, 113};
    	assertArrayEquals(sortedArr, SortComparison.insertionSort(a1), 0);
    	
    	double[] a2 = {113,3.32,33,91.3,55,63.1,1,0,24.8};
    	assertArrayEquals(sortedArr, SortComparison.mergeSortIterative(a2), 0);
    	
    	double[] a3 = {113,3.32,33,91.3,55,63.1,1,0,24.8};
    	assertArrayEquals(sortedArr, SortComparison.mergeSortRecursive(a3), 0);
    	
    	double[] a4 = {113,3.32,33,91.3,55,63.1,1,0,24.8};
    	assertArrayEquals(sortedArr, SortComparison.selectionSort(a4), 0);
    	
    	double[] a5 = {113,3.32,33,91.3,55,63.1,1,0,24.8};
    	assertArrayEquals(sortedArr, SortComparison.quickSort(a5), 0);
    }
    
    @Test
    public void testArray2() {
    	double[] a1 = {3,22,19.8,77,0,0,22,5,108.1,99,24,9,48};
    	double[] sortedArr = {0, 0, 3, 5, 9, 19.8, 22, 22, 24, 48, 77, 99, 108.1};
    	assertArrayEquals(sortedArr, SortComparison.insertionSort(a1), 0);
    	
    	double[] a2 = {3,22,19.8,77,0,0,22,5,108.1,99,24,9,48};
    	assertArrayEquals(sortedArr, SortComparison.mergeSortIterative(a2), 0);
    	
    	double[] a3 = {3,22,19.8,77,0,0,22,5,108.1,99,24,9,48};
    	assertArrayEquals(sortedArr, SortComparison.mergeSortRecursive(a3), 0);
    	
    	double[] a4 = {3,22,19.8,77,0,0,22,5,108.1,99,24,9,48};
    	assertArrayEquals(sortedArr, SortComparison.selectionSort(a4), 0);
    	
    	double[] a5 = {3,22,19.8,77,0,0,22,5,108.1,99,24,9,48};
    	assertArrayEquals(sortedArr, SortComparison.quickSort(a5), 0);
    }
    // ----------------------------------------------------------
    /**
     *  Main Method.
     *  Use this main method to create the experiments needed to answer the experimental performance questions of this assignment.
     *
     */
    public static void main(String[] args)
    {
        String fileName = "C:\\Users\\Zuomin\\eclipse-workspace\\SortingAlgorithms\\tests\\numbersSorted1000.txt";
        String line = null;
        double[] arr = new double[1000];

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            for (int i = 0; i < arr.length; i++) {
            	line = bufferedReader.readLine();
                arr[i] = Double.parseDouble(line);
            }   
            bufferedReader.close();         
        }
        catch(Exception e) {
            e.printStackTrace();              
        }
        long time1 = System.nanoTime();
        SortComparison.selectionSort(arr);
        long time2 = System.nanoTime();
        System.out.println(time2-time1);
    }

}
