package com.quest.controllers.rest;

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

import com.quest.dto.rest.Question.QuestionCreateDTO;
import com.quest.dto.rest.Question.QuestionResponseDTO;
import com.quest.dto.rest.Question.QuestionUpdateDTO;
import com.quest.services.rest.QuestionServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/questions")
@Tag(name = "Question", description = "Question management")
public class QuestionController {
    private final QuestionServices questionServices;

    @Autowired
    public QuestionController(QuestionServices questionServices) {
        this.questionServices = questionServices;
    }

    @PostMapping
    @Operation(summary = "Create a new question")
    public ResponseEntity<QuestionResponseDTO> createQuestion(@RequestBody QuestionCreateDTO questionCreateDTO) {
        QuestionResponseDTO createdQuestion = questionServices.create(questionCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @PostMapping("/many")
    @Operation(summary = "Create multiple questions")
    public ResponseEntity<List<QuestionResponseDTO>> createManyQuestions(
            @RequestBody List<QuestionCreateDTO> questionCreateDTOs) {
        List<QuestionResponseDTO> createdQuestions = questionServices.createMany(questionCreateDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestions);
    }

    @GetMapping
    @Operation(summary = "Get all questions")
    public ResponseEntity<List<QuestionResponseDTO>> findAllQuestions() {
        List<QuestionResponseDTO> questions = questionServices.findAll();
        return ResponseEntity.ok(questions);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing question")
    public ResponseEntity<QuestionResponseDTO> updateQuestion(@PathVariable long id,
            @Valid @RequestBody QuestionUpdateDTO questionUpdateDTO) {

        questionUpdateDTO.setId(id);
        QuestionResponseDTO updatedQuestion = questionServices.update(questionUpdateDTO);

        return ResponseEntity.ok(updatedQuestion);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a question by ID")
    public ResponseEntity<QuestionResponseDTO> findQuestionById(@PathVariable long id) {
        QuestionResponseDTO question = questionServices.findById(id);
        return ResponseEntity.ok(question);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a question by ID")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
