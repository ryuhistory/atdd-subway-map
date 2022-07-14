package nextstep.subway.common.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import nextstep.subway.common.exception.BusinessException;

@RestControllerAdvice
public class CommonControllerAdvice {

	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<CommonResponse> badArgumentException(BusinessException e) {
		return ResponseEntity.ok().body(CommonResponse.fail(e.getMessage(), e.getErrorCode()));
	}

}
