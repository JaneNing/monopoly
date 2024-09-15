package org.jane.monopoly.log;

import org.springframework.stereotype.Component;

@Component
public class StdoutLogService implements LogService {
    @Override
    public void Log(String msg) {
        System.out.println(msg);
    }
}
