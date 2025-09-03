import { useState } from 'react';
import Footer from './Footer';
import NewsLetter from './NewsLetter';
import Hero from './Hero';
import ProductCard from './ProductCard';
import HeaderMain from './Header';
import type { ProductDtoForList } from '../types/Product';
import Header from './Header';

const Application = () => {
  const [cartCount, setCartCount] = useState(0);

  const addToCart = () => setCartCount(prev => prev + 1);


  const products = [
    {
      productId: 1,
      productName: "Apple iPhone 14 Pro Max",
      productDescription: "6.7-inch display, A16 Bionic chip, 48MP camera system.",
      productPrice: 129900,
      productImageUrl:"https://www.apple.com/v/iphone/compare/ai/images/overview/compare_iphone14_pro_max_space_black__cyo6765d9ouq_large.jpg",
      categoryName: "Smartphones",
    },
    {
      productId: 2,
      productName: "Samsung Galaxy S23 Ultra",
      productDescription: "200MP camera, Snapdragon 8 Gen 2, S Pen included.",
      productPrice: 124999,
      productImageUrl:"https://www.apple.com/v/iphone/compare/ai/images/overview/compare_iphone14_pro_max_space_black__cyo6765d9ouq_large.jpg",
      categoryName: "Smartphones",
    },
    {
      productId: 3,
      productName: "Sony WH-1000XM5 Headphones",
      productDescription: "Noise-cancelling wireless over-ear headphones.",
      productPrice: 29990,
      productImageUrl:"https://www.apple.com/v/iphone/compare/ai/images/overview/compare_iphone14_pro_max_space_black__cyo6765d9ouq_large.jpg",
      categoryName: "Headphones",
    },
    {
      productId: 4,
      productName: "Apple MacBook Air M2",
      productDescription: "13.6-inch Liquid Retina Display, M2 chip, 8GB RAM.",
      productPrice: 119900,
      productImageUrl:"https://www.apple.com/v/iphone/compare/ai/images/overview/compare_iphone14_pro_max_space_black__cyo6765d9ouq_large.jpg",
      categoryName: "Laptops",
    },
    {
      productId: 5,
      productName: "Nike Air Jordan 1",
      productDescription: "High-top sneakers, classic retro style.",
      productPrice: 15999,
      productImageUrl:"https://www.apple.com/v/iphone/compare/ai/images/overview/compare_iphone14_pro_max_space_black__cyo6765d9ouq_large.jpg",
      categoryName: "Shoes",
    },
  ];

  return (
    <div className="min-h-screen w-full bg-gradient-to-br from-slate-50 via-blue-50 to-indigo-50">
      <Header cartCount={cartCount}></Header>
      <Hero></Hero>

      {/* Products Section */}
      <section className="mx-auto px-20 py-10">
        <div className="flex flex-col md:flex-row md:items-center md:justify-between mb-16">
          <div>
            <h2 className="text-5xl font-black text-gray-900 mb-4">Featured Products</h2>
          </div>
        </div>

        <div className="grid gap-4 grid-cols-[repeat(auto-fit,minmax(280px,1fr))] px-2">
          {products.map((product: ProductDtoForList) => (
            <ProductCard
              key={product.productId}
              product={product}
              addToCart={addToCart}
            />
          ))}
        </div>
      </section>

      <NewsLetter></NewsLetter>
      <Footer></Footer>
    </div>
  );
};

export default Application;