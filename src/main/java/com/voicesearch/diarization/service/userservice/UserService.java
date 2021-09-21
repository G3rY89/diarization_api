package com.voicesearch.diarization.service.userservice;

import com.voicesearch.diarization.dto.RecognitionDto;
import com.voicesearch.diarization.dto.ResultDto;
import com.voicesearch.diarization.dto.UserEnrollDto;
import com.voicesearch.diarization.util.recognito.MatchResult;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface UserService {
    ResultDto enrollUser(UserEnrollDto userEnrollDto) throws UnsupportedAudioFileException, IOException;
    MatchResult<String> recogniseUser(RecognitionDto recognitionDto) throws UnsupportedAudioFileException, IOException;
}
