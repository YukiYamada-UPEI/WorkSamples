import java.util.ArrayList;

/**
 * Represents a User. User is a UserComponent
 * User also implements Subject, Observer, and Acceptor.
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public class User extends UserComponent implements Subject, Observer, Acceptor {
    private String id;
    private ArrayList<String> followers = new ArrayList<>(); //stores followers user ids
    private ArrayList<String> followings = new ArrayList<>();//stores followings user ids
    private ArrayList<Tweet> feedList = new ArrayList<>(); //stores tweets to show on the news feed
    private UserDBMS userDBMS = null;
    private Tweet last; //The last post of this user is kept track


    /**
     * Constructor
     * @param id
     */
    public User(String id) {
        this.id = id;
    }

    /**
     * @return id of this user
     */
    public String getId() {
        return id;
    }

    /**
     * This method concatenate every tweet messages and
     * return them as a single String.
     * @return String concatenated all the messages from the news feed.
     */
    public String getEveryNewsFeed() {
        String everyMsg = "<html>";
        for (Tweet msg : feedList) {
            everyMsg += msg + "<br>";
        }
        everyMsg += "<html/>";
        return everyMsg;
    }

    /**
     * This method concatenate every followings' IDs and
     * return them as a single String.
     * @return String concatenated all user IDs of followings.
     */
    public String getEveryFollowingsString() {
        String users = "<html>";
        for (String userId : followings) {
            users += userId + "<br>";
        }
        users += "<html/>";
        return users;
    }

    /**
     * This method is called when this instance User is followed by
     * another user. This User adds a new follower into the ArrayList
     * @param follower the User who followed you.
     */
    public void getFollowed(String follower){
        followers.add(follower);
    }

    /**
     * Sets a UserDBMS to this instance when it is null.
     * @param userDBMS UserDBMS instance.
     */
    public void setUserDBMS(UserDBMS userDBMS) {
        if (this.userDBMS == null)
            this.userDBMS = userDBMS;
    }

    /**
     * Add a new User in the following list if it is new.
     * @param id the user ID to follow
     * @return true is the user is followed successfully
     */
    public boolean follow(String id) {
        if (userDBMS.doesUserIdExist(id) && !followings.contains(id) && !id.equals(this.id)) {
            followings.add(id); //does not see the news feed before it follows
            System.out.println("Followed " + id);
            User following = userDBMS.getUserFromDatabase(id);//gets a user from the database
            following.getFollowed(this.id);//observer?
            return true;
        } else {
            System.out.println("failed");
            return false;
        }
    }

    /**
     * Creates a new Tweet instance from a given message,
     * adds the instance into the news feed list,
     * and notify all the observers (followings)
     * @param msg
     */
    public void post(String msg) {
        Tweet tweet = Tweet.createTweet(getId(), msg);
        last = tweet; //the last tweet of this user's is kept
        addFeed(tweet);
        userDBMS.addNewTweet(tweet);
        notifyObservers(); //tells all the followers
    }

    /**
     * Adds a Tweet in the news feed list
     * @param tweet a Tweet to add to the list
     */
    public void addFeed(Tweet tweet) {
        feedList.add(tweet);
    }

    /**
     * Update this user News feed
     * @param tweet
     */
    @Override
    public void update(Tweet tweet) {
        addFeed(tweet);
    }

    /**
     * Notifies all followers of this user
     */
    @Override
    public void notifyObservers() {
        for(String followerId: followers ){
            userDBMS.getUserFromDatabase(followerId).update(last);

        }
    }

    /**
     * Adds a new follower
     * @param observer
     */
    @Override
    public void registerObserver(Observer observer) {

    }

    /**
     * Removes an existing follower
     * @param observer
     */
    @Override
    public void removeObserver(Observer observer) {

    }

    /**
     * Debug message to test JTree users
     */
    @Override
    public void print() {
        System.out.print(" \"" + getId() + "\"");
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
