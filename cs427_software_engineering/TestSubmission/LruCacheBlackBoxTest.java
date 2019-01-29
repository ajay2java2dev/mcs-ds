package BlackBox;

import BlackBox.Setups.LruCacheSetup;
import Utils.LruCacheMethod;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LruCacheBlackBoxTest extends LruCacheSetup {

    @Test
    public void testPositiveRanges(){
        //constructor size = 2
        int[] functionParameters = new int[]{2};
        lruCachePUT.run(LruCacheMethod.Constructor,
                functionParameters,
                null);

        //put key=1, value = 2
        int[] functionParameters2 = new int[]{1,2};
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters2,
                null);

        //put key=1, value = 2
        int[] functionParameters3 = new int[]{2,1};
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters3,
                null);

        lruCachePUT.run(LruCacheMethod.Get,
                functionParameters3,
                1);

        int[] functionParameters4 = new int[]{3,1};
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters4,
                null);
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters3,
                null);
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters2,
                null);
    }

    @Test
    public void testPositiveValues(){
        //constructor size = 2
        int[] functionParameters = new int[]{2};
        lruCachePUT.run(LruCacheMethod.Constructor,
                functionParameters,
                null);

        //put key=1, value = 2
        int[] functionParameters2 = new int[]{1,2};
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters2,
                null);

        //get key = 1
        int[] functionParameters3 = new int[]{1};
        lruCachePUT.run(LruCacheMethod.Get,
                functionParameters3,
                2);
    }

    @Test
    public void testLargeNumbers(){
        //constructor size = 2
        int[] functionParameters = new int[]{2};
        lruCachePUT.run(LruCacheMethod.Constructor,
                functionParameters,
                null);

        //put key=1, value = 2
        int[] functionParameters2 = new int[]{1,123321,2,0,3,121212,4,1231213,5,121212};
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters2,
                null);

        //get key = 1
        int[] functionParameters3 = new int[]{1};
        lruCachePUT.run(LruCacheMethod.Get,
                functionParameters3,123321
                );

        int[] functionParameters4 = new int[]{1,123321,2,0,3,121212,4,1231213,5,121212};
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters4,
                null);

        int[] functionParameters5 = new int[]{1};
        lruCachePUT.run(LruCacheMethod.Get,
                functionParameters5,123321
        );
    }

    @Test
    public void testNullRuns(){
        int[] functionParameters = new int[]{-1,1};
        lruCachePUT.run(LruCacheMethod.valueOf("Constructor"),
                functionParameters,null
        );

        lruCachePUT.run(LruCacheMethod.valueOf("Get"),
                functionParameters,-1
        );

        int[] functionParameters5 = new int[]{5,1000};
        lruCachePUT.run(LruCacheMethod.valueOf("Put"),
                functionParameters5,null
        );

        int[] key = new int[]{5};
        lruCachePUT.run(LruCacheMethod.valueOf("Get"),
                key,1000
        );

    }

    @Test
    public void testNullRunsWithEnumTypes(){
        int[] functionParameters = new int[]{-999999,999999};
        lruCachePUT.run(LruCacheMethod.valueOf("Constructor"),
                functionParameters,null
        );

        lruCachePUT.run(LruCacheMethod.valueOf("Get"),
                functionParameters,-1
        );

        int[] functionParameters5 = new int[]{5,1000};
        lruCachePUT.run(LruCacheMethod.valueOf("Put"),
                functionParameters5,null
        );

        int[] key = new int[]{5};
        lruCachePUT.run(LruCacheMethod.valueOf("Get"),
                key,1000
        );

    }

    @Test
    public void testLruNodes () {

        //constructor size = 2
        int[] functionParameters = new int[]{1};
        lruCachePUT.run(LruCacheMethod.Constructor,
                functionParameters,
                null);

        int[] functionParameters2 = new int[]{1,123321,2,123123,3,121212,4,1231213,5,121212};
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters2,
                null);

        int[] functionParameters3 = new int[]{1,123321,2,123321,3,123321,4,1231213,5,121212};
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters3,
                null);

        //get key = 1
        int[] functionParameters4 = new int[]{3};
        lruCachePUT.run(LruCacheMethod.Get,
                functionParameters3,123321
        );
    }

    @Test
    public void testLruNegativeNodes () {
        int[] functionParameters = new int[]{'c'};
        lruCachePUT.run(LruCacheMethod.Constructor,
                functionParameters,
                null);

        int[] functionParameters2 = new int[]{1,'c'};
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters2,
                null);

        int[] functionParameters3 = new int[]{1};
        lruCachePUT.run(LruCacheMethod.Get,
                functionParameters3,99
        );

        //also
        functionParameters = new int[]{2,1};
        lruCachePUT.run(LruCacheMethod.Constructor,
                functionParameters,
                null);

        functionParameters2 = new int[]{1,2};
        lruCachePUT.run(LruCacheMethod.Put,
                functionParameters2,
                null);
    }


    @Test
    public void testLruCacheMethods () {
        int[] functionParameters = new int[]{'1'};
        lruCachePUT.run(LruCacheMethod.values()[0],functionParameters,null);
        int[] newFuncParams = new int[]{2,3};
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);

        lruCachePUT.run(LruCacheMethod.values()[1],newFuncParams,3);
        lruCachePUT.run(LruCacheMethod.values()[1],newFuncParams,3);
        lruCachePUT.run(LruCacheMethod.values()[1],newFuncParams,3);

        int[] functionParameters2 = new int[]{100};
        lruCachePUT.run(LruCacheMethod.Constructor,functionParameters2,null);
        lruCachePUT.run(LruCacheMethod.Put,new int[]{2323,1212,12,12,13,1111,2222222,3333,2121,1212
        ,321,2323,123123,123123,123123,321,321,321,312},null);
        lruCachePUT.run(LruCacheMethod.Put,new int[]{2323,1212,12,12,13,1111,2222222,3333,2121,1212
                ,321,2323,123123,123123,123123,321,321,321,312},null);
        lruCachePUT.run(LruCacheMethod.Put,new int[]{2323,1212,12,12,13,1111,2222222,3333,2121,1212
                ,321,2323,123123,123123,123123,321,321,321,312},null);
        lruCachePUT.run(LruCacheMethod.Put,new int[]{-2323,-1212,-12,-12,-13,-1111,-2222222,-3333,-2121,-1212
                ,-321,-2323,-123123,-123123,-123123,-321,-321,-321,-312},null);

        int[] functionParameters3 = new int[]{1};
        lruCachePUT.run(LruCacheMethod.Get,functionParameters3,-1);
    }


    @Test
    public void testLruPushMethods () {
        int[] functionParameters = new int[]{'1'};
        lruCachePUT.run(LruCacheMethod.values()[0],functionParameters,null);

        int[] newFuncParams = new int[]{2,3,22,22,22};
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);
        lruCachePUT.run(LruCacheMethod.values()[2],newFuncParams,null);

        lruCachePUT.run(LruCacheMethod.values()[1],newFuncParams,3);
        lruCachePUT.run(LruCacheMethod.values()[1],newFuncParams,3);
        lruCachePUT.run(LruCacheMethod.values()[1],newFuncParams,3);

        int[] functionParameters2 = new int[]{100};
        lruCachePUT.run(LruCacheMethod.Constructor,functionParameters2,null);
        lruCachePUT.run(LruCacheMethod.Put,new int[]{2323,1212,12,12,13,1111,2222222,3333,2121,1212
                ,321,2323,123123,123123,123123,321,321,321,312},null);
        lruCachePUT.run(LruCacheMethod.Put,new int[]{2323,1212,12,12,13,1111,2222222,3333,2121,1212
                ,321,2323,123123,123123,123123,321,321,321,312},null);
        lruCachePUT.run(LruCacheMethod.Put,new int[]{2323,1212,12,12,13,1111,2222222,3333,2121,1212
                ,321,2323,123123,123123,123123,321,321,321,312},null);
        lruCachePUT.run(LruCacheMethod.Put,new int[]{-2323,-1212,-12,-12,-13,-1111,-2222222,-3333,-2121,-1212
                ,-321,-2323,-123123,-123123,-123123,-321,-321,-321,-312},null);

        int[] functionParameters3 = new int[]{1};
        lruCachePUT.run(LruCacheMethod.Get,functionParameters3,-1);
    }


    @Test
    public void testNegativeArrayCache () {

        List<LruCacheMethod> lruMethodList = new ArrayList<LruCacheMethod>();
        lruMethodList.add(LruCacheMethod.Get);
        lruMethodList.add(LruCacheMethod.Put);
        lruMethodList.add(LruCacheMethod.Put);
        lruCachePUT.lruMethodList = lruMethodList;

        lruCachePUT.inputArgsList = new ArrayList<>();

        int[] functionParameters = new int[]{'1'};
        lruCachePUT.run(LruCacheMethod.values()[0],functionParameters,null);

        lruCachePUT.run(LruCacheMethod.Put,new int[]{9999,111},null);

        int[] functionParameters3 = new int[]{1};
        lruCachePUT.run(LruCacheMethod.Get,functionParameters3,-1);

        lruCachePUT.run(LruCacheMethod.Get,functionParameters3,-1);
    }

    @Test
    public void testExceptions () {

        List<LruCacheMethod> lruMethodList = new ArrayList<LruCacheMethod>();
        lruMethodList.add(LruCacheMethod.Get);
        lruCachePUT.lruMethodList = lruMethodList;

        lruCachePUT.inputArgsList = new ArrayList<>();

        int[] functionParameters = new int[]{'1'};
        lruCachePUT.run(LruCacheMethod.values()[0],functionParameters,null);

        lruCachePUT.run(LruCacheMethod.Put,new int[]{'#',111,123},null);

        int[] functionParameters3 = new int[]{1};
        lruCachePUT.run(LruCacheMethod.Get,functionParameters3,-1);

        lruCachePUT.run(LruCacheMethod.Get,functionParameters3,-1);
    }

    @Test
    public void testAlternateFlows () {

        List<LruCacheMethod> lruMethodList = new ArrayList<LruCacheMethod>();
        lruMethodList.add(LruCacheMethod.Get);
        lruCachePUT.lruMethodList = lruMethodList;
        lruCachePUT.inputArgsList = new ArrayList<>();

        int[] functionParameters = new int[]{'1'};
        lruCachePUT.run(LruCacheMethod.values()[0],functionParameters,null);

        lruCachePUT.run(LruCacheMethod.Put,new int[]{'#',111,123},null);
        try {
            LruCacheMethod instance = LruCacheMethod.Get;
            lruCachePUT.inputArgsList = new ArrayList<int[]>();

            int[] functionParameters3 = new int[]{1};
            lruCachePUT.run(instance,functionParameters3,-1);

            lruCachePUT.run(LruCacheMethod.Get,functionParameters3,-1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
