package com.formal.math.repository;

import com.formal.math.entity.Theory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheoryRepository extends JpaRepository<Theory, Long> {
}
