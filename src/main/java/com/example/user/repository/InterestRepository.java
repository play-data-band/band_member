package com.example.user.repository;

import com.example.user.domain.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest,Long> {
}
