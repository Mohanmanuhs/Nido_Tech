import {
    Shield,
    Truck,
    Zap
} from "lucide-react";

export default function HeaderMain() {
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
        </>
    )
};