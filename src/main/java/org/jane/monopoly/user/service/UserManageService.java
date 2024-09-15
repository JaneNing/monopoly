package org.jane.monopoly.user.service;

import lombok.RequiredArgsConstructor;
import org.jane.monopoly.log.LogService;
import org.jane.monopoly.user.dto.UserLoginInfo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserManageService {

    private final LogService logService;

    private Map<Integer, String> userIdToSessionIdMap = new ConcurrentHashMap<>();
    private Map<String, Integer> sessionIdToUserIdMap = new ConcurrentHashMap<>();

    public void userLogin(UserLoginInfo user, String sessionId) {
        userIdToSessionIdMap.putIfAbsent(user.getUserId(), sessionId);
        sessionIdToUserIdMap.putIfAbsent(sessionId, user.getUserId());

        logService.Log(String.format("UserId %d login with sessionId %s", user.getUserId(), sessionId));
    }

    public void userLogout(String sessionId) {
        Integer userId = sessionIdToUserIdMap.remove(sessionId);
        if (userId != null) {
            userIdToSessionIdMap.remove(userId);
        }

        logService.Log(String.format("UserId %d logout with sessionId %s", userId, sessionId));
    }
}
