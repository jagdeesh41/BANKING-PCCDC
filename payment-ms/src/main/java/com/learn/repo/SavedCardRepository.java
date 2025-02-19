package com.learn.repo;

import com.learn.entity.SavedCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedCardRepository extends JpaRepository<SavedCard,Integer> {
    Optional<List<SavedCard>> findByCustomerId(long customerId);
}
