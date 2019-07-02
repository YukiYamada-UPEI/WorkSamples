import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class models a database of the all information of this Twitter application.
 * All Users and UserGroups are stored in a HashMap, and all the Tweet instances are stored in a ArrayList.
 * This class keeps the track of the number of Users, Groups, Tweet and the positive messages.
 * You can ask to retrieve information from this UserDatabase.

 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public class UserDatabase {
    private static UserDatabase instance = null;
    private int numOfUsers;  //number of total users
    private int numOfGroups; //number of total groups
    private int numOfTweets; //number of total tweets
    private int numOfPositiveMsg; //number of total positive messages
    private ArrayList<Tweet> allTweets = new ArrayList<>();
    private HashMap<String, UserComponent> allUsersAndGroupsMap = new HashMap<>();
    private PositiveWordList positiveList = new PositiveWordList();

    /**
     * Private constructor
     */
    private UserDatabase(){

    }

    /**
     * This methods takes a new User and a UserGroup.
     * and adds the User into the HashMap
     *
     * @param id the user id of the new User to be added
     * @param user the new User
     * @param group the UserGroup to which the new User to be added
     * @return true if the User is successfully added
     */
    public boolean addUser(String id, UserComponent user, UserGroup group) {
        if (doesIdExist(id)) {
            System.out.println("User name already exist");
            return false;
        } else {
            allUsersAndGroupsMap.put(id, user);
            numOfUsers++;
            group.addUserComponent(user);
            System.out.printf("User '%s' successfully added!\n", id);
            group.getTreeNode().add(user.getTreeNode());//add JTree
            return true;
        }
    }

    /**
     * Find a user reference from the map with the given key
     *
     * @param id the user id for the user you want
     * @return the User mapped with the given key, if does not exist, return null
     */
    public User getUserFromDatabase(String id) {
        UserComponent user = allUsersAndGroupsMap.get(id);//no user found
        if (isUser(user))
            return (User) user;
        return null;
    }

    /**
     * Find a user group reference from the map with the given key
     *
     * @param id the group id of the UserGroup you wnat
     * @return the UserGroup mapped with the given key, if does not exist, return null
     */
    public UserGroup getGroupFromDatabase(String id) {
        UserComponent group = allUsersAndGroupsMap.get(id);//no user found
        if (isGroup(group))
            return (UserGroup) group;
        return null;
    }

    /**
     * Adds a root UserGroup to the HashMap
     * @param root the root UserGroup.
     */
    public void addRoot(UserGroup root) {
        allUsersAndGroupsMap.put(root.getId(), root);
        numOfGroups++;
    }

    /**
     * Adds a UserGroup to the database if there is no same id
     * @param id the id of the UserGroup to add
     * @param group the UserGroup to add
     * @param parentGroup the parent UserGroup to which the new UserGroup to be added
     * @return true if the UserGroup is successfully added to the HashMap
     */
    public boolean addGroup(String id, UserComponent group, UserGroup parentGroup) {
        if (doesIdExist(id)) {
            System.out.println("Id already exists");
            return false;
        } else {
            allUsersAndGroupsMap.put(id, group);
            numOfGroups++;
            parentGroup.addUserComponent(group);
            System.out.printf("Group '%s' successfully added!\n", id);
            parentGroup.getTreeNode().add(group.getTreeNode());//add JTree
            return true;
        }
    }

    /**
     * Adds a Tweet to the list of Tweets.
     * And checks if the Tweet contains a positive word
     * @param tweet a Tweet
     */
    public void addTweet(Tweet tweet) {
        allTweets.add(tweet);
        numOfTweets++;
        incrementPositive(tweet);
    }

    /**
     * Checks the given Tweet contains a positive word,
     * and if true, increments a counter.
     * @param tweet a Tweet to be evaluated
     */
    public void incrementPositive(Tweet tweet){
        if(positiveList.isPositive(tweet.getMsg())){
            numOfPositiveMsg++;
        }
    }

    /**
     * Checks if the id is already in the HashMap
     * @param id the give id to check
     * @return true if a given key (id) is in the map
     */
    private boolean doesIdExist(String id) {
        UserComponent user = allUsersAndGroupsMap.get(id);
        if (user == null)
            return false;
        return true;
    }

    /**
     * Checks if a given group id is already in the HashMap,
     * and return the UserGroup reference if it exists
     * @param groupId user group to be checked
     * @return GroupUser of the given key
     */
    public UserGroup findGroup(String groupId) {
        UserComponent group = null;
        if (doesIdExist(groupId)) {
            group = allUsersAndGroupsMap.get(groupId);
        }
        if (group instanceof UserGroup) {   //It can potentially be a User
            return (UserGroup) group;
        } else {
            return null;
        }
    }

    /**
     * Checks if the given UserComponent is a User
     * @param user UserComponent
     * @return true if the given UserComponent is a instance of User
     */
    public boolean isUser(UserComponent user) {
        if (user instanceof User)
            return true;
        return false;
    }

    /**
     * Checks if the given UserComponent is a UserGroup
     * @param group UserComponent
     * @return true if the given UserComponent is a instance of UserGroup
     */
    public boolean isGroup(UserComponent group) {
        if (group instanceof UserGroup)
            return true;
        return false;
    }


    /**
     * @return the total number of users
     */
    public int getNumOfUsers() {
        return numOfUsers;
    }

    /**
     * @return the total number of groups
     */
    public int getNumOfGroups() {
        return numOfGroups;
    }

    /**
     * @return the total number of tweets
     */
    public int getNumOfTweets() {
        return numOfTweets;
    }

    /**
     * @return the total number of tweets containing any positive word
     */
    public int getNumOfPositiveMsg() {
        return numOfPositiveMsg;
    }

    /**
     * @return the percentage of the positive tweets
     */
    public float getPercentOfPositiveMsg(){
        return ((float)numOfPositiveMsg/(float)numOfTweets);
    }

    /**
     * Singleton method to restrict the creation of this class just once
     * @return a new UserDatabase if it is not previously created
     */
    public static UserDatabase getInstance() {
        if (instance == null)
            return new UserDatabase();
        return instance;

    }
}
