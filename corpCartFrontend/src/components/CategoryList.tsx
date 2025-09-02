import { useEffect, useState } from "react";
import type { CategoryDto } from "../types/Category";
import { CategoryCard } from "./CategoryCard";
import api from "../api/axios";


const CategoryList = () => {

  const [categories, setCategories] = useState<CategoryDto[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await api.get<CategoryDto[]>('/categories')
        setCategories(response.data);
      } catch (err) {
        console.error("Error fetching categories:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchCategories();
  }, []);
  return (
    <div className="min-h-screen bg-gray-900 p-6 text-white">
      <h1 className="text-3xl font-bold text-center mb-8 text-blue-400">Categories</h1>
      {loading ? (
        <p className="text-center text-gray-400">Loading...</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 max-w-6xl mx-auto">
          {categories.map((category, index) => (
            <CategoryCard key={index} category={category} />
          ))}
        </div>
      )}
    </div>
  );
};

export default CategoryList;
