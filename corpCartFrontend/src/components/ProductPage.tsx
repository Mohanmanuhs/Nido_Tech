import ProductDetails from "./ProductDetails";

const sampleProduct = {
  productId: 101,
  productName: "Bluetooth Speaker",
  productDescription: "Portable wireless speaker with 10h battery life and waterproof build.",
  productPrice: 1799,
  productImageUrl: "https://via.placeholder.com/600x400?text=Bluetooth+Speaker",
  categoryName: "Audio",
};

const ProductPage = () => {
  return <ProductDetails product={sampleProduct} />;
};

export default ProductPage;
