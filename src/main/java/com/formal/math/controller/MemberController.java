package com.formal.math.controller;

import com.formal.math.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/login")
    public void loginGET() {
        log.info("login get...............");
    }
    @GetMapping("/prelogin")
    public String preloginGET(String msg, RedirectAttributes redirectAttributes) {
        log.info("prelogin get...............");
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/member/login";
    }
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @GetMapping("/info")
    public void info() {
        log.info("info...............");
    }
    @GetMapping("/join")
    public void joinGET() {
        log.info("join get...");
    }
    @PostMapping("/join")
    public String joinPOST(String email, Model model, RedirectAttributes redirectAttributes) {
      log.info("join post...");
      String encodedPassword;
      try {
        encodedPassword = memberService.join(email);
      } catch (MemberService.EmailExistsException e) {
        redirectAttributes.addFlashAttribute("msg", "The given email address is already registered!");
        return "redirect:/member/join";
      }
      model.addAttribute("email", email);
      model.addAttribute("epw", encodedPassword);
      return "/member/check";
    }
//    @GetMapping("/check")
//    public void checkGET() {
//        log.info("check get...............");
//    }
    @PostMapping("/check")
    public String checkEmailPOST(String email, String epw, String password, Model model) {
        log.info("check post...............");
        if(memberService.checkPassword(password, epw)){
            model.addAttribute("email", email);
            return "/member/setInfo";
        } else {
            model.addAttribute("msg", "Password Check has failed! Try again.");
            model.addAttribute("email", email);
            model.addAttribute("epw", epw);
            return "/member/check";
        }
    }
//    @GetMapping("/setInfo")
//    public void setInfoGET() {
//        log.info("setInfo get...............");
//    }
    @PostMapping("/setInfo")
    public String setInfoPOST(String email, String password, String name, Model model, RedirectAttributes redirectAttributes) {
        log.info("modify post...............");
        try {
            memberService.setInfo(email, password, name);
        } catch (MemberService.NameExistsException e) {
            model.addAttribute("msg", "The give nickname is already used! Try another.");
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            model.addAttribute("name", name);
            return "/member/setInfo";
        }
        redirectAttributes.addFlashAttribute("msg", "Your account has been registered successfully!");
        return "redirect:/member/login";
    }
    @GetMapping("/password")
    public void passwordGET() {
        log.info("password get...............");
    }
    @PostMapping("/password")
    public String passwordPOST(String email, RedirectAttributes redirectAttributes) {
        log.info("password post...............");
        log.info(email);
        try {
            memberService.checkEmail(email);
        } catch (MemberService.EmailNotExistException e) {
            redirectAttributes.addFlashAttribute("msg", "The given email address is not registered!");
            return "redirect:/member/password";
        }
        redirectAttributes.addFlashAttribute("msg", "A new password was sent to your email address!");
        return "redirect:/member/login";
    }
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @GetMapping("/modify")
    public void modifyGET(Authentication authentication, Model model) {
        log.info("modify get...............");
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        String name = memberService.getName(email);
        model.addAttribute("email", email);
        model.addAttribute("name", name);
    }
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @PostMapping("/modify")
    public String modifyPOST(String email, String password, String name, Model model) {
        log.info("modify post...............");
        try {
            memberService.modify(email, password, name);
        } catch (MemberService.NameExistsException e) {
            model.addAttribute("msg", "The give nickname is already used! Try another.");
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            model.addAttribute("name", name);
            return "/member/modify";
        }
        return "redirect:/logout";
    }
    @GetMapping("/withdraw")
    public String withdrawGET(@RequestParam String email) {
        log.info("withdraw get...............");
        memberService.withdraw(email);
        return "redirect:/logout";
    }
}
