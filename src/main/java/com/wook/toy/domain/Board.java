package com.wook.toy.domain;

import java.math.BigDecimal;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="t_board", schema="toy", catalog = "toy")
@DynamicUpdate
@SequenceGenerator(name = "Board_Seq_Generator", sequenceName = "toy.t_board_seq", initialValue = 1, allocationSize = 1)
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Board_Seq_Generator")
	private BigDecimal boardNumber;
	
	private String boardSection;
	private String boardTitle;
	private String boardContents;
	
	@ColumnDefault("0")
	private BigDecimal boardLikeCnt;
	
	@ColumnDefault("0")
	private BigDecimal commentCnt;
	
	@ColumnDefault("'N'")
	private String fixYn;
	
	@ColumnDefault("to_char(now(), 'YYYYMMDD')")
	private String writeYmd;
	
	private String writerId;
	private BigDecimal fileNumber;
	
	@ColumnDefault("'Y'")
	private String useYn;
	
	@ColumnDefault("to_char(now(), 'YYYYMMDD')")
	private String updtYmd;
	
	@ColumnDefault("'ADMIN'")
	private String updusrId;
	
	@ColumnDefault("'127.0.0.1'")
	private String updusrIp;
	private String rmks;
}
