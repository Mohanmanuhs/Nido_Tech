import { ArrowRight } from "lucide-react";

export default function Hero() {
  return (
    <>
    <section className="bg-gradient-to-br from-gray-900 to-indigo-900 text-white relative overflow-hidden">
        <div className="relative max-w-5xl mx-auto px-4 py-10 text-center text-white">
          <h1 className="text-4xl md:text-6xl font-black mb-6 leading-tight">
            Where{' '}
            <span className="bg-gradient-to-r from-cyan-300 to-blue-400 bg-clip-text text-transparent">
              Trust
            </span>
            <span className="bg-gradient-to-r from-teal-300 to-cyan-400 bg-clip-text text-transparent">
              {' '}Lies
            </span>
          </h1>
          <p className="text-lg md:text-xl mb-8 text-blue-100 max-w-2xl mx-auto">
            Shop the latest trends with unbeatable prices and premium quality
          </p>
          <button className="bg-gradient-to-r from-cyan-400 to-teal-500 hover:from-cyan-500 hover:to-teal-600 text-white px-12 py-4 rounded-full text-lg font-bold transition-all duration-300 hover:scale-110 shadow-2xl">
            <span className="flex items-center space-x-3">
              <span>Shop Now</span>
              <ArrowRight size={18} />
            </span>
          </button>
        </div>
      </section>
    </>
  )
}
