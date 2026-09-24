import com.github.copilot.tool.CopilotTool;
import com.github.copilot.tool.CopilotToolParam;

import java.util.concurrent.CompletableFuture;

public class OrderStatus {

    @CopilotTool(
        value = "Get the current status of an order using its order ID",
        name = "get_order_status"
    )
    public CompletableFuture<String> getOrderStatus(

        @CopilotToolParam(
            value = "The unique ID of the order",
            name = "orderId",
            required = true
        )
        String orderId

    ) {

        System.out.println(
            "[OrderStatus] getOrderStatus() called with: " + orderId
        );

        // Simulating a database / REST API lookup
        String status;

        switch (orderId) {

            case "ORD-1001":
                status = "SHIPPED";
                break;

            case "ORD-1002":
                status = "PROCESSING";
                break;

            case "ORD-1003":
                status = "DELIVERED";
                break;

            case "ORD-1004":
                status = "CANCELLED";
                break;

            default:
                status = "ORDER_NOT_FOUND";
        }

        System.out.println(
            "[OrderStatus] Returning: " + status
        );

        return CompletableFuture.completedFuture(status);
    }
}