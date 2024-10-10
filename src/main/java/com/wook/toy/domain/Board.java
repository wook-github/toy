package com.wook.toy.domain;

import java.math.BigDecimal;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="t_board", schema="toy", catalog = "toy")
@SequenceGenerator(name = "Board_Seq_Generator", sequenceName = "toy.t_board_seq", initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class Board {

	@Id
	@Column(name = "board_number", columnDefinition = "NUMERIC(15,0)")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Board_Seq_Generator")
	private BigDecimal boardNumber;
	
	@Column(name = "board_section", columnDefinition = "BPCHAR(2)")
	private String boardSection;
	
	@Column(name = "board_title", columnDefinition = "VARCHAR(256)")
	private String boardTitle;
	
	@Column(name = "board_contents", columnDefinition = "TEXT")
	private String boardContents;
	
	@Column(name = "board_view_cnt", columnDefinition = "NUMERIC(11,0)")
	@ColumnDefault("0")
	private BigDecimal boardViewCnt;
	
	@Column(name = "write_ymd", columnDefinition = "BPCHAR(8)")
	@ColumnDefault("to_char(now(), 'YYYYMMDD')")
	private String writeYmd;
	
	@Column(name = "writer_id", columnDefinition = "VARCHAR(256)")
	@ColumnDefault("'ADMIN'")
	private String writerId;
	
	@Column(name = "file_number", columnDefinition = "NUMERIC(15,0)")
	private BigDecimal fileNumber;
	
	@Column(name = "use_yn", columnDefinition = "BPCHAR(1)")
	@ColumnDefault("'Y'")
	private String useYn;
	
	@Column(name = "updt_ymd", columnDefinition = "BPCHAR(8)")
	@ColumnDefault("to_char(now(), 'YYYYMMDD')")
	private String updtYmd;
	
	@Column(name = "updusr_id", columnDefinition = "BPCHAR(32)")
	@ColumnDefault("'ADMIN'")
	private String updusrId;
	
	@Column(name = "updusr_ip", columnDefinition = "BPCHAR(32)")
	@ColumnDefault("'127.0.0.1'")
	private String updusrIp;
	
	@Column(name = "rmks", columnDefinition = "VARCHAR(512)")
	private String rmks;
}
