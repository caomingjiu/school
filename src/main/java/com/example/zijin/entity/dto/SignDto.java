package com.example.zijin.entity.dto;

import lombok.*;

/**
 * @author Tao
 * @version 1.0
 * @ClassName SignDto
 * @Description TODO
 * @date 2021-04-04 10:56
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignDto {
    @NonNull
    private String mobile;
    private String password;
    private String sms;
}
