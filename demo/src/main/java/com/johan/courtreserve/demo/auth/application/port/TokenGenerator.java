package com.johan.courtreserve.demo.auth.application.port;

import com.johan.courtreserve.demo.user.domain.model.User;

//Output Port
public interface TokenGenerator {
    String generateAccessToken(User user);
}