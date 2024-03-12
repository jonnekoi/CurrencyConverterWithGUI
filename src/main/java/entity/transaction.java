package entity;
import jakarta.persistence.*;
@Entity
@Table(name="TRANSACTIONS")
public class transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="fromCur")
    private String fromCur;
    @Column(name="toCur")
    private String toCur;
    private double amount;
    private double result;

    public transaction(String fromCur, String toCur, double amount, double result){
        super();
        this.fromCur = fromCur;
        this.toCur = toCur;
        this.amount = amount;
        this.result = result;
    }

    public transaction(){
    }
    public void setFromCur(String fromCur) {
        this.fromCur = fromCur;
    }

    public void setToCur(String toCur) {
        this.toCur = toCur;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setResult(double result){
        this.result = result;
    }
}
