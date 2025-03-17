export interface OrderItemDTO {
    menuItemId: number;
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
    delivery: string;
    items: OrderItemDTO[];
    totalAmount: number;
    status?: string;
    paymentStatus?: string;
    createdAt?: string;
    updatedAt?: string;
}

export interface ApiResponse<T> {
    data?: T;
    error?: {
        type: string;
        message: string;
    };
} 