import { PaymentStatus, type OrderResponseDto } from "../types/Order";

const CustomerOrders = ({ orders }: { orders: OrderResponseDto[] }) => {
  return (
    <div className="min-h-screen bg-gray-900 text-white px-4 py-10">
      <h1 className="text-3xl font-bold text-blue-400 text-center mb-8">My Orders</h1>

      <div className="max-w-5xl mx-auto grid grid-cols-1 sm:grid-cols-2 gap-6">
        {orders.map((order) => (
          <div
            key={order.orderId}
            className="bg-gray-800 rounded-xl shadow-lg hover:shadow-blue-500/40 transition cursor-pointer hover:ring-2 hover:ring-blue-500"
            onClick={() => alert(`Go to order details for ID ${order.orderId}`)}
          >
            <div className="p-5 space-y-2">
              <div className="flex justify-between items-center">
                <h2 className="text-xl font-semibold text-blue-300">
                  Order #{order.orderId}
                </h2>
                <span className="text-xs bg-gray-700 text-gray-300 px-2 py-1 rounded">
                  {new Date(order.orderDate).toLocaleDateString()}
                </span>
              </div>

              <p className="text-sm text-gray-400">
                Status:{" "}
                <span className="text-yellow-400 font-medium">{order.orderStatus}</span>
              </p>
              <p className="text-sm text-gray-400">
                Payment:{" "}
                <span
                  className={`font-medium ${
                    order.paymentStatus == PaymentStatus.PAID
                      ? "text-green-400"
                      : "text-red-400"
                  }`}
                >
                  {order.paymentStatus}
                </span>
              </p>

              <p className="text-sm text-gray-300">
                Delivery by:{" "}
                <span className="text-blue-200">
                  {new Date(order.expectedDeliveryDate).toLocaleDateString()}
                </span>
              </p>

              <p className="text-lg text-green-400 font-bold">
                ₹ {order.totalAmount.toFixed(2)}
              </p>

              <div className="flex justify-end">
                <span className="text-sm text-blue-400 hover:underline flex items-center gap-1">
                  View Details <span className="text-lg">→</span>
                </span>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CustomerOrders;
