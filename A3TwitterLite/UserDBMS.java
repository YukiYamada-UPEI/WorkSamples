/**
 * This class allows Users to query the UserDatabase.
 * User class does not have a direct reference to UserDatabase, but UserDBMS.
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public class UserDBMS {

    private static UserDBMS instance = null;
    private UserDatabase userDatabase; // the main database

    /**
     * Constructor
     * @param userDatabase user Database which this DBMS communicates with
     */
    private UserDBMS(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    /**
     * Returns true if a User with a given user id exists in the database
     * @param userId the user id to look for
     * @return true if a User exists in the database
     */
    public boolean doesUserIdExist(String userId) {
        User user = userDatabase.getUserFromDatabase(userId);
        if (user == null) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if a UserGroup with a given group id exists in the database
     * @param userId the group id to look for
     * @return true if a GroupUser exists in the database
     */
    public User getUserFromDatabase(String userId){
        User user = userDatabase.getUserFromDatabase(userId);
        if (user == null) {
            return null;
        }
        return user;
    }

    /**
     * Adds a Tweet to the database
     * @param tweet a Tweet instance
     */
    public void addNewTweet(Tweet tweet){
        userDatabase.addTweet(tweet);
    }

    /**
     * Singleton method
     * @param database is sent to the UserDBMS constructor
     * @return a new UserDBMS instance if it is null, otherwise returns itself.
     */
    public static UserDBMS getInstance(UserDatabase database) {
        if (instance == null)
            return new UserDBMS(database);
        return instance;
    }
}
