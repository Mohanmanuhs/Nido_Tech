import { OrderStatus, PaymentStatus } from "../types/Order";
import CustomerOrders from "./CustomerOrders";

const sampleOrders = [
  {
    orderId: 101,
    orderDate: "2025-07-01T14:32:00",
    totalAmount: 3599.0,
    orderStatus: OrderStatus.SHIPPED,
    paymentStatus: PaymentStatus.PAID,
    expectedDeliveryDate: "2025-07-06",
  },
  {
    orderId: 102,
    orderDate: "2025-06-28T09:15:00",
    totalAmount: 1799.0,
    orderStatus: OrderStatus.DELIVERED,
    paymentStatus: PaymentStatus.UNPAID,
    expectedDeliveryDate: "2025-07-02",
  },
];

const OrdersPage = () => <CustomerOrders orders={sampleOrders} />;

export default OrdersPage;