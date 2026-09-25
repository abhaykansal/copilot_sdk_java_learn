import com.github.copilot.tool.CopilotTool;
import com.github.copilot.tool.CopilotToolParam;

import java.util.concurrent.CompletableFuture;

public class OrderService {

    @CopilotTool(
        name = "get_order_status",
        value = "Gets the current status of an order using the order ID"
    )
    public CompletableFuture<String> getOrderStatus(
        @CopilotToolParam(
            name = "orderId",
            value = "The unique ID of the order",
            required = true
        )
        String orderId
    ) {

        System.out.println("[TOOL] get_order_status called: " + orderId);

        
      if ("ORD-5000".equals(orderId)) {
        System.out.println("[TOOL] Simulating service failure...");

            return CompletableFuture.failedFuture(
                new RuntimeException(
                    "Order service is temporarily unavailable"
                )
            );
        }


        if ("ORD-5001".equals(orderId)) {
            System.out.println("[TOOL] Simulating service failure...");

            ToolResult result = ToolResult.failureResult(
                "SERVICE_UNAVAILABLE",
                "Order service is temporarily unavailable",
                true
        );

        return CompletableFuture.completedFuture(result.toString());
    }

        if ("ORD-9999".equals(orderId)) {
             System.out.println("[TOOL] Simulating order not found...");
 
            ToolResult result = ToolResult.failureResult(
                "ORDER_NOT_FOUND",
                "Order ORD-9999 does not exist",
                false
        );

        return CompletableFuture.completedFuture(result.toString());
}
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

            default:
               ToolResult result = ToolResult.failureResult("ORDER_NOT_FOUND in order status","Order " + orderId + " does not exist",false);
               return CompletableFuture.completedFuture(result.toString());
        }

        ToolResult result = ToolResult.successResult(status);
        return CompletableFuture.completedFuture(result.toString());
    }


    @CopilotTool(
        name = "get_order_details",
        value = "Gets detailed information about an order using the order ID"
    )
    public CompletableFuture<String> getOrderDetails(
        @CopilotToolParam(
            name = "orderId",
            value = "The unique ID of the order",
            required = true
        )
        String orderId
    ) {

        System.out.println("[TOOL] get_order_details called: " + orderId);

        String details;

        switch (orderId) {
            case "ORD-1001":
                details = """
                        Order ID: ORD-1001
                        Product: Laptop
                        Quantity: 1
                        Price: $1200
                        Status: SHIPPED
                        """;
                break;

            case "ORD-1002":
                details = """
                        Order ID: ORD-1002
                        Product: Keyboard
                        Quantity: 2
                        Price: $150
                        Status: PROCESSING
                        """;
                break;

            default:  
                 ToolResult result = ToolResult.failureResult("ORDER_NOT_FOUND in OrderDetails", "Order " + orderId + " does not exist",false);
                 return CompletableFuture.completedFuture(result.toString());
               
        }

        ToolResult result = ToolResult.successResult(details);
        return CompletableFuture.completedFuture(result.toString());
    }


    @CopilotTool(
        name = "cancel_order",
        value = "Cancels an order using the order ID"
    )
    public CompletableFuture<String> cancelOrder(
        @CopilotToolParam(
            name = "orderId",
            value = "The unique ID of the order",
            required = true
        )
        String orderId
    ) {

        System.out.println("[TOOL] cancel_order called: " + orderId);

        if ("ORD-1001".equals(orderId)) {
            ToolResult result = ToolResult.failureResult(
                "CANNOT_CANCEL",
                "Order ORD-1001 cannot be cancelled because it has already shipped.",
                false
            );
            return CompletableFuture.completedFuture(result.toString());
        }

        if ("ORD-1002".equals(orderId)) {
            ToolResult result = ToolResult.successResult(
                "Order ORD-1002 has been successfully cancelled."
            );
            return CompletableFuture.completedFuture(result.toString());
        }

        ToolResult result = ToolResult.failureResult(
            "ORDER_NOT_FOUND",
            "Order not found in CancelOrder",
            false
        );
        return CompletableFuture.completedFuture(result.toString());
    }
}