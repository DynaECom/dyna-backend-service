package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.CreateReviewDTO;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.models.Client;
import rw.dyna.ecommerce.v1.models.Product;
import rw.dyna.ecommerce.v1.models.Review;
import rw.dyna.ecommerce.v1.models.User;
import rw.dyna.ecommerce.v1.repositories.IReviewRepository;
import rw.dyna.ecommerce.v1.services.IClientService;
import rw.dyna.ecommerce.v1.services.IProductService;
import rw.dyna.ecommerce.v1.services.IReviewService;
import rw.dyna.ecommerce.v1.services.IUserServices;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements IReviewService {
    private final IProductService productService;
    private final IClientService clientService;
    private final IReviewRepository reviewRepository;

    public ReviewServiceImpl(IProductService productService, IUserServices userServices, IClientService clientService, IReviewRepository reviewRepository) {
        this.productService = productService;
        this.clientService = clientService;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review createReview(CreateReviewDTO dto) {
        Product product = productService.getProductById(UUID.fromString(dto.getProductId()));
        Client client = clientService.getClientById(UUID.fromString(dto.getUserId()));
        Review review = new Review(product, client, dto.getReview(), dto.getRating());
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(CreateReviewDTO review, UUID id) {
        return null;
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review getById(UUID id) {
        return reviewRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Review with id "+id+" not found"));
    }

    @Override
    public void deleteById(UUID id) {
        reviewRepository.deleteById(id);
    }
}
