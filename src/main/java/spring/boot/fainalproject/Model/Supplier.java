package spring.boot.fainalproject.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "username cannot be empty")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    @NotBlank(message = "Password cannot be null")
    @Column(columnDefinition = "varchar(300) not null")
    private String password;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Must be a valid email format")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String email;

    @NotNull(message = "commercial Register shouldn't be null")
    @Column(columnDefinition = "int not null unique")
    private int commercialRegister;

    @NotNull(message = "license Number shouldn't be null")
    @Column(columnDefinition = "int not null unique")
    private int licenseNumber;

    @Column(columnDefinition = "varchar(10) not null unique")
    @Pattern(regexp = "^05\\d{8}$",message = "Phone number most be as 05XXXXXXXX")
    private String phoneNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    // One-to-many relationship with Offer
    @OneToMany(mappedBy = "supplier")
    private Set<Offer> offers;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "supplier_recycle")
    private Set<RecyclingRequest> recyclingRequests;
//
//    // One-to-many relationship with Recycling
//    @OneToMany(mappedBy = "supplier")
//    private Set<RecyclingRequest> recyclingRequest;
}
