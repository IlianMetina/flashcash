package com.example.flashcash.controllers;

import com.example.flashcash.DTO.FriendRequestDto;
import com.example.flashcash.models.Friend;
import com.example.flashcash.models.User;
import com.example.flashcash.services.FriendService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/contact")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService){
        this.friendService = friendService;
    }

    @GetMapping
    public String contactPage(@AuthenticationPrincipal User user, Model model){
        List<Friend> allFriendsRequest = friendService.findAllFriendsByUser(user);
        List<Friend> allFriends = friendService.findAllFriendsRequestByUser(user);
        model.addAttribute("allFriendsRequests", allFriendsRequest);
        model.addAttribute("allRequests", allFriends);
        return "contact";
    }

    @PostMapping("add")
    public String addContact(@Valid @ModelAttribute FriendRequestDto friendRequestDto, @AuthenticationPrincipal User user, BindingResult result, Model model){
        if(result.hasErrors()) return "contact";
        friendService.add(friendRequestDto, user);
        return "redirect:/contact";
    }

}
