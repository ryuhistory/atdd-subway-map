package nextstep.subway.common.response;

import static nextstep.subway.common.response.ResultCode.*;

import nextstep.subway.common.exception.errorcode.ErrorCode;

public class CommonResponse<T> {
	private ResultCode resultCode;
	private T data;
	private String message;
	private ErrorCode errorCode;

	public CommonResponse() {
	}

	public CommonResponse(ResultCode resultCode, T data, String message, ErrorCode errorCode) {
		this.resultCode = resultCode;
		this.data = data;
		this.message = message;
		this.errorCode = errorCode;
	}

	public static <T> CommonResponse<T> success() {
		return new CommonResponse(SUCCESS, null, "", null);
	}

	public static <T> CommonResponse<T> success(T data) {
		return new CommonResponse(SUCCESS, data, "", null);
	}

	public static <T> CommonResponse<T> fail(String message, ErrorCode errorCode) {
		return new CommonResponse<>(FAIL, null, message, errorCode);
	}

	public ResultCode getResultCode() {
		return resultCode;
	}

	public T getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
