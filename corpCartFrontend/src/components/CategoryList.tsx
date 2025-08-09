type CategoryDto = {
  categoryImage: string;
  categoryName: string;
};

const CategoryCard = ({ category }: { category: CategoryDto }) => {
  return (
    <div className="bg-gray-800 rounded-2xl shadow-xl hover:shadow-blue-500/40 transition overflow-hidden">
      <img
        src={category.categoryImage}
        alt={category.categoryName}
        className="w-full h-40 object-cover"
      />
      <div className="p-4">
        <h3 className="text-lg font-semibold text-blue-400">{category.categoryName}</h3>
      </div>
    </div>
  );
};

const CategoryList = ({ categories }: { categories: CategoryDto[] }) => {
  return (
    <div className="min-h-screen bg-gray-900 p-6 text-white">
      <h1 className="text-3xl font-bold text-center mb-8 text-blue-400">Categories</h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 max-w-6xl mx-auto">
        {categories.map((category, index) => (
          <CategoryCard key={index} category={category} />
        ))}
      </div>
    </div>
  );
};

export default CategoryList;
