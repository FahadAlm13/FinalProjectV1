package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.Model.PriceOffer;
import spring.boot.fainalproject.Service.PriceOfferService;

@RestController
@RequestMapping("/api/v1/price-offer")
@RequiredArgsConstructor
public class PriceOfferController {
    private final PriceOfferService priceOfferService;

    @GetMapping
    public ResponseEntity getAllPriceOffers() {
        return ResponseEntity.status(200).body(priceOfferService.getAllPriceOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity getPriceOfferById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(priceOfferService.getPriceOfferById(id));
    }

    @PostMapping
    public ResponseEntity createPriceOffer(@Valid @RequestBody PriceOffer priceOffer) {
        priceOfferService.createPriceOffer(priceOffer);
        return ResponseEntity.status(201).body("PriceOffer created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePriceOffer(@PathVariable Integer id, @Valid @RequestBody PriceOffer priceOfferDetails) {
        priceOfferService.updatePriceOffer(id, priceOfferDetails);
        return ResponseEntity.status(200).body("PriceOffer updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePriceOffer(@PathVariable Integer id) {
        priceOfferService.deletePriceOffer(id);
        return ResponseEntity.status(200).body("PriceOffer deleted successfully");
    }
}
