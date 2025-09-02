import { useState } from "react";
import {
    Grid3X3,
    Phone,
    Receipt,
    Search,
    Shield,
    ShoppingCart,
    Truck,
    User,
    Zap
} from "lucide-react";

export default function HeaderMain() {
    const [activeButton, setActiveButton] = useState("but1");
    const [cartCount] = useState(3);
    const [menuOpen, setMenuOpen] = useState(false);
    const headerButtons = [
        { id: "but1", label: "Home", icon: Receipt, color: "from-violet-500 to-purple-600" },
        { id: "but2", label: "Categories", icon: Grid3X3, color: "from-blue-500 to-indigo-600" },
        { id: "but4", label: "Contact", icon: Phone, color: "from-emerald-500 to-teal-600" },
    ];

    const features = [
        { icon: Truck, title: "Free Shipping", desc: "On orders over $50" },
        { icon: Shield, title: "Secure Payment", desc: "100% protected" },
        { icon: Zap, title: "Fast Delivery", desc: "2-day shipping" },
    ];

    return (
        <>
            {/* Normal Header (non-sticky) */}
            <header className="bg-gradient-to-br from-gray-900 to-indigo-900 text-white shadow-xl">
                {/* Top Bar */}
                <div className="border-b border-gray-600 py-4 px-4">
                    <div className="flex flex-col md:flex-row lg:px-5 md:justify-between md:items-center text-center md:text-left">
                        {/* Nido Tech Section */}
                        <div className="mb-3 md:mb-0">
                            <div className="font-bold text-2xl">NIDO-TECHNOLOGY</div>
                            <div className="text-sm opacity-90">Premium E-Commerce Solutions</div>
                        </div>

                        {/* Features (hidden on small, flex on md+) */}
                        <div className="hidden md:flex flex-wrap gap-4 justify-center md:justify-start">
                            {features.map((feature, idx) => {
                                const Icon = feature.icon;
                                return (
                                    <div
                                        key={idx}
                                        className="flex items-center space-x-2 bg-white/20 px-4 py-2 rounded-full"
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
            </header>

            {/* 🔥 Sticky Buttons Row */}
            <div className="bg-gradient-to-br from-gray-900 to-indigo-900 text-white shadow-xl sticky top-0 z-40">
                <div className="px-3 py-3 flex flex-col gap-4 md:flex-row md:items-center md:justify-between">

                    {/* Mobile Menu (hamburger + search + cart) */}
                    <div className="flex items-center justify-between gap-2 md:hidden">
                        {/* Hamburger */}
                        <button
                            onClick={() => setMenuOpen(!menuOpen)}
                            className="p-2 rounded-lg bg-white/20 hover:bg-white/30 transition"
                        >
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                className="h-6 w-6 text-white"
                                fill="none"
                                viewBox="0 0 24 24"
                                stroke="currentColor"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    strokeWidth={2}
                                    d="M4 6h16M4 12h16M4 18h16"
                                />
                            </svg>
                        </button>

                        {/* Search box (aligned to end/right) */}
                        <div className="ml-auto w-full max-w-[200px] sm:max-w-[250px]">
                            <div className="relative">
                                <Search
                                    className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-500"
                                    size={18}
                                />
                                <input
                                    type="text"
                                    placeholder="Search..."
                                    className="w-full pl-9 pr-3 py-2 bg-white/70 border border-black/50 rounded-xl 
      text-sm focus:outline-none focus:ring-2 focus:ring-purple-400"
                                />
                            </div>
                        </div>

                        {/* Cart */}
                        <button className="p-2 rounded-xl hover:bg-purple-100 transition-all duration-300 relative bg-white">
                            <ShoppingCart size={20} className="text-gray-700" />
                            {cartCount > 0 && (
                                <span className="absolute -top-1.5 -right-1.5 bg-gradient-to-r from-purple-500 to-pink-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center font-bold">
                                    {cartCount}
                                </span>
                            )}
                        </button>
                    </div>


                    {/* Buttons Row (hidden on mobile, shown on desktop) */}
                    <div className={`flex-wrap gap-2 ${menuOpen ? "flex" : "hidden"} md:flex`}>
                        {headerButtons.map((button) => {
                            const Icon = button.icon;
                            const isActive = activeButton === button.id;
                            return (
                                <button
                                    key={button.id}
                                    onClick={() => setActiveButton(button.id)}
                                    className={`flex-shrink-0 px-3 py-2 rounded-2xl font-semibold transition-all duration-300 hover:scale-105 
              ${isActive
                                            ? `bg-gradient-to-r ${button.color} text-white shadow-xl`
                                            : "bg-white/60 hover:bg-gray-500 hover:text-white"
                                        }`}
                                >
                                    <div className="flex items-center space-x-1">
                                        <Icon size={18} />
                                        <span>{button.label}</span>
                                    </div>
                                </button>
                            );
                        })}
                    </div>

                    {/* Desktop Search + User + Cart */}
                    <div className="hidden md:flex items-center justify-end gap-3 flex-1">
                        <div className="flex-1 max-w-[550px] min-w-[250px]">
                            <div className="relative">
                                <Search
                                    className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-500"
                                    size={20}
                                />
                                <input
                                    type="text"
                                    placeholder="Search..."
                                    className="w-full pl-10 pr-6 py-2 md:py-3 bg-white/70 border border-black/60 rounded-2xl 
            focus:outline-none focus:ring-2 focus:ring-purple-400"
                                />
                            </div>
                        </div>
                        <button className="p-2 md:p-3 rounded-2xl hover:bg-purple-100 transition-all duration-300">
                            <User size={22} className="text-gray-600" />
                        </button>
                        {cartCount > 0 && (
                            <button className="p-2 md:p-3 rounded-2xl hover:bg-purple-100 transition-all duration-300 relative">
                                <ShoppingCart size={22} className="text-gray-600" />
                                <span className="absolute -top-2 -right-2 bg-gradient-to-r from-purple-500 to-pink-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center font-bold">
                                    {cartCount}
                                </span>
                            </button>
                        )}
                    </div>
                </div>
            </div>
        </>
    );
}