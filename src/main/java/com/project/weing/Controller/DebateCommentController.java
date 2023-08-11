package com.project.weing.Controller;

import com.project.weing.Service.DebateCommentService;
import com.project.weing.Entity.DebateComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sec3")
public class DebateCommentController {
    private final DebateCommentService debateCommentService;

    @Autowired
    public DebateCommentController(DebateCommentService debateCommentService) {
        this.debateCommentService = debateCommentService;
    }

    // 댓글 생성 API
    @PostMapping
    public ResponseEntity<DebateComment> createComment(@RequestBody DebateComment debateComment) {
        DebateComment createdDebateComment = debateCommentService.createComment(debateComment);
        return new ResponseEntity<>(createdDebateComment, HttpStatus.CREATED);
    }

    // 댓글 수정 API
    @PutMapping("/{commentId}")
    public ResponseEntity<DebateComment> updateComment(
            @PathVariable Long commentId,
            @RequestBody DebateComment updatedDebateComment) {
        DebateComment updated = debateCommentService.updateComment(commentId, updatedDebateComment);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // 댓글 삭제 API
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        debateCommentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // @PathVariable 어노테이션은 경로(/debate/{writerNum})에서 변수 값({writerNum})을 추출하여
    // 메소드 내에서 사용할 수 있도록 해주는 Spring의 기능


    @GetMapping("/debate/{writeNum}")
    public ResponseEntity<List<DebateComment>> getCommentsForDebate(
            @PathVariable Long writeNum,
            @RequestParam(required = false) String optionSelected) {

        // 특정 토론에 대한 모든 댓글 목록 조회
        List<DebateComment> debateComments = debateCommentService.getCommentsForDebate(writeNum);

        if (optionSelected != null) {
            // 옵션 선택에 따른 댓글 필터링을 위한 스트림과 람다식 사용
            List<DebateComment> filteredComments = debateComments.stream()
                    .filter(comment -> optionSelected.equals(comment.getOptionSelected())) // 옵션 선택과 일치하는 댓글만 필터링
                    .collect(Collectors.toList()); // 필터링된 댓글을 리스트로 변환

            return new ResponseEntity<>(filteredComments, HttpStatus.OK); // 필터링된 댓글 반환
        }

        return new ResponseEntity<>(debateComments, HttpStatus.OK); // 모든 댓글 반환
    }
}




