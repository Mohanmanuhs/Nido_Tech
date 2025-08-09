import CartPage from "./CartPage";

const sampleCart = {
  totalAmount: 0, 
  cartItemDtos: [
    {
      cartItemId: 1,
      productId: 101,
      productName: "Wireless Mouse",
      productImageUrl: "https://via.placeholder.com/150",
      cartItemPrice: 1099,
      cartItemQuantity: 2,
    },
    {
      cartItemId: 2,
      productId: 202,
      productName: "Mechanical Keyboard",
      productImageUrl: "https://via.placeholder.com/150",
      cartItemPrice: 2399,
      cartItemQuantity: 1,
    },
  ],
};

const CartContainer = () => <CartPage initialCart={sampleCart} />;

export default CartContainer;