/*
 * Interface for the visitor pattern
 * 
 * Handles the analytics for the admin panel to retrieve data
 */

public interface AdminAnalytics {
    public void accept(Visitor visitor);
}
