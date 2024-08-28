package com.wook.toy.domain;

import java.math.BigDecimal;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="t_answer", schema="toy", catalog = "toy")
@SequenceGenerator(name = "Answer_Seq_Generator", sequenceName = "toy.t_answer_seq", initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Answer_Seq_Generator")
	private BigDecimal answerNumber;
	
	private BigDecimal boardNumber;
	private String answerContent;
	
	@ColumnDefault("to_char(now(), 'YYYYMMDD')")
	private String writeYmd;
	
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
