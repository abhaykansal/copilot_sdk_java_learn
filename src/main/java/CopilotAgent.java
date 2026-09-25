import com.github.copilot.CopilotClient;
import com.github.copilot.rpc.MessageOptions;
import com.github.copilot.rpc.PermissionHandler;
import com.github.copilot.rpc.SessionConfig;
import com.github.copilot.rpc.ToolDefinition;

import java.util.List;

public class CopilotAgent {

    public static void main(String[] args) throws Exception {

        /*
         * ==========================================================
         * 1. Create the business class containing our tool
         * ==========================================================
         */

    //    OrderStatus orderStatus = new OrderStatus();
        OrderService orderService = new OrderService();

        /*
         * ==========================================================
         * 2. Convert @CopilotTool methods into ToolDefinitions
         * ==========================================================
         *
         * fromObject() looks at OrderStatus and discovers:
         *
         * @CopilotTool
         * getOrderStatus()
         *
         * It creates the ToolDefinition required by Copilot.
         */

 //       List<ToolDefinition> tools = ToolDefinition.fromObject(orderStatus);

            
        List<ToolDefinition> tools =
            ToolDefinition.fromObject(orderService);

            
        /*
         * ==========================================================
         * 3. Display discovered tools
         * ==========================================================
         */

        System.out.println(
            "Number of tools discovered: " + tools.size()
        );

        for (ToolDefinition tool : tools) {

            System.out.println(
                "Tool name: " + tool.name()
            );

            System.out.println(
                "Tool description: " + tool.description()
            );

            System.out.println(
                "Tool parameters: " + tool.parameters()
            );
        }

        /*
         * ==========================================================
         * 4. Create Copilot Client
         * ==========================================================
         */

        try (var client = new CopilotClient()) {

            /*
             * Start Copilot runtime
             */

            client.start().get();

            /*
             * ======================================================
             * 5. Create session AND REGISTER THE TOOLS
             * ======================================================
             *
             * This is the critical step.
             *
             * .setTools(tools)
             *
             * tells Copilot that this session has our custom
             * get_order_status tool.
             */

            var session = client.createSession(

                new SessionConfig()

                    .setModel("auto")

                    .setOnPermissionRequest(
                        PermissionHandler.APPROVE_ALL
                    )

                    .setTools(tools)

            ).get();

            /*
             * ======================================================
             * 6. Ask Copilot to get an order status
             * ======================================================
             */

            var response = session.sendAndWait(

                new MessageOptions()
                    .setPrompt(
                        "Please get order status of ORD-9999 ?"
                    )

            ).get();

            /*
             * ======================================================
             * 7. Print final Copilot response
             * ======================================================
             */

            System.out.println();
            System.out.println(
                "=========================================="
            );
            System.out.println(
                "COPILOT FINAL RESPONSE"
            );
            System.out.println(
                "=========================================="
            );

            System.out.println(
                response.getData().content()
            );

            /*
             * ======================================================
             * 8. Stop Copilot
             * ======================================================
             */

            client.stop().get();
        }
    }
}