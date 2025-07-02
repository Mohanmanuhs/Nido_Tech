import { useState } from "react";

const AddProduct = () => {
  const [form, setForm] = useState({
    productName: "",
    productDescription: "",
    productPrice: "",
    productImageUrl: "",
    categoryId: "",
  });

  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: "" });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    const newErrors: typeof errors = {};
    if (!form.productName.trim()) newErrors.productName = "Product name is required";
    if (!form.productPrice || isNaN(Number(form.productPrice)))
      newErrors.productPrice = "Valid price is required";

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    // Submit logic here
    console.log("Product Submitted:", form);
  };

  return (
    <div className="min-h-screen bg-gray-900 flex items-center justify-center px-4">
      <div className="bg-gray-800 shadow-lg rounded-2xl max-w-md w-full p-8 space-y-6 text-white">
        <h2 className="text-3xl font-bold text-center text-blue-400">Add New Product</h2>

        <form onSubmit={handleSubmit} className="space-y-4">
          {[
            { label: "Product Name", name: "productName", type: "text" },
            { label: "Product Price", name: "productPrice", type: "number" },
            { label: "Product Image URL", name: "productImageUrl", type: "text" },
            { label: "Category ID", name: "categoryId", type: "number" },
          ].map((field) => (
            <div key={field.name}>
              <label className="block text-sm font-medium mb-1 text-gray-300">{field.label}</label>
              <input
                type={field.type}
                name={field.name}
                value={form[field.name as keyof typeof form]}
                onChange={handleChange}
                className="w-full px-4 py-2 bg-gray-700 text-white border border-gray-600 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400"
              />
              {errors[field.name] && (
                <p className="text-sm text-red-400 mt-1">{errors[field.name]}</p>
              )}
            </div>
          ))}

          <div>
            <label className="block text-sm font-medium mb-1 text-gray-300">Product Description</label>
            <textarea
              name="productDescription"
              value={form.productDescription}
              onChange={handleChange}
              className="w-full px-4 py-2 bg-gray-700 text-white border border-gray-600 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400"
              rows={3}
            ></textarea>
          </div>

          <button
            type="submit"
            className="w-full bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 rounded-xl transition"
          >
            Submit Product
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddProduct;
