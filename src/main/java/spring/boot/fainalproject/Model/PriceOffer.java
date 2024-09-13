package spring.boot.fainalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PriceOffer")
public class PriceOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = " Price cannot be null" )
    @Column(columnDefinition = "int not null")
    private double price;

    @NotBlank(message = "status cannot be null")
    @Column(columnDefinition = "varchar(10) not null")
    @Pattern(regexp = "PENDING|APPROVED|REJECTED|CANCELED", message = "Status must be either PNDING|APPROVED|REJECTED|CANCELED")
    private String status;

    @OneToOne
    @MapsId
    @JoinColumn(name = "recyclingRequest")
    private RecyclingRequest recyclingRequest;

    @OneToOne
    @JoinColumn(name = "offer_id", referencedColumnName = "id")
    @JsonIgnore
    private Offer offer;
}