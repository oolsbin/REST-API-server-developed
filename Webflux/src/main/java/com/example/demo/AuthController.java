//package com.example.demo;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.jwt.user.exception.TokenRefreshException;
//import com.example.demo.jwt.user.model.RefreshToken;
//import com.example.demo.jwt.user.request.LoginRequest;
//import com.example.demo.jwt.user.request.TokenRefreshRequest;
//import com.example.demo.jwt.user.response.JwtResponse;
//import com.example.demo.jwt.user.response.MessageResponse;
//import com.example.demo.jwt.user.response.TokenRefreshResponse;
//import com.example.demo.jwt.user.security.jwt.JwtUtils;
//import com.example.demo.jwt.user.service.RefreshTokenServiceImpl;
//import com.example.demo.jwt.user.service.UserDetailsImpl;
//
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//
//@RestController
//public class AuthController {
//
//	@Autowired
//	AuthenticationManager authenticationManager;
//
//	@Autowired
//	RefreshToken userService;
//
//	@Autowired
//	JwtUtils jwtUtils;
//
//	@Autowired
//	RefreshTokenServiceImpl refreshTokenService;
//	
////    @Autowired
////    UserRepository userRepository;
//
////    @Autowired
////    RoleRepository roleRepository;
//    
//    @Autowired
//    PasswordEncoder encoder;
//
////	@RequestMapping(value="/login", method = RequestMethod.GET)
////	public void Login() {
////		System.out.println("/request /join : GET");
////	}
//
//	// post로 호출시 토큰발생
//	@PostMapping("/login")
//	public ResponseEntity<?> Login(@Valid @RequestBody LoginRequest loginRequest) {
//		// 1. 권한 인증 확인 객체 :Authentication 인증 후 > Authorization 인가 : 인증된 사용자가 요청된 자원에
//		// 접근가능한지를 결정 : 역할1. loginRequest(사용자)정보가 올바른지 manager에서 검사한다 > 역할2.그 후 토큰을 넘겨준다.
//		Authentication authentication = authenticationManager.authenticate(//로그인 정보의 유효성 검사(Security내장기능)
//				new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));
//
//		// 인증된 사용자 정보인 `Principal`(id)을 `Authentication`에서 관리하고
//		// `Authentication`을 `SecurityContext`가 관리하고
//		// `SecurityContext`는 `SecurityContextHolder`가 관리한다.
//
//		// 2. 인증자 사용정보를 가져온다.
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//		String jwt = jwtUtils.generateJwtToken(userDetails);
//
//		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//				.collect(Collectors.toList());
//
//		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
//
//									//응답으로 날릴 것
//		return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
//				userDetails.getUsername(), userDetails.getEmail(), roles));
//
//	}
//	
////	//회원가입
////	@PostMapping("/signup")
////	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
////	    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
////	      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
////	    }
////
////	    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
////	      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
////	    }
////
////
////	    
////	    // Create new user's account
////	    UserVO user = new UserVO(signUpRequest.getUsername(), signUpRequest.getEmail(),
////	        encoder.encode(signUpRequest.getPassword()));
////
////	    Set<String> strRoles = signUpRequest.getRole();
////	    Set<Role> roles = new HashSet<>();
////
////	    if (strRoles == null) {
////	      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
////	          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////	      roles.add(userRole);
////	    } else {
////	      strRoles.forEach(role -> {
////	        switch (role) {
////	        case "admin":
////	          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
////	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////	          roles.add(adminRole);
////
////	          break;
////	        case "mod":
////	          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
////	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////	          roles.add(modRole);
////
////	          break;
////	        default:
////	          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
////	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////	          roles.add(userRole);
////	        }
////	      });
////	    }
////
////	    user.setRoles(roles);
////	    userRepository.save(user);
////
////	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
////	  }
//	
//	//refreshtoken 발행
//	 @PostMapping("/refreshtoken")
//	  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
//	    String requestRefreshToken = request.getRefreshToken();
//	    
//	    return refreshTokenService.findByToken(requestRefreshToken)
//	    		.map(refreshTokenService::verifyExpiration)
//	    		.map(RefreshToken::getUser)
//	    		.map(user -> {
//	    			String token = jwtUtils.generateTokenFromUsername(user.getUsername());
//	    		return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
//	    		}).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
//	    	            "Refresh token is not in database!"));
//	 }
//	 
//	 @PostMapping("/signout")
//	  public ResponseEntity<?> logoutUser() {
//	    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//	    Long userId = userDetails.getId();
//	    refreshTokenService.deleteByUserId(userId);
//	    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
//	  }
//
//}
