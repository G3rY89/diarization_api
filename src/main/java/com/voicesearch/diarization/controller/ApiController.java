package com.voicesearch.diarization.controller;

import com.voicesearch.diarization.dto.RecognitionDto;
import com.voicesearch.diarization.dto.ResultDto;
import com.voicesearch.diarization.dto.UserEnrollDto;
import com.voicesearch.diarization.service.userservice.UserServiceImpl;
import com.voicesearch.diarization.util.recognito.MatchResult;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Base64;

@RestController
public class ApiController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping(value= "/enroll")
    @CrossOrigin(origins = "https://dev.mynds.ai")
    public ResultDto enrollUser(HttpServletRequest requestEntity, @RequestHeader("UserName") String userName) throws UnsupportedAudioFileException, IOException {
        UserEnrollDto userEnrollDto = new UserEnrollDto();
        userEnrollDto.setUserName(userName);

        byte[] voiceSample = IOUtils.toByteArray(requestEntity.getInputStream());
        userEnrollDto.setVoiceSample(voiceSample);

        return userService.enrollUser(userEnrollDto);
    }

    @PostMapping(value= "/recognize", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @CrossOrigin(origins = "https://dev.mynds.ai")
    public MatchResult<String> recogniseVoice(HttpServletRequest requestEntity, @RequestParam("base64String") MultipartFile file, @RequestHeader("VoiceAssistantName") String voiceAssistantName) throws UnsupportedAudioFileException, IOException {
        RecognitionDto recognitionDto = new RecognitionDto();
//        String base64String = requestEntity.getParameter("base64String");
//        System.out.println(base64String);
//        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByte = file.getBytes();

        recognitionDto.setVoiceAssistantName(voiceAssistantName);
        recognitionDto.setVoiceSampleToBeIdentified(decodedByte);

        return userService.recogniseUser(recognitionDto);
    }
}
