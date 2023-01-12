package com.jinjin.jintranet.admin.schedule.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.jinjin.jintranet.common.PageUtils;
import com.jinjin.jintranet.member.service.MemberService;
import com.jinjin.jintranet.model.Member;
import com.jinjin.jintranet.model.Schedule;
import com.jinjin.jintranet.schedule.service.ScheduleService;
import com.jinjin.jintranet.security.auth.PrincipalDetail;

@Controller
public class AdminScheduleController {
	
	
	private MemberService memberService;
	
	private final ScheduleService scheduleService;
    
    public AdminScheduleController(MemberService memberService, ScheduleService scheduleService) {
    	this.memberService = memberService;
    	this.scheduleService = scheduleService;
    }
	
	/**
     * 일정신청관리(관) > 목록 페이지 이동
     */
    @GetMapping(value = "/admin/schedule.do")
    public String main(Model model, HttpServletRequest request) throws Exception {
       // loggingCurrentMethod(LOGGER);
        try {
        	model.addAttribute("members", memberService.findAll());
        	model.addAttribute("todaySchedules" , scheduleService.todaySchedules());
        } catch (Exception e) {
        }
        return "admin-schedule/admin-schedule";
    }

    /**
     * 일정신청관리(관) > 목록 조회
     */
    @GetMapping(value = "/admin/schedule/search.do")
    public ResponseEntity<Map<String, Object>> findSchedule(
    		@AuthenticationPrincipal PrincipalDetail principal ,
            @RequestParam(value = "m", required = false) Integer m,
            @RequestParam(value = "r", required = false, defaultValue = "''") String r,
            @RequestParam(value = "y", required = false, defaultValue = "''") String y,
            @RequestParam(value = "n", required = false, defaultValue = "''") String n,
            @PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request) throws Exception {

    	List<String> statusList = new ArrayList<>();
    	if(!"".equals(r)) {
    		statusList.add(r);
    		statusList.add("C");
    	}
    	
    	statusList.add(y); statusList.add(n);
    	
    	Map<String, Object> map = new HashMap<>();
        try {
            Page<Schedule> approvesList = scheduleService.approvesList(principal.getMember(), m , statusList , pageable);
            String page = PageUtils.page(approvesList, "schedules" , request);
            
            map.put("list" , approvesList);
            map.put("page" , page);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    

    /**
     * 일정신청관리(관) > 신청내역 조회
     */
    @GetMapping(value = "/admin/schedule/{id}.do")
    public ResponseEntity<Schedule> findById(@PathVariable("id") int id) throws Exception {
        try {
            Schedule vo = scheduleService.findById(id);
            return new ResponseEntity<>(vo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    

    /**
     * 일정신청관리(관) > 신청내역 처리
     * status
     * R : 대기
     * Y : 승인
     * N : 비승인
     * C : 취소요청
     * D : 취소요청 승인
     * 취소요청 비승인 = Y
     */
    @PutMapping(value = "/admin/schedule/{id}.do")
    public ResponseEntity<String> approve(
            @PathVariable("id") int id,
            @RequestBody Schedule schedule,
            @AuthenticationPrincipal PrincipalDetail principal) throws Exception {
        try {
            scheduleService.approves(id, schedule , principal.getMember());
            return new ResponseEntity<>("정상적으로 처리되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("처리 중 오류가 발생했습니다.", HttpStatus.CONFLICT);
        }
    }
    

    /**
     * 일정신청관리(관) > 휴가일수조회
     */
    @GetMapping(value = "/admin/schedule/vacationDays.do")
    public ResponseEntity<List<Member>> vacationDays() throws Exception {
        try {
            List<Member> list = scheduleService.vacationDays();

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
}
