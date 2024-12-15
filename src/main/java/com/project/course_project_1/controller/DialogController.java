package com.project.course_project_1.controller;

import com.project.course_project_1.entity.Message;
import com.project.course_project_1.entity.User;
import com.project.course_project_1.service.DialogService;
import com.project.course_project_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DialogController {

    @Autowired
    private DialogService dialogService;

    @Autowired
    private UserService userService;

    @PostMapping("/dialogs/send")
    public String sendMessage(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam String content, Model model) {
        User sender = userService.findUserById(senderId);
        User receiver = userService.findUserById(receiverId);
        Message message = dialogService.sendMessage(sender, receiver, content);
        model.addAttribute("message", message);
        return "messageSent"; // Предполагается, что у вас есть Thymeleaf шаблон messageSent.html
    }

    @GetMapping("/dialogs/between")
    public String getMessagesBetweenUsers(@RequestParam Long userId1, @RequestParam Long userId2, Model model) {
        User user1 = userService.findUserById(userId1);
        User user2 = userService.findUserById(userId2);
        List<Message> messages = dialogService.getMessagesBetweenUsers(user1, user2);
        model.addAttribute("messages", messages);
        return "messagesList"; // Предполагается, что у вас есть Thymeleaf шаблон messagesList.html
    }
}