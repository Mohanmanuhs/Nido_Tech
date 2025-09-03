import { useEffect, useState } from "react";
import type { CartItemDto } from "../types/CartItem";
import type { CartDto } from "../types/Cart";
import api from "../api/axios";

const CartPage = () => {
    const [cart, setCart] = useState<CartItemDto[]>([]);
    const [totalAmount, setTotalAmount] = useState<number>(0);
    const [changedItems, setChangedItems] = useState<Record<number, number>>({}); // cartItemId -> newQty
    const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    // ✅ Fetch cart
    const fetchCart = async () => {
        try {
            const res = await api.get<CartDto>("cart", { withCredentials: true });
            setCart(res.data.cartItemDtos);
            setTotalAmount(res.data.totalAmount);
        } catch (err) {
            console.error("Failed to fetch cart", err);
        }
    };

    useEffect(() => {
        fetchCart();
    }, []);

    // ✅ Handle quantity change locally
    const updateQuantity = (id: number, newQty: number) => {
        setCart((prev) =>
            prev.map((item) =>
                item.cartItemId === id ? { ...item, cartItemQuantity: newQty } : item
            )
        );
        setChangedItems((prev) => ({ ...prev, [id]: newQty }));
    };

    const proceedToCheckout = async () => {

    };

    // ✅ Auto-save changed quantities (debounced batch)
    useEffect(() => {
        if (Object.keys(changedItems).length === 0) return;

        const timer = setTimeout(async () => {
            try {
                const updates = Object.entries(changedItems).map(([id, qty]) => ({
                    cartItemId: Number(id),
                    cartItemQuantity: qty,
                }));

                await api.put("cart/items/batch-update", updates, {
                    withCredentials: true,
                });

                console.log("Batch saved ✅", updates);
                setChangedItems({});
                fetchCart(); // refresh totals
            } catch (err) {
                console.error("Failed to batch update", err);
            }
        }, 1000); // 1s debounce

        return () => clearTimeout(timer);
    }, [changedItems]);

    // ✅ Save changes on page unload/close
    useEffect(() => {
        const handleUnload = () => {
            if (Object.keys(changedItems).length === 0) return;

            const updates = Object.entries(changedItems).map(([id, qty]) => ({
                cartItemId: Number(id),
                cartItemQuantity: qty,
            }));

            // Use sendBeacon for last-moment save
            navigator.sendBeacon(
                `${API_BASE_URL}/cart/items/batch-update`,
                new Blob([JSON.stringify(updates)], { type: "application/json" })
            );
        };

        window.addEventListener("beforeunload", handleUnload);
        window.addEventListener("pagehide", handleUnload);

        return () => {
            window.removeEventListener("beforeunload", handleUnload);
            window.removeEventListener("pagehide", handleUnload);
        };
    }, [changedItems]);

    // ✅ Remove item (immediate API call)
    const removeItem = async (id: number) => {
        try {
            setCart((prev) => prev.filter((item) => item.cartItemId !== id));
            await api.delete(`cart/item/${id}`, { withCredentials: true });
            console.log(`Item ${id} removed ✅`);
            fetchCart(); // refresh totals
        } catch (err) {
            console.error("Failed to remove item", err);
        }
    };

    const clearCart = async () => {
        const confirmClear = confirm("Are you sure you want to clear the cart?");
        if (confirmClear) {
            try {
                await api.delete('cart', { withCredentials: true });
                console.log("cart cleared");
                fetchCart()
            } catch (err) {
                console.error("Failed to clear cart", err);
            }
        }
    };

    return (
        <div className="min-h-screen bg-gray-900 text-white px-4 py-10">
            <div className="max-w-5xl mx-auto flex flex-col h-screen">
                {/* Header */}
                <div className="flex justify-between items-center mb-8">
                    <h1 className="text-3xl font-bold text-blue-400">Your Cart</h1>
                    {cart.length > 0 && (
                        <button
                            onClick={clearCart}
                            className="bg-red-600 hover:bg-red-700 px-4 py-2 rounded-lg text-sm"
                        >
                            Clear Cart
                        </button>
                    )}
                </div>

                {/* Cart Items */}
                {cart.length === 0 ? (
                    <p className="text-center text-gray-400">Your cart is empty.</p>
                ) : (
                    <>
                        <div className="flex-1 overflow-y-auto pr-2 pb-32">
                            <div className="space-y-6">
                                {cart.map((item) => (
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
                                                    -
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