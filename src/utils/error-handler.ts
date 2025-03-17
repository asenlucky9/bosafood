import { ApiError as ApiErrorType } from '../types/api.types';

export class ApiError extends Error {
    type: string;
    status?: number;

    constructor(message: string, type: string, status?: number) {
        super(message);
        this.type = type;
        this.status = status;
        Object.setPrototypeOf(this, ApiError.prototype);
    }
}

export function handleApiError(error: Error | ApiError): ApiErrorType {
    console.error('API Error:', error);
    
    if (error instanceof ApiError && error.type === 'AuthError') {
        // Handle authentication errors
        window.location.href = '/login';
    }

    return {
        message: error.message || 'An unexpected error occurred',
        type: (error instanceof ApiError) ? error.type : 'UnknownError'
    };
} 