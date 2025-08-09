import CategoryList from "./CategoryList";

const sampleCategories = [
  {
    categoryImage: "https://via.placeholder.com/300x200?text=Electronics",
    categoryName: "Electronics",
  },
  {
    categoryImage: "https://via.placeholder.com/300x200?text=Clothing",
    categoryName: "Clothing",
  },
  {
    categoryImage: "https://via.placeholder.com/300x200?text=Home+Appliances",
    categoryName: "Home Appliances",
  },
];

const CategoriesPage = () => {
  return <CategoryList categories={sampleCategories} />;
};

export default CategoriesPage;
