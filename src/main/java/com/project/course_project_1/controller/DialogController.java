package com.project.course_project_1.controller;

import com.project.course_project_1.entity.Dialog;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DialogController {

    @Autowired
    private DialogService dialogService;

    @Autowired
    private UserService userService;

    @GetMapping("/dialogs")
    public String showDialogs(Model model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.findUserByUsername(username);
        List<Dialog> dialogs = dialogService.getDialogsForUser(currentUser.getId());
        model.addAttribute("dialogs", dialogs);
        return "dialogs";
    }

    @GetMapping("/dialogs/{id}")
    public String showChat(@PathVariable Long id, Model model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.findUserByUsername(username);
        Dialog dialog = dialogService.getDialogById(id);
        List<Message> messages = dialogService.getMessagesBetweenUsers(dialog.getUser1(), dialog.getUser2());
        model.addAttribute("dialog", dialog);
        model.addAttribute("messages", messages);
        model.addAttribute("currentUser", currentUser);
        return "chat";
    }

    @PostMapping("/messages/send")
    public String sendMessage(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam String content, Model model) {
        Message message = dialogService.sendMessage(senderId, receiverId, content);
        model.addAttribute("message", message);
        return "redirect:/dialogs/" + message.getDialog().getId();
    }

    @PostMapping("/dialogs/start/{friendId}")
    public String startChat(@PathVariable Long friendId, Model model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.findUserByUsername(username);
        User friend = userService.findUserById(friendId);
        Dialog dialog = dialogService.getDialog(currentUser, friend).orElseGet(() -> dialogService.createDialog(currentUser, friend));
        return "redirect:/dialogs/" + dialog.getId();
    }
}
