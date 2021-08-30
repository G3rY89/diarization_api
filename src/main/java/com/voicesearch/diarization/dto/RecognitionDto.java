package com.voicesearch.diarization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecognitionDto {

    private String voiceAssistantName;
    private byte[] voiceSampleToBeIdentified;
}
