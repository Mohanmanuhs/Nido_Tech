import CustomerOrders from "./CustomerOrders";

type OrderStatus = 'PENDING' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';
type PaymentStatus = 'PAID' | 'UNPAID' | 'FAILED';


const sampleOrders = [
  {
    orderId: 101,
    orderDate: "2025-07-01T14:32:00",
    totalAmount: 3599.0,
    orderStatus: "SHIPPED" as OrderStatus,
    paymentStatus: "PAID" as PaymentStatus,
    expectedDeliveryDate: "2025-07-06",
  },
  {
    orderId: 102,
    orderDate: "2025-06-28T09:15:00",
    totalAmount: 1799.0,
    orderStatus: "DELIVERED" as OrderStatus,
    paymentStatus: "PAID" as PaymentStatus,
    expectedDeliveryDate: "2025-07-02",
  },
];

const OrdersPage = () => <CustomerOrders orders={sampleOrders} />;

export default OrdersPage;