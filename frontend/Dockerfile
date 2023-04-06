# Use the official Node.js image as the base image
FROM node:16 AS build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package.json package-lock.json ./

# Install dependencies
RUN npm ci --verbose

# Copy the rest of the application code to the working directory
COPY . .

# Build the frontend application
RUN npm run build

# Use the official Nginx image
FROM nginx:stable-alpine

# Copy the built frontend application to the Nginx html directory
COPY --from=build /app/build /usr/share/nginx/html

# Expose the Nginx server port
EXPOSE 80

# Start the Nginx server
CMD ["nginx", "-g", "daemon off;"]
