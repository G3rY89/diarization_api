package com.voicesearch.diarization.service.userservice;

import com.voicesearch.diarization.dto.RecognitionDto;
import com.voicesearch.diarization.dto.ResultDto;
import com.voicesearch.diarization.dto.UserEnrollDto;
import com.voicesearch.diarization.model.UserEnroll;
import com.voicesearch.diarization.repository.UserRepository;
import com.voicesearch.diarization.util.recognito.Recognito;
import com.voicesearch.diarization.util.recognito.VoicePrint;
import org.springframework.stereotype.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository service){
        this.userRepository = service;
    }

    @Override
    public ResultDto enrollUser(UserEnrollDto userEnrollDto) throws UnsupportedAudioFileException, IOException {

        ResultDto result = new ResultDto();

        Recognito<String> recognito = new Recognito<String>(16000.0f);

        VoicePrint print = recognito.createVoicePrint(userEnrollDto.getUserName(), new File(String.valueOf(userEnrollDto.getVoiceSample())));

        UserEnroll dbUserModel = new UserEnroll();
        dbUserModel.setUserName(userEnrollDto.getUserName());
        dbUserModel.setVoiceSample(print.toString());

        UserEnroll savedUser = userRepository.save(dbUserModel);

        if(savedUser != null){
            result.setSuccess(true);
            result.setMessage("Sikeres mentés!");
        } else {
            result.setSuccess(false);
            result.setMessage("Sikertelen mentés");
        }

        return result;
    }

    @Override
    public ResultDto recogniseUser(RecognitionDto recognitionDto) {

        ResultDto result = new ResultDto();

        UserEnroll voiceAssistant = userRepository.getByUserName(recognitionDto.getVoiceAssistantName());

        Recognito<String> recognito = new Recognito(16000.0f);

        return null;
    }
}
