import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/*
 * Class: A class for twitters users' news feed
 * Design Patterns: Observer
 * 
 * Observer
 */

public class SocialMediaFeed implements PropertyChangeListener {

    private ArrayList<String> tweets;
    private User user;
    // private Users tweetFromUser;

    public SocialMediaFeed() {
        tweets = new ArrayList<String>();
    }

    public void printTweets() {
        System.out.println("News Feed:");
        tweets.forEach(System.out::println);
    }

    // listens for property changes from users and adds their tweets
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setUser((User) evt.getSource());
        tweets.add((String) evt.getNewValue());
    }

    // getters and setters

    public ArrayList<String> getTweets() {
        return tweets;
    }

    public void setTweets(ArrayList<String> tweets) {
        this.tweets = tweets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // public Users getTweetFromUser() {
    // return tweetFromUser;
    // }

    // public void setTweetFromUser(Users tweetFromUser) {
    // this.tweetFromUser = tweetFromUser;
    // }

}
