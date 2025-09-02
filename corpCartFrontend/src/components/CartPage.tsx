import { useState } from "react";
import type { CartDto } from "../types/Cart";
import type { CartItemDto } from "../types/CartItem";

// Example API services (replace with real ones)
const cartApi = {
  updateCart: async (items: CartItemDto[]) => {
    console.log("API CALL: update cart", items);
    return { success: true };
  },
  clearCart: async () => {
    console.log("API CALL: clear cart");
    return { success: true };
  },
  checkout: async () => {
    console.log("API CALL: checkout");
    return { success: true, orderId: 1234 };
  },
};

const CartPage = ({ initialCart }: { initialCart: CartDto }) => {
  const [cartItems, setCartItems] = useState<CartItemDto[]>(initialCart.cartItemDtos);
  const [isSaving, setIsSaving] = useState(false);

  const updateQuantity = (id: number, newQuantity: number) => {
    if (newQuantity < 0) return; // prevent 0 or negative
    setCartItems((prev) =>
      prev.map((item) =>
        item.cartItemId === id ? { ...item, cartItemQuantity: newQuantity } : item
      )
    );
  };

  const removeItem = (id: number) => {
    setCartItems((prev) => prev.filter((item) => item.cartItemId !== id));
  };

  const clearCart = async () => {
    const confirmClear = confirm("Are you sure you want to clear the cart?");
    if (confirmClear) {
      const res = await cartApi.clearCart();
      if (res.success) setCartItems([]);
    }
  };

  const saveCart = async () => {
    setIsSaving(true);
    try {
      const res = await cartApi.updateCart(cartItems);
      if (res.success) {
        alert("Cart updated successfully!");
      }
    } catch (err) {
      console.error(err);
      alert("Failed to update cart.");
    } finally {
      setIsSaving(false);
    }
  };

  const proceedToCheckout = async () => {
    const res = await cartApi.checkout();
    if (res.success) {
      alert(`Checkout successful! Order ID: ${res.orderId}`);
    }
  };

  const totalAmount = cartItems.reduce(
    (sum, item) => sum + item.cartItemPrice * item.cartItemQuantity,
    0
  );

  return (
    <div className="min-h-screen bg-gray-900 text-white px-4 py-10">
      <div className="max-w-5xl mx-auto flex flex-col h-screen">
        {/* Header */}
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-blue-400">Your Cart</h1>
          {cartItems.length > 0 && (
            <button
              onClick={clearCart}
              className="bg-red-600 hover:bg-red-700 px-4 py-2 rounded-lg text-sm"
            >
              Clear Cart
            </button>
          )}
        </div>

        {/* Cart Items */}
        {cartItems.length === 0 ? (
          <p className="text-center text-gray-400">Your cart is empty.</p>
        ) : (
          <>
            <div className="flex-1 overflow-y-auto pr-2 pb-32">
              <div className="space-y-6">
                {cartItems.map((item) => (
                  <div
                    key={item.cartItemId}
                    className="flex bg-gray-800 rounded-xl overflow-hidden shadow-lg hover:shadow-blue-500/40 transition"
                  >
                    <img
                      src={item.productImageUrl}
                      alt={item.productName}
                      className="w-32 h-32 object-cover"
                    />
                    <div className="flex-1 p-4 space-y-1">
                      <h3 className="text-lg font-semibold text-blue-300">{item.productName}</h3>
                      <p className="text-sm text-gray-400">
                        Price: ₹ {item.cartItemPrice.toFixed(2)}
                      </p>
                      <div className="flex items-center gap-2 mt-2">
                        {/* Decrease button */}
                        <button
                          onClick={() =>
                            updateQuantity(item.cartItemId, Math.max(1, item.cartItemQuantity - 1))
                          }
                          className="px-2 py-1 bg-gray-700 hover:bg-gray-600 rounded text-lg font-bold"
                        >
                          −
                        </button>

                        {/* Input field */}
                        <input
                          type="number"
                          min={1}
                          value={item.cartItemQuantity === 0 ? "" : item.cartItemQuantity} // allow blank
                          onChange={(e) => {
                            const val = e.target.value;
                            updateQuantity(item.cartItemId, val === "" ? 0 : Number(val));
                          }}
                          onBlur={(e) => {
                            // Reset to at least 1 if left blank
                            if (e.target.value === "" || Number(e.target.value) < 1) {
                              updateQuantity(item.cartItemId, 1);
                            }
                          }}
                          className="w-20 px-2 py-1 bg-gray-700 rounded text-center"
                        />

                        {/* Increase button */}
                        <button
                          onClick={() => updateQuantity(item.cartItemId, item.cartItemQuantity + 1)}
                          className="px-2 py-1 bg-gray-700 hover:bg-gray-600 rounded text-lg font-bold"
                        >
                          +
                        </button>
                      </div>

                      <p className="text-sm text-green-400 pt-2 font-semibold">
                        Subtotal: ₹ {(item.cartItemPrice * item.cartItemQuantity).toFixed(2)}
                      </p>
                    </div>
                    <div className="flex items-center p-4">
                      <button
                        onClick={() => removeItem(item.cartItemId)}
                        className="bg-red-600 hover:bg-red-700 px-3 py-1 text-sm rounded-md"
                      >
                        Remove
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Fixed Checkout Bar */}
            <div className="fixed bottom-0 left-0 right-0 bg-gray-800 border-t border-gray-700 p-4">
              <div className="max-w-5xl mx-auto flex justify-between items-center gap-4">
                <h2 className="text-2xl font-bold text-green-400">
                  Total: ₹ {totalAmount.toFixed(2)}
                </h2>
                <div className="flex gap-3">
                  <button
                    onClick={saveCart}
                    disabled={isSaving}
                    className="bg-yellow-600 hover:bg-yellow-700 px-6 py-2 rounded-xl text-white font-semibold disabled:opacity-50"
                  >
                    {isSaving ? "Saving..." : "Save Changes"}
                  </button>
                  <button
                    onClick={proceedToCheckout}
                    className="bg-blue-600 hover:bg-blue-700 px-6 py-2 rounded-xl text-white font-semibold"
                  >
                    Proceed to Checkout
                  </button>
                </div>
              </div>
            </div>
          </>
        )}
      </div>
    </div>
  );
};
export default CartPage;