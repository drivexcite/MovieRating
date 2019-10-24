FROM node:latest as build-stage
WORKDIR /app
COPY frontend/package*.json ./
RUN npm install
COPY ./ .
RUN npm run build

FROM nginx as production-stage
RUN mkdir /app
ENV backendUrl=http://movierating-backend/
COPY --from=build-stage /app/target/dist /app
COPY nginx.conf /etc/nginx/nginx.conf