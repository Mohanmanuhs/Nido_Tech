import { useState } from "react";

const AddCategory = () => {
  const [form, setForm] = useState({
    categoryImage: "",
    categoryName: "",
  });

  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: "" });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    const newErrors: typeof errors = {};
    if (!form.categoryName.trim()) newErrors.categoryName = "Category name is required";
    if (!form.categoryImage.trim()) newErrors.categoryImage = "Image URL is required";

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    // Submit logic here (e.g. axios.post)
    console.log("Category submitted:", form);
  };

  return (
    <div className="min-h-screen bg-gray-900 flex items-center justify-center px-4">
      <div className="bg-gray-800 shadow-2xl rounded-2xl max-w-md w-full p-8 space-y-6 text-white">
        <h2 className="text-3xl font-bold text-center text-blue-400">Add Category</h2>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium mb-1 text-gray-300">Category Name</label>
            <input
              type="text"
              name="categoryName"
              value={form.categoryName}
              onChange={handleChange}
              className="w-full px-4 py-2 bg-gray-700 text-white border border-gray-600 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400"
            />
            {errors.categoryName && (
              <p className="text-sm text-red-400 mt-1">{errors.categoryName}</p>
            )}
          </div>

          <div>
            <label className="block text-sm font-medium mb-1 text-gray-300">Image URL</label>
            <input
              type="text"
              name="categoryImage"
              value={form.categoryImage}
              onChange={handleChange}
              className="w-full px-4 py-2 bg-gray-700 text-white border border-gray-600 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400"
            />
            {errors.categoryImage && (
              <p className="text-sm text-red-400 mt-1">{errors.categoryImage}</p>
            )}
          </div>

          <button
            type="submit"
            className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 rounded-xl transition"
          >
            Add Category
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddCategory;
