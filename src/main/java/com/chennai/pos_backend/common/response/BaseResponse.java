    package com.chennai.pos_backend.common.response;

    import com.fasterxml.jackson.annotation.JsonInclude;
    import lombok.Getter;

    import java.time.LocalDateTime;

    // Every controller across every service returns this instead of ResponseEntity<T>.
    // The real HTTP status still comes from the response (200 for success, 4xx/5xx set
    // by GlobalExceptionHandler for errors) - `status` here just mirrors it in the body
    // so clients don't have to inspect headers to know success/failure.
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class BaseResponse<T> {

        private final boolean success;
        private final int status;
        private final String message;
        private final T data;
        private final LocalDateTime timestamp;

        private BaseResponse(boolean success, int status, String message, T data) {
            this.success = success;
            this.status = status;
            this.message = message;
            this.data = data;
            this.timestamp = LocalDateTime.now();
        }

        public static <T> BaseResponse<T> success(T data) {
            return new BaseResponse<>(true, 200, "Success", data);
        }

        public static <T> BaseResponse<T> success(T data, String message) {
            return new BaseResponse<>(true, 200, message, data);
        }

        public static <T> BaseResponse<T> created(T data, String message) {
            return new BaseResponse<>(true, 201, message, data);
        }

        public static <T> BaseResponse<T> error(int status, String message) {
            return new BaseResponse<>(false, status, message, null);
        }
    }

