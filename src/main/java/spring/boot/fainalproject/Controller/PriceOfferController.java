package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.Model.PriceOffer;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.PriceOfferService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price-offer")
@RequiredArgsConstructor
public class PriceOfferController {
    private final PriceOfferService priceOfferService;

    @GetMapping
    public ResponseEntity getAllPriceOffers(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(priceOfferService.getAllPriceOffers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getPriceOfferById(@AuthenticationPrincipal User user ,@PathVariable Integer id) {
        return ResponseEntity.status(200).body(priceOfferService.getPriceOfferById(user.getId() , id));
    }

    @PostMapping("/create")
    public ResponseEntity createPriceOffer(@AuthenticationPrincipal User user,
                                           @RequestParam Integer recycleId,

                                           @RequestParam Double priceOffer) {
        priceOfferService.createPriceOffer(user.getId(), recycleId, priceOffer);
        return ResponseEntity.status(200).body("PriceOffer created successfully");
    }
    @PutMapping("/approve-priceOffer/{priceOfferId}")
    public ResponseEntity approvePriceOffer(@AuthenticationPrincipal User user ,@PathVariable Integer priceOfferId) {
        priceOfferService.approvePriceOffer(user.getId(), priceOfferId);
        return ResponseEntity.status(200).body("PriceOffer approved successfully");
    }

    @PutMapping("/update")
    public ResponseEntity updatePriceOffer(@AuthenticationPrincipal User user,
                                           @RequestParam Integer priceOfferId,
                                           @RequestParam Double price) {
        priceOfferService.updatePriceOffer(priceOfferId, user.getId(), price);
        return ResponseEntity.status(200).body("PriceOffer updated successfully");
    }

    @DeleteMapping("/delete/{priceOfferId}")
    public ResponseEntity deletePriceOffer(@AuthenticationPrincipal User user,
                                           @PathVariable Integer priceOfferId
    ) {
        priceOfferService.cancelPriceOffer(priceOfferId, user.getId());
        return ResponseEntity.status(200).body("PriceOffer deleted successfully");
    }

    @GetMapping("/approved-offers/{supplierId}")
    public ResponseEntity<List<PriceOffer>> getApprovedOffersBySupplier(@PathVariable Integer supplierId) {
        List<PriceOffer> approvedOffers = priceOfferService.getApprovedOffersBySupplier(supplierId);
        return ResponseEntity.status(200).body(approvedOffers);
    }
    @GetMapping("/pending-offers/facility/{facilityId}")
    public ResponseEntity<List<PriceOffer>> getPendingOffersByFacility(@PathVariable Integer facilityId) {
        List<PriceOffer> pendingOffers = priceOfferService.getPendingOffersByFacility(facilityId);
        return ResponseEntity.status(200).body(pendingOffers);
    }

}
