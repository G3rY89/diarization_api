package com.voicesearch.diarization.controller;

import com.voicesearch.diarization.dto.RecognitionDto;
import com.voicesearch.diarization.dto.ResultDto;
import com.voicesearch.diarization.dto.UserEnrollDto;
import com.voicesearch.diarization.service.userservice.UserServiceImpl;
import com.voicesearch.diarization.util.recognito.MatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@RestController
public class ApiController {

    @Autowired
    UserServiceImpl userService;

    /**
     * Enroll an entity's voice into database
     * @param requestEntity
     * @param userName
     * @return The result of the insertion
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    @PostMapping(value= "/enroll")
    @CrossOrigin(origins = "https://localhost")
    public ResultDto enrollUser(HttpServletRequest requestEntity, @RequestHeader("UserName") String userName) throws UnsupportedAudioFileException, IOException {
        UserEnrollDto userEnrollDto = new UserEnrollDto();
        userEnrollDto.setUserName(userName);

        InputStream stream = requestEntity.getInputStream();

        byte[] voiceSample = stream.readAllBytes();
        userEnrollDto.setVoiceSample(voiceSample);

        return userService.enrollUser(userEnrollDto);
    }

    /**
     * Recognise a voice sample
     * @param requestEntity
     * @param voiceAssistantName
     * @return
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    @PostMapping(value= "/recognize")
    @CrossOrigin(origins = "https://localhost")
    public MatchResult<String> recogniseVoice(HttpServletRequest requestEntity, @RequestHeader("VoiceAssistantName") String voiceAssistantName) throws UnsupportedAudioFileException, IOException {
        RecognitionDto recognitionDto = new RecognitionDto();

        recognitionDto.setVoiceAssistantName(voiceAssistantName);
        byte[] voiceSample = IOUtils.readAllBytes(requestEntity.getInputStream());
        recognitionDto.setVoiceSampleToBeIdentified(voiceSample);

        return userService.recogniseUser(recognitionDto);
    }
}
