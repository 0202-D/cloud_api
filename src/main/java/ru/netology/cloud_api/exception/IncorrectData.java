package ru.netology.cloud_api.exception;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IncorrectData {
    private String info;
}
