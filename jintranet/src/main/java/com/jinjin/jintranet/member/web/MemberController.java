package com.jinjin.jintranet.member.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinjin.jintranet.commuting.service.CommutingService;
import com.jinjin.jintranet.member.dto.MemberEditDTO;
import com.jinjin.jintranet.member.dto.MemberSaveDTO;
import com.jinjin.jintranet.member.dto.PasswordEditDTO;
import com.jinjin.jintranet.member.service.MemberService;
import com.jinjin.jintranet.model.Commuting;
import com.jinjin.jintranet.model.KakaoProfile;
import com.jinjin.jintranet.model.Member;
import com.jinjin.jintranet.model.OAuthToken;
import com.jinjin.jintranet.model.Schedule;
import com.jinjin.jintranet.notice.dto.NoticeSearchDTO;
import com.jinjin.jintranet.notice.service.NoticeService;
import com.jinjin.jintranet.schedule.dto.ScheduleViewDTO;
import com.jinjin.jintranet.schedule.service.ScheduleService;
import com.jinjin.jintranet.security.auth.PrincipalDetail;

@Controller
public class MemberController {
	
	private MemberService memberService;
	
	private ScheduleService scheduleService;
	
	private CommutingService commutingService;
	
	private NoticeService noticeService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	
	public MemberController(MemberService memberService, ScheduleService scheduleService,
			CommutingService commutingService, NoticeService noticeService) {
		this.memberService = memberService;
		this.scheduleService = scheduleService;
		this.commutingService = commutingService;
		this.noticeService = noticeService;
	}

	@GetMapping("/login.do")
	public String loginPage() {
		return "login/index";
	}
	
	/*@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수

		RestTemplate rt = new RestTemplate();
		RestTemplate rt2 = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "temp");
		params.add("redirect_uri", "http://localhost:8080/jintranet/auth/kakao/callback");
		params.add("code", code);

		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
		);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Member kakaoMember = Member.builder()
				.memberId(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(encoder.encode(code))
				.build();
		kakaoMember.setCreatedBy("kakao");
		
		if(memberService.findOAuthById(kakaoMember.getMemberId()) == null) {
			memberService.write(new MemberSaveDTO(kakaoMember));
		}
		return "main/index";
	}*/
	
	
	@GetMapping(value = "/main.do")
    public String main(Model model, HttpServletRequest request, 
    		@PageableDefault(size=6, sort="id", direction = Sort.Direction.DESC) Pageable pageable,
    		@AuthenticationPrincipal PrincipalDetail principal) throws Exception {
		
		List<String> yearList = scheduleService.yearList(principal.getMember());
		List<Schedule> vacation = scheduleService.findByMemberId(principal.getMember());
		List<NoticeSearchDTO> notice = noticeService.findNotices(pageable , null, null).getContent();
		Integer cnt = scheduleService.cnt(principal.getMember());
		
		model.addAttribute("noticeList" , notice);
		model.addAttribute("vacation" , vacation);
		model.addAttribute("cnt" , cnt);
		model.addAttribute("yearList" , yearList);
		model.addAttribute("todaySchedules" , scheduleService.todaySchedules());
		model.addAllAttributes(commutingService.getWorkTime(principal.getMember()));
        return "main/index";
    }
	
	@GetMapping(value = "/main/searching.do")
    public ResponseEntity<List<ScheduleViewDTO>> searching(
    		@RequestParam(value ="st", required = false , defaultValue ="") String st, 
    		@RequestParam(value ="y", required = false , defaultValue ="") String y , 
    		@AuthenticationPrincipal PrincipalDetail principal
    		) throws Exception {
        try {
        	List<ScheduleViewDTO> dtoList = new ArrayList<>();
        	List<Schedule> mainSchedules = scheduleService.mainSchedules(principal.getMember(), y);
        	
        	for(Schedule s : mainSchedules) {
        		dtoList.add(new ScheduleViewDTO(s));
        	}
        	
        	
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
	
	@GetMapping("/join.do")
	public String joinPage() {
		return "joinPage";
	}
	@PostMapping("/auth/joinProc")
	public ResponseEntity<String> save(@Validated @RequestBody MemberSaveDTO memberDTO , BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
         	return new ResponseEntity<>(bindingResult.getFieldErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
         }
		
		memberService.write(memberDTO);
		return new ResponseEntity<String>("회원가입이 완료되었습니다.",HttpStatus.OK);
	}
	
	@PostMapping("/main/goToWorkButton.do")
	public ResponseEntity<String> commutingInsert(@RequestBody Commuting commuting , @AuthenticationPrincipal PrincipalDetail principal) {
		commutingService.write(commuting , principal.getMember());
		return new ResponseEntity<String>("",HttpStatus.OK);
	}
	
	@GetMapping("/member/edit.do")
	public String edit(Model model, HttpServletRequest request , @AuthenticationPrincipal PrincipalDetail principal) throws Exception {
		try {
			model.addAttribute("memberInfo" , new MemberSaveDTO(memberService.findById(principal.getMember().getId())));
			model.addAttribute("todaySchedules" , scheduleService.todaySchedules());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "member/member-edit";
	}
	
	@PutMapping("/member/edit.do")
	public ResponseEntity<String> edit(@Validated @RequestBody MemberEditDTO memberDTO,BindingResult bindingResult , 
			@AuthenticationPrincipal PrincipalDetail principal) throws Exception {

		if (bindingResult.hasErrors()) {
         	return new ResponseEntity<>(bindingResult.getFieldErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
         }
		
		memberService.edit(principal, memberDTO);

		return new ResponseEntity<String>("정상적으로 정보가 수정되었습니다.",HttpStatus.OK);
	}
	
	@GetMapping("/member/p/edit.do")
	public String pEdit(Model model, HttpServletRequest request) throws Exception {
		try {
			model.addAttribute("todaySchedules" , scheduleService.todaySchedules());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "member/password-edit";
	}
	
	@PutMapping("/member/p/edit.do")
	public ResponseEntity<String> pEdit(@Validated @RequestBody PasswordEditDTO passwordDTO,BindingResult bindingResult , 
			@AuthenticationPrincipal PrincipalDetail principal) throws Exception {

		if (bindingResult.hasErrors()) {
         	return new ResponseEntity<>(bindingResult.getFieldErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
         }
		
		String msg = memberService.passwordEdit(principal.getMember().getId(), passwordDTO);

		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
}
