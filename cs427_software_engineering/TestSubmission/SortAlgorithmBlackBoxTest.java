package BlackBox;

import BlackBox.Setups.SortSetup;
import Utils.SortUtils;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;

public class SortAlgorithmBlackBoxTest extends SortSetup {

    @Test
    public void testArrayBoundaryValues() {
        int maxVal = 9999; //Integer max values is making my JVM to crash

        int[] input = new int[maxVal];
        int[] expectedOut = new int[maxVal];

        int j = 0;
        for (int i = maxVal-1; i >= 0; i--) {
            input[j] = i;
            j++;
        }

        for (int j1 =0; j1 < maxVal; j1++) {
            expectedOut[j1] = j1;
        }

        sortAlgorithmPUT.run(input,expectedOut);
    }

    @Test
    public void testWithOneElement() {
        int arr[] = {1};
        int exp[] = {1};
        CycleSort sort = new CycleSort();
        sort.sort(arr);
        assertArrayEquals(exp, arr);
    }

    @Test
    public void testWithRepeatedElements() {
        for(int i = 0; i < 100; i++) {
            int arr[] = {10, 10, 1010, 10, 10, 1};
            int exp[] = {1, 10, 10, 10, 10, 1010};
            CycleSort sort = new CycleSort();
            sort.sort(arr);
            assertArrayEquals(exp, arr);
        }
    }

    @Test
    public void testWithMaxAndMin() {
        int arr[] = {999999999,999999999,999999999,-999999999,-999999999,-999999998};
        int exp[] = {-999999999,-999999999,-999999998,999999999,999999999,999999999};
        CycleSort sort = new CycleSort();
        sort.sort(arr);
        assertArrayEquals(exp, arr);
    }

    @Test
    public void testWithArrayReversed() {
        int arr[] = {10,9,8,7,6,5,4,3,2,1,0,-1,-2,-3,-4,-5,-6,-7,-8,-9,-10};
        int exp[] = {-10,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,10};
        CycleSort sort = new CycleSort();
        sort.sort(arr);
        assertArrayEquals(exp, arr);
    }

    @Test
    public void testWithSignedChars() {
        int arr[] = {'a', 'b', 'a', 'b', 'a', 'b', 'a', 'b', 'a', 'b', 'a', 'b', 'a', 'b', 'a', 'b', 'a', 'b', 'a', 'b', 'a', 'b',
                '1', 99999, -1, -00222, 2222};
        int exp[] = {-00222, -1, '1', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b',
                2222, 99999};
        CycleSort sort = new CycleSort();
        sort.sort(arr);
        assertArrayEquals(exp, arr);
    }

    @Test
    public void testWithEmptyArrs() {
        int [] arr1 = new int[10];
        int [] arr2 = new int[10];
        CycleSort sort = new CycleSort();
        sort.sort(arr1);
        sort.sort(arr2);
        assertArrayEquals(arr1, arr2);
    }

    @Test
    public void testWithRandomArrs() {
        int size = 5000;
        CycleSort sort = new CycleSort();
        int a [] = new int[size];
        int b [] = new int[size];

        for (int i = 0; i < size; i++) {
            int val = (int)(Math.random()*10);
            a [i] = val;
            b [i] = val;
            for (int j = 0; j < i; j++) {
                int val1 = (int)(Math.random()*10);
                if (a[i] == a[j]) {
                    a[j] = val1; //What's this! Another random number!
                }
                if (b[i] == b[j]) {
                    b[j] = val1; //What's this! Another random number!
                }
            }
        }
        Collections.shuffle(Arrays.asList(a));
        Collections.shuffle(Arrays.asList(b));

        sort.sort(a);
        sort.sort(b);
        assertArrayEquals(a, b);
    }

    @Test
    public void testArrayNewBoundaryValues() {
        int arr[] = {1, 8, 3, 9, 10, 10, 2, 4 };
        int exp[] = {1, 2, 3, 4, 8, 9, 10, 10  };
        int n = arr.length;
        CycleSort sort = new CycleSort();
        sort.sort(arr);
        assertArrayEquals(exp,arr);
        
        int newarr[] = {1, 8, 3, 9, 10, 10, 2, 4 };
        SortUtils utils = new SortUtils();
        utils.swap(newarr, 1, 2);
        int swappedarr[] = {1, 3, 8, 9, 10, 10, 2, 4 };
        assertArrayEquals(swappedarr,newarr);
        utils.print(newarr);
        utils.print(Arrays.asList(newarr));
        
    }

    @Test
    public void testPositiveValues(){
        int[] input = new int[]{2123,1221,'a','@','c',999999999};
        int[] expectedOutput = new int[]{'@','a','c',1221,2123,999999999};
        sortAlgorithmPUT.run(input,expectedOutput);

        int[] input1 = new int[]{1,2,3,4,5,6,7,'a','c','b'};
        int[] expectedOutput1 = new int[]{1,2,3,4,5,6,7,'a','b','c'};
        sortAlgorithmPUT.run(input1,expectedOutput1);
    }

    @Test
    public void testArrayOfChars () {
        int[] input = new int[]{'u','n','i','v','e','r','s','i','t','y','o','f','i','l','l','i','n','o','i','s'};
        int[] expectedOutput = new int[]{'e','f','i','i','i','i','i','l','l','n','n','o','o','r','s','s','t','u','v',
                'y'};
        sortAlgorithmPUT.run(input,expectedOutput);
    }

    @Test
    public void testSpecialChars () {
        int[] input = new int[]{'@','$','+'};
        int[] expectedOutput = new int[]{'$','+','@'};
        sortAlgorithmPUT.run(input,expectedOutput);
    }

    @Test
    public void testSpecialVals () {
        int[] input = new int[0];
        int[] expectedOutput = input;
        sortAlgorithmPUT.run(input,expectedOutput);
    }

    @Test
    public void testSpecialValsInLoop () {
        for (int i = 0 ; i< 5; i++) {
            int[] input = null;
            int[] expectedOutput=null;
            if (i < 4) {
                input = new int[i];
                expectedOutput = input;
            }
            sortAlgorithmPUT.run(input, expectedOutput);
        }

    }


    @Test
    public void testNegativeNumbers () {
        int[] input = new int[]{-1,2,4,99999,-99999};
        int[] expectedOutput = new int[]{-99999,-1,2,4,99999};
        sortAlgorithmPUT.run(input,expectedOutput);

        int[] newInput = null;
        int[] newExpectedOutput = null;
        sortAlgorithmPUT.run(newInput,newExpectedOutput);
    }

    @Test
    public void testMixedValues () {
        int[] input = new int[]{-1,2,4,99999,-99999,'c','b','a'};
        int[] expectedOutput = new int[]{-99999,-1,2,4,'a','b','c',99999};
        sortAlgorithmPUT.run(input,expectedOutput);
    }

    @Test
    public void testScenario () {
        int[] input = new int[10];
        int[] expectedOutput = new int[10];
        sortAlgorithmPUT.run(input,expectedOutput);

        input = null;
        sortAlgorithmPUT.run(input,null);
    }
}
