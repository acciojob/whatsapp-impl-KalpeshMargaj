package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String name, String mobile) throws Exception {
        if(userMobile.contains(mobile))
        {
            throw new Exception("User already exists");
        }
        else
        {
            userMobile.add(mobile);
            User user=new User(name,mobile);
            return "SUCCESS";
        }
    }

    public Group createGroup(List<User> users) {
        if(users.size()==2)
        {
            Group group =new Group();
            group.setName(users.get(1).getName());
            group.setNumberOfParticipants(2);
            groupUserMap.put(group,users);
            adminMap.put(group,users.get(0));
            groupMessageMap.put(group,new ArrayList<Message>());
            return group;
        }
        this.customGroupCount++;
        Group group = new Group();
        group.setName("Group "+this.customGroupCount);
        group.setNumberOfParticipants(users.size());
        groupUserMap.put(group,users);
        adminMap.put(group,users.get(0));
        groupMessageMap.put(group,new ArrayList<Message>());
        return group;
    }

    public int createMessage(String content) {
        this.messageId++;
        Message message = new Message();
        message.setId(messageId);
        message.setContent(content);
        return message.getId();
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        if(groupUserMap.containsKey(group))
        {
            List<User> l= groupUserMap.get(group);
            if(l.contains(sender))
            {
                List<Message> list= groupMessageMap.get(group);
                list.add(message);
                groupMessageMap.put(group,list);
                senderMap.put(message,sender);
                return list.size();
            }
            throw new Exception("You are not allowed to send message");
        }
        throw new Exception("Group does not exist");
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        if(groupUserMap.containsKey(group))
        {
            if(approver==adminMap.get(group))
            {
                List<User> list = groupUserMap.get(group);
                if(list.contains(user))
                {
                    adminMap.put(group,user);
                    return "SUCCESS";
                }
                throw new Exception("User is not a participant");
            }
            throw new Exception("Approver does not have rights");
        }
        throw new Exception("Group does not exist");
    }

    public int removeUser(User user) {
        return 0;
    }

    public String findMessage(Date start, Date end, int k) {
        return "Group does not exist";
    }
}
