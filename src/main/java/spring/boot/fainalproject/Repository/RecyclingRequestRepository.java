package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.RecyclingRequest;

import java.util.List;

@Repository
public interface RecyclingRequestRepository extends JpaRepository<RecyclingRequest, Integer> {
    RecyclingRequest findRecyclingRequestById(Integer id);

    @Query("SELECT r FROM RecyclingRequest r WHERE r.price_offer.id = :priceOfferId")
    RecyclingRequest findByPriceOfferId(@Param("priceOfferId") Integer priceOfferId);


    // Custom query to find all recycling requests by supplier ID
    @Query("SELECT r FROM RecyclingRequest r WHERE r.supplier_recycle.id = :supplierId")
    List<RecyclingRequest> findRecyclingRequestsBySupplierId(Integer supplierId);

}
