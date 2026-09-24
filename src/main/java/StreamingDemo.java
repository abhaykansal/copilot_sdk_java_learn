
import com.github.copilot.CopilotClient;
import com.github.copilot.generated.AssistantMessageDeltaEvent;
import com.github.copilot.generated.SessionIdleEvent;
import com.github.copilot.rpc.MessageOptions;
import com.github.copilot.rpc.PermissionHandler;
import com.github.copilot.rpc.SessionConfig;
import java.util.concurrent.CompletableFuture;

public class StreamingDemo {

    public static void main(String[] args) throws Exception {
        try (var client = new CopilotClient()) {
            // 1. Start Copilot
            client.start().get();

            // 2. Create a streaming session
            var session = client.createSession(
                new SessionConfig()
                    .setModel("auto")
                    .setOnPermissionRequest(
                        PermissionHandler.APPROVE_ALL
                    )
                    .setStreaming(true)
            ).get();

            // 3. Future used to know when streaming is complete
            var done = new CompletableFuture<Void>();

            // 4. Listen for every response chunk
            session.on(
                AssistantMessageDeltaEvent.class,
                delta -> {
                    System.out.print(
                        delta.getData().deltaContent()
                    );
                }
            );

            // 5. Listen for end of processing
            session.on(
                SessionIdleEvent.class,
                idle -> {
                    System.out.println();
                    done.complete(null);
                }
            );

            System.out.println("Copilot: ");

            // 6. Send prompt
            session.send(
                new MessageOptions()
                    .setPrompt(
                        "Explain GitHub Copilot SDK in detail. " +
                        "Give me a simple example."
                    )
            ).get();

            // 7. Wait until streaming is complete
            done.get();

            // 8. Stop Copilot
            client.stop().get();
        }
    }
}