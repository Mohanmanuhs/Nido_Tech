import type { ProductDto } from "../types/Product";
import { useEffect, useState } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/thumbs";
import api from "../api/axios";

const ProductDetails = ({ productId }: { productId: number }) => {
  const [product, setProduct] = useState<ProductDto | null>(null);
  const [loading, setLoading] = useState(true);
  const [mainSwiper, setMainSwiper] = useState<any>(null);
  const [activeIndex, setActiveIndex] = useState(0);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await api.get<ProductDto>(`products/${productId}`)
        setProduct(response.data);
      } catch (err) {
        console.error("Error fetching products:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchCategories();
  }, []);

  return (
    <div className="min-h-screen bg-gray-900 text-white px-6 py-10">
      {loading || !product ? (
        <p className="text-center text-gray-400">Loading...</p>
      ) : (
        <div className="max-w-6xl mx-auto grid grid-cols-1 md:grid-cols-2 gap-4">

          {/* Left: Main Swiper */}
          <div className="bg-gray-800 p-4 rounded-xl">
            <Swiper
              onSwiper={setMainSwiper}
              onSlideChange={(s) => setActiveIndex(s.activeIndex)}
              className="w-full h-96 rounded-lg"
            >
              {product.productImageUrls.map((img, i) => (
                <SwiperSlide key={i}>
                  <img
                    src={img}
                    alt={`${product.productName} ${i}`}
                    className="w-full h-96 object-contain"
                  />
                </SwiperSlide>
              ))}
            </Swiper>

            <Swiper
              spaceBetween={4}
              slidesPerView={"auto"}
              freeMode
              watchSlidesProgress
              className="mt-4"
            >
              {product.productImageUrls.map((img, i) => (
                <SwiperSlide
                  key={i}
                  style={{ width: 80 }}
                  className="!m-1 !p-0 flex items-center justify-center"
                >
                  <div
                    className={`w-20 h-20 overflow-hidden border rounded-lg cursor-pointer ${activeIndex == i ? "border-blue-600" : "border-gray-400"} hover:border-blue-600`}
                    onMouseEnter={() => mainSwiper?.slideTo(i)}
                    role="button"
                    aria-label={`Thumbnail ${i + 1}`}
                  >
                    <img
                      src={img}
                      alt={`Thumbnail ${i}`}
                      className="w-full h-full object-fill"
                      draggable={false}
                    />
                  </div>
                </SwiperSlide>
              ))}
            </Swiper>

          </div>
          {/* Right: Product Info */}
          <div className="bg-gray-800 p-6 rounded-xl space-y-6">
            {/* Product Title */}
            <h1 className="text-3xl font-bold text-blue-400">{product.productName}</h1>

            {/* Ratings & Category */}
            <div className="flex items-center gap-4 text-sm">
              <span className="bg-green-500 text-white px-2 py-1 rounded-md">4.3 ★</span>
              <span className="text-gray-400">{product.categoryName}</span>
            </div>

            {/* Price */}
            <div className="text-3xl font-bold text-green-400">₹ {product.productPrice}</div>
            <p className="text-sm text-gray-400 line-through">₹ {(product.productPrice * 1.2).toFixed(0)}</p>
            <p className="text-sm text-green-500">20% off</p>

            {/* Offers */}
            <div>
              <h3 className="text-lg font-semibold text-yellow-400">Available Offers</h3>
              <ul className="list-disc list-inside text-gray-300 text-sm space-y-1">
                <li>Bank Offer: 10% instant discount on select cards</li>
                <li>Special Price: Get extra ₹200 off</li>
                <li>Free Delivery on your first order</li>
              </ul>
            </div>

            {/* Description */}
            <p className="text-gray-300">{product.productDescription}</p>

            {/* Action Buttons */}
            <div className="flex gap-4 pt-4">
              <button className="flex-1 bg-yellow-500 hover:bg-yellow-600 text-black font-semibold px-4 py-3 rounded-lg">
                Add to Cart
              </button>
              <button className="flex-1 bg-orange-500 hover:bg-orange-600 text-white font-semibold px-4 py-3 rounded-lg">
                Buy Now
              </button>
            </div>

            {/* Delivery Info */}
            <div className="pt-4 border-t border-gray-700">
              <p className="text-sm text-gray-400">
                Delivery by <span className="text-white font-semibold">Tomorrow, 9 AM</span>
              </p>
              <p className="text-sm text-gray-400">Free delivery for orders above ₹500</p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ProductDetails;