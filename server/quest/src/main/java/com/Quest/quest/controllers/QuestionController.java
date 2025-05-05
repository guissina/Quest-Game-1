package com.Quest.quest.controllers;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Quest.quest.dto.Question.QuestionCreateDTO;
import com.Quest.quest.dto.Question.QuestionResponseDTO;
import com.Quest.quest.dto.Question.QuestionUpdateDTO;
import com.Quest.quest.services.QuestionServices;

@Mapper(componentModel = "spring")
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionServices questionServices;

    @Autowired
    public QuestionController(QuestionServices questionServices) {
        this.questionServices = questionServices;
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDTO> createQuestion(@RequestBody QuestionCreateDTO questionCreateDTO) {
        QuestionResponseDTO createdQuestion = questionServices.create(questionCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> updateQuestion(@RequestBody QuestionUpdateDTO questionUpdateDTO) {
        QuestionResponseDTO updatedQuestion = questionServices.update(questionUpdateDTO);
        return ResponseEntity.ok(updatedQuestion);
    }

    @PostMapping("/findById")
    public ResponseEntity<QuestionResponseDTO> findQuestionById(@RequestBody long id) {
        QuestionResponseDTO question = questionServices.findById(id);
        return ResponseEntity.ok(question);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteQuestion(@RequestBody Long id) {
        questionServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
