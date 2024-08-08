package com.c201.aebook.utils.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	/* 400 BAD_REQUEST : 클라이언트 요청이 유효하지 않아 더 이상 작업을 진행하지 않는 경우 */
	DUPLICATED_REVIEW(BAD_REQUEST, "이미 해당 도서에 서평을 작성하였습니다"), // 희주
	INVALID_ISBN(BAD_REQUEST, "유효하지 않은 ISBN입니다."), // 희주
	INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
	MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
	DUPLICATED_NOTIFICATION(BAD_REQUEST, "이미 해당 도서에 알림 신청을 하였습니다"),

	/* 401 UNAUTHORIZED : 클라이언트가 요청한 리소스에 대한 인증 정보가 없거나 잘못된 경우 */
	INVALID_CLIENT_TOKEN(UNAUTHORIZED, "유효하지 않은 클라이언트 토큰입니다."), // 희주

	/* 403 FORBIDDEN : 클라이언트가 요청한 리소스에 대한 권한이 없는 경우 */
	FORBIDDEN_USER(FORBIDDEN, "권한이 없는 요청입니다."), // 희주

	/* 404 NOT_FOUND : 해당하는 DATA를 찾을 수 없음 */
	USER_NOT_FOUND(NOT_FOUND, "해당 사용자 정보를 찾을 수 없습니다"), // 희주
	BOOK_NOT_FOUND(NOT_FOUND, "존재하지 않는 도서입니다."), // 희주

	STORY_NOT_FOUND(NOT_FOUND, "존재하지 않는 동화입니다."), // 예진

	REVIEW_NOT_FOUND(NOT_FOUND, "존재하지 않는 서평입니다."), // 희주
	REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
	PAINTING_NOT_FOUND(NOT_FOUND, "존재하지 않는 그림입니다."),
	NOTIFICATION_NOT_FOUND(NOT_FOUND, "존재하지 않는 알림입니다."),

	/* 409 CONFLICT : 클라이언트의 요청이 서버 상태와 충돌하는 경우 */
	DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다.");

	private final HttpStatus httpStatus;
	private final String message;
}
