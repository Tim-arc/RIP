package com.project.course_project_1.service;

import com.project.course_project_1.entity.Dialog;
import com.project.course_project_1.entity.Message;
import com.project.course_project_1.entity.User;
import com.project.course_project_1.repository.DialogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DialogService {

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    public Dialog createDialog(User user1, User user2) {
        Dialog dialog = new Dialog(user1, user2);
        return dialogRepository.save(dialog);
    }

    public Optional<Dialog> getDialog(User user1, User user2) {
        return dialogRepository.findByUser1AndUser2(user1, user2);
    }

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        User sender = userService.findUserById(senderId);
        User receiver = userService.findUserById(receiverId);
        Optional<Dialog> dialogOptional = getDialog(sender, receiver);
        Dialog dialog = dialogOptional.orElseGet(() -> createDialog(sender, receiver));
        Message message = new Message(sender, receiver, content, dialog);
        dialog.addMessage(message);
        return messageService.saveMessage(message);
    }

    public List<Message> getMessagesBetweenUsers(User user1, User user2) {
        Optional<Dialog> dialogOptional = getDialog(user1, user2);
        return dialogOptional.map(dialog -> dialog.getMessages().stream().collect(Collectors.toList())).orElse(List.of());
    }

    public List<Dialog> getDialogsForUser(Long userId) {
        User user = userService.findUserById(userId);
        return dialogRepository.findAllByUser1OrUser2(user, user);
    }

    public Dialog getDialogById(Long id) {
        return dialogRepository.findById(id).orElseThrow(() -> new RuntimeException("Dialog not found"));
    }
}
