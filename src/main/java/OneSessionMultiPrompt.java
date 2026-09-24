
import com.github.copilot.CopilotClient;
import com.github.copilot.rpc.MessageOptions;
import com.github.copilot.rpc.PermissionHandler;
import com.github.copilot.rpc.SessionConfig;

public class OneSessionMultiPrompt {

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
                    .setPrompt("My name is abhay kansal, I am a software qa automation engineer")
            ).get();

            System.out.println(
                response.getData().content()
            );

            var response1 = session.sendAndWait(
                new MessageOptions()
                    .setPrompt("What is my name?"))
            .get();
            
            System.out.println(response1.getData().content());


            var response2 = session.sendAndWait(
                new MessageOptions()
                    .setPrompt("What is my profile?"))
            .get();
            
            System.out.println(response2.getData().content());

            client.stop().get();
        }
    }
}