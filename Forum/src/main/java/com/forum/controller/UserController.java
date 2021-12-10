package com.forum.controller;

import com.forum.forum.MainTheme;
import com.forum.forum.MessageModel;
import com.forum.model.GetMessageModel;
import com.forum.model.MessageForSave;
import com.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/getAllMessages")
    public ResponseEntity<List<MessageModel>> getAllMessages(@RequestBody GetMessageModel request) {
        System.out.println(request);
        return ResponseEntity.ok(userService.getAllMessages(request));
    }

    @GetMapping("/getAllThemes")
    public ResponseEntity<List<MainTheme>> getAllThemes() {
        return ResponseEntity.ok(userService.getAllThemes());
    }

    @PostMapping("/getNewMessages")
    public ResponseEntity<List<MessageModel>> getNewMessages(@RequestBody GetMessageModel request) {
        return ResponseEntity.ok(userService.getNewMessages(request));
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<MessageForSave> saveMessage(@RequestBody MessageForSave model) {
        return ResponseEntity.ok(userService.saveMessage(model));
    }

}
