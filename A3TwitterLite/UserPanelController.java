import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;

/**
 * UserPanelController implements ActionListener and FocusListener.
 * This class is instantiated when a AdminController opens an individual user view.
 * UserPanelController has a corresponding User and UserPanel as its instance variables.
 * This class accounts for following and tweeting.
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public class UserPanelController implements ActionListener, Acceptor {

    private String userId;
    private User user;
    private UserPanel userPanel;

    private final String FOLLOW_BUTTON = "Follow";
    private final String POST_BUTTON = "Post";

    /**
     * Constructor
     * @param userPanel
     * @param user
     */
    public UserPanelController(UserPanel userPanel, User user) {
        this.user = user;
        this.userId = user.getId();
        this.userPanel = userPanel;
        this.userPanel.setController(this);
        // Creates a listener for each focus needed
        this.userPanel.setFocusFollowListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                userPanel.focusUserId();
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (userPanel.getUserIdTextArea().equals(""))
                    userPanel.unfocusUserId();
            }
        });
        this.userPanel.setFocusTweetListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                userPanel.focusTweet();
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (userPanel.getTweetTextArea().equals(""))
                    userPanel.unfocusTweer();
            }
        });

        startTimer();
    }

    /**
     * Timer to refresh the user panel screen every second
     */
    public void startTimer() {
        class TimerListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                userPanel.updateNewsFeed(user.getEveryNewsFeed());
                userPanel.updateFollowings(user.getEveryFollowingsString());
            }
        }

        Timer t = new Timer(1000 , new TimerListener() );
        t.start();
    }

    /**
     * UserPanel listener actions
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e){
        String source = e.getActionCommand();

        switch (source) {
            case (FOLLOW_BUTTON):
                followPressed();
                userPanel.unfocusUserId();
                break;

            case (POST_BUTTON):
                postPressed();
                break;
        }
    }

    /**
     * Helper method used in actionPerformed
     */
    public void followPressed(){
        user.follow(userPanel.getUserId());
        userPanel.updateFollowings(user.getEveryFollowingsString());
        userPanel.unfocusUserId();
    }

    /**
     * Helper method used in actionPerformed
     */
    public void postPressed(){
        user.post(userPanel.getTweetToPost());
        userPanel.updateNewsFeed(user.getEveryNewsFeed());
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
