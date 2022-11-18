import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/*
 * Design pattern: Composite
 */

public class TreeView extends JTree {

    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode admin;
    private ArrayList<User> userList;

    public TreeView() {
        userList = new ArrayList<>();
        root = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(root);

        // cs3560 = new DefaultMutableTreeNode("CS3560");
        admin = new DefaultMutableTreeNode("admin");

        // // cs3560.add(john);
        root.add(admin);

        this.setModel(treeModel);
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void setRoot(DefaultMutableTreeNode root) {
        this.root = root;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public void setTreeModel(DefaultTreeModel treeModel) {
        this.treeModel = treeModel;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

}
