package com.Quest.quest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Quest.quest.dto.Question.QuestionCreateDTO;
import com.Quest.quest.dto.Question.QuestionResponseDTO;
import com.Quest.quest.dto.Question.QuestionUpdateDTO;
import com.Quest.quest.services.QuestionServices;

import jakarta.validation.Valid;

@RestController
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

    @GetMapping
    public ResponseEntity<List<QuestionResponseDTO>> findAllQuestions() {
        List<QuestionResponseDTO> questions = questionServices.findAll();
        return ResponseEntity.ok(questions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> updateQuestion(@PathVariable long id,
            @Valid @RequestBody QuestionUpdateDTO questionUpdateDTO) {

        questionUpdateDTO.setId(id);
        QuestionResponseDTO updatedQuestion = questionServices.update(questionUpdateDTO);

        return ResponseEntity.ok(updatedQuestion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> findQuestionById(@PathVariable long id) {
        QuestionResponseDTO question = questionServices.findById(id);
        return ResponseEntity.ok(question);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
