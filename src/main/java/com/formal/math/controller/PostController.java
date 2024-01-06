package com.formal.math.controller;

import com.formal.math.dto.PageRequestDTO;
import com.formal.math.dto.PostDTO;
import com.formal.math.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/post")
@Log4j2
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;
  @GetMapping("/list")
  public void list(PageRequestDTO pageRequestDTO, Model model) {
    log.info("list............" + pageRequestDTO);
    model.addAttribute("responseDTO", postService.listWithRC(pageRequestDTO));
  }
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/register")
  public void register(@ModelAttribute PageRequestDTO pageRequestDTO){
    log.info("register get...");
  }
  @PreAuthorize("hasRole('USER')")
  @PostMapping("/register")
  public String registerPost(@Valid PostDTO dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    log.info("register post...");
    if(bindingResult.hasErrors()) {
      log.info("has errors.....");
      redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
      return "redirect:/post/register";
    }
    log.info("dto..." + dto);
    Long pno = postService.register(dto);
    log.info("pno: " + pno);
    redirectAttributes.addFlashAttribute("result", "The post of number "+pno+" has been registered");
    return "redirect:/post/list";
  }
  @GetMapping("/read")
  public void read(Long pno, Long total, PageRequestDTO pageRequestDTO, Model model) {
    PostDTO postDTO = postService.get(pno);
    log.info(postDTO);
    model.addAttribute("dto", postDTO);
    model.addAttribute("total", total);
  }
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/modify")
  public void modify(Long pno, Long total, PageRequestDTO pageRequestDTO, Model model) {
    PostDTO postDTO = postService.get(pno);
    log.info(postDTO);
    model.addAttribute("dto", postDTO);
    model.addAttribute("total", total);
  }
  @PreAuthorize("hasRole('USER')")
  @PostMapping("/modify")
  public String modify(@RequestParam Long total, PageRequestDTO pageRequestDTO, @Valid PostDTO postDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    log.info("modify post......." + postDTO);
    String link = pageRequestDTO.getLink();
    if(bindingResult.hasErrors()) {
      log.info("has errors.......");
      redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
      return "redirect:/post/modify?pno="+postDTO.getPno()+"&total="+total+"&"+link;
    }
    postService.modify(postDTO);
    redirectAttributes.addFlashAttribute("result", "modified");
    return "redirect:/post/read?pno="+postDTO.getPno()+"&total="+total+"&"+link;
  }
//  @PostMapping("/remove")
//  public String remove(Long pno, RedirectAttributes redirectAttributes) {
//    log.info("remove post.. " + pno);
//    postService.remove(pno);
//    redirectAttributes.addFlashAttribute("result", "removed");
//    return "redirect:/post/list";
//  }
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/login")
  public String login(Long pno, Long total, PageRequestDTO pageRequestDTO, Model model) {
    PostDTO postDTO = postService.get(pno);
    log.info(postDTO);
    model.addAttribute("dto", postDTO);
    model.addAttribute("total", total);
    model.addAttribute("loggedIn", true);
    return "/post/read";
  }
}
