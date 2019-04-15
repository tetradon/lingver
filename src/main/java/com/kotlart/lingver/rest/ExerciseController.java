package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.dto.ExerciseHistoryDto;
import com.kotlart.lingver.model.dto.ExerciseItemDto;
import com.kotlart.lingver.model.dto.ExerciseResultDto;
import com.kotlart.lingver.service.ExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity wordTranslation(@RequestParam("ids") List<Long> translationIds) {
        final List<ExerciseItemDto> exerciseItems = exerciseService.generateWordTranslationTrainingSet(translationIds);
        return ResponseEntity.ok(exerciseItems);
    }

    @GetMapping(value = "/translation-word")
    public ResponseEntity translationWord(@RequestParam("ids") List<Long> translationIds) {
        final List<ExerciseItemDto> exerciseItems = exerciseService.generateTranslationWordTrainingSet(translationIds);
        return ResponseEntity.ok(exerciseItems);
    }

    @PostMapping(value = "/result")
    public ResponseEntity saveSingleResult(@RequestBody ExerciseResultDto result) {
        final ExerciseHistoryDto dto = modelMapper.map(exerciseService.saveResult(result), ExerciseHistoryDto.class);
        return ResponseEntity.ok(dto);
    }

}
