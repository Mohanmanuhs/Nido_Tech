import { useState } from 'react';
import { Filter, ChevronDown } from 'lucide-react';
import Footer from './Footer';
import NewsLetter from './NewsLetter';
import Hero from './Hero';
import ProductCard from './ProductCard';
import HeaderWrapper from './HeaderWrapper';

const Application = () => {
  const [favorites, setFavorites] = useState(new Set());
  const [sortBy, setSortBy] = useState('featured');
  const [cartCount, setCartCount] = useState(3);

  const addToCart = () => setCartCount(prev => prev + 1);


  const products = [
    {
      id: 1, name: 'Premium Wireless Headphones', price: 299.99, originalPrice: 399.99,
      rating: 4.8, reviews: 2847, image: '/api/placeholder/300/300', tag: 'Best Seller',
      tagColor: 'bg-gradient-to-r from-emerald-500 to-green-600',
      features: ['Noise Cancelling', '30h Battery', 'Premium Audio']
    },
    {
      id: 2, name: 'Smart Fitness Watch', price: 199.99, originalPrice: 249.99,
      rating: 4.6, reviews: 1523, image: '/api/placeholder/300/300', tag: 'New',
      tagColor: 'bg-gradient-to-r from-blue-500 to-cyan-600',
      features: ['Heart Rate Monitor', 'GPS Tracking', 'Water Resistant']
    },
    {
      id: 3, name: 'Designer Backpack', price: 89.99, originalPrice: 129.99,
      rating: 4.7, reviews: 892, image: '/api/placeholder/300/300', tag: 'Sale',
      tagColor: 'bg-gradient-to-r from-red-500 to-pink-600',
      features: ['Premium Materials', 'Laptop Compartment', 'Ergonomic Design']
    },
    {
      id: 4, name: 'Organic Coffee Beans', price: 24.99, originalPrice: 34.99,
      rating: 4.9, reviews: 2156, image: '/api/placeholder/300/300', tag: 'Organic',
      tagColor: 'bg-gradient-to-r from-purple-500 to-indigo-600',
      features: ['Fair Trade', 'Single Origin', 'Freshly Roasted']
    },
    {
      id: 5, name: 'Organic Coffee Beans', price: 24.99, originalPrice: 34.99,
      rating: 4.9, reviews: 2156, image: '/api/placeholder/300/300', tag: 'Organic',
      tagColor: 'bg-gradient-to-r from-purple-500 to-indigo-600',
      features: ['Fair Trade', 'Single Origin', 'Freshly Roasted']
    }
  ];

  const toggleFavorite = (productId: unknown) => {
    const newFavorites = new Set(favorites);
    if (newFavorites.has(productId)) {
      newFavorites.delete(productId);
    } else {
      newFavorites.add(productId);
    }
    setFavorites(newFavorites);
  };


  return (
    <div className="min-h-screen w-full bg-gradient-to-br from-slate-50 via-blue-50 to-indigo-50">
      <HeaderWrapper></HeaderWrapper>
      <Hero></Hero>

      {/* Products Section */}
      <section className="mx-auto px-15 py-20">
        <div className="flex flex-col md:flex-row md:items-center md:justify-between mb-16">
          <div>
            <h2 className="text-5xl font-black text-gray-900 mb-4">Featured Products</h2>
            <p className="text-xl text-gray-600">Discover our handpicked selection</p>
          </div>

          <div className="flex items-center space-x-4 mt-8 md:mt-0">
            <button className="flex items-center space-x-3 px-8 py-4 bg-white/80 border rounded-2xl hover:bg-white hover:shadow-xl transition-all duration-300">
              <Filter size={20} />
              <span>Filters</span>
            </button>

            <div className="relative">
              <select
                value={sortBy}
                onChange={(e) => setSortBy(e.target.value)}
                className="appearance-none bg-white/80 border rounded-2xl pl-6 pr-12 py-4 focus:outline-none focus:ring-4 focus:ring-purple-500/30"
              >
                <option value="featured">Sort by: Featured</option>
                <option value="price-low">Price: Low to High</option>
                <option value="price-high">Price: High to Low</option>
                <option value="rating">Highest Rated</option>
              </select>
              <ChevronDown className="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-400 pointer-events-none" size={20} />
            </div>
          </div>
        </div>

        <div className="grid gap-4 grid-cols-[repeat(auto-fit,minmax(280px,1fr))] px-2">
          {products.map((product: any) => (
            <ProductCard
              key={product.id}
              product={product}
              isFavorite={favorites.has(product.id)}
              toggleFavorite={toggleFavorite}
              addToCart={() => addToCart()}
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