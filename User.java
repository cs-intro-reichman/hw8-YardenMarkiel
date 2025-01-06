public class User {

    static int maxfCount = 10;
    private String name;
    private String[] follows;
    private int fCount;

    public User(String name) {
        this.name = name;
        follows = new String[maxfCount];
        fCount = 0;
    }

    public User(String name, boolean gettingStarted) {
        this(name);
        follows[0] = "Foo";
        follows[1] = "Bar";
        follows[2] = "Baz";
        fCount = 3;
    }

    public String getName() {
        return name;
    }

    public String[] getfFollows() {
        return follows;
    }

    public int getfCount() {
        return fCount;
    }

    public boolean follows(String name) {
        if (name == null) return false;
        for (int i = 0; i < fCount; i++) {
            if (follows[i] != null && follows[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean addFollowee(String name) {
        if (name == null || fCount == maxfCount || follows(name)) return false;
        follows[fCount] = name;
        fCount++;
        return true;
    }

    public boolean removeFollowee(String name) {
        if (name == null) return false;
        for (int i = 0; i < fCount; i++) {
            if (follows[i] != null && follows[i].equals(name)) {
                follows[i] = follows[fCount - 1];
                follows[fCount - 1] = null;
                fCount--;
                return true;
            }
        }
        return false;
    }

    public int countMutual(User other) {
        if (other == null) return 0;
        int mutualCount = 0;
        for (int i = 0; i < fCount; i++) {
            if (follows[i] == null) continue;
            for (int j = 0; j < other.getfCount(); j++) {
                if (follows[i].equals(other.getfFollows()[j])) {
                    mutualCount++;
                    break;
                }
            }
        }
        return mutualCount;
    }

    public boolean isFriendOf(User other) {
        if (other == null) return false;
        return this.follows(other.getName()) && other.follows(this.name);
    }

    public String toString() {
        String ans = name + " -> ";
        for (int i = 0; i < fCount; i++) {
            ans += follows[i] + " ";
        }
        return ans.trim(); 
    }
}