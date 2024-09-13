package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.PriceOffer;
import spring.boot.fainalproject.Repository.PriceOfferRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceOfferService {
    private final PriceOfferRepository priceOfferRepository;

    public List<PriceOffer> getAllPriceOffers() {
        return priceOfferRepository.findAll();
    }

    public PriceOffer getPriceOfferById(Integer id) {
        PriceOffer priceOffer = priceOfferRepository.findPriceOfferById(id);
        if (priceOffer == null) {
            throw new ApiException("Price Offer not found");
        }
        return priceOffer;
    }

    public void createPriceOffer(PriceOffer priceOffer) {
        priceOfferRepository.save(priceOffer);
    }

    public void updatePriceOffer(Integer id, PriceOffer priceOfferDetails) {
        PriceOffer priceOffer = priceOfferRepository.findPriceOfferById(id);
        if (priceOffer == null) {
            throw new ApiException("Price Offer not found");
        }
        priceOffer.setPrice(priceOfferDetails.getPrice());
        priceOffer.setStatus(priceOfferDetails.getStatus());
        priceOffer.setRecyclingRequest(priceOfferDetails.getRecyclingRequest());
        priceOfferRepository.save(priceOffer);
    }

    public void deletePriceOffer(Integer id) {
        PriceOffer priceOffer = priceOfferRepository.findPriceOfferById(id);
        if (priceOffer == null) {
            throw new ApiException("Price Offer not found");
        }
        priceOfferRepository.delete(priceOffer);
    }
}

