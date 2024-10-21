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
@Table(name="t_file", schema="toy", catalog = "toy")
@SequenceGenerator(name = "File_Seq_Generator", sequenceName = "toy.t_file_seq", initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class File {

	@Id
	@Column(name = "file_number", columnDefinition = "NUMERIC(15,0)")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "File_Seq_Generator")
	private BigDecimal fileNumber;
	
	@Column(name = "board_number", columnDefinition = "NUMERIC(15,0)")
	private BigDecimal boardNumber;
	
	@Column(name = "file_path", columnDefinition = "VARCHAR(32)")
	private String filePath;
	
	@Column(name = "file_ext", columnDefinition = "VARCHAR(32)")
	private String fileExt;
	
	@Column(name = "file_name", columnDefinition = "VARCHAR(128)")
	private String fileName;
	
	@Column(name = "file_orgn_name", columnDefinition = "VARCHAR(512)")
	private String fileOrgnName;
	
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
