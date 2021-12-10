package com.forum.service;

import com.forum.forum.MainTheme;
import com.forum.forum.MessageModel;
import com.forum.model.GetMessageModel;
import com.forum.model.MessageForSave;
import com.forum.repo.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    ForumRepository forumRepository;

    public List<MessageModel> getAllMessages(GetMessageModel getMessageModel) {
        return forumRepository.getAllMessages(getMessageModel.getMainTheme(), getMessageModel.getSubTheme());
    }

    public List<MessageModel> getNewMessages(GetMessageModel getMessageModel) {
        return forumRepository.getNewestMessages(getMessageModel.getMainTheme(), getMessageModel.getSubTheme(), getMessageModel.getLastSeenTime());
    }

    public List<MainTheme> getAllThemes() {
        return forumRepository.getAllThemes();
    }

    public MessageForSave saveMessage(MessageForSave messageModel) {
        forumRepository
                .getSubThemeByName(messageModel.getMainThemeName(), messageModel.getSubThemeName())
                .getMessageModelList().add(MessageModel
                        .builder()
                        .message(messageModel.getMessage())
                        .userName(messageModel.getUserName())
                        .dateTime(LocalDateTime.now())
                        .build());
        return messageModel;
    }

}
