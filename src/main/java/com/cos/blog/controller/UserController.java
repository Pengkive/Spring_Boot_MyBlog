package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용 (시큐리티가 필요없는 곳)
// 그냥 주소가 /이면 index.jsp 허용
// static이하에 있는 /js/**, /css/**, /image/**


@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) {//@ResponseBody: Data를 리턴해주는 컨트롤러 함수
		
		//POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
		//Retrofit2, OkHttp, RestTemplate 으로 요청할 수 있음
		
		//HttpHeader Object 생성
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//BodyData를 받을 Object
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type","authorization_code");
		params.add("client_id", "65fcfe922d0fa539a1bf32c45062e422");
		params.add("redirect_ur", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		//body 데이터와 header값을 가지고 있는 entity
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<>(params, headers);
		
		//Http 요청하기 - POST 방식으로 - 그리고 response 응답받음
		ResponseEntity<String> response = rt.exchange(
					"https://kauth.kakao.com/oauth/token", 
					HttpMethod.POST,
					kakaoTokenRequest,
					String.class
		);
		
		//Gson, Json Simple, ObjectMapper();
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
			System.out.println("카카오 엑세스 토큰: "+oauthToken.getAccess_token());
		
		//카카오 프로필 반환받기
		//HttpHeader Object 생성
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		//header값을 가지고 있는 entity
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
				new HttpEntity<>(headers2);
		
		//Http 요청하기 - POST 방식으로 - 그리고 response 응답받음
		ResponseEntity<String> response2 = rt2.exchange(
					"https://kapi.kakao.com/v2/user/me", 
					HttpMethod.POST,
					kakaoProfileRequest,
					String.class
		);

			System.out.println("response2: " + response2.getBody());
		
		//Gson, Json Simple, ObjectMapper();
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
			System.out.println("카카오 id: "+kakaoProfile.getId());
			System.out.println("카카오 E-mail: "+kakaoProfile.getKakao_account().getEmail());
		
		return response2.getBody();
		
	}
	
}