export const OrderStatus = {
  PENDING: "PENDING",
  SHIPPED: "SHIPPED",
  DELIVERED: "DELIVERED",
  CANCELLED: "CANCELLED",
} as const;

export type OrderStatus = typeof OrderStatus[keyof typeof OrderStatus];

export const PaymentStatus = {
  PAID: "PAID",
  UNPAID: "UNPAID",
  FAILED: "FAILED",
} as const;

export type PaymentStatus = typeof PaymentStatus[keyof typeof PaymentStatus];

export type OrderResponseDto = {
  orderId: number;
  orderDate: string; // ISO string
  totalAmount: number;
  orderStatus: OrderStatus;
  paymentStatus: PaymentStatus;
  expectedDeliveryDate: string; // ISO date
};

export type OrderItemDto = {
  orderItemId: number;
  orderItemQuantity: number;
  orderItemPrice: number;
  productId: number;
  productName: string;
  productImageUrl: string;
};

export type OrderDetailsDto = {
  orderDto: OrderResponseDto;
  orderItemDtos: OrderItemDto[];
};