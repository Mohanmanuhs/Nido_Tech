import ProductCard from "./ProductCard";

const sampleProduct = {
  productId: 1,
  productName: "Wireless Headphones",
  productDescription: "Noise-cancelling over-ear headphones with deep bass.",
  productPrice: 2499,
  productImageUrl: "https://assets.bose.com/content/dam/cloudassets/Bose_DAM/Web/consumer_electronics/global/products/headphones/qc-headphonearn/product_silo_image/AEM_QCH_BLUE-DUSK_PDP_ECOMM-GALLERY_IMG-1.png/_jcr_content/renditions/cq5dam.web.320.320.png",
  categoryName: "Electronics",
};

const ProductPage = () => {
  return (
    <div className="min-h-screen bg-gray-900 flex items-center justify-center p-4">
      <ProductCard product={sampleProduct} />
    </div>
  );
};

export default ProductPage;
