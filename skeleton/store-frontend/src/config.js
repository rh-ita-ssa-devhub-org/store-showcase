// Runtime configuration - reads from window.APP_CONFIG (loaded from public/config.js)
// Falls back to environment variable for local development, then to default
export const Config = {
  apiUrl: window.APP_CONFIG?.apiUrl || process.env.REACT_APP_API_URL || 'http://localhost:8080/order'
};
