import java.util.HashMap;
import java.util.Map;

public class IndexOfHappiness {
    private final String country;
    private final String region;
    private final int happinessRank;
    private final double happinessScore;
    private final double whiskerHigh;
    private final double whiskerLow;
    private final double economy;
    private final double family;
    private final double health;
    private final double freedom;
    private final double generosity;
    private final double trustGovernment;
    private final double dystopiaResidual;

    public IndexOfHappiness(Map<String, String> values){
        this.country = values.get("country");
        this.region = values.get("region");
        this.happinessRank = Integer.parseInt(values.get("happinessRank"));
        this.happinessScore = Double.parseDouble(values.get("happinessScore"));
        this.whiskerHigh = Double.parseDouble(values.get("whiskerHigh"));
        this.whiskerLow = Double.parseDouble(values.get("whiskerLow"));
        this.economy = Double.parseDouble(values.get("economy"));
        this.family = Double.parseDouble(values.get("family"));
        this.health = Double.parseDouble(values.get("health"));
        this.freedom = Double.parseDouble(values.get("freedom"));
        this.generosity = Double.parseDouble(values.get("generosity"));
        this.trustGovernment = Double.parseDouble(values.get("trustGovernment"));
        this.dystopiaResidual = Double.parseDouble(values.get("dystopiaResidual"));
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public int getHappinessRank() {
        return happinessRank;
    }

    public double getHappinessScore() {
        return happinessScore;
    }

    public double getWhiskerLow() {
        return whiskerLow;
    }

    public double getEconomy() {
        return economy;
    }

    public double getFamily() {
        return family;
    }

    public double getFreedom() {
        return freedom;
    }

    public double getGenerosity() {
        return generosity;
    }

    public double getHealth() {
        return health;
    }

    public double getDystopiaResidual() {
        return dystopiaResidual;
    }

    public double getTrustGovernment() {
        return trustGovernment;
    }

    public double getWhiskerHigh() {
        return whiskerHigh;
    }
}
