package com.voicesearch.diarization.service.userservice;

import com.voicesearch.diarization.dto.RecognitionDto;
import com.voicesearch.diarization.dto.ResultDto;
import com.voicesearch.diarization.dto.UserEnrollDto;
import com.voicesearch.diarization.model.UserEnroll;
import com.voicesearch.diarization.repository.UserRepository;
import com.voicesearch.diarization.util.recognito.MatchResult;
import com.voicesearch.diarization.util.recognito.Recognito;
import com.voicesearch.diarization.util.recognito.VoicePrint;
import org.springframework.stereotype.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository service){
        this.userRepository = service;
    }

    @Override
    public ResultDto enrollUser(UserEnrollDto userEnrollDto) throws UnsupportedAudioFileException, IOException {

        ResultDto result = new ResultDto();

        Recognito<String> recognito = new Recognito<String>(48000.0f);

        File wavAudioFile = new File("VoiceSample.wav");
        try
        {
            FileOutputStream os = new FileOutputStream(wavAudioFile, true);
            os.write(userEnrollDto.getVoiceSample());
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        VoicePrint print = recognito.createVoicePrint(userEnrollDto.getUserName(), wavAudioFile);

        UserEnroll dbUserModel = new UserEnroll();
        dbUserModel.setUserName(userEnrollDto.getUserName());
        dbUserModel.setVoiceSample(print.toString());

        UserEnroll savedUser = userRepository.save(dbUserModel);

        if(savedUser != null){
            result.setSuccess(true);
            result.setMessage("Sikeres mentés!");
            wavAudioFile.delete();

        } else {
            result.setSuccess(false);
            result.setMessage("Sikertelen mentés");
        }

        return result;
    }

    @Override
    public ResultDto recogniseUser(RecognitionDto recognitionDto) throws UnsupportedAudioFileException, IOException {

        ResultDto result = new ResultDto();
        result.setSuccess(false);

        Recognito<String> recognito = new Recognito(48000.0f);

        File voiceToBeIdentified = new File("VoiceToBeIdentified.wav");
        try
        {
            FileOutputStream os = new FileOutputStream(voiceToBeIdentified, true);
            os.write(recognitionDto.getVoiceSampleToBeIdentified());
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        List<UserEnroll> storedUsers = userRepository.findAll();

        Map<String, VoicePrint> storedVoicePrints = new HashMap<String, VoicePrint>();

        for(UserEnroll storedUser : storedUsers){
            String[] res = storedUser.getVoiceSample().substring(1, storedUser.getVoiceSample().length() - 1).split(",");
            VoicePrint vp = new VoicePrint(Arrays.stream(res).mapToDouble(Double::parseDouble).toArray());
            storedVoicePrints.put(storedUser.getUserName(), vp);
        }

        List<MatchResult<String>> matches = recognito.identify(voiceToBeIdentified, recognitionDto.getVoiceAssistantName(), storedVoicePrints);
        MatchResult<String> match = matches.get(0);

        if(match.getKey().equals(recognitionDto.getVoiceAssistantName())) {
            result.setSuccess(true);
            result.setMessage("Stored voice is matching with the uploaded voice");
        } else {
            result.setSuccess(true);
            result.setMessage("Stored voice is not matching with the uploaded voice");
        }
        voiceToBeIdentified.delete();

        return result;
    }

    private static double[] toDoubleArray(byte[] byteArray){
        double[] doubles = new double[byteArray.length / 3];
        for (int i = 0, j = 0; i != doubles.length; ++i, j += 3) {
            doubles[i] = (double)( (byteArray[j  ] & 0xff) |
                    ((byteArray[j+1] & 0xff) <<  8) |
                    ( byteArray[j+2]         << 16));
        }
        return doubles;
    }
}
