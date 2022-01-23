package com.voicesearch.diarization.controller;

import com.voicesearch.diarization.dto.RecognitionDto;
import com.voicesearch.diarization.dto.ResultDto;
import com.voicesearch.diarization.dto.UserEnrollDto;
import com.voicesearch.diarization.service.userservice.UserServiceImpl;
import com.voicesearch.diarization.util.recognito.MatchResult;
import org.hibernate.engine.query.spi.ParameterParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping(value= "/enroll")
    @CrossOrigin(origins = "https://localhost")
    public ResultDto enrollUser(HttpServletRequest requestEntity, @RequestHeader("UserName") String userName) throws UnsupportedAudioFileException, IOException {
        UserEnrollDto userEnrollDto = new UserEnrollDto();
        userEnrollDto.setUserName(userName);

        byte[] voiceSample = IOUtils.readAllBytes(requestEntity.getInputStream());
        userEnrollDto.setVoiceSample(voiceSample);

        return userService.enrollUser(userEnrollDto);
    }

    @PostMapping(value= "/recognize")
    @CrossOrigin(origins = "https://localhost")
    public MatchResult<String> recogniseVoice(HttpServletRequest requestEntity, @RequestHeader("VoiceAssistantName") String voiceAssistantName) throws UnsupportedAudioFileException, IOException {
        RecognitionDto recognitionDto = new RecognitionDto();
        String base64String = requestEntity.getParameter("base64String");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByte = decoder.decode(base64String);

        recognitionDto.setVoiceAssistantName(voiceAssistantName);
        recognitionDto.setVoiceSampleToBeIdentified(decodedByte);

        return userService.recogniseUser(recognitionDto);
    }
}
