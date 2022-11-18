public class App {
    // practice driver class for testing only
    public static void main(String[] args) throws Exception {
        User sally = new User("sally");
        User john = new User("john");
        User bob = new User("bob");
        UserGroup newGroup = new UserGroup("Group 1");
        UserGroup newGroup2 = new UserGroup("Group 2");
        UserTotal userTotal = new UserTotal();
        GroupTotal groupTotal = new GroupTotal();
        MessageTotal messageTotal = new MessageTotal();
        PositiveMessage positiveMessage = new PositiveMessage(messageTotal);

        // test add users to group
        newGroup.addUser(sally);
        newGroup.addUser(bob);

        // test add group to grouop
        newGroup2.addUser(newGroup2);

        // test add users to user total
        userTotal.addUser(sally);
        userTotal.addUser(john);
        userTotal.addUser(bob);

        // test add group to user group total
        groupTotal.addGroup(newGroup);

        // test add followers
        sally.addFollowers(bob);
        bob.addFollowers(sally);

        // test add following
        bob.addFollowing(sally);
        bob.addFollowing(john);

        sally.addFollowing(bob);
        sally.addFollowing(john);

        john.addFollowing(bob);
        john.addFollowing(sally);

        // add listeners to news feed
        sally.addPropertyChangeListener(bob.getMyFeed());
        john.addPropertyChangeListener(bob.getMyFeed());
        bob.addPropertyChangeListener(sally.getMyFeed());

        // test posting tweets
        sally.addTweet("Today was a good day");
        messageTotal.addMessage(sally.getTweet());

        john.addTweet("I had a great day today");
        messageTotal.addMessage(john.getTweet());

        bob.addTweet("That sandwhich was ok");
        messageTotal.addMessage(bob.getTweet());

        // test news feed
        bob.getMyFeed().printTweets();
        // sally.getMyFeed().printTweets();

        // test admin analytics
        // System.out.println("User total: ");
        // userTotal.accept(new UserVisitor());

        // System.out.println("Group total: ");
        // groupTotal.accept(new UserVisitor());

        // System.out.println("Total messages: ");
        // messageTotal.accept(new UserVisitor());

        positiveMessage.findPositiveWords(messageTotal.getMessageList());
        System.out.println("Total positive messages: ");
        positiveMessage.accept(new UserVisitor());
    }
}
