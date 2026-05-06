package com.example.flashcash.services;

import com.example.flashcash.DTO.FriendRequestDto;
import com.example.flashcash.models.Friend;
import com.example.flashcash.models.FriendStatus;
import com.example.flashcash.models.User;
import com.example.flashcash.repositories.FriendRepository;
import com.example.flashcash.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.flashcash.utils.ContactUtils.isEmailOrPhone;

@Service
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public FriendService(UserRepository userRepository, FriendRepository friendRepository){
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    public List<Friend> findAllFriendsByUser(User user){
        return friendRepository.findByUserAndStatus(user, FriendStatus.ADDED);
    }

    public List<Friend> findAllFriendsRequestByUser(User user){
        return friendRepository.findByFriendUserAndStatus(user, FriendStatus.PENDING);
    }

    public Friend add(FriendRequestDto friendRequestDto, User sender){
        String isEmailOrPhone = isEmailOrPhone(friendRequestDto.getRecipient());
        User receiver = null;
        if(isEmailOrPhone.contains("email")){
            receiver = userRepository.findByEmail(friendRequestDto.getRecipient()).orElseThrow(() -> new RuntimeException("User not found"));
        }else{
            receiver = userRepository.findByPhoneNumber(friendRequestDto.getRecipient()).orElseThrow(() -> new RuntimeException("User not found"));
        }

        Friend friend = new Friend();
        friend.setFriendUser(receiver);
        friend.setUser(sender);
        friend.setAddedAt(LocalDate.now());
        friend.setStatus(FriendStatus.PENDING);

        friendRepository.save(friend);

        return friend;
    }

//    public Friend accept(){
//
//    }
//
//    public Friend refuse(){
//
//    }
}
