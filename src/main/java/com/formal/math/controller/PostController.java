package com.formal.math.controller;

import com.formal.math.dto.PageRequestDTO;
import com.formal.math.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
@Log4j2
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;
  @GetMapping("/list")
  public void list(PageRequestDTO pageRequestDTO, Model model) {
    log.info("list............" + pageRequestDTO);
    model.addAttribute("responseDTO", postService.getList(pageRequestDTO));
  }
}
