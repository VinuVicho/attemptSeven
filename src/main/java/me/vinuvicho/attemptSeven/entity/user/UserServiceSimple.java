package me.vinuvicho.attemptSeven.entity.user;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceSimple {

    private UserDao userDao;

    public void onlyBlockUser(User mainUser, User toBlock) {
        Set<User> blockedUsers = new HashSet<>();
        if (mainUser.getBlockedUsers() != null) {
            blockedUsers = mainUser.getBlockedUsers();
        }
        blockedUsers.add(toBlock);
        mainUser.getSubscribedTo().remove(toBlock);         //not sure if that works
        toBlock.getSubscribedTo().remove(mainUser);
        mainUser.getSubscribers().remove(toBlock);
        toBlock.getSubscribers().remove(mainUser);
        mainUser.setBlockedUsers(blockedUsers);
        userDao.save(mainUser);
        userDao.save(toBlock);
    }

    public void onlyAddUser(User mainUser, User toFollow) {

        Set<User> subscribedTo = new HashSet<>();
        Set<User> subscribers = new HashSet<>();
        if (mainUser.getSubscribedTo() != null) {
            subscribedTo = mainUser.getSubscribedTo();
        }
        if ((mainUser.getBlockedUsers() != null && mainUser.getBlockedUsers().contains(toFollow)) ||        //check does it work
                toFollow.getBlockedUsers() != null && toFollow.getBlockedUsers().contains(mainUser)) {
            throw new IllegalStateException("User blocked");
        }
        justAddFriend(mainUser, toFollow, subscribedTo, subscribers, userDao);
    }

    static void justAddFriend(User mainUser, User toFollow, Set<User> subscribedTo, Set<User> subscribers, UserDao userDao) {
        subscribedTo.add(toFollow);
        mainUser.setSubscribedTo(subscribedTo);
        if (toFollow.getSubscribers() != null) {
            subscribers = toFollow.getSubscribers();
        }
        subscribers.add(mainUser);
        toFollow.setSubscribers(subscribers);

        userDao.save(mainUser);
        userDao.save(toFollow);
    }

}
