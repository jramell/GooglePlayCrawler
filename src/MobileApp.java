import java.util.ArrayList;

public class MobileApp {

    private String title;
    private String description;
    private String numberOfRatings;
    private String rating;
    private String recentChanges;
    private String numberOf5StarRatings;
    private String numberOf4StarRatings;
    private String link;

    public void addRecentChange(String recentChange)
    {
        recentChanges = "";
        recentChanges.concat(recentChange);
        recentChanges.concat("\n");
    }

    public String toString()
    {
        String rta = "";
        rta += title + "\n";
        rta += "Description:\n" + description + "\n \n";
        rta += "Scored " + rating + " from " + numberOfRatings + " ratings\n";
        rta += "5 star ratings: " + numberOf5StarRatings + "\n";
        rta += "4 star ratings: " + numberOf4StarRatings + "\n \n";
        rta += "What's new: \n \n" + ((recentChanges != null)? recentChanges : "");
        return rta;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(String numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRecentChanges() {
        return recentChanges;
    }

    public void setRecentChanges(String recentChanges) {
        this.recentChanges = recentChanges;
    }

    public String getNumberOf5StarRatings() {
        return numberOf5StarRatings;
    }

    public void setNumberOf5StarRatings(String numberOf5StarRatings) {
        this.numberOf5StarRatings = numberOf5StarRatings;
    }

    public String getNumberOf4StarRatings() {
        return numberOf4StarRatings;
    }

    public void setNumberOf4StarRatings(String numberOf4StartRatings) {
        this.numberOf4StarRatings = numberOf4StartRatings;
    }
}
