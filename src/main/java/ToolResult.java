
public class ToolResult {

    private final boolean success;
    private final String errorType;
    private final String message;
    private final boolean retryable;
    private final String data;

    public ToolResult(
            boolean success,
            String errorType,
            String message,
            boolean retryable,
            String data) {

        this.success = success;
        this.errorType = errorType;
        this.message = message;
        this.retryable = retryable;
        this.data = data;
    }

    public static ToolResult successResult(String data) {
        return new ToolResult(
                true,
                null,
                null,
                false,
                data
        );
    }

    public static ToolResult failureResult(
            String errorType,
            String message,
            boolean retryable) {

        return new ToolResult(
                false,
                errorType,
                message,
                retryable,
                null
        );
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return """
                {
                  "success": %s,
                  "errorType": "%s",
                  "message": "%s",
                  "retryable": %s,
                  "data": "%s"
                }
                """.formatted(
                success,
                errorType,
                message,
                retryable,
                data
        );
    }
}
