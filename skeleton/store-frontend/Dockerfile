# Stage 1: Build the React application
FROM image-registry.openshift-image-registry.svc:5000/openshift/nodejs:20-ubi8 AS build

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy source code
COPY . .

# Build the application
RUN npm run build

# Stage 2: Serve with nginx (UBI-based for OpenShift)
FROM registry.access.redhat.com/ubi8/nginx-120

# Copy built React app to nginx html directory
COPY --from=build /app/build /opt/app-root/src

# Create nginx config for React Router (SPA) and health endpoint
USER 0
RUN echo 'server { \
    listen 8080; \
    server_name localhost; \
    root /opt/app-root/src; \
    index index.html; \
    location / { \
        try_files $uri $uri/ /index.html; \
    } \
    location /health { \
        access_log off; \
        return 200 "healthy"; \
        add_header Content-Type text/plain; \
    } \
}' > /etc/nginx/nginx.conf.d/default.conf
USER 1001

# Expose port 8080 (OpenShift default)
EXPOSE 8080

# Run nginx (default CMD from base image)
CMD ["nginx", "-g", "daemon off;"]
