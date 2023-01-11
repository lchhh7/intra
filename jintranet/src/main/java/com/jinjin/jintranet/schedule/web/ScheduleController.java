package com.jinjin.jintranet.schedule.web;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.jinjin.jintranet.common.DateUtils;
import com.jinjin.jintranet.common.MenuUtils;
import com.jinjin.jintranet.common.VacationDaysUtils;
import com.jinjin.jintranet.holiday.service.HolidayService;
import com.jinjin.jintranet.member.dto.VacationDaysDTO;
import com.jinjin.jintranet.member.service.MemberService;
import com.jinjin.jintranet.model.Holiday;
import com.jinjin.jintranet.model.Member;
import com.jinjin.jintranet.model.Schedule;
import com.jinjin.jintranet.schedule.dto.ScheduleCancelDTO;
import com.jinjin.jintranet.schedule.dto.ScheduleInsertDTO;
import com.jinjin.jintranet.schedule.dto.ScheduleSearchDTO;
import com.jinjin.jintranet.schedule.dto.ScheduleUpdateDTO;
import com.jinjin.jintranet.schedule.dto.ScheduleViewDTO;
import com.jinjin.jintranet.schedule.service.ScheduleService;
import com.jinjin.jintranet.security.auth.PrincipalDetail;

@Controller
public class ScheduleController {
	
	@PersistenceContext
	private EntityManager em;
	
	private MemberService memberService;
	
	private ScheduleService scheduleService;
	
	private HolidayService holidayService;
	
	private MenuUtils menuUtils;
	
	private VacationDaysUtils vacationDaysUtils;
	
	 public ScheduleController(MemberService memberService, ScheduleService scheduleService,
			HolidayService holidayService, MenuUtils menuUtils , VacationDaysUtils vacationDaysUtils) {
		this.memberService = memberService;
		this.scheduleService = scheduleService;
		this.holidayService = holidayService;
		this.menuUtils = menuUtils;
		this.vacationDaysUtils = vacationDaysUtils;
	}

