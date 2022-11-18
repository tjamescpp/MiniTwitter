import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * The UI for user view
 * Design Pattern: Singleton pattern
 */

public class UserViewPanel extends JFrame implements ActionListener {

    // labels
    private JLabel userIdLabel;
    private JLabel followingLabel;
    private JLabel tweetLabel;
    private JLabel newsFeedLabel;
    private JLabel userIdLabel2;

    // texts
    private JTextArea tweetMessage;
    private JTextField userIdTextField;

    // buttons
    private JButton postTweet;
    private JButton followUser;

    // lists
    private JList<String> currentFollowing;
    private JList<String> newsFeed;
    private ArrayList<String> twitterFeed;

    // panels
    private JPanel userViewPanel;
    private JPanel topPanel;

    // class objects
    private static MainFrame mainFrame;
    private SocialMediaFeed userFeed;
    private User user;
    private AdminControlPanel admin;
    private MessageTotal messageTotal;

    public UserViewPanel() {
        initComponents();
    }

    private void initComponents() {

        Random randInt = new Random();
        int n = randInt.nextInt(500);
        twitterFeed = new ArrayList<>();

        this.setTitle("User View");
        this.pack();
        this.setBounds(n, n, 600, 400);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        // user view panel properties
        userViewPanel = new JPanel();
        userViewPanel.setBounds(0, 0, 600, 400);
        userViewPanel.setBackground(Color.gray);
        userViewPanel.setLayout(null);
        userViewPanel.setVisible(true);

        // init objects
        userFeed = new SocialMediaFeed();
        messageTotal = new MessageTotal();
        user = new User();

        // init components
        userIdLabel = new JLabel();
        userIdLabel2 = new JLabel("User ID: ");
        followingLabel = new JLabel("Following");
        tweetLabel = new JLabel("What's on your mind?");
        newsFeedLabel = new JLabel("News Feed");

        tweetMessage = new JTextArea();
        userIdTextField = new JTextField();

        followUser = new JButton("Follow");
        postTweet = new JButton("Post Tweet");

        currentFollowing = new JList<String>();
        newsFeed = new JList<String>();

        // JLabels
        followingLabel.setBounds(15, 55, 75, 20);
        tweetLabel.setBounds(15, 180, 150, 20);
        newsFeedLabel.setBounds(15, 255, 100, 20);
        userIdLabel.setBounds(15, 15, 150, 20);
        userIdLabel.setFont(new Font("Serif", Font.BOLD, 18));
        userIdLabel.setForeground(Color.WHITE);
        userIdLabel2.setBounds(300, 15, 75, 20);
        userIdLabel2.setForeground(Color.white);

        // JLists
        currentFollowing.setBounds(15, 75, 570, 100);
        newsFeed.setBounds(15, 275, 570, 75);
        // newsFeed.setAutoscrolls(true);
        // addToNewsFeed();

        // JTextArea
        tweetMessage.setBounds(15, 200, 350, 50);
        tweetMessage.setLineWrap(true);

        userIdTextField.setBounds(350, 15, 80, 20);
        userIdTextField.setText("@");

        // JButtons
        postTweet.setBounds(450, 200, 80, 20);
        postTweet.addActionListener(this);

        followUser.setBounds(450, 15, 80, 20);
        followUser.addActionListener(this);

        // JPanels
        topPanel = new JPanel();
        topPanel.setBounds(0, 0, 600, 50);
        topPanel.setBackground(Color.darkGray);
        topPanel.setLayout(null);

        // add components
        topPanel.add(userIdLabel);
        topPanel.add(userIdLabel2);
        topPanel.add(userIdTextField);
        topPanel.add(followUser);

        userViewPanel.add(topPanel);
        userViewPanel.add(followingLabel);
        userViewPanel.add(currentFollowing);
        userViewPanel.add(tweetLabel);
        userViewPanel.add(tweetMessage);
        userViewPanel.add(postTweet);
        userViewPanel.add(newsFeedLabel);
        userViewPanel.add(newsFeed);

        this.add(userViewPanel);
    }

    public void addToNewsFeed(SocialMediaFeed feed) {

        String[] tweets = new String[0];
        int size = feed.getTweets().size();

        twitterFeed.add(feed.getTweets().get(size - 1));

        for (int i = 0; i < twitterFeed.size(); i++) {
            // twitterFeed.add(feed.getTweets().get(size - 1));
            System.out.println(twitterFeed.get(i));
        }

        tweets = new String[twitterFeed.size()];

        // for (int i = 0; i < tweets.length; i++) {
        // tweets[i] = feed.getTweets().get(i);
        // System.out.println(tweets[i]);
        // }

        for (int i = 0; i < twitterFeed.size(); i++) {
            tweets[i] = twitterFeed.get(i);
        }

        newsFeed.setListData(tweets);
    }

    public void addToCurrentFollowing() {
        int size = user.getFollowing().size();
        ArrayList<User> username = user.getFollowing();
        String[] following = new String[size];

        for (int i = 0; i < size; i++) {
            following[i] = "- " + username.get(i).getUsername();
        }

        currentFollowing.setListData(following);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == postTweet) {
            System.out.println("Post Tweet Button Clicked");
            postTweet();
            updateFollowersFeed(tweetMessage.getText());

        } else if (e.getSource() == followUser) {
            System.out.println("Follow Button Clicked");
            System.out.println("Following: " + userIdTextField.getText());

            User following = new User();
            following = admin.findUser(userIdTextField.getText());

            System.out.println("Found: " + following.getUsername());

            addFollowing(following);
            // updateFollowersFeed();

            userIdTextField.setText("@");

        }
    }

    private void addFollowing(User following) {
        user.getFollowing().add(following);
        following.getFollowers().add(user);
        following.addPropertyChangeListener(user.getMyFeed());
        addToCurrentFollowing();

        following.getMyFeed().printTweets();
    }

    private void postTweet() {
        user.addTweet(user.getUsername() + ": " + tweetMessage.getText());
        messageTotal.addMessage(user.getTweet());
        admin.getMessageTotal().addMessage(user.getTweet());
        addToNewsFeed(userFeed);

        userFeed.printTweets();

        tweetMessage.setText("");
    }

    private void updateFollowersFeed(String tweet) {
        for (int i = 0; i < user.getFollowers().size(); i++) {
            if (user.getFollowers().get(i) == admin.getUserPanels().get(i).getUser()) {
                admin.getUserPanels().get(i).getTwitterFeed().add(tweet);
                admin.getUserPanels().get(i).addToNewsFeed(user.getFollowers().get(i).getMyFeed());
            }
        }
    }

    public void setUserId(String userId) {
        this.userIdLabel.setText(userId);
    }

    public User getTwitterUser() {
        return user;
    }

    public void setTwitterUser(User twitterUser) {
        this.user = twitterUser;
        twitterUser.addPropertyChangeListener(userFeed);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame newMainFrame) {
        mainFrame = newMainFrame;
    }

    public SocialMediaFeed getFeed() {
        return userFeed;
    }

    public void setFeed(SocialMediaFeed feed) {
        this.userFeed = feed;
    }

    public AdminControlPanel getAdmin() {
        return admin;
    }

    public void setAdmin(AdminControlPanel admin) {
        this.admin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<String> getTwitterFeed() {
        return twitterFeed;
    }

    public void setTwitterFeed(ArrayList<String> twitterFeed) {
        this.twitterFeed = twitterFeed;
    }

}
