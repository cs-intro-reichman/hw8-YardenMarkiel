/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network with some users. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    public User getUser(String name) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().toLowerCase().equals(name.toLowerCase())) {
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network. */
    public boolean addUser(String name) {
        if (userCount == users.length) return false;
        if (getUser(name) != null) return false;
        User newone = new User(name);
        users[userCount] = newone;
        userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. */
    public boolean addFollowee(String name1, String name2) {
        if (name1 == null || name2 == null) return false; 
        if (name1.equalsIgnoreCase(name2)) return false; 
        User user1 = getUser(name1);
        User user2 = getUser(name2);
        if (user1 == null || user2 == null) return false; 
        return user1.addFollowee(name2);
    }

    /** For the user with the given name, recommends another user to follow. */
    public String recommendWhoToFollow(String name) {
        if (!isInNet(name)) return null;
        int maxfollowee = 0;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(name)) {
                index1 = i;
                break;
            }
        }
        for (int i = 0; i < userCount; i++) {
            if (i == index1) continue;
            if (users[i].countMutual(users[index1]) > maxfollowee) {
                maxfollowee = users[i].countMutual(users[index1]);
                index2 = i;
            }
        }
        if (maxfollowee == 0) return null;  // No recommendation
        return users[index2].getName();
    }

    /** Computes and returns the name of the most popular user in this network. */
    public String mostPopularUser() {
        int counter, max = 0, index = -1;
        for (int i = 0; i < userCount; i++) {
            counter = 0;
            for (int j = 0; j < userCount; j++) {
                if (i == j || users[j] == null) continue;
                if (users[j].follows(users[i].getName())) counter++;
            }
            if (counter > max) {
                max = counter;
                index = i;
            }
        }
        if (index == -1) {
            return null;  // No popular user found
        } else {
            return users[index].getName();
        }
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. */
    private int followeeCount(String name) {
        int followcount = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i] != null && users[i].follows(name)) {
                followcount++;
            }
        }
        return followcount;
    }

    /** Checks if a user is in the network. */
    public boolean isInNet(String name) {
        for (int i = 0; i < userCount; i++) {
            if (users[i] != null && users[i].getName().equals(name)) return true;
        }
        return false;
    }

    /** Returns a textual description of all the users in this network, and who they follow. */
    public String toString() {
        String result = "Network:";
        for (int i = 0; i < userCount; i++) {
            result += "\n" + users[i].getName() + " ->";
            if (users[i].getfCount() > 0) {
                for (int j = 0; j < users[i].getfCount(); j++) {
                    result += " " + users[i].getfFollows()[j];
                }
                result += " ";
            }
        }
        return result;
    }
}