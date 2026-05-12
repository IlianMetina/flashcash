package com.example.flashcash.controllers;

import com.example.flashcash.DTO.FriendRequestDto;
import com.example.flashcash.DTO.FriendResponseDto;
import com.example.flashcash.models.Friend;
import com.example.flashcash.models.User;
import com.example.flashcash.services.FriendService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        List<FriendResponseDto> allFriends = friendService.findAllAcceptedFriends(user);
        List<Friend> allRequests = friendService.findPendingRequests(user);
        model.addAttribute("allFriendsRequests", allFriends);
        model.addAttribute("allRequests", allRequests);
        model.addAttribute("friendRequestDto", new FriendRequestDto());
        return "contact";
    }

    @PostMapping("add")
    public String addContact(@Valid @ModelAttribute FriendRequestDto friendRequestDto, BindingResult result, @AuthenticationPrincipal User user, Model model){
        if(result.hasErrors()){
            model.addAttribute("allFriendsRequests", friendService.findAllAcceptedFriends(user));
            model.addAttribute("allRequests", friendService.findPendingRequests(user));
            model.addAttribute("friendRequestDto", friendRequestDto);
            return "contact";
        }
        try{
            friendService.add(friendRequestDto, user);
        } catch (RuntimeException e) {
            model.addAttribute("allFriendsRequests", friendService.findAllAcceptedFriends(user));
            model.addAttribute("allRequests", friendService.findPendingRequests(user));
            model.addAttribute("friendRequestDto", friendRequestDto);
            model.addAttribute("error", "Aucun utilisateur trouvé pour cet email ou ce téléphone.");
            return "contact";
        }
        return "redirect:/contact";
    }

    @PostMapping("refuse")
    public String refuseRequest(@RequestParam Long friendId){

        friendService.refuse(friendId);
        return "redirect:/contact";
    }

    @PostMapping("accept")
    public String acceptRequest(@RequestParam Long friendId){

        friendService.accept(friendId);
        return "redirect:/contact";
    }

}
