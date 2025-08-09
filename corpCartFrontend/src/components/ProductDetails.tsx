type ProductDto = {
  productId: number;
  productName: string;
  productDescription: string;
  productPrice: number;
  productImageUrl: string;
  categoryName: string;
};

const ProductDetails = ({ product }: { product: ProductDto }) => {
  return (
    <div className="min-h-screen bg-gray-900 text-white flex justify-center items-center p-6">
      <div className="bg-gray-800 max-w-3xl w-full rounded-2xl shadow-2xl overflow-hidden flex flex-col md:flex-row">
        <img
          src={product.productImageUrl}
          alt={product.productName}
          className="w-full md:w-1/2 h-64 md:h-auto object-cover"
        />
        <div className="p-6 flex-1 space-y-4">
          <h1 className="text-3xl font-bold text-blue-400">{product.productName}</h1>
          <p className="text-gray-300">{product.productDescription}</p>
          <div className="flex items-center justify-between pt-4">
            <span className="text-2xl font-bold text-green-400">₹ {product.productPrice}</span>
            <span className="text-sm bg-gray-700 text-gray-300 px-3 py-1 rounded-full">
              {product.categoryName}
            </span>
          </div>
          <div className="pt-4">
            <button className="bg-blue-600 hover:bg-blue-700 transition px-4 py-2 rounded-lg text-white font-medium">
              Add to Cart
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;
