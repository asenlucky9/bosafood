import apiService from './api.service.js';
import { CONFIG } from '../config/config.js';

class AuthService {
    isAuthenticated() {
        return !!localStorage.getItem(CONFIG.AUTH_TOKEN_KEY);
    }

    async login(email, password) {
        const response = await apiService.login({ email, password });
        if (response.data?.token) {
            this.setSession(response.data);
            return true;
        }
        return false;
    }

    async register(userData) {
        return await apiService.register(userData);
    }

    logout() {
        apiService.logout();
        this.clearSession();
    }

    setSession(authResult) {
        localStorage.setItem(CONFIG.AUTH_TOKEN_KEY, authResult.token);
        if (authResult.refreshToken) {
            localStorage.setItem(CONFIG.REFRESH_TOKEN_KEY, authResult.refreshToken);
        }
    }

    clearSession() {
        localStorage.removeItem(CONFIG.AUTH_TOKEN_KEY);
        localStorage.removeItem(CONFIG.REFRESH_TOKEN_KEY);
    }

    getToken() {
        return localStorage.getItem(CONFIG.AUTH_TOKEN_KEY);
    }

    getRefreshToken() {
        return localStorage.getItem(CONFIG.REFRESH_TOKEN_KEY);
    }

    isTokenExpired() {
        const token = this.getToken();
        if (!token) return true;

        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            return Date.now() >= payload.exp * 1000;
        } catch {
            return true;
        }
    }
}

const authService = new AuthService();
export default authService; 