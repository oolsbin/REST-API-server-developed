package com.example.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ItemsVO {
	private List<ItemVO> itemVO;

	public List<ItemVO> getItem() {
		return itemVO;
	}

	public void setItem(List<ItemVO> itemVO) {
		this.itemVO = itemVO;
	}
}
