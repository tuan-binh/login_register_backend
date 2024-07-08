package com.ra.dto;

import com.ra.constants.EHttpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseWrapper<T> {
	private EHttpStatus status;
	private int code;
	private T data;
}
