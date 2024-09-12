package spring.boot.fainalproject.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_product")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "product name can not be null")
    @Column(columnDefinition = "varchar(100) not null")
    private String productName;

    @Column(columnDefinition = "int not null")
    @Positive(message = "Enter positive number")
    private int quantity;

    @NotEmpty(message = "Total amount can not be null")
    @Column(columnDefinition = "double not null")
    private double totalAmount;

    @NotEmpty(message = "shipping can not be null")
    @Column(columnDefinition = "varchar(50) not null")
    // pattr..
    private String shippingMethod;

    private String imgURL;

    @ManyToOne
    @JsonIgnore
    private Facility facility_orders;

    @ManyToOne
    @JsonIgnore
    private Customer customer_orders;

    @ManyToMany
    @JsonIgnore
    private Set<Product> products;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
    private Set<Review> reviews;


}