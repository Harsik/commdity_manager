/**
 * ========================================================
 *
 * @FileName : NotFoundException.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 쿼리 검색 중 값이 없을 때 생성되는 오류를 제어하기 위한 클래스
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
     * 런타임 예외 오류 시 출력할 수 있는 메세지를 입력 받아 오류 제어하는 메소드
     *
     * @param message
     * @history 2020 02 19 최초 생성
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * 런타임 예외 오류 시 출력할 수 있는 메세지와 원인을 입력 받아 오류 제어하는 메소드
     *
     * @param message
     * @param cause
     * @history 2020 02 19 최초 생성
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}