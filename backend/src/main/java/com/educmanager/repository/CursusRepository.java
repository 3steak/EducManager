package com.educmanager.repository;

import com.educmanager.entity.Cursus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursusRepository extends JpaRepository<Cursus, Long> {
}
