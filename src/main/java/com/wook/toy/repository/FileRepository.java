package com.wook.toy.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wook.toy.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, BigDecimal> {
	
	File findByFileNumberAndUseYn(BigDecimal fileNumber, String useYn);
}
