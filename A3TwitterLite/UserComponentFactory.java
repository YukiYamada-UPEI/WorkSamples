import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class produces an instance of a User or a Group.
 * The creation method accepts an id and the DefaultMutableTreeNode which has the id in it.
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public class UserComponentFactory {

    private UserDBMS userDBMS;

    /**
     * Constructor
     * @param userDBMS
     */
    public UserComponentFactory(UserDBMS userDBMS) {
        this.userDBMS = userDBMS;
    }


    /**
     * Creates a new User
     * @param id the user id
     * @param node the JTree node of the new user
     * @return an instantiated User
     */
    public User createUser(String id, DefaultMutableTreeNode node) {
        User user = new User(id);
        user.setUserDBMS(userDBMS); //user DBMS is assigned to each user
        user.setTreeNode(node);
        return user;
    }

    /**
     * Creates a new UserGroup
     * @param id the group id
     * @param node the JTree node of the new UserGroup
     * @return an instantiated UserGroup
     */
    public UserGroup createGroup(String id, DefaultMutableTreeNode node) {
        UserGroup group = new UserGroup(id);
        group.setTreeNode(node);
        return group;
    }

}
