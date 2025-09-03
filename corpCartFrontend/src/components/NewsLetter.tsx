export default function NewsLetter() {
  return (
    <>
    <section className="bg-gradient-to-br from-gray-900 to-indigo-900 text-white relative overflow-hidden">
        <div className="relative max-w-7xl mx-auto px-4 py-24 text-center">
          <h2 className="text-5xl font-black text-white mb-6">Stay Updated</h2>
          <p className="text-xl text-purple-100 mb-12 max-w-2xl mx-auto">
            Get exclusive deals and special offers delivered to your inbox
          </p>

          <div className="max-w-2xl mx-auto">
            <div className="flex flex-col md:flex-row gap-4">
              <input
                type="email"
                placeholder="Enter your email address"
                className="flex-1 text-black px-8 py-6 bg-white/90 border rounded-2xl focus:outline-none focus:ring-4 focus:ring-white/30 text-lg"
              />
              <button className="bg-gradient-to-r from-orange-400 to-pink-500 hover:from-orange-500 hover:to-pink-600 text-white px-12 py-6 rounded-2xl font-bold hover:scale-105">
                Subscribe Now
              </button>
            </div>
          </div>
        </div>
      </section>
    </>
  )
}
