package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BodyVO {
	private ItemsVO itemsVO;

	public ItemsVO getItems() {
		return itemsVO;
	}

	public void setItems(ItemsVO itemsVO) {
		this.itemsVO = itemsVO;
	}
}
