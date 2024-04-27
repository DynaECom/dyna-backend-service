package rw.dyna.ecommerce.v1.services;


import rw.dyna.ecommerce.v1.dtos.CreateReviewDTO;
import rw.dyna.ecommerce.v1.models.Review;

import java.util.List;
import java.util.UUID;

public interface IReviewService {
    Review createReview(CreateReviewDTO dto);
    Review updateReview(CreateReviewDTO review, UUID id);
    List<Review> getAllReviews();
    Review getById(UUID id);
    void deleteById(UUID id);


}
