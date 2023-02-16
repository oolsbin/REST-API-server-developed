package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xml.sax.SAXException;

import com.example.demo.airport.AirportService;
import com.example.demo.dto.ListVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import io.swagger.models.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

//swagger-ui 타이틀 이름과 설명
@Tag(name = "air Controller", description = "항공관련 컨트롤러") // 스프링 독
@RestController
@RequiredArgsConstructor
public class ApiController {

	private AirportService airportService;

	// DB연결 Test클래스
	@RequestMapping(value = "/index", method = { RequestMethod.POST, RequestMethod.GET })
	public String index() {
		String test = airportService.selectTest();
		System.out.println("조회테스트" + test);
		return "index";
	}

	// 공항목록조회(airportId:공항ID, airportNm:공항명)
	@GetMapping("/find")
	@Operation(summary = "공항", description = "공항을 조회한다.")
	public ResponseEntity<ListVO> callapihttp(@RequestParam(value = "공항ID", required = false) String airlineId,
			@RequestParam(value = "공항명", required = false) String airlineNm) throws IOException, ParseException {

		StringBuilder result = new StringBuilder();

		Gson gson = new Gson();

		String response = "";

		System.out.println("airlineId = " + airlineId);
		System.out.println("airlineNm = " + airlineNm);

		BufferedReader br = null;
		ListVO vo = null;

		try {
			// 1. URL을 만들기 위한 StringBuilder
			String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getAirmanList?"
					+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
					+ "&airlineId=" + airlineId + "&airlineNm=" + airlineNm + "&_type=json";

			// 2. URL 객체 생성
			URL url = new URL(urlBuilder);
			System.out.println("요청URL = " + urlBuilder);

			// 3. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// 4. 통신을 위한 메소드 SET
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

			int responseCode = conn.getResponseCode();

			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			}

			System.out.println("Response code: " + conn.getResponseCode());
			System.out.println("------------------------------------------------------------");

			String returnLine;
			StringBuilder resultBuild = new StringBuilder();
			while ((returnLine = br.readLine()) != null) {
				resultBuild.append(returnLine);
			}

			response = resultBuild.toString();
			vo = gson.fromJson(response, ListVO.class);
			System.out.println(vo);// @객체아이디
			System.out.println(gson);// vo의 toString vo에 대한 정보

			System.out.println("------------------------------------------------------------");
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(vo);
			System.out.println(jsonString);
			System.out.println("------------------------------------------------------------");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (br != null) {
				br.close();
			}
		}
		System.out.println("연결됨");
		System.out.println(response);

		System.out.println("------------------------ JSONParser_item ------------------------------------");

		JSONParser paser = new JSONParser(); // JSON Parser객채를 만듭니다. parser를 통해서 파싱을 합니다.

		JSONObject obj = (JSONObject) paser.parse(response); // Parser로 문자열 데이터를 JSON데이터로 변환합니다.

		// response 가져오기
		JSONObject parse_response = (JSONObject) obj.get("response"); // response key값에 맞는 Value인 JSON객체를 가져옵니다.

		// response 로 부터 body 찾아오기
		JSONObject parse_body = (JSONObject) parse_response.get("body");

		// body 로 부터 items 받아오기
		JSONObject parse_items = (JSONObject) parse_body.get("items");

		// items로 부터 item 를 받아옵니다. item : 뒤에 [ 로 시작하므로 jsonarray입니다.
		JSONArray parse_item = (JSONArray) parse_items.get("item");

		System.out.println(parse_items);
		System.out.println("------------------------ data ------------------------------------");
		System.out.println(parse_item.size());

		// 각각 요소 출력
		for (int i = 0; i < parse_item.size(); i++) { // 해당 JSONArray객체에 값을 차례대로 가져와서 읽습니다.
			JSONObject data_list = (JSONObject) parse_item.get(i);
			String airportId = (String) data_list.get("공항아이디");
			String airportNm = (String) data_list.get("공항번호");
			
//			ListVO list_vo = new ListVO(airlineId, airlineNm);
//			airportService.insertList_insert(list_vo);

			System.out.println("배열의 " + i + "번째 요소");
			System.out.println(data_list);
		}
		return ResponseEntity.ok(vo);
	}
//
//    //생성자 주입
//    @Autowired
//    public ApiController(AirportService airportService) {
//        this.airportService = airportService;
//    }
//
//    //검색시 데이터가 없으면 db에 추가하도록 처리한다.
//    @GetMapping("/db")
//    public void searchInfo(Model model) throws ParserConfigurationException, SAXException, IOException {
//
//        System.out.println("파싱 스타트 체크");
//
//        CompanyInfoExplorer apiExplorer = new CompanyInfoExplorer();
//
//        //파싱하여 리턴한 데이터 값들을 list에 담아주기 위해 사용
//        List<GovDataDTO> list = apiExplorer.parsingData("(주)");
//
//        //List에 담겨있는 정보들을 db에 넣기 위해서 사용
//        for (GovDataDTO dataDTO : list) {
//
//            companyInfoService.insertInfo(dataDTO);
//
//        }
//
//        model.addAttribute("companyList",companyInfoService.companyList());
//
//        log.info("파싱 정보 입력끝");
//    }

	@GetMapping("/apitest2")
	public String callapihttp2() {

		StringBuffer result = new StringBuffer();

		try {
			String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?"
					+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
					+ "&depAirportId=NAARKJJ" + "&arrAirportId=NAARKPC" + "&depPlandTime=20211201"
					+ "&airlineId=AAR&numOfRows=10" + "&pageNo=1" + "&_type=json";
			URL url = new URL(urlBuilder);
			// 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 5. 통신을 위한 메소드 SET
			conn.setRequestMethod("GET");

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			String returnLine;
//        result.append("<xmp>");
			while ((returnLine = br.readLine()) != null) {
				result.append(returnLine + "/n");
			}
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* System.out.println(result); */
		return result + "";
	}
}