
import com.github.copilot.CopilotClient;
import com.github.copilot.rpc.MessageOptions;
import com.github.copilot.rpc.PermissionHandler;
import com.github.copilot.rpc.SessionConfig;

public class HelloCopilot {

    public static void main(String[] args) throws Exception {

        try (var client = new CopilotClient()) {

            client.start().get();

            var session = client.createSession(
                new SessionConfig()
                    .setModel("auto")
                    .setOnPermissionRequest(
                        PermissionHandler.APPROVE_ALL
                    )
            ).get();

            var response = session.sendAndWait(
                new MessageOptions()
                    .setPrompt("Please explain the purpose of copilot sdk in java in 2 lines")
            ).get();

            System.out.println(
                response.getData().content()
            );

            client.stop().get();
        }
    }
}