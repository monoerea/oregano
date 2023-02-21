package com.mono.oregano.domain.useCase;

import com.mono.oregano.data.model.Model;

import java.io.IOException;
import java.util.ArrayList;

public class IntroSort {

    // the actual data that has to be sorted
    private ArrayList<Model> objList;

    // the number of elements in the data
    private int n;

    // Constructor to initialize the size
    // of the data
    IntroSort(ArrayList<Model> data) {
        objList = data;
        this.n = data.size();
    }

    private int toInt(String name){
        int sum = 0;
        for(int i = 0; i < name.length(); i++){
            int asciiValue = name.charAt(i);
            sum = sum + asciiValue;
        }
        return sum;
    }

    // The utility function to insert the data
    private void dataAppend(Model temp) {
        objList.add(temp);
        n++;
    }

    // The utility function to swap two elements
    private void swap(int i, int j) {
        Model temp = objList.get(i);
        objList.set(i, objList.get(j));
        objList.set(j,temp) ;
    }

    // To maxHeap a subtree rooted with node i which is
    // an index in a[]. heapN is size of heap
    private void maxHeap(int i, int heapN, int begin) {
        Model temp = objList.get(begin + i - 1);
        int child;

        while (i <= heapN / 2) {
            child = 2 * i;

            if (child < heapN
                    && toInt(objList.get(begin + child - 1).getObjectName()) < toInt(objList.get(begin + child).getObjectName()))
                child++;

            if (toInt(temp.getObjectName()) >= toInt(objList.get(begin + child - 1).getObjectName()))
                break;

            objList.set(begin + child - 1, objList.get(begin + child - 1));
            i = child;
        }
        objList.set(begin + i - 1, temp);
    }

    // Function to build the heap (rearranging the array)
    private void heapify(int begin, int end, int heapN) {
        for (int i = (heapN) / 2; i >= 1; i--)
            maxHeap(i, heapN, begin);
    }

    // main function to do heapsort
    private void heapSort(int begin, int end) {
        int heapN = end - begin;

        // Build heap (rearrange array)
        this.heapify(begin, end, heapN);

        // One by one extract an element from heap
        for (int i = heapN; i >= 1; i--) {

            // Move current root to end
            swap(begin, begin + i);

            // call maxHeap() on the reduced heap
            maxHeap(1, i, begin);
        }
    }

    // function that implements insertion sort
    private void insertionSort(int left, int right) {

        for (int i = left; i <= right; i++) {
            Model key = objList.get(i);
            int j = i;

            // Move elements of arr[0..i-1], that are
            // greater than the key, to one position ahead
            // of their current position
            while (j > left && toInt(objList.get(j - 1).getObjectName()) > toInt(key.getObjectName())) {
                objList.set(j, objList.get(j - 1));
                j--;
            }
            objList.set(j, key);
        }
    }

    // Function for finding the median of the three elements
    private int findPivot(int a1, int b1, int c1) {
        int max = Math.max(Math.max(toInt(objList.get(a1).getObjectName()), toInt(objList.get(b1).getObjectName())), toInt(objList.get(c1).getObjectName()));
        int min = Math.min(Math.min(toInt(objList.get(a1).getObjectName()), toInt(objList.get(b1).getObjectName())), toInt(objList.get(c1).getObjectName()));
        int median = max ^ min ^ toInt(objList.get(a1).getObjectName()) ^ toInt(objList.get(b1).getObjectName()) ^ toInt(objList.get(c1).getObjectName());
        if (median == toInt(objList.get(a1).getObjectName()))
            return a1;
        if (median == toInt(objList.get(b1).getObjectName()))
            return b1;
        return c1;
    }

    // This function takes the last element as pivot, places
    // the pivot element at its correct position in sorted
    // array, and places all smaller (smaller than pivot)
    // to the left of the pivot
    // and greater elements to the right of the pivot
    private int partition(int low, int high) {

        // pivot
        Model pivot = objList.get(high);

        // Index of smaller element
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {

            // If the current element is smaller
            // than or equal to the pivot
            if (toInt(objList.get(j).getObjectName()) <= toInt(pivot.getObjectName())) {

                // increment index of smaller element
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return (i + 1);
    }

    // The main function that implements Introsort
    // low  --> Starting index,
    // high  --> Ending index,
    // depthLimit  --> recursion level
    private void sortDataUtil(int begin, int end, int depthLimit) {
        if (end - begin > 16) {
            if (depthLimit == 0) {

                // if the recursion limit is
                // occurred call heap sort
                this.heapSort(begin, end);
                return;
            }

            depthLimit = depthLimit - 1;
            int pivot = findPivot(begin,
                    begin + ((end - begin) / 2) + 1,
                    end);
            swap(pivot, end);

            // p is partitioning index,
            // arr[p] is now at right place
            int p = partition(begin, end);

            // Separately sort elements before
            // partition and after partition
            sortDataUtil(begin, p - 1, depthLimit);
            sortDataUtil(p + 1, end, depthLimit);
        } else {
            // if the data set is small,
            // call insertion sort
            insertionSort(begin, end);
        }
    }

    // A utility function to begin the
    // Introsort module
    private void sortData() {

        // Initialise the depthLimit
        // as 2*log(length(data))
        int depthLimit
                = (int) (2 * Math.floor(Math.log(n) /
                Math.log(2)));

        this.sortDataUtil(0, n - 1, depthLimit);
    }

    // A utility function to print the array data
    private void printData() {
        for (int i = 0; i < n; i++)
            System.out.print(objList.get(i).getObjectName() + " ");
    }

    // Driver code
    public static void main(String args[]) throws IOException {

        ArrayList<Model> myList= new ArrayList<>();
        //TODO:fill
        int n = myList.size();

        IntroSort introsort = new IntroSort(myList);

        introsort.sortData();
        introsort.printData();
    }
}
