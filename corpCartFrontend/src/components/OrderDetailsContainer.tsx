import { OrderStatus, PaymentStatus } from "../types/Order";
import OrderDetailsPage from "./OrderDetailsPage";

const sampleOrderDetails = {
  orderDto: {
    orderId: 12345,
    orderDate: "2025-07-02T10:45:00",
    totalAmount: 5898.0,
    orderStatus: OrderStatus.DELIVERED,
    paymentStatus: PaymentStatus.PAID,
    expectedDeliveryDate: "2025-07-05",
  },
  orderItemDtos: [
    {
      orderItemId: 1,
      productId: 101,
      productName: "Bluetooth Headphones",
      productImageUrl: "https://via.placeholder.com/150",
      orderItemPrice: 1999,
      orderItemQuantity: 2,
    },
    {
      orderItemId: 2,
      productId: 202,
      productName: "Laptop Stand",
      productImageUrl: "https://via.placeholder.com/150",
      orderItemPrice: 1900,
      orderItemQuantity: 1,
    },
  ],
};

const OrderDetailsContainer = () => <OrderDetailsPage orderDetails={sampleOrderDetails} />;

export default OrderDetailsContainer;