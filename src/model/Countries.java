package model;
/**
 * Model of the Countries
 * @author Corey
 */
public class Countries {
    /**
     * ID of the country
     */
    private int countryID;
    /**
     * Name of the country
     */
    private String country;

    /**
     * Countries constructor
     * @param countryID ID of the country
     * @param country Name of the country
     */
    public Countries(int countryID, String country){
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * get country ID
     * @return getCountryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * country ID
     * @param countryID country ID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * get country
     * @return getCountry
     */
    public String getCountry() {
        return country;
    }

    /**
     * country
     * @param country country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
