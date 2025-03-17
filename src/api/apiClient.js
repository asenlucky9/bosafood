import { CONFIG } from '../config/config';

// Auth header helper
const getAuthHeader = () => {
    const token = localStorage.getItem(CONFIG.AUTH_TOKEN_KEY);
    return token ? { 'Authorization': `Bearer ${token}` } : {};
};

// Generic request handler
const handleResponse = async (response) => {
    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.error?.message || 'An error occurred');
    }
    return response.json();
};

// API client configuration
export const apiClient = {
    // Order endpoints
    orders: {
        create: async (orderData) => {
            const response = await fetch(`${CONFIG.API_BASE_URL}/orders`, {
                method: 'POST',
                headers: {
                    ...CONFIG.DEFAULT_HEADERS,
                    ...getAuthHeader()
                },
                body: JSON.stringify(orderData)
            });
            return handleResponse(response);
        },

        getAll: async () => {
            const response = await fetch(`${CONFIG.API_BASE_URL}/orders`, {
                headers: {
                    ...CONFIG.DEFAULT_HEADERS,
                    ...getAuthHeader()
                }
            });
            return handleResponse(response);
        },

        getById: async (orderId) => {
            const response = await fetch(`${CONFIG.API_BASE_URL}/orders/${orderId}`, {
                headers: {
                    ...CONFIG.DEFAULT_HEADERS,
                    ...getAuthHeader()
                }
            });
            return handleResponse(response);
        },

        getByUserId: async (userId) => {
            const response = await fetch(`${CONFIG.API_BASE_URL}/orders/user/${userId}`, {
                headers: {
                    ...CONFIG.DEFAULT_HEADERS,
                    ...getAuthHeader()
                }
            });
            return handleResponse(response);
        },

        updateStatus: async (orderId, status) => {
            const response = await fetch(`${CONFIG.API_BASE_URL}/orders/${orderId}/status`, {
                method: 'PUT',
                headers: {
                    ...CONFIG.DEFAULT_HEADERS,
                    ...getAuthHeader()
                },
                body: JSON.stringify({ status })
            });
            return handleResponse(response);
        },

        updatePaymentStatus: async (orderId, paymentStatus) => {
            const response = await fetch(`${CONFIG.API_BASE_URL}/orders/${orderId}/payment?paymentStatus=${paymentStatus}`, {
                method: 'PUT',
                headers: {
                    ...CONFIG.DEFAULT_HEADERS,
                    ...getAuthHeader()
                }
            });
            return handleResponse(response);
        }
    }
};

// Order status enum (matching backend)
export const OrderStatus = {
    PENDING: 'PENDING',
    CONFIRMED: 'CONFIRMED',
    PREPARING: 'PREPARING',
    READY: 'READY',
    IN_DELIVERY: 'IN_DELIVERY',
    DELIVERED: 'DELIVERED',
    CANCELLED: 'CANCELLED'
}; 