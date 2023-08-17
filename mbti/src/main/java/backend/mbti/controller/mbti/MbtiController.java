package backend.mbti.controller.mbti;

import backend.mbti.domain.dto.MbtiGroupRequest;
import backend.mbti.service.mbti.MbtiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mbti")
public class MbtiController {

    private final MbtiService mbtiService;

    // MBTI 퍼센트
    @GetMapping
    public ResponseEntity<Map<String, Map<String, Double>>> getGroupMatchPercentages(@RequestParam String groupName) {
        Map<String, Map<String, Double>> groupMatchPercentages = mbtiService.calculateGroupMatchPercentages(groupName);
        return new ResponseEntity<>(groupMatchPercentages, HttpStatus.OK);
    }

    // MBTI 섹션 2 그룹 데이터 저장
    @PostMapping
    public ResponseEntity<String> saveMbtiData(@RequestBody MbtiGroupRequest mbtiGroupRequest) {
        if (mbtiService.saveMbtiData(mbtiGroupRequest)) {
            return new ResponseEntity<>("MBTI 데이터 저장 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("MBTI 데이터 저장 실패", HttpStatus.BAD_REQUEST);
        }
    }
}
