package com.jinjin.jintranet.notice.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jinjin.jintranet.common.FileUtils;
import com.jinjin.jintranet.model.Notice;
import com.jinjin.jintranet.model.NoticeAttach;
import com.jinjin.jintranet.notice.dto.NoticeSaveDTO;
import com.jinjin.jintranet.notice.service.NoticeService;
import com.jinjin.jintranet.schedule.service.ScheduleService;
import com.jinjin.jintranet.security.auth.PrincipalDetail;


@Controller
public class NoticeController {
    
    private ScheduleService scheduleService;
    
   private final NoticeService noticeService;
    
    
    public NoticeController(NoticeService noticeService,ScheduleService scheduleService) {
    	this.noticeService = noticeService;
    	this.scheduleService = scheduleService;
    }
    
    @GetMapping(value = "/notice.do")
    public String main(Model model, HttpServletRequest request , 
    		@RequestParam(value = "searchType" , required = false , defaultValue = "") String searchType ,
    		@RequestParam(value ="keyword", required = false , defaultValue ="") String keyword, 
    		@PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        try {
        	model.addAttribute("searchType" , searchType);
        	model.addAttribute("keyword" , keyword);
        	model.addAttribute("noticeList", noticeService.findNotices(pageable , keyword , searchType));
        	model.addAttribute("todaySchedules" , scheduleService.todaySchedules());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "notice/notice";
    }
    
    @GetMapping(value = "/notice/write.do")
    public String write(Model model, HttpServletRequest request) throws Exception {
        try {
        	model.addAttribute("todaySchedules" , scheduleService.todaySchedules());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "notice/notice-write";
    }

    @PostMapping(value = "/notice/write.do")
    public ResponseEntity<String> write(@Validated @RequestBody NoticeSaveDTO dto, BindingResult bindingResult , 
    		@AuthenticationPrincipal PrincipalDetail principal) throws Exception {
        
        if (bindingResult.hasErrors()) {
        	return new ResponseEntity<>(bindingResult.getFieldErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        if ("<p>&nbsp;</p>".equals(dto.getContent())) {
            return new ResponseEntity<>("????????? ??????????????????.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(noticeService.write(dto , principal.getMember()) , HttpStatus.OK);
    }
   
    @GetMapping("/notice/view.do")
    public String view(Model model, @RequestParam("id") Integer id, HttpServletRequest request) throws Exception {
        try {
        	List<NoticeAttach> attachList = noticeService.findById(id).getAttaches().stream().filter(m -> m.getDeletedBy() == null).toList();
        	Notice notice = noticeService.findById(id);
        	notice.setAttaches(attachList);
        	
        	model.addAttribute("notice", notice);
        	model.addAttribute("todaySchedules" , scheduleService.todaySchedules());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "notice/notice-view";
    }
    
    @DeleteMapping(value = "/notice.do")
    public ResponseEntity<String> delete(@RequestBody NoticeSaveDTO dto , 
    		@AuthenticationPrincipal PrincipalDetail principal) throws Exception {
    	noticeService.delete(dto , principal.getMember());
        return new ResponseEntity<>("???????????? ????????? ?????????????????????." , HttpStatus.OK);
    }
	
    
    @GetMapping(value = "/notice/edit.do")
    public String edit(Model model, @RequestParam("id") Integer id, HttpServletRequest request) throws Exception {
        try {
        	List<NoticeAttach> attachList = noticeService.findById(id).getAttaches().stream().filter(m -> m.getDeletedBy() == null).toList();
        	Notice notice = noticeService.findById(id);
        	notice.setAttaches(attachList);
        	
        	model.addAttribute("notice", notice);
        	model.addAttribute("todaySchedules" , scheduleService.todaySchedules());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return "notice/notice-edit";
    }

    @PostMapping("/notice/edit.do")
    public ResponseEntity<String> edit(@Validated @RequestBody NoticeSaveDTO dto,
    		@AuthenticationPrincipal PrincipalDetail principal, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
        	return new ResponseEntity<>(bindingResult.getFieldErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        if ("<p>&nbsp;</p>".equals(dto.getContent())) {
            return new ResponseEntity<>("????????? ??????????????????.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(noticeService.edit(dto,principal) , HttpStatus.OK);
    }
    
    @PostMapping("/notice/upload.do")
    public ResponseEntity<List<NoticeAttach>> upload(MultipartHttpServletRequest request) throws Exception {
        try {
            return new ResponseEntity<>(FileUtils.upload(request, "notice_attach"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    @DeleteMapping(value = "/notice/attach.do")
    public ResponseEntity<String> deleteAttach(@RequestParam("id") Integer id , 
    		@AuthenticationPrincipal PrincipalDetail principal) throws Exception {
        return noticeService.deleteAttach(id , principal.getMember());
    }
	
    @PostMapping("/notice/download.do")
    public void download(@RequestParam int id,
                         HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        noticeService.download(id, request, response);
    }
}
