
import java.util.InputMismatchException;
import java.util.Random;
import java.io.*;
import java.util.Scanner;

public class Mergesort {
public static int[] createRandomArray(int arrayLength) {
int[] array = new int[arrayLength];
Random random = new Random();
for (int i = 0; i < array.length; i++) {
  //Random Nukber 1-100
array[i] = random.nextInt(101);
    }
return array;
  }

public static void writeArrayToFile(int[] array, String filename) {
File file = new File(filename);
try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
for (int element : array) {
        writer.write(String.valueOf(element));
        writer.newLine();
      }
} catch (IOException e) {
  //Do nothing if an exception occurs.
}
  }

// Merging the two subarrays L and M into array.
public static void merge(int[] array, int left, int mid, int right) {
  // Calculate the size of the subarrays
  int n1 = mid - left + 1;
  int n2 = right - mid;

  // Creating temporary arrays to store the subarrays.
  int[] L = new int[n1];
  int[] M = new int[n2];

  // Copying elements from the original array to subarrays.
  for (int i = 0; i < n1; i++)
    L[i] = array[left + i];
  for (int j = 0; j < n2; j++)
    M[j] = array[mid + 1 + j];

  // Initializing the indices for the subarrays and the original array
  int i, j, k;
  i = 0;
  j = 0;
  k = left;

  // Comparing and coping the elements from the subarrays to the original array in sorted order.
  while (i < n1 && j < n2) {
    if (L[i] <= M[j]) {
      array[k] = L[i];
      i++;
    } else {
      array[k] = M[j];
      j++;
    }
    k++;
  }

  // Copying the remaining elements from the subarrays to the original array if any.
  while (i < n1) {
    array[k] = L[i];
    i++;
    k++;
  }

  while (j < n2) {
    array[k] = M[j];
    j++;
    k++;
  }
}

// Dividing the array into two subarrays to sort and merge them.
public static void mergeSort(int[] array, int left, int right) {
  if (left < right) {
    // Finding the mid point.
    int mid = (left + right) / 2;

    // Recursively sorting the left and right subarrays.
    mergeSort(array, left, mid);
    mergeSort(array, mid + 1, right);

    // Merging sorted subarrays.
    merge(array, left, mid, right);
  }
}

//Instructions for taking user input. 
public static void main(String[] args) {
int[] array;
try (Scanner keyboard = new Scanner(System.in)) {
        // Ask the user for input mode.
        System.out.println("Enter 1 to type in an integer or 2 to give an input file:");
        int mode = keyboard.nextInt();
if (mode == 1) {
System.out.println("Enter the length of the array:");
            int length = keyboard.nextInt();
array = createRandomArray(length);
        } else if (mode == 2) {
            // Asking for the name of the input file.
            System.out.println("Enter the name of the input file:");
            String filename = keyboard.next();
try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                // Converting the BufferedReader to an IntStream and then to an array.
                // The IntStream is a stream of primitive int values.
                // The toArray method returns an array containing the elements of the stream.
                array = reader.lines().mapToInt(Integer::parseInt).toArray();
            } catch (IOException e) {
                // Handling the exception.
                System.out.println("IO error: " + e.getMessage());
                return;
            }
        } else {
            // Invalid input mode message.
            System.out.println("Invalid input mode: " + mode);
            return;
        }
    } catch (InputMismatchException e) {
        System.out.println("Invalid input: " + e.getMessage());
        return;
    }

    // Writing the array to a file named "output.txt".
    writeArrayToFile(array, "output.txt");

    // Sorting the array using merge sort.
    mergeSort(array, 0, array.length - 1);

    // Writing the sorted array to a file named "sorted.txt".
    writeArrayToFile(array, "sorted.txt");
}
}