	/**
     * 일정관리 > 메인화면
     */
    @GetMapping(value = "/schedule.do")
    public String main(Model model, HttpServletRequest request, @AuthenticationPrincipal PrincipalDetail principal) throws Exception {
        try {
            Member member = principal.getMember();
            
            
            LocalDate now = LocalDate.now();
            
            int year = now.getYear();
            int month = now.getMonthOfYear();
            int date = now.getDayOfMonth();
			
            model.addAttribute("vacationDays",  new VacationDaysDTO(vacationDaysUtils.getMemberVacationDays(member, year, month, date)));
			        
            model.addAttribute("approves", memberService.findApproves());
            model.addAllAttributes(menuUtils.getDefaultMenu(request , principal.getMember()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "schedule/schedule";
    }
    
    /**
     * 일정관리 > 일정 목록
     */
    @GetMapping(value = "/schedule/search.do")
    public ResponseEntity<Map<String,Object>> findScheduleAll(
    		@AuthenticationPrincipal PrincipalDetail principal ,
            @RequestParam(value = "m", required = false, defaultValue = "") String m,
            @RequestParam(value = "sc", required = false, defaultValue = "''") String sc,
            @RequestParam(value = "fv", required = false, defaultValue = "''") String fv,
            @RequestParam(value = "hv", required = false, defaultValue = "''") String hv,
            @RequestParam(value = "ow", required = false, defaultValue = "''") String ow,
            @RequestParam(value = "sd") String sd,
            @RequestParam(value = "ed") String ed) throws Exception {

        Map<String, Object> map = new HashMap<>();
        
        StringJoiner sj = new StringJoiner(",");
        sj.add(sc); sj.add(fv); sj.add(hv); sj.add(ow);
        
        Schedule schedule = Schedule.builder().type(sj.toString())
        		.strDt(DateUtils.toLocalDateTime(sd)).endDt(DateUtils.toLocalDateTime(ed)).build();
        if("m".equals(m)) schedule.setMember(principal.getMember());
        
        try {
            List<ScheduleSearchDTO> list = scheduleService.read(schedule);
           List<Holiday> holidays = holidayService.findByMonth(DateUtils.toLocalDateTime(sd), DateUtils.toLocalDateTime(ed));
            map.put("list", list);
           map.put("holidays", holidays);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    /**
     * 일정관리 > 일정 등록
     */
    @PostMapping(value = "/schedule.do")
    public ResponseEntity<String> write(@Valid @RequestBody ScheduleInsertDTO scheduleDTO, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetail principal) throws Exception {
        try {
        	if (bindingResult.hasErrors()) {
             	return new ResponseEntity<>(bindingResult.getFieldErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
             }

            if ("VA".equals(scheduleDTO.getType())) {
                String vacationType = scheduleDTO.getVacationType();
                if (!("1".equals(vacationType) || "2".equals(vacationType) || "3".equals(vacationType))) {
                    return new ResponseEntity<>("올바른 휴가 종류를 선택해주세요.", HttpStatus.BAD_REQUEST);
                }

                if (scheduleDTO.getApproveId() == null) {
                    return new ResponseEntity<>("결제자를 선택해주세요.", HttpStatus.BAD_REQUEST);
                }
            }

            if ("OT".equals(scheduleDTO.getType())) {
                if (scheduleDTO.getApproveId() == null) {
                    return new ResponseEntity<>("결제자를 선택해주세요.", HttpStatus.BAD_REQUEST);
                }
            }

            if (!isValidDate(new LocalDate(scheduleDTO.getStrDt()) , new LocalDate(scheduleDTO.getEndDt()))){
                return new ResponseEntity<>("일정 종료일과 시작일을 올바르게 입력해주세요.", HttpStatus.BAD_REQUEST);
            }
            
          String typeCorrection =  "VA".equals(scheduleDTO.getType()) ? ("1".equals(scheduleDTO.getVacationType()) ? "FV" : "HV") : scheduleDTO.getType();
          scheduleDTO.setType(typeCorrection);
          
          Member approve =  (scheduleDTO.getApproveId() == null) ? null : memberService.findById(scheduleDTO.getApproveId());
          scheduleService.write(scheduleDTO, principal.getMember() , approve);
            
        	
          
            return new ResponseEntity<>("일정을 정상적으로 등록했습니다.", HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>("일정 등록 중 오류가 발생했습니다.", HttpStatus.CONFLICT);
        }
    }

   //일정관리 > 일정 선택
     
    @GetMapping(value = "/schedule/{id}.do")
    public ResponseEntity<ScheduleViewDTO> findById(@PathVariable("id") Integer id) throws Exception {
        

        try {
            Schedule schedule = scheduleService.findById(id);
            
            return new ResponseEntity<>(new ScheduleViewDTO(schedule), HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // 일정관리 > 일정 수정
    @PutMapping(value = "/schedule/{id}.do")
    public ResponseEntity<String> edit(@PathVariable("id") Integer id,@Validated @RequestBody ScheduleUpdateDTO scheduleDTO, BindingResult bindingResult ) throws Exception {
        try {
        	 if (bindingResult.hasErrors()) {
             	return new ResponseEntity<>(bindingResult.getFieldErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
             }
        	
        	 if (!isValidDate(new LocalDate(scheduleDTO.getStrDt()) , new LocalDate(scheduleDTO.getEndDt()))){
                return new ResponseEntity<>("일정 종료일과 시작일을 올바르게 입력해주세요.", HttpStatus.BAD_REQUEST);
            }
            
            scheduleService.edit(id, scheduleDTO.DTOtoEntity());
            return new ResponseEntity<>("일정을 정상적으로 수정했습니다.", HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>("일정 수정 중 오류가 발생했습니다.", HttpStatus.CONFLICT);
        }
    }
    // 일정관리 > 일정 취소요청
     
    @PutMapping(value = "/schedule/cancel/{id}.do")
    public ResponseEntity<String> cancel(@PathVariable("id") Integer id, @RequestBody ScheduleCancelDTO scheduleDTO) throws Exception {
        try {
            Schedule schedule = scheduleService.findById(id);
            if (schedule == null) { return new ResponseEntity<>("유효하지 않은 일정입니다.", HttpStatus.BAD_REQUEST);
            }
            if (!"Y".equals(schedule.getStatus())) {
                return new ResponseEntity<>("승인 상태인 일정만 취소요청 할 수 있습니다.", HttpStatus.BAD_REQUEST);
            }

            LocalDateTime startDt = schedule.getStrDt();
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(startDt)) {
                return new ResponseEntity<>("지난 일정은 취소요청 할 수 없습니다.", HttpStatus.BAD_REQUEST);
            }
            
            scheduleService.cancel(id, scheduleDTO.DTOtoEntity());
            return new ResponseEntity<>("일정을 정상적으로 취소 요청했습니다.", HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>("일정 취소요청 중 오류가 발생했습니다.", HttpStatus.CONFLICT);
        }
    }

    
    // 일정관리 > 일정 삭제
    @DeleteMapping(value = "/schedule/{id}.do")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) throws Exception {
        try {
            scheduleService.delete(id);
            return new ResponseEntity<>("일정을 정상적으로 삭제했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("일정 삭제 중 오류가 발생했습니다.", HttpStatus.CONFLICT);
        }
    }
    
    private boolean isValidDate(LocalDate strDt , LocalDate endDt) {
        if (strDt.isAfter(endDt)) return false;

        return true;
    }
	
}
