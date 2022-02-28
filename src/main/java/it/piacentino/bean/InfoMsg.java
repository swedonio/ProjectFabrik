package it.piacentino.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoMsg implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private String code;
	private String message;
}
