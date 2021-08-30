package com.voicesearch.diarization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEnrollDto {

    private String userName;
    private byte[] voiceSample;
}
