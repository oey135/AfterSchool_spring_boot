package com.mirim.board.login;

import com.mirim.board.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {


    // 컨트롤러에 가기 전에 처리해줘~ 로직
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 읽기는 로그인 상관없어~
        if (request.getMethod().equals("GET") && request.getRequestURI().startsWith("/posts")) {}
        // 세션이 없으면 만들지 마
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER_ID) == null) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        // 통과 -> 컨트롤러로 요청을 보냄
        return true;
    }
}
