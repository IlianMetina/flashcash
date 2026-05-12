package com.example.flashcash.services;

import com.example.flashcash.DTO.FriendRequestDto;
import com.example.flashcash.DTO.FriendResponseDto;
import com.example.flashcash.models.Friend;
import com.example.flashcash.models.FriendStatus;
import com.example.flashcash.models.User;
import com.example.flashcash.repositories.FriendRepository;
import com.example.flashcash.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<Friend> findPendingRequests(User user) {
        return friendRepository.findByFriendUserAndStatus(user, FriendStatus.PENDING);
    }

    public List<FriendResponseDto> findAllAcceptedFriends(User user){
        List<Friend> sentAndAccepted = friendRepository.findByUserAndStatus(user, FriendStatus.ADDED);
        List<Friend> receivedAndAccepted = friendRepository.findByFriendUserAndStatus(user, FriendStatus.ADDED);

        List<Friend> allFriends = new ArrayList<>();
        allFriends.addAll(sentAndAccepted);
        allFriends.addAll(receivedAndAccepted);

        List<FriendResponseDto> result = new ArrayList<>();
        for (Friend friend : allFriends){
            FriendResponseDto dto = new FriendResponseDto();
            User other = friend.getUser().getId().equals(user.getId()) ? friend.getFriendUser() : friend.getUser();
            dto.setFirstName(other.getFirstName());
            dto.setLastName(other.getLastName());
            dto.setEmail(other.getEmail());
            dto.setPhoneNumber(other.getPhoneNumber());
            dto.setAddedAt(friend.getAddedAt());
            result.add(dto);
        }


        return result;
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

    public void refuse(Long friendId){
        friendRepository.deleteById(friendId);
    }

    public void accept(Long friendId){
        Friend friend = friendRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Friend not found"));
        friend.setStatus(FriendStatus.ADDED);
        friendRepository.save(friend);
    }

    public Friend findById(Long id){
        return friendRepository.findById(id).orElseThrow(() -> new RuntimeException("Friend not found"));
    }
}
