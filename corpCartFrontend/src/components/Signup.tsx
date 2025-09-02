import { useState } from "react";
import api from "../api/axios";
import { useParams } from "react-router-dom";
import type { SignUpRequestBody } from "../types/Signup";

const Signup = () => {
  const [form, setForm] = useState({
    email: "",
    name: "",
    address: "",
    password: "",
    phone: "",
    companyName: "",
    securityKey: "",
  });
  const { role } = useParams();
  const isAdmin = role === "admin";

  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const validate = () => {
    const newErrors: typeof errors = {};
    if (!form.name.trim()) newErrors.name = "Name may not be blank";
    if (!form.address.trim()) newErrors.address = "Address may not be blank";
    if (!form.password.trim()) newErrors.password = "Password may not be blank";
    if (form.password.length < 6) newErrors.password = "Password must be at least 6 characters";
    return newErrors;
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: "" });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const newErrors = validate();
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }
    try {
      let requestBody: SignUpRequestBody = {
        name: form.name,
        email: form.email,
        password: form.password,
      };

      if (isAdmin) {
        requestBody = { ...requestBody, securityKey: form.securityKey };
      } else {
        requestBody = { ...requestBody, address: form.address, phone: form.phone, companyName: form.companyName, }
      }

      const response = await api.post(
        `users/${isAdmin ? "adminRegister" : "register"}`,
        requestBody,
        {
          withCredentials: true,
        }
      );
      console.log("Register successful:", response.data);
    } catch (error: any) {
      if (error.response) {
        console.error("Register failed:", error.response.data);
      } else {
        console.error("Register error:", error.message);
      }
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 dark:bg-gray-900 px-4">
      <div className="w-full max-w-md bg-white dark:bg-gray-800 rounded-2xl shadow-md p-8 space-y-6">
        <h2 className="text-2xl font-bold text-gray-800 dark:text-white text-center">Sign Up</h2>

        <form onSubmit={handleSubmit} className="space-y-4">
          {[
            { label: "Name", name: "name", type: "text" },
            { label: "Email", name: "email", type: "email" },
            { label: "Password", name: "password", type: "password" },
            isAdmin ? { label: "Security Key", name: "securityKey", type: "text" } : null,
          ]
            .filter(Boolean)
            .map((field) => (
              <div key={field!.name}>
                <label className="block text-sm text-gray-600 dark:text-gray-300 mb-1">
                  {field!.label}
                </label>
                <input
                  type={field!.type}
                  name={field!.name}
                  value={form[field!.name as keyof typeof form] || ""}
                  onChange={handleChange}
                  className="w-full px-4 py-2 rounded-xl border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-900 text-gray-800 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                {errors[field!.name] && (
                  <p className="text-sm text-red-500 mt-1">{errors[field!.name]}</p>
                )}
              </div>
            ))}
          {!isAdmin && (
            <>
              {[
                { label: "Address", name: "address", type: "text" },
                { label: "Phone", name: "phone", type: "tel" },
                { label: "Company Name", name: "companyName", type: "text" },
              ]
                .map((field) => (
                  <div key={field.name}>
                    <label className="block text-sm text-gray-600 dark:text-gray-300 mb-1">
                      {field.label}
                    </label>
                    <input
                      type={field.type}
                      name={field.name}
                      value={form[field.name as keyof typeof form] || ""}
                      onChange={handleChange}
                      className="w-full px-4 py-2 rounded-xl border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-900 text-gray-800 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                    {errors[field.name] && (
                      <p className="text-sm text-red-500 mt-1">{errors[field.name]}</p>
                    )}
                  </div>
                ))}</>
          )}

          <button
            type="submit"
            className="w-full bg-blue-600 text-white font-semibold py-2 rounded-xl hover:bg-blue-700 transition"
          >
            Register
          </button>
        </form>

        <p className="text-center text-sm text-gray-500">
          Already have an account?{" "}
          <a href="/login" className="text-blue-600 hover:underline">
            Login
          </a>
        </p>
      </div>
    </div>
  );
};

export default Signup;