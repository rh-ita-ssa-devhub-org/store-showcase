# Single stage: Build and serve with Node.js
FROM image-registry.openshift-image-registry.svc:5000/openshift/nodejs:20-ubi8

WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install
RUN npm ci

# Copy source code
COPY . .

# Build the application
RUN npm run build

# Install serve to serve static files
RUN npm install -g serve

# Expose port 8080 (OpenShift default)
EXPOSE 8080

# Serve the built application
CMD ["serve", "-s", "build", "-l", "8080"]
