import { ShoppingCart } from 'lucide-react';
import type { ProductDtoForList } from '../types/Product';

const ProductCard = ({ product, addToCart }: { product: ProductDtoForList, addToCart: () => void }) => (
  <div
    key={product.productId}
    className="bg-white/80 rounded-3xl shadow-md hover:shadow-2xl transition-transform duration-300 hover:-translate-y-0.5 overflow-hidden flex flex-col h-full"
  >
    {/* Product Image */}
    <div className="relative bg-gradient-to-br from-gray-100 to-gray-200 aspect-[4/3] sm:aspect-square overflow-hidden">
      <img
        src={product.productImageUrl}
        alt={product.productName}
        className="w-full h-full object-fill transition-transform duration-500 hover:scale-101"
      />
    </div>

    {/* Product Info */}
    <div className="p-4 sm:p-6 flex flex-col justify-between flex-1">
      <div className="mb-3">
        <h3 className="font-semibold text-base sm:text-lg md:text-xl text-gray-900 mb-2 line-clamp-2 hover:text-purple-600 transition-colors">
          {product.productName}
        </h3>
      </div>

      <div className="mb-4 flex flex-col sm:flex-row sm:items-center sm:justify-between gap-2">
        <div className="flex items-center gap-2">
          <span className="text-2xl font-bold text-gray-900">${product.productPrice}</span>
        </div>
      </div>

      <button
        onClick={addToCart}
        className="mt-auto w-full bg-gradient-to-r from-purple-600 to-orange-500 hover:from-purple-700 hover:to-orange-600 text-white py-3 sm:py-4 rounded-2xl font-bold transition-all duration-300 hover:scale-101 shadow-lg"
      >
        <span className="flex items-center justify-center gap-2">
          <ShoppingCart size={18} />
          <span>Add to Cart</span>
        </span>
      </button>
    </div>
  </div>

);

export default ProductCard;