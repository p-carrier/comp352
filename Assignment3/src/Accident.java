/*
 * Philippe Carrier 40153985
 * COMP352 Section AA
 * Assignment # 3
 * Sunday, June 14, 2020
 * */
import java.util.Date;

/**
 * Information about accident of car.
 */
public class Accident {

    private final Date date;
    private String description;

    public Accident(Date date, String description) {
        this.date = date;
        this.description = description;
    }

    /**
     * Get the date of the accident
     * @return  The date of the accident
     */
    public Date getDate() {
        return date;
    }

    /**
     * Get the description of the accident
     * @return  the description of the accident
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the accident
     * @param description   The description of the accident
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * View the values of the accident object
     * @return  The values of the accident object
     */
    @Override
    public String toString() {
        return "Accident{" +
                "date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
