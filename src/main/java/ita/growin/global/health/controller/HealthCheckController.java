package ita.growin.global.health.controller;

import ita.growin.global.exception.BusinessException;
import ita.growin.global.exception.errorcode.BusinessErrorCode;
import ita.growin.global.response.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("test/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UP");
    }

    @GetMapping("/health")
    public APIResponse<?> test() {
        return APIResponse.success("test");
    }

    @GetMapping("/business-error")
    public APIResponse<?> businessError() {
        throw BusinessException.of(BusinessErrorCode.MEMBER_NOT_FOUND);
    }

    @GetMapping("/common-error")
    public APIResponse<?> commonError() throws Exception {
        throw new Exception("INTERNAL_SERVER_ERROR");
    }
}
