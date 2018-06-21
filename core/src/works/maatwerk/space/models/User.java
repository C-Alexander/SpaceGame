package works.maatwerk.space.models;

public class User {
    private final String username;
    private String password;
    private Float credits;
    private String token;
    private String factionId;
    private Faction faction;

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

    public User(String username, String password, Float credits, String token, String factionId) {
        this.password = password;
        this.credits = credits;
        this.token = token;
        this.factionId = factionId;
        this.username = username;
    }

    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public User(String username) {

        this.username = username;
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

    public String getFactionId() {
        return factionId;
    }

    public void setFactionId(String factionId) {
        this.factionId = factionId;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public Faction getFaction() {
        return faction;
    }
}
