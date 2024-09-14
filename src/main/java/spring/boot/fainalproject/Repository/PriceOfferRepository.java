package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.PriceOffer;

import java.util.List;

@Repository
public interface PriceOfferRepository extends JpaRepository<PriceOffer, Integer> {
    PriceOffer findPriceOfferById(Integer id);

    @Query("SELECT COUNT(po) FROM PriceOffer po JOIN po.suppliers s WHERE s.id = :supplierId AND po.status = 'APPROVED'")
    int countApprovedPriceOffersBySupplier(@Param("supplierId") Integer supplierId);


}
