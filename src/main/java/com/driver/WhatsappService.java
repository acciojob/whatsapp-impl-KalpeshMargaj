package com.driver;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {
    WhatsappRepository whatsappRepository = new WhatsappRepository();

    public String createUser(String name, String mobile) throws Exception {
        String ans=whatsappRepository.createUser(name,mobile);
        return ans;
    }

    public Group createGroup(List<User> users) {
        Group group = whatsappRepository.createGroup(users);
        return group;
    }

    public int createMessage(String content) {
        int ans = whatsappRepository.createMessage(content);
        return ans;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        int ans = whatsappRepository.sendMessage(message,sender,group);
        return ans;
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        String ans = whatsappRepository.changeAdmin(approver,user,group);
        return ans;
    }

    public int removeUser(User user) {
        int ans=whatsappRepository.removeUser(user);
        return ans;
    }

    public String findMessage(Date start, Date end, int k) {
        String ans = whatsappRepository.findMessage(start,end,k);
        return ans;
    }
}
