/*
 * Philippe Carrier 40153985
 * COMP352 Section AA
 * Assignment # 3
 * Sunday, June 14, 2020
 * */
import java.util.Stack;

/**
 * Information about the car
 */
public class Car {

    private String model;
    private Stack<Accident> accidents;

    public Car(String model) {
        this.model = model;
        accidents = new Stack<>();
    }

    /**
     * Get the accident that the car object have
     * @return  The accidents of the car object
     */
    public Stack<Accident> getAccidents() {
        return accidents;
    }

    /**
     * Set the accident of the car object
     * @param accident  An accident of the car object
     */
    public void setAccidents(Accident accident) {
        this.accidents.push(accident);
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", accidents=" + accidents +
                '}';
    }
}
