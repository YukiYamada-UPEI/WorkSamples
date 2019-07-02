import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a Group. UserGroup is a UserComponent
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public class UserGroup extends UserComponent implements Acceptor{
    private String id;
    private ArrayList<UserComponent> userList  = new ArrayList<>();


    /**
     * Constructor
     * @param id
     */
    public UserGroup(String id) {
        this.id = id;
    }

    /**
     * @return a group id of this UserGroup
     */
    public String getId() {
        return id;
    }

    /**
     * Adds a User or a UserGroup to the list of UserComponent
     * @param user can be a User or a UserGroup
     */
    public void addUserComponent(UserComponent user) {
        userList.add(user);
        this.treeNode.add(user.getTreeNode());//The JTree's node gets updated
    }

    /**
     * Prints every UserComponents using the composite and iterator pattern
     */
    @Override
    public void print() {
        System.out.print(" (["+ getId() + "]");
        Iterator<UserComponent> iterator = userList.iterator();
        while (iterator.hasNext()){
            UserComponent userComponent = (UserComponent)iterator.next();
            userComponent.print();
        }
        System.out.print(")");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

