package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.ExerciseItem;
import com.kotlart.lingver.service.ExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("exercise")
public class ExerciseController {

    private final ModelMapper modelMapper;

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ModelMapper modelMapper, ExerciseService exerciseService) {
        this.modelMapper = modelMapper;
        this.exerciseService = exerciseService;
    }

    @GetMapping(value = "/word-translation")
    public ResponseEntity test(@RequestParam("ids") List<Long> translationIds) {
        final List<ExerciseItem> exerciseItems = exerciseService.generateWordTranslationTrainingSet(translationIds);
        return ResponseEntity.ok(exerciseItems);
    }
}
