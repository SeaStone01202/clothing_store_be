package com.java6.asm.clothing_store.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ZaloUserRegisterResponse {
    @JsonProperty("id")
    private String zaloId;  // 🔥 ID duy nhất của Zalo
    @JsonProperty("name")
    private String name;    // 🔥 Tên user
    @JsonProperty("picture")
    private String picture; // 🔥 Ảnh đại diện
}
