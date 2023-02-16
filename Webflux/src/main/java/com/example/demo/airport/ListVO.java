package com.example.demo.airport;

import java.util.List;
import javax.annotation.Generated;

import org.apache.ibatis.type.Alias;

import io.swagger.models.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
public class ListVO {

	private String airlineId, airlineNm;

	public ListVO(String airlineId, String airlineNm) {
		super();
		this.airlineId = airlineId;
		this.airlineNm = airlineNm;
	}

	public String getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(String airlineId) {
		this.airlineId = airlineId;
	}

	public String getAirlineNm() {
		return airlineNm;
	}

	public void setAirlineNm(String airlineNm) {
		this.airlineNm = airlineNm;
	}

	private Response response;

	@Getter
	@Setter
	class Response {
		private Header header;
		private Body body;

		public Header getHeader() {
			return header;
		}

		public void setHeader(Header header) {
			this.header = header;
		}

		public Body getBody() {
			return body;
		}

		public void setBody(Body body) {
			this.body = body;
		}

		@Getter
		@Setter
		public class Header {

			private String resultCode;
			private String resultMsg;

			public String getResultCode() {
				return resultCode;
			}

			public void setResultCode(String resultCode) {
				this.resultCode = resultCode;
			}

			public String getResultMsg() {
				return resultMsg;
			}

			public void setResultMsg(String resultMsg) {
				this.resultMsg = resultMsg;
			}

		}

		@Getter
		@Setter
		public class Body {

			private Items items;

			public Items getItems() {
				return items;
			}

			public void setItems(Items items) {
				this.items = items;
			}

		}

		@Getter
		@Setter
		public class Items {

			private List<Item> item;

			public List<Item> getItem() {
				return item;
			}

			public void setItem(List<Item> item) {
				this.item = item;
			}

		}

		@Getter
		@Setter
		public class Item {

			private String airlineId;
			private String airlineNm;

			public String getAirlineId() {
				return airlineId;
			}

			public void setAirlineId(String airlineId) {
				this.airlineId = airlineId;
			}

			public String getAirlineNm() {
				return airlineNm;
			}

			public void setAirlineNm(String airlineNm) {
				this.airlineNm = airlineNm;
			}

		}

	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}