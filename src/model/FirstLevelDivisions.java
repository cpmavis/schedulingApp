package model;
/**
 * Model of the FirstLevelDivisions
 * @author Corey
 */
public class FirstLevelDivisions {
    /**
     * ID of the division
     */
    private int divisionID;
    /**
     * Name of the division
     */
    private String division;
    /**
     * ID of the country
     */
    private int countryID;

    /**
     * FirstLevelDivisions Constructor
     * @param divisionID ID of the divisono
     * @param division name of the division
     * @param countryID ID of the country
     */
    public FirstLevelDivisions(int divisionID, String division, int countryID){
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * get division ID
     * @return getDivisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * division ID
     * @param divisionID division ID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * get division
     * @return getDivision
     */
    public String getDivision() {
        return division;
    }

    /**
     * division
     * @param division division
     */
    public void setDivision(String division) {
        this.division = division;
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

}
