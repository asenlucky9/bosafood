// Common Types
export interface ApiResponse<T> {
    data?: T;
    error?: ApiError;
}

export interface ApiError {
    message: string;
    type: string;
}

// Auth Types
export interface LoginCredentials {
    email: string;
    password: string;
}

export interface LoginResponse {
    token: string;
    refreshToken?: string;
    user: UserDTO;
}

export interface RegisterData {
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    phoneNumber?: string;
    address?: string;
}

// User Types
export interface UserDTO {
    id: number;
    email: string;
    firstName: string;
    lastName: string;
    phoneNumber?: string;
    address?: string;
    role: UserRole;
}

export enum UserRole {
    USER = 'USER',
    ADMIN = 'ADMIN'
}

// Menu Types
export interface CategoryDTO {
    id: number;
    name: string;
    description: string;
}

export interface MenuItemDTO {
    id: number;
    name: string;
    description: string;
    price: number;
    categoryId: number;
    category?: CategoryDTO;
    imageUrl?: string;
    isVegetarian: boolean;
    isSpicy: boolean;
    isAvailable: boolean;
    preparationTime?: number;
    calories?: number;
    allergens?: string[];
}

// Cart Types
export interface CartItemDTO {
    id?: number;
    menuItemId: number;
    menuItem?: MenuItemDTO;
    quantity: number;
    unitPrice: number;
    subtotal: number;
    specialInstructions?: string;
}

export interface CartDTO {
    id?: number;
    userId: number;
    items: CartItemDTO[];
    totalAmount: number;
}

// Order Types
export interface OrderItemDTO {
    id?: number;
    menuItemId: number;
    menuItem?: MenuItemDTO;
    quantity: number;
    unitPrice: number;
    subtotal: number;
    specialInstructions?: string;
}

export interface OrderDTO {
    id?: number;
    userId: number;
    customerName: string;
    email: string;
    phone: string;
    deliveryMethod: DeliveryMethod;
    deliveryAddress?: string;
    items: OrderItemDTO[];
    totalAmount: number;
    status: OrderStatus;
    paymentStatus: PaymentStatus;
    paymentMethod: PaymentMethod;
    specialInstructions?: string;
    createdAt?: string;
    updatedAt?: string;
}

export enum OrderStatus {
    PENDING = 'PENDING',
    CONFIRMED = 'CONFIRMED',
    PREPARING = 'PREPARING',
    READY = 'READY',
    IN_DELIVERY = 'IN_DELIVERY',
    DELIVERED = 'DELIVERED',
    CANCELLED = 'CANCELLED'
}

export enum PaymentStatus {
    PENDING = 'PENDING',
    PAID = 'PAID',
    FAILED = 'FAILED',
    REFUNDED = 'REFUNDED'
}

export enum PaymentMethod {
    CREDIT_CARD = 'CREDIT_CARD',
    DEBIT_CARD = 'DEBIT_CARD',
    CASH = 'CASH'
}

export enum DeliveryMethod {
    DELIVERY = 'DELIVERY',
    PICKUP = 'PICKUP'
}

// Review Types
export interface ReviewDTO {
    id?: number;
    userId: number;
    userName: string;
    menuItemId: number;
    menuItemName: string;
    rating: number;
    comment: string;
    createdAt?: string;
}

// Special Offer Types
export interface SpecialOfferDTO {
    id?: number;
    title: string;
    description: string;
    discountPercentage: number;
    startDate: string;
    endDate: string;
    menuItemIds: number[];
    menuItems?: MenuItemDTO[];
    termsAndConditions?: string;
    minimumOrderValue?: number;
    maximumDiscount?: number;
    isActive: boolean;
} 