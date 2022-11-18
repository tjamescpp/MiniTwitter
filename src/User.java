import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/*
 * Class: Twitter users
 * Design Patterns: Observer, Composite
 * 
 * Observable
 */

public class User {

    private String tweet;
    private String username;
    private PropertyChangeSupport support;
    private SocialMediaFeed myFeed;
    private ArrayList<User> followers;
    private ArrayList<User> following;
    private ArrayList<String> tweetList;

    public User() {

    }

    public User(String username) {
        setUsername(username);
        tweet = "";
        support = new PropertyChangeSupport(this);
        myFeed = new SocialMediaFeed();
        myFeed.setUser(this);
        followers = new ArrayList<>();
        following = new ArrayList<>();
        tweetList = new ArrayList<>();
        addPropertyChangeListener(myFeed);
    }

    // Adds a property change listener
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void addFollowers(User newFollower) {
        followers.add(newFollower);
    }

    public void addFollowing(User newFollowing) {
        following.add(newFollowing);
        newFollowing.addFollowers(this);
    }

    // fires a property change to all listeners whenever this user posts a tweet
    public void addTweet(String tweet) {
        support.firePropertyChange("tweet", this.tweet, tweet);
        tweetList.add(tweet);
        setTweet(tweet);
    }

    // getters and setter

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public SocialMediaFeed getMyFeed() {
        return myFeed;
    }

    public void setMyFeed(SocialMediaFeed myFeed) {
        this.myFeed = myFeed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public ArrayList<User> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }

    // may need a method to add user to jtree

}
