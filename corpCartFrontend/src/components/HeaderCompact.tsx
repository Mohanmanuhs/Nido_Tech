export default function HeaderCompact() {
  return (
    <header className="bg-white shadow-lg py-2 px-4">
      <div className="max-w-7xl mx-auto flex items-center justify-between">
        <div className="text-xl font-bold text-purple-600">Nido</div>
        <div className="flex items-center gap-4">
          <button className="text-sm font-medium text-gray-700">Login</button>
          <button className="text-sm font-medium text-gray-700">Cart</button>
        </div>
      </div>
    </header>
  );
}
