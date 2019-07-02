import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class is a parent abstract class of User and UserGroup class.
 * Each UserComponent has a tree node for itself.
 * This class is useful for the composite pattern.
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public abstract class UserComponent {


    protected DefaultMutableTreeNode treeNode; //The Tree node to be added to the JTree

    /**
     * @return treeNode of this UserComponent
     */
    public DefaultMutableTreeNode getTreeNode() {
        return treeNode;
    }

    /**
     * @param node a node of this UserComponent
     */
    public void setTreeNode(DefaultMutableTreeNode node){
        this.treeNode = node;
    }

    /**
     * Prints the information of the UserComponent
     */
    public void print() {
        throw new UnsupportedOperationException();
    }
}
