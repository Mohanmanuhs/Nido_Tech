export type SignUpRequestBody = {
  name: string;
  email: string;
  password: string;
  address?: string;
  phone?: string;
  companyName?: string;
  securityKey?: string;
};