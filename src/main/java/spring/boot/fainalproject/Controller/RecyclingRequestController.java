package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.Model.RecyclingRequest;
import spring.boot.fainalproject.Service.RecyclingRequestService;

@RestController
@RequestMapping("/api/v1/recycling-request")
@RequiredArgsConstructor
public class RecyclingRequestController {

    private final RecyclingRequestService recyclingRequestService;

    @GetMapping
    public ResponseEntity getAllRecyclingRequests() {
        return ResponseEntity.status(200).body(recyclingRequestService.getAllRecyclingRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity getRecyclingRequestById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(recyclingRequestService.getRecyclingRequestById(id));
    }

    @PostMapping
    public ResponseEntity addRecyclingRequest(@Valid @RequestBody RecyclingRequest recyclingRequest,
                                              @RequestParam Integer facilityId,
                                              @RequestParam Integer supplierId) {
        recyclingRequestService.addRecyclingRequest(recyclingRequest, facilityId, supplierId);
        return ResponseEntity.status(201).body("Recycling request added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRecyclingRequest(@PathVariable Integer id,
                                                 @Valid @RequestBody RecyclingRequest recyclingRequest,
                                                 @RequestParam Integer facilityId,
                                                 @RequestParam Integer supplierId) {
        recyclingRequestService.updateRecyclingRequest(id, recyclingRequest, facilityId, supplierId);
        return ResponseEntity.status(200).body("Recycling request updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRecyclingRequest(@PathVariable Integer id) {
        recyclingRequestService.deleteRecyclingRequest(id);
        return ResponseEntity.status(200).body("Recycling request deleted successfully");
    }
}
