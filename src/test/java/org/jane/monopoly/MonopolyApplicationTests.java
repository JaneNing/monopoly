package org.jane.monopoly;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jane.monopoly.user.dto.UserLoginInfo;
import org.jane.monopoly.websocket.dto.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MonopolyApplicationTests {

    @Test
    void contextLoads() {
        ObjectMapper mapper = new ObjectMapper();

        UserLoginInfo userLoginInfo = UserLoginInfo.builder().userId(1).build();
        String data = null;
        try {
            data = mapper.writeValueAsString(userLoginInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Message message = Message.builder().type("userLogin").data(data).build();

        try {
            String s = mapper.writeValueAsString(message);
            System.out.println(s);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
