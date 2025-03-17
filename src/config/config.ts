interface Config {
    API_BASE_URL: string;
    DEFAULT_HEADERS: {
        'Content-Type': string;
        [key: string]: string;
    };
    AUTH_TOKEN_KEY: string;
    REFRESH_TOKEN_KEY: string;
}

export const CONFIG: Config = {
    API_BASE_URL: 'http://localhost:8081/api',
    DEFAULT_HEADERS: {
        'Content-Type': 'application/json'
    },
    AUTH_TOKEN_KEY: 'token',
    REFRESH_TOKEN_KEY: 'refreshToken'
}; 