FROM node:latest as build-stage
WORKDIR /app
COPY frontend/package*.json ./
RUN npm install
COPY ./frontend .
RUN npm run build

FROM nginx as production-stage
RUN mkdir /app
ENV backendUrl=http://movierating-backend:8080
COPY --from=build-stage /app/target/dist /app
COPY frontend/nginx.conf /etc/nginx/nginx.conf