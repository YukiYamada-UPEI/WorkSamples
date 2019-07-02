/**
 * This class models a Tweet. Tweet has a user ID of its creator and the massage as a String.
 * Tweet can only be created by calling static createTweet method
 * by sending a user id and a massage as parameters.
 *
 * @author Yuki Yamada
 * @author Aleix Molla
 * @version 2018/03/30
 */
public class Tweet {
    private String userId;
    private String msg;

    /**
     * Constructor
     * @param userId
     * @param msg
     */
    private Tweet(String userId, String msg) {
        this.userId = userId;
        this.msg = msg;
    }

    /**
     * @return the String message of this Tweet
     */
    public String getMsg() {
        return msg;
    }

    /**
     * A new Tweet can only be created by calling this static method.
     * @param userId user id of the Tweet
     * @param msg the message of the Tweet
     * @return a created instance of a Tweet
     */
    public static Tweet createTweet (String userId, String msg){
        return new Tweet(userId, msg);
    }

    /**
     * @return a description of the class
     */
    public String toString(){
        return userId + ":  " + msg;
    }

}
