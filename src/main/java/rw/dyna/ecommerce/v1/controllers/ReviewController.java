package rw.dyna.ecommerce.v1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.dyna.ecommerce.v1.dtos.CreateReviewDTO;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.services.IReviewService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final IReviewService reviewService;

    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createReview(@Valid @RequestBody CreateReviewDTO dto){
        return ResponseEntity.ok().body(ApiResponse.success(createReview(dto),"Review created successfully!"));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllReviews(){
        return ResponseEntity.ok().body(ApiResponse.success(reviewService.getAllReviews()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getReview(@PathVariable("id") UUID id){
        return ResponseEntity.ok().body(ApiResponse.success(reviewService.getById(id)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete (@PathVariable("id") UUID id){
        reviewService.deleteById(id);
        return ResponseEntity.ok().body(ApiResponse.success("Review deleted successfully!"));
    }

}
