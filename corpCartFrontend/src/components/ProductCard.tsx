import { Heart, ShoppingCart, Star } from 'lucide-react';

const StarRating = ({ rating }: { rating: number }) => (
  <div className="flex">
    {[...Array(5)].map((_, i) => (
      <Star
        key={i}
        size={18}
        className={i < Math.floor(rating) ? 'text-yellow-400 fill-current' : 'text-gray-300'}
      />
    ))}
  </div>
);

const ProductCard = ({
  product,
  isFavorite,
  toggleFavorite,
  addToCart
}: {
  product: any;
  isFavorite: boolean;
  toggleFavorite: (id: string) => void;
  addToCart: () => void;
}) => (
  <div
  key={product.id}
  className="bg-white/80 rounded-3xl shadow-md hover:shadow-2xl transition-transform duration-300 hover:-translate-y-0.5 overflow-hidden flex flex-col h-full"
>
  {/* Product Image */}
  <div className="relative bg-gradient-to-br from-gray-100 to-gray-200 aspect-[4/3] sm:aspect-square overflow-hidden">
    <img
      src={product.image}
      alt={product.name}
      className="w-full h-full object-cover transition-transform duration-500 hover:scale-110"
    />
    <div className={`absolute top-4 left-4 ${product.tagColor} text-white px-3 py-1 rounded-xl text-xs font-bold`}>
      {product.tag}
    </div>
    <div className="absolute top-4 right-4">
      <button
        onClick={() => toggleFavorite(product.id)}
        className={`w-10 h-10 sm:w-12 sm:h-12 rounded-2xl flex items-center justify-center transition-all duration-300 hover:scale-110 ${
          isFavorite
            ? 'bg-gradient-to-r from-red-500 to-pink-600 text-white'
            : 'bg-white/90 text-gray-600 hover:bg-red-500 hover:text-white'
        }`}
      >
        <Heart size={18} fill={isFavorite ? 'currentColor' : 'none'} />
      </button>
    </div>
  </div>

  {/* Product Info */}
  <div className="p-4 sm:p-6 flex flex-col justify-between flex-1">
    <div className="mb-3">
      <h3 className="font-semibold text-base sm:text-lg md:text-xl text-gray-900 mb-2 line-clamp-2 hover:text-purple-600 transition-colors">
        {product.name}
      </h3>
      <div className="mb-3 flex flex-wrap gap-2">
        {product.features.map((feature: string, idx: number) => (
          <span key={idx} className="text-xs bg-purple-100 text-purple-700 px-2 py-1 rounded-md">
            {feature}
          </span>
        ))}
      </div>
      <div className="flex items-center gap-2 mb-3">
        <StarRating rating={product.rating} />
        <span className="text-sm text-gray-600">
          {product.rating} ({product.reviews.toLocaleString()})
        </span>
      </div>
    </div>

    <div className="mb-4 flex flex-col sm:flex-row sm:items-center sm:justify-between gap-2">
      <div className="flex items-center gap-2">
        <span className="text-2xl font-bold text-gray-900">${product.price}</span>
        <span className="text-sm text-gray-500 line-through">${product.originalPrice}</span>
      </div>
      <span className="bg-green-500 text-white px-3 py-1 rounded-full text-xs font-bold">
        Save ${(product.originalPrice - product.price).toFixed(2)}
      </span>
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