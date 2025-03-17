import { CONFIG } from '../config/config';
import {
    ApiResponse, LoginCredentials, LoginResponse, RegisterData,
    UserDTO, CategoryDTO, MenuItemDTO, CartDTO, CartItemDTO,
    OrderDTO, OrderStatus, ReviewDTO, SpecialOfferDTO
} from '../types/api.types';

class ApiService {
    private baseUrl: string;

    constructor() {
        this.baseUrl = CONFIG.API_BASE_URL;
    }

    private getAuthHeaders(): HeadersInit {
        const token = localStorage.getItem(CONFIG.AUTH_TOKEN_KEY);
        return {
            ...CONFIG.DEFAULT_HEADERS,
            'Authorization': `Bearer ${token}`
        };
    }

    private async handleRequest<T>(
        url: string,
        options: RequestInit = {}
    ): Promise<ApiResponse<T>> {
        try {
            const response = await fetch(url, options);
            const data = await response.json();
            
            if (!response.ok) {
                throw new Error(data.error?.message || 'An error occurred');
            }
            
            return data;
        } catch (error) {
            return {
                error: {
                    message: error.message,
                    type: options.method || 'GET' + 'Error'
                }
            };
        }
    }

    // Authentication APIs
    async login(credentials: LoginCredentials): Promise<ApiResponse<LoginResponse>> {
        const response = await this.handleRequest<LoginResponse>(
            `${this.baseUrl}/users/login`,
            {
                method: 'POST',
                headers: CONFIG.DEFAULT_HEADERS,
                body: JSON.stringify(credentials)
            }
        );

        if (response.data?.token) {
            localStorage.setItem(CONFIG.AUTH_TOKEN_KEY, response.data.token);
            if (response.data.refreshToken) {
                localStorage.setItem(CONFIG.REFRESH_TOKEN_KEY, response.data.refreshToken);
            }
        }

        return response;
    }

    async register(userData: RegisterData): Promise<ApiResponse<UserDTO>> {
        return this.handleRequest<UserDTO>(
            `${this.baseUrl}/users/register`,
            {
                method: 'POST',
                headers: CONFIG.DEFAULT_HEADERS,
                body: JSON.stringify(userData)
            }
        );
    }

    async logout(): Promise<void> {
        localStorage.removeItem(CONFIG.AUTH_TOKEN_KEY);
        localStorage.removeItem(CONFIG.REFRESH_TOKEN_KEY);
    }

    // Menu Categories APIs
    async getCategories(): Promise<ApiResponse<CategoryDTO[]>> {
        return this.handleRequest<CategoryDTO[]>(
            `${this.baseUrl}/categories`,
            { headers: CONFIG.DEFAULT_HEADERS }
        );
    }

    // Menu Items APIs
    async getMenuItems(params: Record<string, string> = {}): Promise<ApiResponse<MenuItemDTO[]>> {
        const queryString = new URLSearchParams(params).toString();
        return this.handleRequest<MenuItemDTO[]>(
            `${this.baseUrl}/menu-items?${queryString}`,
            { headers: CONFIG.DEFAULT_HEADERS }
        );
    }

    async getMenuItem(id: number): Promise<ApiResponse<MenuItemDTO>> {
        return this.handleRequest<MenuItemDTO>(
            `${this.baseUrl}/menu-items/${id}`,
            { headers: CONFIG.DEFAULT_HEADERS }
        );
    }

    // Cart APIs
    async getCart(): Promise<ApiResponse<CartDTO>> {
        return this.handleRequest<CartDTO>(
            `${this.baseUrl}/cart`,
            { headers: this.getAuthHeaders() }
        );
    }

    async addToCart(item: CartItemDTO): Promise<ApiResponse<CartDTO>> {
        return this.handleRequest<CartDTO>(
            `${this.baseUrl}/cart/items`,
            {
                method: 'POST',
                headers: this.getAuthHeaders(),
                body: JSON.stringify(item)
            }
        );
    }

    async updateCartItem(itemId: number, data: Partial<CartItemDTO>): Promise<ApiResponse<CartDTO>> {
        return this.handleRequest<CartDTO>(
            `${this.baseUrl}/cart/items/${itemId}`,
            {
                method: 'PUT',
                headers: this.getAuthHeaders(),
                body: JSON.stringify(data)
            }
        );
    }

    async removeFromCart(itemId: number): Promise<ApiResponse<void>> {
        return this.handleRequest<void>(
            `${this.baseUrl}/cart/items/${itemId}`,
            {
                method: 'DELETE',
                headers: this.getAuthHeaders()
            }
        );
    }

    // Order APIs
    async createOrder(orderData: Omit<OrderDTO, 'id'>): Promise<ApiResponse<OrderDTO>> {
        return this.handleRequest<OrderDTO>(
            `${this.baseUrl}/orders`,
            {
                method: 'POST',
                headers: this.getAuthHeaders(),
                body: JSON.stringify(orderData)
            }
        );
    }

    async getOrders(): Promise<ApiResponse<OrderDTO[]>> {
        return this.handleRequest<OrderDTO[]>(
            `${this.baseUrl}/orders`,
            { headers: this.getAuthHeaders() }
        );
    }

    async getOrder(orderId: number): Promise<ApiResponse<OrderDTO>> {
        return this.handleRequest<OrderDTO>(
            `${this.baseUrl}/orders/${orderId}`,
            { headers: this.getAuthHeaders() }
        );
    }

    async updateOrderStatus(orderId: number, status: OrderStatus): Promise<ApiResponse<OrderDTO>> {
        return this.handleRequest<OrderDTO>(
            `${this.baseUrl}/orders/${orderId}/status`,
            {
                method: 'PUT',
                headers: this.getAuthHeaders(),
                body: JSON.stringify({ status })
            }
        );
    }

    // Review APIs
    async createReview(reviewData: Omit<ReviewDTO, 'id' | 'createdAt'>): Promise<ApiResponse<ReviewDTO>> {
        return this.handleRequest<ReviewDTO>(
            `${this.baseUrl}/reviews`,
            {
                method: 'POST',
                headers: this.getAuthHeaders(),
                body: JSON.stringify(reviewData)
            }
        );
    }

    async getMenuItemReviews(menuItemId: number): Promise<ApiResponse<ReviewDTO[]>> {
        return this.handleRequest<ReviewDTO[]>(
            `${this.baseUrl}/reviews/menu-item/${menuItemId}`,
            { headers: CONFIG.DEFAULT_HEADERS }
        );
    }

    // Special Offers APIs
    async getCurrentOffers(): Promise<ApiResponse<SpecialOfferDTO[]>> {
        return this.handleRequest<SpecialOfferDTO[]>(
            `${this.baseUrl}/special-offers/current`,
            { headers: CONFIG.DEFAULT_HEADERS }
        );
    }

    // User Profile APIs
    async getUserProfile(): Promise<ApiResponse<UserDTO>> {
        return this.handleRequest<UserDTO>(
            `${this.baseUrl}/users/profile`,
            { headers: this.getAuthHeaders() }
        );
    }

    async updateUserProfile(profileData: Partial<UserDTO>): Promise<ApiResponse<UserDTO>> {
        return this.handleRequest<UserDTO>(
            `${this.baseUrl}/users/profile`,
            {
                method: 'PUT',
                headers: this.getAuthHeaders(),
                body: JSON.stringify(profileData)
            }
        );
    }
}

export default new ApiService(); 