package it.piacentino.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bonifico {

	@NotNull(message = "Il campo non può essere vuoto")
	private Long accountId;
	@NotNull(message = "Il campo non può essere vuoto")
	@Size(min = 1)
	private String receiverName;
	@NotNull(message = "Il campo non può essere vuoto")
	@Size(min = 1)
	private String description;
	@NotNull(message = "Il campo non può essere vuoto")
	@Size(min = 1)
	private String currency;
	@NotNull(message = "Il campo non può essere vuoto")
	private Integer amount;
	@NotNull(message = "Il campo non può essere vuoto")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private  Date executionDate;

	
}
