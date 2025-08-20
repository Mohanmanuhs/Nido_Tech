import type { CategoryDto } from "../types/Category";

export const CategoryCard = ({ category }: { category: CategoryDto }) => {
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
