# Base image
FROM nginx:alpine

# Build dir copy to container
COPY build/ /usr/share/nginx/html

# NGINX config (optional)
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
