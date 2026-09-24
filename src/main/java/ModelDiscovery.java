import com.github.copilot.CopilotClient;
import com.github.copilot.rpc.ModelInfo;

public class ModelDiscovery {

    public static void main(String[] args) throws Exception {

        try (var client = new CopilotClient()) {

            client.start().get();

            var models = client.listModels().get();

            System.out.println("Available Models:");
            System.out.println("=================");

            for (ModelInfo model : models) {

                System.out.println(
                    "ID   : " + model.getId()
                );

                System.out.println(
                    "Name : " + model.getName()
                );

                System.out.println("-----------------");
            }

            client.stop().get();
        }
    }
}