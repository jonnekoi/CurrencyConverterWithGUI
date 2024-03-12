package entity;

import jakarta.persistence.*;
@Entity
@Table(name="CURRENCIES")
public class currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="abbreviation")
    private String abbreviation;
    @Column(name="name")
    private String name;
    private double conversion_rate;

    public currency(String abbreviation, String name, double conversion_rate){
        super();
        this.abbreviation = abbreviation;
        this.name = name;
        this.conversion_rate = conversion_rate;
    }
    public currency(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getConversion_rate() {
        return conversion_rate;
    }

    public void setConversion_rate(double conversion_rate) {
        this.conversion_rate = conversion_rate;
    }
}
