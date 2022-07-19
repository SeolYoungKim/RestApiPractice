package ToyProject.RestApiPractice.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResult {

    private final String code;
    private final String message;

    private final Map<String, String> field = new HashMap<>();

    @Builder
    public ErrorResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void fieldAdd(String filed, String defaultMessage) {
        field.put(filed, defaultMessage);
    }


}
