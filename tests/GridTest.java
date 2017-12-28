import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;


public class GridTest{
    String[] testLayout = {"0","1", "2", "3", "4","5","6","7","8","9","10","11","12","13","14","16"};
    Grid testGrid = new Grid();

    @Test
    public void testSetGridLayoutArray()  {
 //   testGrid.printGridLayout();
    testGrid.setGridLayoutArray();
    //testGrid.printGridLayout();
    Assert.assertArrayEquals(testGrid.getGridLayoutArray(), testLayout);

    }

    @Test
    public void testGetEmptyIndex() {
        /*
        testGridLayout will look like this
        test will simulate clicking on 11 and it should return 15 as the empty index

        0  1  2  3
        4  5  6  7
        8  9  10 11
        12 13 14 --

        Test will simulate clicking on 11 and it should return 15 as the empty index
         */

        testGrid.setGridLayoutArray();
        Assert.assertEquals(15, testGrid.getEmptyIndex(11));

    }

    @Test
    public void testSetGridLayoutTo(){
        Assert.assertNotSame(testLayout, testGrid.getGridLayoutArray());
        String[] newTestGridLayout = {"0","1", "2", "3", "4","5","6","7","8","9","10","11","12","13","14","16"};
        testGrid.setGridLayoutArrayTo(newTestGridLayout);
        Assert.assertArrayEquals(newTestGridLayout,testGrid.getGridLayoutArray());

    }
    @Test
    public void testCheckTopFalse() {
        /*
        testGridLayout:
        0  1  2  3
        4  5  6  7
        8  9  10 11
        12 13 14 --
        */

        testGrid.setGridLayoutArrayTo(testLayout);
        Assert.assertEquals(testGrid.checkTop(11), false);
    }
    @Test
    public void testCheckTopTrue(){
        /*
        testGridLayout:
        0  1  2  3
        4  5  6  7
        8  9  10 --
        12 13 14 11
        */
        String[] newTestGridLayout = {"0","1", "2", "3", "4","5","6","7","8","9","10","16","12","13","14","11"};

        testGrid.setGridLayoutArrayTo(newTestGridLayout);
        Assert.assertArrayEquals(newTestGridLayout,testGrid.getGridLayoutArray());
        Assert.assertEquals( true, testGrid.checkTop(15));
    }

    @Test
    public void testCheckBottomTrue() {
 /*
        testGridLayout:
        0  1  2  3
        4  5  6  7
        8  9  10 11
        12 13 14 --
        */
        testGrid.setGridLayoutArrayTo(testLayout);
        Assert.assertEquals(true, testGrid.checkBottom(11));
    }
    @Test
    public void testCheckBottomFalse(){
       /*
        testGridLayout:
        0  1  2  3
        4  5  6  7
        8  9  10 11
        12 13 14 --
        */
        testGrid.setGridLayoutArrayTo(testLayout);
        Assert.assertEquals( false , testGrid.checkBottom(10) );
    }

    @Test
    public void testCheckLeftFalse(){
         /*
        testGridLayout:
        0  1  2  3
        4  5  6  7
        8  9  10 11
        12 13 14 --
        */
        testGrid.setGridLayoutArrayTo(testLayout);
        Assert.assertEquals(false, testGrid.checkLeft(11));
    }
    @Test
    public void testCheckLeftTrue() {
        /*
        testGridLayout:
        0  1  2  3
        4  5  6  7
        8  9  10 11
        12 13 -- 14
        */

        String[] newTestGridLayout = {"0","1", "2", "3", "4","5","6","7","8","9","10","11","12","13","16","14"};
        testGrid.setGridLayoutArrayTo(newTestGridLayout);
        Assert.assertEquals( true , testGrid.checkLeft(15));
    }

    @Test
    public void testCheckRight(){
        /*
        testGridLayout:
        0  1  2  3
        4  5  6  7
        8  9  10 11
        12 13 14 --
        */
        testGrid.setGridLayoutArrayTo(testLayout);
        Assert.assertEquals(true, testGrid.checkRight(14));
        Assert.assertEquals( false , testGrid.checkRight(13));
    }

    @Test
    public void testGetIndex() {
        /*
        testGridLayout:
        0  1  2  3
        4  5  6  7
        8  9  10 11
        12 13 14 --
        */
        testGrid.setGridLayoutArrayTo(testLayout);
        for(int i=0; i<14; i++) {
            Assert.assertEquals(i, testGrid.getIndex(Integer.toString(i)));
        }


    }


    @Test
    public void testCheckComplexity(){
        /*
        testGridLayout:
        0  1  2  3
        4  5  6  7
        8  9  10 11
        12 13 14 --
        */
        testGrid.setGridLayoutArrayTo(testLayout);

        Assert.assertEquals(0,testGrid.checkComplexity());

        String[] newTestGridLayout = {"0","1", "2", "3", "4","5","6","7","8","9","10","11","14","13","12","16"};
        testGrid.setGridLayoutArrayTo(newTestGridLayout);
        Assert.assertEquals(3, testGrid.checkComplexity());


        String[] newTestGridLayout1 = {"14","13", "12", "11","10","9","8","7","6","5","4","3","2","1","0","16"};
        testGrid.setGridLayoutArrayTo(newTestGridLayout1);

        Assert.assertEquals(105, testGrid.checkComplexity());
    }

    @Test
    public void testCheckIfCompletedFalse() {
        String[] newTestGridLayout = {"14", "13", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0", "16"};
        testGrid.setGridLayoutArrayTo(newTestGridLayout);
        Assert.assertEquals(false, testGrid.checkIfCompleted());

    }

    @Test
    public void testCheckIfCompletedTrue() {
        testGrid.setGridLayoutArray();
        Assert.assertEquals(true, testGrid.checkIfCompleted());
    }

    @Test
    public void shuffle()  {
        testGrid.setGridLayoutArray();
        testGrid.shuffle();
        Assert.assertNotSame(testLayout, testGrid.getGridLayoutArray());

    }

}