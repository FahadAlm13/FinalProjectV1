package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.DTO.FacilityDTO;
import spring.boot.fainalproject.Model.Facility;
import spring.boot.fainalproject.Model.FacilityRequest;
import spring.boot.fainalproject.Model.Offer;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;
import spring.boot.fainalproject.Repository.FacilityRepository;
import spring.boot.fainalproject.Repository.FacilityRequestRepository;
import spring.boot.fainalproject.Repository.OfferRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final AuthRepository authRepository;
    private final FacilityRequestRepository facilityRequestRepository;
    private final OfferRepository offerRepository;

    public List<Facility> getAllFacilities(){
        return facilityRepository.findAll();

    }
    public void registerFacility(FacilityDTO facilityDTO) {
        User user = new User();
        user.setUsername(facilityDTO.getUsername());
        user.setName(facilityDTO.getName());

        String hashedPassword = new BCryptPasswordEncoder().encode(facilityDTO.getPassword());
        user.setPassword(hashedPassword);
        user.setRole("FACILITY");

        authRepository.save(user);

        Facility facility = new Facility();

        facility.setEmail(facilityDTO.getEmail());
        facility.setPhoneNumber(facilityDTO.getPhoneNumber());
        facility.setCommericalRegister(facilityDTO.getCommericalRegister());
        facility.setLicenseNumber(facilityDTO.getLicenseNumber());

        facility.setUser(user);
        facilityRepository.save(facility);
    }

    public void updateFacility(Integer facility_id, FacilityDTO facilityDTO) {

        Facility existingFacility = facilityRepository.findFacilityById(facility_id);
        if (existingFacility == null) {
            throw new ApiException("Facility not found");
        }
        User user = existingFacility.getUser();
        user.setUsername(facilityDTO.getUsername());
        user.setName(facilityDTO.getName());
        String hashedPassword = new BCryptPasswordEncoder().encode(facilityDTO.getPassword());
        user.setPassword(hashedPassword);
        user.setRole("FACILITY");

        existingFacility.setEmail(facilityDTO.getEmail());
        existingFacility.setPhoneNumber(facilityDTO.getPhoneNumber());
        existingFacility.setCommericalRegister(facilityDTO.getCommericalRegister());
        existingFacility.setLicenseNumber(facilityDTO.getLicenseNumber());

        existingFacility.setUser(user);

        authRepository.save(user);
        facilityRepository.save(existingFacility);
    }
    public void deleteFacility(Integer facility_id) {
        Facility existingFacility = facilityRepository.findFacilityById(facility_id);
        if (existingFacility == null) {
            throw new ApiException("Facility not found");
        }

        User user = existingFacility.getUser();
        authRepository.delete(user);
        facilityRepository.delete(existingFacility);
    }
    public void acceptOffer(Integer facilityId, Integer facilityRequestId, Integer offerId) {
        // Fetch the FacilityRequest by ID
        FacilityRequest facilityRequest = facilityRequestRepository.findFacilityRequestById(facilityRequestId);

        // Check if the facility associated with the request matches the provided facilityId
        if (!facilityRequest.getFacility().getId().equals(facilityId)) {
            throw new ApiException("Facility does not have permission to accept this offer");
        }

        // Fetch the Offer by ID
        Offer acceptedOffer = offerRepository.findOfferById(offerId);

        // Check if the offer belongs to the facility request
        if (!acceptedOffer.getFacilityRequest().getId().equals(facilityRequestId)) {
            throw new ApiException("Offer does not belong to the facility request");
        }

        // Set the status of the accepted offer to APPROVED
        acceptedOffer.setStatus("APPROVED");
        offerRepository.save(acceptedOffer);

        // Reject all other offers for this facility request
        List<Offer> otherOffers = offerRepository.findOfferByFacilityRequestId(facilityRequestId);
        for (Offer offer : otherOffers) {
            if (!offer.getId().equals(offerId)) {
                offer.setStatus("REJECTED");
                offerRepository.save(offer);
            }
        }
    }
}
