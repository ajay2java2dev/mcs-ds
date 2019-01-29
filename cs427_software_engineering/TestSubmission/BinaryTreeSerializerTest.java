package WhiteBox;

import Utils.TreeNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BinaryTreeSerializerTest {

    @Test
    public void serializeTestWithNull(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        TreeNode root = null;
        String ret = bs.serialize(root);
        assertEquals("[null]",ret);

        TreeNode root1= new TreeNode(0);
        String rootret1 = bs.serialize(root1);
        assertEquals("[0,null,null]",rootret1);
    }

    @Test
    public void serializeTestJustRoot(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        TreeNode root = new TreeNode(0);
        root.left = null;
        root.right = new TreeNode(1);
        String ret = bs.serialize(root);
        assertEquals("[0,null,1,null,null]",ret);
    }

    @Test
    public void serializeTestWithMoreNull(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        TreeNode root1= new TreeNode(0);
        String rootret1 = bs.serialize(root1);
        assertEquals("[0,null,null]",rootret1);

        root1= new TreeNode(2);
        root1.val = 5;
        root1.left =null;
        root1.right =null;
        rootret1 = bs.serialize(root1);
        assertEquals("[5,null,null]",rootret1);

        root1= new TreeNode(0);
        root1.val = +0;
        root1.left = new TreeNode(-4);
        root1.right = new TreeNode(+4);
        rootret1 = bs.serialize(root1);
        assertEquals("[0,-4,4,null,null,null,null]",rootret1);

    }

    @Test
    public void serializeTestWithObject(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        TreeNode root = new TreeNode(5);

        root.left = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(10);

        root.right = new TreeNode(7);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(8);

        String ret = bs.serialize(root);
        assertEquals("[5,4,7,3,10,6,8,null,null,null,null,null,null,null,null]",ret);
    }

    @Test
    public void serializeTestWithNodeList(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        TreeNode root = new TreeNode(5);

        root.left = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(10);
        root.left.right.right = null;
        root.left.right.left = null;

        root.right = new TreeNode(7);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(8);

        String ret = bs.serialize(root);
        assertEquals("[5,4,7,3,10,6,8,null,null,null,null,null,null,null,null]",ret);
    }

    @Test
    public void serializeLevelForNull(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        TreeNode root = new TreeNode(5);

        root.left = new TreeNode(4);
        root.left.left = null;
        root.left.right = null;

        String ret = bs.serialize(root);
        assertEquals("[5,4,null,null,null]",ret);
    }


    @Test
    public void serializeTestWithNegativeValues(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        TreeNode root = new TreeNode(-5);

        root.left = new TreeNode(-4);
        root.left.left = new TreeNode(-3);
        root.left.right = new TreeNode(-10);

        root.right = new TreeNode(-7);
        root.right.left = new TreeNode(-6);
        root.right.right = new TreeNode(-8);
        String ret = bs.serialize(root);
        assertEquals("[-5,-4,-7,-3,-10,-6,-8,null,null,null,null,null,null,null,null]",ret);
    }

    @Test
    public void deserializeWithNullRoot(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        String s = "[0,1,1,null,null,null,null]";
        String s1 = bs.serialize(bs.deserialize(s));
        assertEquals(s,s1);
    }

    @Test
    public void deserializeWithAll(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        String s = "[null]";
        TreeNode root = null;
        TreeNode rootret = bs.deserialize(s);
        assertEquals(root,rootret);

        String s1 = "[]";
        TreeNode rootret1 = bs.deserialize(s1);
        assertEquals(root,rootret1);

        bs = new BinaryTreeSerializerImpl();
        s = "[n,n,n,n]";

        root = new TreeNode(5);

        root.left = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(10);

        root.right = new TreeNode(7);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(8);

        TreeNode treeNode = bs.deserialize(s);
        assertNotEquals(root,treeNode);


        bs = new BinaryTreeSerializerImpl();
        s = "[5,null,null,1,2,3,4]";
        treeNode = bs.deserialize(s);

        root = new TreeNode(5);
        root.left = null;
        root.right = null;
        /*
        root.right.right = new TreeNode(2);
        root.right.right.left = new TreeNode(3);
        root.right.right.left.right = new TreeNode(3);
        root.right.right.left.left = new TreeNode(4);
        */

        assertEquals(root,treeNode);
        s1 = bs.serialize(root);
        assertEquals("[5,null,null]",s1);

        BinaryTreeSerializer bs1 = new BinaryTreeSerializerImpl();
        assertNotEquals(bs1.toString(),bs.toString());
        String s2 = "[5,6,null,1,2,3,4,null,null,null,null,null,null,null,null]";
        TreeNode treeNode2 = bs1.deserialize(s2);

        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(6);
        root2.left.left = new TreeNode(1);
        root2.left.left.left = new TreeNode(3);
        root2.left.left.right = new TreeNode(4);
        root2.left.right = new TreeNode(2);
        root2.right = null;
        assertEquals(root2,treeNode2);

        BinaryTreeSerializer bs3 = new BinaryTreeSerializerImpl();
        String s3 = "[5,null,7,1,2,3,4,null,null,null,null,null,null,null,null]";

        TreeNode treeNode3 = bs3.deserialize(s3);

        TreeNode root3 = new TreeNode(5);
        root3.left = null;
        root3.right = new TreeNode(7);
        root3.right.left = new TreeNode(1);
        root3.right.right = new TreeNode(2);
        root3.right.left.left = new TreeNode(3);
        root3.right.left.right = new TreeNode(4);
        assertEquals(root3,treeNode3);
    }

    @Test
    public void deserializeWithNull(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        String s = "[null]";
        TreeNode root = null;
        TreeNode rootret = bs.deserialize(s);
        assertEquals(root,rootret);

        String s1 = "[]";
        TreeNode rootret1 = bs.deserialize(s1);
        assertEquals(root,rootret1);
    }

    @Test
    public void deserializeWithEmpty(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        TreeNode root = null;
        String s1 = "[]";
        TreeNode rootret1 = bs.deserialize(s1);
        assertEquals(root,rootret1);
    }

    @Test
    public void deSerializeTestWithEmpty(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        String s = "[n,n,n,n]";

        TreeNode root = new TreeNode(5);

        root.left = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(10);

        root.right = new TreeNode(7);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(8);

        TreeNode treeNode = bs.deserialize(s);
        assertNotEquals(root,treeNode);
    }

    @Test
    public void deSerializeTestWithNoPart(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        String s1 = "[5,n,n,n]";
        String s2 = "[5]";
        TreeNode root = new TreeNode(5);

        root.left = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(10);

        root.right = new TreeNode(7);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(8);

        TreeNode treeNode1 = bs.deserialize(s1);
        assertNotEquals(root,treeNode1);

        TreeNode treeNode2 = bs.deserialize(s2);
        assertNotEquals(root,treeNode2);
    }

    @Test
    public void deSerializeTestWithObject(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        String s = "[5,4,7,3,10,6,8,null,null,null,null,null,null,null,null]";

        TreeNode root = new TreeNode(5);

        root.left = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(10);

        root.right = new TreeNode(7);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(8);

        TreeNode treeNode = bs.deserialize(s);
        assertEquals(root,treeNode);
    }

    @Test
    public void deSerializeTestWithNegative(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        String s = "[-5,-4,-7,-3,-10,-6,-8,null,null,null,null,null,null,null,null]";

        TreeNode root = new TreeNode(-5);

        root.left = new TreeNode(-4);
        root.left.left = new TreeNode(-3);
        root.left.right = new TreeNode(-10);

        root.right = new TreeNode(-7);
        root.right.left = new TreeNode(-6);
        root.right.right = new TreeNode(-8);

        TreeNode treeNode = bs.deserialize(s);
        assertEquals(root,treeNode);
    }

    @Test
    public void deSerializeTestWithNullInBetween(){
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        String s = "[5,null,null,1,2,3,4]";
        TreeNode treeNode = bs.deserialize(s);

        TreeNode root = new TreeNode(5);
        root.left = null;
        root.right = null;
        /*
        root.right.right = new TreeNode(2);
        root.right.right.left = new TreeNode(3);
        root.right.right.left.right = new TreeNode(3);
        root.right.right.left.left = new TreeNode(4);
        */

        assertEquals(root,treeNode);
        String s1 = bs.serialize(root);
        assertEquals("[5,null,null]",s1);

        BinaryTreeSerializer bs1 = new BinaryTreeSerializerImpl();
        assertNotEquals(bs1.toString(),bs.toString());
        String s2 = "[5,6,null,1,2,3,4,null,null,null,null,null,null,null,null]";
        TreeNode treeNode2 = bs1.deserialize(s2);

        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(6);
        root2.left.left = new TreeNode(1);
        root2.left.left.left = new TreeNode(3);
        root2.left.left.right = new TreeNode(4);
        root2.left.right = new TreeNode(2);
        root2.right = null;
        assertEquals(root2,treeNode2);

        BinaryTreeSerializer bs3 = new BinaryTreeSerializerImpl();
        String s3 = "[5,null,7,1,2,3,4,null,null,null,null,null,null,null,null]";

        TreeNode treeNode3 = bs3.deserialize(s3);

        TreeNode root3 = new TreeNode(5);
        root3.left = null;
        root3.right = new TreeNode(7);
        root3.right.left = new TreeNode(1);
        root3.right.right = new TreeNode(2);
        root3.right.left.left = new TreeNode(3);
        root3.right.left.right = new TreeNode(4);
        assertEquals(root3,treeNode3);


    }

    @Test
    public void compareTreeNodes () {
        TreeNode root1 = new TreeNode(-5);
        root1.left = new TreeNode(-4);
        root1.left.left = new TreeNode(-3);
        root1.left.right = new TreeNode(-10);
        root1.right = new TreeNode(-7);
        root1.right.left = new TreeNode(-6);
        root1.right.right = new TreeNode(-8);

        TreeNode root2 = new TreeNode(-5);
        root2.left = new TreeNode(-4);
        root2.left.left = new TreeNode(-3);
        root2.left.right = new TreeNode(-10);
        root2.right = new TreeNode(-7);
        root2.right.left = new TreeNode(-6);
        root2.right.right = new TreeNode(-8);

        assertEquals(true,root1.equals(root2));

        TreeNode root3 = new TreeNode(1);
        TreeNode root4 = new TreeNode(1);
        root3.left = new TreeNode(3);
        root3.left.left = null;
        root3.right = new TreeNode(4);
        root4.left = new TreeNode(3);
        root4.right = new TreeNode(4);
        root4.left.left = null;

        assertEquals(root3.hashCode(),root4.hashCode());
        assertEquals(true,root3.equals(root3));
        assertEquals(true,root3.equals(root4));
        assertEquals(false,root3.equals(new Object()));
        assertEquals(false,root3.equals(null));

        TreeNode root5 = new TreeNode(1);
        TreeNode root6 = new TreeNode(1);
        root5.left = null;
        root6.left = null;
        root5.right = new TreeNode(1);
        root6.right = null;
        assertEquals(false,root5.equals(root6));
    }
}
