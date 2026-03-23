package com.fu.carrenting.repository;

import com.fu.carrenting.entity.Review;
import com.fu.carrenting.entity.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
}