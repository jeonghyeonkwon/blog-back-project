package com.jeonghyeon.blogapi.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
public class SecurityUtil {

    public static Optional<String> getCurrnetAccountId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null){
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }
        String uuid = null;
        if(authentication.getPrincipal() instanceof UserDetails){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            uuid = userDetails.getUsername();

        }
        return Optional.ofNullable(uuid);
    }
}
