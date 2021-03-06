package com.coderhouse.service.impl;

import com.coderhouse.cache.CacheClient;
import com.coderhouse.handle.ApiRestException;
import com.coderhouse.model.document.Message;
import com.coderhouse.repository.MessageRepository;
import com.coderhouse.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;
    private final CacheClient<Message> cache;

    @Override
    public Message create(Message message) {
        try {
            var data = repository.save(message);
            return saveMessageInCache(data);
        } catch (JsonProcessingException e) {
            log.error("Error converting message to string", e);
        }
        return message;
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public Message getMessageById(String id) {
        try {
            var dataFromCache = cache.recover(id, Message.class);
            if (!Objects.isNull(dataFromCache)) {
                return dataFromCache;
            }
            var dataFromDatabase = repository.findById(id)
                    .orElseThrow(ApiRestException::new);
            return saveMessageInCache(dataFromDatabase);
        } catch (JsonProcessingException e) {
            log.error("Error converting message to string", e);
        } catch (ApiRestException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Message saveMessageInCache(Message message) throws JsonProcessingException {
        return cache.save(message.getId(), message);
    }

}
