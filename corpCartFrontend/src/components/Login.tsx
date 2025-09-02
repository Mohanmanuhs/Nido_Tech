import { useState } from "react";
import api from "../api/axios";
import { useParams } from "react-router-dom";

const Login = () => {
  const { role } = useParams();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [securityKey, setSecurityKey] = useState("");

  const isAdmin = role === "admin";

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      let requestBody;
      if(isAdmin){
        requestBody = {email,password,securityKey};
      }else{
        requestBody = {email,password};
      }
      const response = await api.post(
        `users/${isAdmin?"adminLogin":"login"}`,
        requestBody,
        {
          withCredentials: true,
        }
      );
      console.log("Login successful:", response.data);
    } catch (error: any) {
      if (error.response) {
        console.error("Login failed:", error);
      } else {
        console.error("Login error:", error);
      }
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 dark:bg-gray-900 px-4">
      <div className="w-full max-w-md bg-white dark:bg-gray-800 rounded-2xl shadow-md p-8 space-y-6">
        <h2 className="text-2xl font-bold text-gray-800 dark:text-white text-center">
          Login to your account
        </h2>

        <form onSubmit={handleLogin} className="space-y-4">
          <div>
            <label className="block text-sm text-gray-600 dark:text-gray-300 mb-1">
              Email
            </label>
            <input
              type="email"
              className="w-full px-4 py-2 rounded-xl border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-900 text-gray-800 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              placeholder="you@example.com"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-600 dark:text-gray-300 mb-1">
              Password
            </label>
            <input
              type="password"
              className="w-full px-4 py-2 rounded-xl border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-900 text-gray-800 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              placeholder="••••••••"
            />
          </div>

          {isAdmin && (
            <div>
              <label className="block text-sm text-gray-600 dark:text-gray-300 mb-1">
                Security Key
              </label>
              <input
                type="password"
                className="w-full px-4 py-2 rounded-xl border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-900 text-gray-800 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={securityKey}
                onChange={(e) => setSecurityKey(e.target.value)}
                required
                placeholder="••••••••"
              />
            </div>
          )}

          <button
            type="submit"
            className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 rounded-xl transition"
          >
            Login
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
