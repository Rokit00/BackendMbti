package backend.mbti.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 나중에 지울 파일 로그 테스트용

@Slf4j
@RestController
public class LogController {

    @GetMapping("/log/{userid}")
    public String log(@PathVariable String userid) {
//        String name = "OK";

//        log.trace("trace log={}", name);
//        log.debug("debug log={}", name);
        log.info("mappingPath log={}", userid);
//        log.warn("warn log={}", name);
//        log.error("error log={}", name);

        return "ok";
    }
}
