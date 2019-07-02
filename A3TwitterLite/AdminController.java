import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Represents the main controller. This class is instantiated when the app starts.
 * This implements ActionListener, TreeSelectionListener, and Acceptor.
 *
 * AdminController accounts for a creation of users and groups by using the UserFactory.
 * The created users and groups are sent to the UserDatabase and stored.
 * This class also accounts for updating AdminPanel and creates a UserPanel with UserPanelController.
 * Singleton class.
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public class AdminController implements ActionListener, TreeSelectionListener, Acceptor {
    private static AdminController instance = null;

    private AdminControlPanel adminControlPanel;
    private UserDatabase userDatabase = UserDatabase.getInstance();
    private UserDBMS userDBMS = UserDBMS.getInstance(userDatabase);
    private UserComponentFactory userComponentFactory = new UserComponentFactory(userDBMS);
    private UserGroup root;
    private String currentSelectedNodeId;

    private final String OPEN_USER_VIEW_BUTTON = "Open User View";
    private final String SHOW_USER_TTL_BUTTON = "Show Total Users";
    private final String SHOW_GRP_TTL_BUTTON = "Show Total Groups";
    private final String SHOW_MSG_TTL_BUTTON = "Show Total Messages";
    private final String SHOW_POS_MSG_BUTTON = "Show Total Positives";
    private final String ADD_USER = "Add User";
    private final String ADD_GROUP = "Add Group";


    /**
     * Set up every JComponent
     */
    public AdminController() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Root");
        currentSelectedNodeId = "Root";
        System.out.println("Admin controller");
        adminControlPanel = AdminControlPanel.getInstance(rootNode);
        adminControlPanel.setController(this);
        // Creates a listener for each focus needed
        adminControlPanel.setUserFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                adminControlPanel.focusUserTextArea();
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (adminControlPanel.getUserId().equals(""))
                    adminControlPanel.unfocusUserTextArea();
            }
        });
        adminControlPanel.setGroupFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                adminControlPanel.focusGroupTextArea();
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (adminControlPanel.getGroupId().equals(""))
                    adminControlPanel.unfocusGroupTextArea();
            }
        });
        root = userComponentFactory.createGroup("Root", rootNode);


        userDatabase.addRoot(root);
    }

    /**
     * AdminPanel listener actions
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String source = e.getActionCommand();

        switch (source) {

            case (OPEN_USER_VIEW_BUTTON):
                openUserViewButtonPressed();
                break;

            case (SHOW_USER_TTL_BUTTON):
                adminControlPanel.setInfoLabel("<html>TOTAL<br>USERS:  " + userDatabase.getNumOfUsers() + "</html>");
                root.print();
                System.out.println();
                break;

            case (SHOW_GRP_TTL_BUTTON):
                adminControlPanel.setInfoLabel("<html>TOTAL<br>GROUPS:  " + userDatabase.getNumOfGroups() + "</html>");
                break;

            case (SHOW_MSG_TTL_BUTTON):
                adminControlPanel.setInfoLabel("<html>TOTAL<br>MESSAGE:  " + userDatabase.getNumOfTweets() + "</html>");
                break;

            case (SHOW_POS_MSG_BUTTON):
                adminControlPanel.setInfoLabel("<html>POSITIVE PERCENTAGE:<br>  " + (userDatabase.getPercentOfPositiveMsg())*100 + "%</html>");
                break;

            case (ADD_USER):
                addUser();
                adminControlPanel.unfocusUserTextArea();
                break;

            case (ADD_GROUP):
                addGroup();
                adminControlPanel.unfocusGroupTextArea();
                break;

        }

    }

    /**
     * When JTree item is press, gets current selected node
     * @param e
     */
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        Object obj = e.getPath().getLastPathComponent();
        if (obj instanceof DefaultMutableTreeNode) {
            currentSelectedNodeId = (String) ((DefaultMutableTreeNode) obj).getUserObject();
        }
    }

    /**
     * Opens the User panel menu
     */
    public void openUserViewButtonPressed() {
        String userId = currentSelectedNodeId;
        User user = userDatabase.getUserFromDatabase(userId);
        if (user == null) {
            adminControlPanel.setInfoLabel("The user does not exist");
        } else {
            new UserPanelController(new UserPanel(user.getId()), user);

        }
    }

    /**
     * Helper method used in actionPerformed
     */
    public void addUser() {
        String userId = adminControlPanel.getUserId();
        String groupId = currentSelectedNodeId;
        UserGroup group = userDatabase.findGroup(groupId);
        if (group == null) {
            adminControlPanel.setInfoLabel("Group does not exist");
        } else {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(userId);
            User user = userComponentFactory.createUser(userId, node);

            if (userDatabase.addUser(userId, user, group)) {
                adminControlPanel.updateTree();
            }
        }
    }

    /**
     * Helper method used in actionPerformed
     */
    public void addGroup() {
        String groupId = adminControlPanel.getGroupId();
        String parent = currentSelectedNodeId;
        UserGroup parentGroup = userDatabase.findGroup(parent);
        if (parentGroup == null) {
            adminControlPanel.setInfoLabel("Group does not exist");
        } else {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(groupId);
            UserGroup group = userComponentFactory.createGroup(groupId, node);//creates a group
            if (userDatabase.addGroup(groupId, group, parentGroup)) {
                adminControlPanel.updateTree();
            }
        }
    }

    /**
     * Generates a unique instance of AdminController, no more than one can be created
     * @return AdminController
     */
    public static AdminController getInstance() {
        if (instance == null)
            return new AdminController();
        return instance;
    }

    /**
     * Todo: Implement for future Visitor pattern usage
     * @param visitor
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
