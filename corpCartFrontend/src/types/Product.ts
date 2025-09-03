export type ProductDto = {
  productId: number;
  productName: string;
  productDescription: string;
  productPrice: number;
  productImageUrls: string[];
  categoryName: string;
};

export type ProductDtoForList = {
  productId: number;
  productName: string;
  productDescription: string;
  productPrice: number;
  productImageUrl: string;
  categoryName: string;
};