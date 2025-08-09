type OrderStatus = 'PENDING' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';
type PaymentStatus = 'PAID' | 'UNPAID' | 'FAILED';

type OrderResponseDto = {
  orderId: number;
  orderDate: string;
  totalAmount: number;
  orderStatus: string; // or OrderStatus
  paymentStatus: string; // or PaymentStatus
  expectedDeliveryDate: string;
};

type OrderItemDto = {
  orderItemId: number;
  orderItemQuantity: number;
  orderItemPrice: number;
  productId: number;
  productName: string;
  productImageUrl: string;
};

type OrderDetailsDto = {
  orderDto: OrderResponseDto;
  orderItemDtos: OrderItemDto[];
};

const OrderDetailsPage = ({ orderDetails }: { orderDetails: OrderDetailsDto }) => {
  const { orderDto, orderItemDtos } = orderDetails;

  return (
    <div className="min-h-screen bg-gray-900 text-white px-4 py-10">
      <div className="max-w-5xl mx-auto space-y-8">
        {/* Order Summary */}
        <div className="bg-gray-800 rounded-xl p-6 shadow-lg space-y-3">
          <h2 className="text-2xl font-bold text-blue-400">Order #{orderDto.orderId}</h2>
          <p className="text-gray-300">Placed on: {new Date(orderDto.orderDate).toLocaleString()}</p>
          <p className="text-gray-300">
            Delivery by:{" "}
            <span className="text-blue-300">{new Date(orderDto.expectedDeliveryDate).toDateString()}</span>
          </p>
          <p className="text-gray-300">
            Order Status:{" "}
            <span className="text-yellow-400 font-medium">{orderDto.orderStatus}</span>
          </p>
          <p className="text-gray-300">
            Payment Status:{" "}
            <span
              className={`font-medium ${
                orderDto.paymentStatus === "PAID" ? "text-green-400" : "text-red-400"
              }`}
            >
              {orderDto.paymentStatus}
            </span>
          </p>
          <p className="text-xl font-bold text-green-400">Total: ₹ {orderDto.totalAmount.toFixed(2)}</p>
        </div>

        {/* Items */}
        <div className="space-y-6">
          <h3 className="text-xl font-semibold text-blue-300">Ordered Items</h3>
          {orderItemDtos.map((item) => (
            <div
              key={item.orderItemId}
              className="flex bg-gray-800 rounded-xl shadow-md overflow-hidden"
            >
              <img
                src={item.productImageUrl}
                alt={item.productName}
                className="w-32 h-32 object-cover"
              />
              <div className="p-4 flex-1 space-y-1">
                <h4 className="text-lg font-semibold text-blue-200">{item.productName}</h4>
                <p className="text-sm text-gray-400">Quantity: {item.orderItemQuantity}</p>
                <p className="text-sm text-green-400">
                  Price: ₹ {(item.orderItemPrice * item.orderItemQuantity).toFixed(2)}
                </p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default OrderDetailsPage;
