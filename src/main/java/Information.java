import java.sql.Blob;

public class Information {
    String name;
    String description;
    String country;
    String sector;
    String industry;
    String currency;
    String website;
    Blob logo;

    public Information(String name, String description, String country, String sector, String industry, String currency, String website, Blob logo){
        this.name = name;
        this.description = description;
        this.country = country;
        this.sector = sector;
        this.industry = industry;
        this.currency = currency;
        this.website = website;
        this.logo = logo;
    }
}
