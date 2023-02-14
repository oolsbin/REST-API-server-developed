//@RestController
//public class ApiController {
//	
////	@GetMapping("/api")
////	public String projectInfo() {
////		return "project";
////	}
////	@GetMapping("api")
////	public String api(Model model) {
////		(String)model.addAttribute
////	}
//
//
//	@GetMapping("/find")
//	
//	public String callapihttp()throws IOException {
//		StringBuilder result = new StringBuilder();
//		
////	StringBuffer result = new StringBuffer();
////		
//    try {
//    	// 1. URL을 만들기 위한 StringBuilder
//        String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getAirmanList?" +
//        			"serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"+
//        			"&_type=json";
//   		
//        		; /*URL*/
//        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키
//    // 3. URL 객체 생성
//        URL url = new URL(urlBuilder);
//        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//             
//        // 5. 통신을 위한 메소드 SET
//        conn.setRequestMethod("GET");
//        
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//        
//        String returnLine;
//
//        while((returnLine = br.readLine()) !=null) {
//        	result.append(returnLine);
//        }
//        conn.disconnect();
//    }catch(Exception e) {
//    	e.printStackTrace();
//    }
//    System.out.println("연결됨");
//    return result+"";
//	}
