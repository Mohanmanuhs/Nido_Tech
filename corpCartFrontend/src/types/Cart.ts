import type { CartItemDto } from "./CartItem";

export type CartDto = {
  totalAmount: number;
  cartItemDtos: CartItemDto[];
};