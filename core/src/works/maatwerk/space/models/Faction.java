package works.maatwerk.space.models;

public class Faction {
    private final String id;
    private final String name;
    private float tax;
    private final String icon;
    private final String flag;

    public Faction(String id, String name, float tax, String icon, String flag) {
        this.id = id;
        this.name = name;
        this.tax = tax;
        this.icon = icon;
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getTax() {
        return tax;
    }

    public String getIcon() {
        return icon;
    }

    public String getFlag() {
        return flag;
    }
}
