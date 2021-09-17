package com.voicesearch.diarization.controller;

import com.voicesearch.diarization.dto.RecognitionDto;
import com.voicesearch.diarization.dto.ResultDto;
import com.voicesearch.diarization.dto.UserEnrollDto;
import com.voicesearch.diarization.service.userservice.UserServiceImpl;
import org.hibernate.engine.query.spi.ParameterParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

@RestController
public class ApiController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping(value= "/enroll")
    public ResultDto enrollUser(HttpServletRequest requestEntity, @RequestHeader("UserName") String userName) throws UnsupportedAudioFileException, IOException {
        UserEnrollDto userEnrollDto = new UserEnrollDto();
        userEnrollDto.setUserName(userName);

        byte[] voiceSample = IOUtils.readAllBytes(requestEntity.getInputStream());
        userEnrollDto.setVoiceSample(voiceSample);

        return userService.enrollUser(userEnrollDto);
    }

    @PostMapping(value= "/recognize")
    public ResultDto recogniseVoice(HttpServletRequest requestEntity, @RequestHeader("VoiceAssistantName") String voiceAssistantName) throws UnsupportedAudioFileException, IOException {
        RecognitionDto recognitionDto = new RecognitionDto();

        recognitionDto.setVoiceAssistantName(voiceAssistantName);
        byte[] voiceSample = IOUtils.readAllBytes(requestEntity.getInputStream());
        recognitionDto.setVoiceSampleToBeIdentified(voiceSample);

        return userService.recogniseUser(recognitionDto);
    }
}
