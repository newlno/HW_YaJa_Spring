package com.example.YaJaSpring.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 리스폰스 엔티티 / 자바에서 제공한 기능 / 리스폰스디티오 안써도 괜찮을듯?


@Getter
@AllArgsConstructor
public class ResponseDto<T> {
  private boolean success;
  private T data;
  private Error error;

  public static <T> ResponseDto<T> success(T data) {
    return new ResponseDto<>(true, data, null);
  }

  public static <T> ResponseDto<T> fail(String code, String message) {
    return new ResponseDto<>(false, null, new Error(code, message));
  }

  @Getter
  @AllArgsConstructor
  static class Error {
    private String code;
    private String message;
  }
}
