import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

/**
 * UserPanel is a JPanel. It accounts for a user view.
 * It accepts an user id to follow, a tweet, and shows news feed.
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public class UserPanel extends JPanel {

    private JPanel currentFollowingsPanel, newsFeedPanel;
    private JScrollPane followingScrollPane, newsFeedScrollPane;
    private JLabel followingsLabel, newsFeedLabel;
    private JTextArea userIdTextArea, tweetTextArea;
    private JButton followBtn, postBtn;

    private Color bluish = new Color(179,230,255),
                greenish = new Color(204,255,153),
                darkbluish = new Color(85, 178,255);

    /**
     * Constructor
     * @param title
     */
    public UserPanel(String title) {
        createComponents();

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 400));
        createComponents();
        setLayout();

        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2,dim.height/2-frame.getSize().height/2);
    }
    /**
     * @return String
     */
    public String getUserId(){
        return userIdTextArea.getText();
    }
    /**
     * @return String
     */
    public String getTweetToPost(){
        return tweetTextArea.getText();
    }
    /**
     * @return String
     */
    public String getUserIdTextArea() {
        return userIdTextArea.getText();
    }
    /**
     * @return String
     */
    public String getTweetTextArea(){
        return tweetTextArea.getText();
    }
    /**
     * @param controller
     */
    public void setController(ActionListener controller){
        followBtn.addActionListener(controller);
        postBtn.addActionListener(controller);

    }
    /**
     * @param controller
     */
    public void setFocusTweetListener(FocusListener controller){
        tweetTextArea.addFocusListener(controller);
    }
    /**
     * @param controller
     */
    public void setFocusFollowListener(FocusListener controller){
        userIdTextArea.addFocusListener(controller);
    }

    /**
     * Updates the following List
     * @param followingsList
     */
    public void updateFollowings(String followingsList){
        followingsLabel.setText(followingsList);
    }

    /**
     * Updates the News Feed
     * @param newsFeed
     */
    public void updateNewsFeed(String newsFeed) {
        newsFeedLabel.setText(newsFeed);
    }

    /**
     * Changes UserId text field when focus acquired
     */
    public void focusUserId() {
        userIdTextArea.setText("");
    }

    /**
     * Changes UserId text field when focus is lost and is empty
     */
    public void unfocusUserId() {
        userIdTextArea.setText("Enter a userId");
    }

    /**
     * Changes Tweet text field when focus acquired
     */
    public void focusTweet() {
        tweetTextArea.setText("");
    }

    /**
     * Changes Tweet text field when focus is lost and is empty
     */
    public void unfocusTweer() {
        tweetTextArea.setText("Post tweet here...");
    }

    /**
     * Set ups all Swing components to be use by this user panel
     */
    private void createComponents() {

        currentFollowingsPanel = new JPanel();
        followingsLabel = new JLabel("Followings");
        followingsLabel.setBorder(BorderFactory.createEtchedBorder());
        followingScrollPane = new JScrollPane(followingsLabel);
        followingScrollPane.setPreferredSize(new Dimension(260,140));
        newsFeedPanel = new JPanel();
        newsFeedLabel = new JLabel("News Feed");
        newsFeedLabel.setBorder(BorderFactory.createEtchedBorder());
        newsFeedScrollPane = new JScrollPane(newsFeedLabel);
        newsFeedScrollPane.setPreferredSize(new Dimension(260,140));
        userIdTextArea = new JTextArea("Enter a userId");
        userIdTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userIdTextArea.setBackground(greenish);
        tweetTextArea = new JTextArea("Post tweet here...");
        tweetTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tweetTextArea.setBackground(greenish);
        followBtn = new JButton("Follow");
        followBtn.setPreferredSize(new Dimension(200,50));
        followBtn.setBorderPainted(true);
        postBtn = new JButton("Post");
        postBtn.setPreferredSize(new Dimension(200,50));

        currentFollowingsPanel.setBackground(bluish);
        newsFeedPanel.setBackground(bluish);
        this.setBackground(darkbluish);

    }

    /**
     * Sets ups the layout to be use by this user panel
     */
    private void setLayout() {

        currentFollowingsPanel.add(followingScrollPane);
        newsFeedPanel.add(newsFeedScrollPane);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(userIdTextArea)
                                .addComponent(currentFollowingsPanel)
                                .addComponent(tweetTextArea)
                                .addComponent(newsFeedPanel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(followBtn)
                                .addComponent(postBtn)
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(userIdTextArea)
                                .addComponent(followBtn))
                        .addComponent(currentFollowingsPanel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tweetTextArea)
                                .addComponent(postBtn))
                        .addComponent(newsFeedPanel)
        );
    }
}
