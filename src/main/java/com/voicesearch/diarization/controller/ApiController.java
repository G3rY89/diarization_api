package com.voicesearch.diarization.controller;

import com.voicesearch.diarization.dto.RecognitionDto;
import com.voicesearch.diarization.dto.ResultDto;
import com.voicesearch.diarization.dto.UserEnrollDto;
import com.voicesearch.diarization.service.userservice.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping(value= "/enroll")
    public ResultDto enrollUser(@RequestBody UserEnrollDto userEnrollDto) {
        return userService.enrollUser(userEnrollDto);
    }

    @PostMapping(value= "/recognize")
    public ResultDto recogniseVoice(RecognitionDto recognitionDto) {
        return userService.recogniseUser(recognitionDto);
    }
}
