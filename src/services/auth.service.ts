import apiService from './api.service';
import { CONFIG } from '../config/config';
import { ApiResponse, LoginCredentials, LoginResponse, RegisterData, UserDTO } from '../types/api.types';
import { ApiError, handleApiError } from '../utils/error-handler';

class AuthService {
    isAuthenticated(): boolean {
        return !!this.getToken();
    }

    async login(email: string, password: string): Promise<boolean> {
        try {
            const credentials: LoginCredentials = { email, password };
            const response = await apiService.login(credentials);
            
            if (response.error) {
                throw new ApiError(response.error.message, response.error.type);
            }

            if (response.data?.token) {
                this.setSession(response.data);
                return true;
            }
            return false;
        } catch (error) {
            handleApiError(error as Error);
            return false;
        }
    }

    async register(userData: RegisterData): Promise<ApiResponse<UserDTO>> {
        try {
            const response = await apiService.register(userData);
            if (response.error) {
                throw new ApiError(response.error.message, response.error.type);
            }
            return response;
        } catch (error) {
            return { error: handleApiError(error as Error) };
        }
    }

    logout(): void {
        apiService.logout();
        this.clearSession();
    }

    setSession(authResult: LoginResponse): void {
        localStorage.setItem(CONFIG.AUTH_TOKEN_KEY, authResult.token);
        if (authResult.refreshToken) {
            localStorage.setItem(CONFIG.REFRESH_TOKEN_KEY, authResult.refreshToken);
        }
    }

    clearSession(): void {
        localStorage.removeItem(CONFIG.AUTH_TOKEN_KEY);
        localStorage.removeItem(CONFIG.REFRESH_TOKEN_KEY);
    }

    getToken(): string | null {
        return localStorage.getItem(CONFIG.AUTH_TOKEN_KEY);
    }

    getRefreshToken(): string | null {
        return localStorage.getItem(CONFIG.REFRESH_TOKEN_KEY);
    }

    async refreshAccessToken(): Promise<boolean> {
        const refreshToken = this.getRefreshToken();
        if (!refreshToken) {
            return false;
        }

        try {
            // Implement refresh token logic here when backend endpoint is ready
            // const response = await apiService.refreshToken(refreshToken);
            // if (response.data?.token) {
            //     this.setSession(response.data);
            //     return true;
            // }
            return false;
        } catch (error) {
            handleApiError(error as Error);
            return false;
        }
    }

    // Helper method to check if token is expired
    isTokenExpired(): boolean {
        const token = this.getToken();
        if (!token) return true;

        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            const expirationTime = payload.exp * 1000; // Convert to milliseconds
            return Date.now() >= expirationTime;
        } catch {
            return true;
        }
    }
}

export default new AuthService(); 