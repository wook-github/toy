package com.wook.toy.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wook.toy.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, BigDecimal> {
	
	List<File> findByBoardNumberAndUseYn(BigDecimal boardNumber, String useYn);
	
	File findByFileNumber(BigDecimal fileNumber);
}
