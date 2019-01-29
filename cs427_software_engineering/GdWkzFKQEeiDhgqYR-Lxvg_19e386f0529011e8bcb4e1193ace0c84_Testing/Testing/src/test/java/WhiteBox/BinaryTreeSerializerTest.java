package WhiteBox;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Utils.TreeNode;

public class BinaryTreeSerializerTest {

    @Test
    public void serializeTest(){
        //@TODO: Delete/modify me
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        TreeNode root = null;
        String ret = bs.serialize(root);
        assertEquals("[null]",ret);
    }

    @Test
    public void deserializeTest2(){
        //@TODO: Delete/modify me
        BinaryTreeSerializer bs = new BinaryTreeSerializerImpl();
        String s = "[null]";
        TreeNode root = null;
        TreeNode rootret = bs.deserialize(s);
        assertEquals(root,rootret);
    }

    //@TODO: Create more tests
}
