import { CONFIG } from '../config/config.js';

class ApiService {
    constructor() {
        this.baseUrl = CONFIG.API_BASE_URL;
    }

    getAuthHeaders() {
        const token = localStorage.getItem(CONFIG.AUTH_TOKEN_KEY);
        return {
            ...CONFIG.DEFAULT_HEADERS,
            'Authorization': `Bearer ${token}`
        };
    }

    // Authentication APIs
    async login(credentials) {
        try {
            const response = await fetch(`${this.baseUrl}/users/login`, {
                method: 'POST',
                headers: CONFIG.DEFAULT_HEADERS,
                body: JSON.stringify(credentials)
            });
            const data = await response.json();
            return data;
        } catch (error) {
            return { error: { message: error.message, type: 'AuthError' } };
        }
    }

    async register(userData) {
        try {
            const response = await fetch(`${this.baseUrl}/users/register`, {
                method: 'POST',
                headers: CONFIG.DEFAULT_HEADERS,
                body: JSON.stringify(userData)
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'RegisterError' } };
        }
    }

    async logout() {
        localStorage.removeItem(CONFIG.AUTH_TOKEN_KEY);
        localStorage.removeItem(CONFIG.REFRESH_TOKEN_KEY);
    }

    // Menu Categories APIs
    async getCategories() {
        try {
            const response = await fetch(`${this.baseUrl}/categories`, {
                headers: CONFIG.DEFAULT_HEADERS
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'CategoryError' } };
        }
    }

    // Menu Items APIs
    async getMenuItems(params = {}) {
        try {
            const queryString = new URLSearchParams(params).toString();
            const response = await fetch(`${this.baseUrl}/menu-items?${queryString}`, {
                headers: CONFIG.DEFAULT_HEADERS
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'MenuItemError' } };
        }
    }

    async getMenuItem(id) {
        try {
            const response = await fetch(`${this.baseUrl}/menu-items/${id}`, {
                headers: CONFIG.DEFAULT_HEADERS
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'MenuItemError' } };
        }
    }

    // Cart APIs
    async getCart() {
        try {
            const response = await fetch(`${this.baseUrl}/cart`, {
                headers: this.getAuthHeaders()
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'CartError' } };
        }
    }

    async addToCart(item) {
        try {
            const response = await fetch(`${this.baseUrl}/cart/items`, {
                method: 'POST',
                headers: this.getAuthHeaders(),
                body: JSON.stringify(item)
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'CartError' } };
        }
    }

    async updateCartItem(itemId, data) {
        try {
            const response = await fetch(`${this.baseUrl}/cart/items/${itemId}`, {
                method: 'PUT',
                headers: this.getAuthHeaders(),
                body: JSON.stringify(data)
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'CartError' } };
        }
    }

    async removeFromCart(itemId) {
        try {
            const response = await fetch(`${this.baseUrl}/cart/items/${itemId}`, {
                method: 'DELETE',
                headers: this.getAuthHeaders()
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'CartError' } };
        }
    }

    // Order APIs
    async createOrder(orderData) {
        try {
            const response = await fetch(`${this.baseUrl}/orders`, {
                method: 'POST',
                headers: this.getAuthHeaders(),
                body: JSON.stringify(orderData)
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'OrderError' } };
        }
    }

    async getOrders() {
        try {
            const response = await fetch(`${this.baseUrl}/orders`, {
                headers: this.getAuthHeaders()
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'OrderError' } };
        }
    }

    async getOrder(orderId) {
        try {
            const response = await fetch(`${this.baseUrl}/orders/${orderId}`, {
                headers: this.getAuthHeaders()
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'OrderError' } };
        }
    }

    async updateOrderStatus(orderId, status) {
        try {
            const response = await fetch(`${this.baseUrl}/orders/${orderId}/status`, {
                method: 'PUT',
                headers: this.getAuthHeaders(),
                body: JSON.stringify({ status })
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'OrderError' } };
        }
    }

    // Review APIs
    async createReview(reviewData) {
        try {
            const response = await fetch(`${this.baseUrl}/reviews`, {
                method: 'POST',
                headers: this.getAuthHeaders(),
                body: JSON.stringify(reviewData)
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'ReviewError' } };
        }
    }

    async getMenuItemReviews(menuItemId) {
        try {
            const response = await fetch(`${this.baseUrl}/reviews/menu-item/${menuItemId}`, {
                headers: CONFIG.DEFAULT_HEADERS
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'ReviewError' } };
        }
    }

    // Special Offers APIs
    async getCurrentOffers() {
        try {
            const response = await fetch(`${this.baseUrl}/special-offers/current`, {
                headers: CONFIG.DEFAULT_HEADERS
            });
            return await response.json();
        } catch (error) {
            return { error: { message: error.message, type: 'OfferError' } };
        }
    }
}

const apiService = new ApiService();
export default apiService; 