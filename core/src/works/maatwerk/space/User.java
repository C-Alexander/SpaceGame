package works.maatwerk.space;

public class User {
    private final String username;
    private String password;
    private Float credits;
    private String token;

    public User(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public User(String username, String password, Float credits, String token) {
        this.username = username;
        this.password = password;
        this.credits = credits;
        this.token = token;
    }

    public User(String username) {
        System.out.println(username);
        this.username = username;
    }

    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public Float getCredits() {
        return credits;
    }

    public void setCredits(Float credits) {
        this.credits = credits;
    }
}