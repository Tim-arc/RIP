package com.project.course_project_1.controller;

import com.project.course_project_1.entity.Message;
import com.project.course_project_1.entity.User;
import com.project.course_project_1.service.DialogService;
import com.project.course_project_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private DialogService dialogService;

    @Autowired
    private UserService userService;

    @PostMapping("/messages/sendMessage")
    public String sendMessage(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam String content, Model model) {
        Message message = dialogService.sendMessage(senderId, receiverId, content);
        model.addAttribute("message", message);
        return "redirect:/dialogs/" + message.getDialog().getId();
    }

    @GetMapping("/messages/between")
    public String getMessagesBetweenUsers(@RequestParam Long userId1, @RequestParam Long userId2, Model model) {
        User user1 = userService.findUserById(userId1);
        User user2 = userService.findUserById(userId2);
        List<Message> messages = dialogService.getMessagesBetweenUsers(user1, user2);
        model.addAttribute("messages", messages);
        return "messagesList";
    }
}
