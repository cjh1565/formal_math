package com.formal.math.service;

import com.formal.math.dto.PageRequestDTO;
import com.formal.math.dto.PageResponseDTO;
import com.formal.math.dto.ReplyDTO;
import com.formal.math.entity.Member;
import com.formal.math.entity.Post;
import com.formal.math.entity.Reply;
import com.formal.math.repository.MemberRepository;
import com.formal.math.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private Reply dtoToEntity(ReplyDTO dto) {
        Post post = Post.builder().pno(dto.getPno()).build();
        Member member = memberRepository.findByName(dto.getReplier()).orElseThrow();
        Reply reply = Reply.builder()
          .rno(dto.getRno())
          .replyText(dto.getReplyText())
          .post(post)
          .replier(member)
          .build();
        return reply;
    }
    private ReplyDTO entityToDTO (Reply reply) {
        ReplyDTO dto = ReplyDTO.builder()
          .rno(reply.getRno())
          .pno(reply.getPost().getPno())
          .replyText(reply.getReplyText())
          .replier(reply.getReplier().getName())
          .regDate(reply.getRegDate())
          .modDate(reply.getModDate())
          .build();
        return  dto;
    }
    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);
        Long rno = replyRepository.save(reply).getRno();
        return rno;
    }
    @Override
    public ReplyDTO read(Long rno) {
        Optional<Reply> replyOptional = replyRepository.findById(rno);
        Reply reply = replyOptional.orElseThrow();
        return entityToDTO(reply);
    }
    @Override
    public void modify(ReplyDTO replyDTO) {
        Optional<Reply> replyOptional = replyRepository.findById(replyDTO.getRno());
        Reply reply = replyOptional.orElseThrow();
        reply.changeReplyText(replyDTO.getReplyText());
        replyRepository.save(reply);
    }
    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
    @Override
    public PageResponseDTO<ReplyDTO> getListOfPost(Long pno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0? 0: pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());
        Page<Reply> result = replyRepository.listOfPost(pno, pageable);
        List<ReplyDTO> dtoList =
                result.getContent().stream().map(reply -> entityToDTO(reply))
                        .collect(Collectors.toList());
        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
