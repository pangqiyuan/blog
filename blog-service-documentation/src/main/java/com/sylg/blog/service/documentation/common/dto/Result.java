package com.sylg.blog.service.documentation.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 返回结果实体类
 * @author 忆地球往事
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
	private String code;
	private String msg;

}