package com.example.flashcash.repositories;

import com.example.flashcash.models.Friend;
import com.example.flashcash.models.FriendStatus;
import com.example.flashcash.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUserAndStatus(User user, FriendStatus status);
    List<Friend> findByFriendUserAndStatus(User friendUser, FriendStatus status);
}
