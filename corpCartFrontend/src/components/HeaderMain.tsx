import { useState } from 'react';
import {
    Gift, Grid3X3, Menu, NotebookIcon, Phone, Receipt, Search, Shield,
    ShoppingCart, Truck, User, X, Zap
} from 'lucide-react';

export default function HeaderMain() {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [activeButton, setActiveButton] = useState('but1');
    const [cartCount, setCartCount] = useState(3);

    const headerButtons = [
        { id: 'but1', label: 'Invoice', icon: Receipt, color: 'from-violet-500 to-purple-600' },
        { id: 'but2', label: 'Categories', icon: Grid3X3, color: 'from-blue-500 to-indigo-600' },
        { id: 'but3', label: 'Offers', icon: Gift, color: 'from-pink-500 to-rose-600' },
        { id: 'but4', label: 'Contact', icon: Phone, color: 'from-emerald-500 to-teal-600' },
        { id: 'but5', label: 'Inventory', icon: NotebookIcon, color: 'from-yellow-500 to-orange-600' }
    ];

    const features = [
        { icon: Truck, title: 'Free Shipping', desc: 'On orders over $50' },
        { icon: Shield, title: 'Secure Payment', desc: '100% protected' },
        { icon: Zap, title: 'Fast Delivery', desc: '2-day shipping' }
    ];

    return (
        <header className="bg-white/90 bg-gradient-to-br from-gray-900 to-indigo-900 backdrop-blur-xl shadow-2xl sticky top-0 z-50">
            {/* Top Bar */}
            <div className="md:block hidden border-b border-gray-600 text-white py-4 px-4">
                <div className="flex flex-row lg:px-5 justify-between items-center">
                    {/* Left - Logo */}
                    <div className="flex items-center w-full md:w-auto">
                        <div>
                            <div className="font-bold text-2xl">NIDO-TECHNOLOGY</div>
                            <div className="text-sm opacity-90">Premium E-Commerce Solutions</div>
                        </div>
                    </div>

                    {/* Right - Features */}
                    <div className="flex flex-wrap justify-end items-center gap-4 w-full md:w-auto">
                        {features.map((feature, index) => {
                            const Icon = feature.icon;
                            return (
                                <div
                                    key={index}
                                    className="flex items-center space-x-2 bg-white/20 px-4 py-2 rounded-full hover:bg-white/30 transition-all duration-300 cursor-pointer"
                                >
                                    <Icon size={16} />
                                    <div className="text-xs">
                                        <div className="font-semibold">{feature.title}</div>
                                        <div className="opacity-80">{feature.desc}</div>
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                </div>
            </div>

            {/* Main Header */}
            <div className="mx-auto px-4 py-4 flex flex-wrap items-center justify-between gap-4">
                {/* Logo + Mobile Menu */}
                <div className="flex items-center space-x-3 flex-shrink-0">
                    <button
                        onClick={() => setIsMenuOpen(!isMenuOpen)}
                        className="p-3 rounded-2xl hover:bg-purple-100 transition-all duration-300 md:hidden"
                    >
                        {isMenuOpen ? <X size={24} /> : <Menu size={24} />}
                    </button>

                </div>


                {/* Header Buttons */}
                <div className="hidden lg:flex flex-wrap items-center justify-center gap-1.5 flex-grow">

                    {headerButtons.map((button) => {    
                        const Icon = button.icon;
                        const isActive = activeButton === button.id;
                        return (
                            <button
                                key={button.id}
                                onClick={() => setActiveButton(button.id)}
                                className={`px-5 py-2 rounded-2xl font-semibold transition-all duration-300 hover:scale-105 ${isActive
                                    ? `bg-gradient-to-r ${button.color} text-white shadow-xl`
                                    : 'bg-white/60 hover:bg-gray-500 hover:text-white'
                                    }`}
                            >
                                <div className="flex items-center space-x-2">
                                    <Icon size={18} />
                                    <span>{button.label}</span>
                                </div>
                            </button>
                        );
                    })}
                </div>

                {/* Search Bar */}
                <div className="md:flex flex-1/9 sm:max-w-3xl lg:max-w-4xl">
                    <div className="relative w-full">
                        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-500" size={20} />
                        <input
                            type="text"
                            placeholder="Search for products..."
                            className="w-full pl-10 pr-6 py-3 bg-white/70 border border-black/60 rounded-2xl focus:outline-none focus:ring-4 focus:ring-purple-500/30 transition-all duration-300"
                        />
                    </div>
                </div>

                {/* Actions */}
                <div className="flex items-center space-x-3">
                    <button className="hidden sm:block p-3 rounded-2xl hover:bg-purple-100 transition-all duration-300">
                        <User size={24} className="text-gray-600" />
                    </button>
                    <button className="p-3 rounded-2xl hover:bg-purple-100 transition-all duration-300 relative">
                        <ShoppingCart size={24} className="text-gray-600" />
                        <span className="absolute -top-2 -right-2 bg-gradient-to-r from-purple-500 to-pink-500 text-white text-sm rounded-full w-6 h-6 flex items-center justify-center font-bold">
                            {cartCount}
                        </span>
                    </button>
                </div>
            </div>

            {/* Mobile Menu */}
            {isMenuOpen && (
                <div className="md:hidden bg-white/95 backdrop-blur-xl border-t">
                    <div className="px-4 py-4">
                        <div className="relative mb-6">
                            <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                            <input
                                type="text"
                                placeholder="Search..."
                                className="w-full pl-12 pr-4 py-4 bg-white/70 border rounded-2xl focus:outline-none focus:ring-2 focus:ring-purple-500"
                            />
                        </div>
                        <div className="grid grid-cols-2 gap-3">
                            {headerButtons.map((button) => {
                                const Icon = button.icon;
                                return (
                                    <button
                                        key={button.id}
                                        onClick={() => setActiveButton(button.id)}
                                        className="flex items-center space-x-2 p-4 rounded-2xl bg-gray-100 hover:bg-gray-200"
                                    >
                                        <Icon size={18} />
                                        <span>{button.label}</span>
                                    </button>
                                );
                            })}
                        </div>
                    </div>
                </div>
            )}
        </header>
    );
}
